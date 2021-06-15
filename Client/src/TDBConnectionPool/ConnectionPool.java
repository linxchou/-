package TDBConnectionPool;



import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Hashtable;

import java.util.TimerTask;

import org.apache.log4j.Logger;



public class ConnectionPool implements TDBConnectionPool.IConnectionPool {
    public static final Logger log= Logger.getLogger(String.valueOf(ConnectionPool.class));
    private DBPropertyBean propertyBean=null;
    //连接池可用状态
    private Boolean isActive =true;
    //空闲连接池。用于List读写频繁，使用LinkedList存储比较合适
    private Hashtable<Integer,Connection> freeConnections=new Hashtable<Integer,Connection>();

    //活动连接池。活动连接数<=允许最大连接数(maxConnections)
    private Hashtable<Integer,Connection> activeConnections=new Hashtable<Integer,Connection>();

    //当前线程获得的连接
    private ThreadLocal<Connection> currentConnection=new ThreadLocal<Connection>();

    private ConnectionPool(){
        super();
    }

    public static ConnectionPool CreateConnectionPool(DBPropertyBean propertyBean){
        ConnectionPool connPool=new ConnectionPool();
        connPool.propertyBean=propertyBean;

        /*
        for(int i=0;i< propertyBean.getInitConnections();i++){
            try{
                Connection conn=connPool.NewConnection();
                connPool.freeConnections.add(conn);
            }catch (SQLException |ClassNotFoundException e){
                log.error(connPool.propertyBean.getNodeName()+"节点初始化失败");
                return null;
            }
        }

         */
        for(int i=0;i< connPool.propertyBean.getInitConnections();i++){
            try{
                Connection conn= connPool.NewConnection();
                connPool.freeConnections.put(conn.hashCode(),conn);
            }catch(SQLException | ClassNotFoundException e){
                log.error(connPool.propertyBean.getNodeName()+"节点初始化失败");
                return null;
            }
        }
        connPool.isActive=true;
        return connPool;
    }

    /**
     * 检测连接是否有效
     * @param conn
     * @return Boolean
     */

    private Boolean isValidConnection(Connection conn)throws SQLException{
        try{
            if(conn==null||conn.isClosed()){
                return false;
            }
        }catch (SQLException e){
            throw new SQLException(e);
        }
        return true;
    }

    /**
     * 创建一个新的连接
     * @return conn
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private Connection NewConnection()throws ClassNotFoundException,SQLException{
        Connection conn=null;
        try{
            if(this.propertyBean !=null){
                conn= DriverManager.getConnection(this.propertyBean.getUrl(),
                        this.propertyBean.getUsername(),
                        this.propertyBean.getPassword());
            }
        }catch (SQLException e){
            throw new SQLException(e);
        }


        if(conn!=null){
            ConProxyHandler handler=new ConProxyHandler(this,conn);
            Connection conn_return= (Connection) Proxy.newProxyInstance(ConnectionPool.class.getClassLoader(),new Class[]{Connection.class},handler);

            System.out.println("生成代理类："+conn_return);
            return conn_return;
        }
        return conn;
    }

    @Override
    public synchronized Connection getConnection(){
        Connection conn=null;

        if(this.getActiveNum()<this.propertyBean.getMaxConnections()){
            //当前使用的连接没有到达最大连接数
            //在连接池没有达到最大连接数之前，如果有可用的空闲连接就直接使用空闲连接，如果没有，就创建新的连接
            if(this.getFreeNum()>0){
                //如果空闲池中有连接，就从空闲池中直接取
                int key=(int)this.freeConnections.keySet().toArray()[0];
                log.info("如果空闲池中有连接，就从空闲池中直接获取");
                //conn=this.freeConnections.pollFirst();
                conn=this.freeConnections.get(key);
                this.freeConnections.remove(key);
                //连接闲置久了也会超时，因此空闲池中的有效连接会越来越少，需要另一个进程进行扫描检测，不断保持一定数量的可用连接

                //由于数据库连接闲置久了会超时关闭，因此需要连接池采用机制保证每次请求的连接都是有效可用的
                try{
                    if(this.isValidConnection(conn)){
                        this.activeConnections.put(conn.hashCode(),conn);
                        currentConnection.set(conn);
                    }else{
                        conn=getConnection();
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
                System.out.println(this.propertyBean.getNodeName()+"空闲连接数"+getFreeNum());
                System.out.println(this.propertyBean.getNodeName()+"活动连接数"+getActiveNum());
            }else{
                log.info("如果空闲池无可用连接，就创建新的连接");
                try{
                    conn=this.NewConnection();
                    this.activeConnections.put(conn.hashCode(),conn);
                }catch (ClassNotFoundException | SQLException e){
                    e.printStackTrace();
                }
            }
        }else{
            //当前已经达到最大连接数
            //当连接池中的恶活动连接数达到最大连接数，新的请求进入等待状态，直到有连接被释放
            log.info("当前已到达最大连接数");
            long startTime=System.currentTimeMillis();
            //进入
            try{
                this.wait(this.propertyBean.getConninterval());
            }catch (InterruptedException e){
                log.error("线程等待被打断");
            }

            //若线程超时前被唤醒并成功获取连接，就不会走到return null
            //若线程超时前没有获取连接，则返回null
            //如果timeout设置为0，就无限重连

            if(this.propertyBean.getTimeout()!=0){
                if(System.currentTimeMillis()-startTime>this.propertyBean.getTimeout())
                    return null;
            }

            conn=this.getConnection();


        }
        return conn;
    }

    @Override
    public Connection getCurrentConnection(){
        Connection conn=currentConnection.get();
        try{
            if(!isValidConnection(conn)){
                conn=this.getConnection();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public synchronized void releaseConn(Connection conn)throws SQLException{
        log.info(Thread.currentThread().getName()+"关闭连接： activateConnections。remove："+conn);
        this.activeConnections.remove(conn.hashCode());
        this.currentConnection.remove();
        //活动连接池删除的连接，相应地加入到空闲连接池

        try{
            if(isValidConnection(conn)){
                freeConnections.put(conn.hashCode(),conn);
            }else{
                Connection newconn=this.NewConnection();
                freeConnections.put(newconn.hashCode(),newconn);
            }
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        System.out.println(this.propertyBean.getNodeName()+"空闲连接数"+getFreeNum());
        System.out.println(this.propertyBean.getNodeName()+"活动连接数"+getActiveNum());
        //唤醒等待中的线程
        this.notifyAll();
    }

    @Override
    public synchronized void destroy(){
        for(int key:this.freeConnections.keySet()){
            Connection conn=this.freeConnections.get(key);
            try{
                if(this.isValidConnection(conn)){
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        for(int key:this.activeConnections.keySet()){
            Connection conn=this.activeConnections.get(key);
            try{
                if(this.isValidConnection(conn)){
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        this.isActive=false;
        this.freeConnections.clear();
        this.activeConnections.clear();
    }

    @Override
    public boolean isActive(){
        return this.isActive;
    }

    @Override
    public void checkPool(){
        /*
        final String nodename=this.propertyBean.getNodeName();
        ScheduledExecutorService ses= Executors.newScheduledThreadPool(2);

        ses.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                System.out.println(nodename+"空闲连接数："+getFreeNum());
                System.out.println(nodename+"活动连接数："+getActiveNum());
            }
        },1,1, TimeUnit.SECONDS);

        ses.scheduleAtFixedRate(new checkFreepools(this),1,5,TimeUnit.SECONDS);

         */
    }

   @Override
    public synchronized int getActiveNum(){
        return this.activeConnections.size();
   }
   @Override
    public synchronized int getFreeNum(){
        return this.freeConnections.size();
   }

   //连接池内保证最小连接数量
   class checkFreepools extends TimerTask{
        private ConnectionPool conpool=null;

        public checkFreepools(ConnectionPool cp){
            this.conpool=cp;
        }
        @Override
       public void run(){
            if(this.conpool!=null&& this.conpool.isActive()){
                int poolstotalnum= conpool.getFreeNum()+ conpool.getActiveNum();
                int subnum=conpool.propertyBean.getMinConnections()-poolstotalnum;

                if(subnum>0){
                    System.out.println(conpool.propertyBean.getNodeName()+"扫描并维持空闲池中的最小连接数，需补充"+subnum+"个连接");
                    for(int i=0;i<subnum;i++){
                        try{
                            Connection newconn=conpool.NewConnection();
                            conpool.freeConnections.put(newconn.hashCode(),newconn);
                        }catch (ClassNotFoundException | SQLException e){
                            e.printStackTrace();
                        }
                    }
                }
            }


        }


   }


   class ConProxyHandler implements InvocationHandler{
        private Connection conn;
        private ConnectionPool cp;

        public ConProxyHandler(ConnectionPool cp,Connection conn){
            this.cp=cp;
            this.conn=conn;
        }

        @Override
       public Object invoke(Object proxy, Method method, Object[]args)throws Throwable{
            if(method.getName().equals("close")){
                cp.releaseConn(this.conn);
                return this.conn;
            }else{
                return method.invoke(this.conn,args);
            }
        }
   }



}
