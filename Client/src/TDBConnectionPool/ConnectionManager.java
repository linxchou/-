package TDBConnectionPool;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    private final Logger log= Logger.getLogger(String.valueOf(TDBConnectionPool.ConnectionPool.class));

    private static ConnectionManager dbm=null;

    /**
     * 加载的驱动器名称集合
     */

    private Set<String> drivers=new HashSet<String>();

    /**
     * 数据库连接池字典
     * 为每个节点创建一个连接池（可配置多个节点）
     */

    private ConcurrentHashMap<String, TDBConnectionPool.IConnectionPool>pools =new ConcurrentHashMap<String, TDBConnectionPool.IConnectionPool>();

    private ConnectionManager(){
        createPools();
    }

    /**
     * 装载JDBC驱动程序，并创建连接池
     */

    private void createPools(){
        String str_nodenames= TDBConnectionPool.PropertiesManager.getProperty("nodename");

        for(String str_nodename: str_nodenames.split(",")){
            TDBConnectionPool.DBPropertyBean dbPropertyBean=new TDBConnectionPool.DBPropertyBean();
            dbPropertyBean.setNodeName(str_nodename);

            //验证url配置正确性
            String url= TDBConnectionPool.PropertiesManager.getProperty(str_nodename+".url");
            if(url==null){
                log.error(str_nodename+"节点的连接字符串为空，请检查配置文件");
                continue;
            }
            dbPropertyBean.setUrl(url);

            //验证driver配置正确性

            String driver= TDBConnectionPool.PropertiesManager.getProperty(str_nodename+".driver");
            if(driver==null){
                log.error(str_nodename+"节点的driver驱动为空，请检查配置文件");
                continue;
            }
            dbPropertyBean.setDriverName(driver);

            //验证user配置正确性
            String user= TDBConnectionPool.PropertiesManager.getProperty(str_nodename+".user");
            if(user == null){
                log.error(str_nodename+"节点的用户名设置为空，请检查配置文件");
                continue;
            }
            dbPropertyBean.setUsername(user);

            //验证password配置正确性
            String password= TDBConnectionPool.PropertiesManager.getProperty(str_nodename+".password");
            if(password==null){
                log.error(str_nodename+"节点的密码设置为空，请检查配置文件");
                continue;
            }

            dbPropertyBean.setPassword(password);

            String str_minconnections= TDBConnectionPool.PropertiesManager.getProperty(str_nodename+".minconnections");
            int minConn;
            try{
                minConn=Integer.parseInt(str_minconnections);
            }catch (NumberFormatException e){
                log.error(str_nodename+"节点最小连接数设置错误，默认设为5");
                minConn=5;
            }

            dbPropertyBean.setMinConnections(minConn);

            String str_initconnections= TDBConnectionPool.PropertiesManager.getProperty(str_nodename+".initconnections");
            int initConn;
            try{
                initConn=Integer.parseInt(str_initconnections);
            }catch (NumberFormatException e){
                log.error(str_nodename+"节点初始连接数设置错误，默认为5");
                initConn=5;
            }

            dbPropertyBean.setInitConnections(initConn);


            //验证最大连接数配置正确性
            String str_maxconnections= TDBConnectionPool.PropertiesManager.getProperty(str_nodename + ".maxconnections");
            int maxConn;
            try {
                maxConn = Integer.parseInt(str_maxconnections);
            } catch (NumberFormatException e) {
                log.error(str_nodename + "节点最大连接数设置错误，默认设为20");
                maxConn=20;
            }
            dbPropertyBean.setMaxConnections(maxConn);

            //验证conninterval配置正确性
            String str_conninterval= TDBConnectionPool.PropertiesManager.getProperty(str_nodename + ".conninterval");
            int conninterval;
            try {
                conninterval = Integer.parseInt(str_conninterval);
            } catch (NumberFormatException e) {
                log.error(str_nodename + "节点重新连接间隔时间设置错误，默认设为500ms");
                conninterval = 500;
            }
            dbPropertyBean.setConninterval(conninterval);

            //验证timeout配置正确性
            String str_timeout= TDBConnectionPool.PropertiesManager.getProperty(str_nodename + ".timeout");
            int timeout;
            try {
                timeout = Integer.parseInt(str_timeout);
            } catch (NumberFormatException e) {
                log.error(str_nodename + "节点连接超时时间设置错误，默认设为2000ms");
                timeout = 2000;
            }
            dbPropertyBean.setTimeout(timeout);


            //创建驱动
            if(!drivers.contains(dbPropertyBean.getDriverName())){
                try{
                    Class.forName(dbPropertyBean.getDriverName());
                    log.info("加载JDBC驱动"+dbPropertyBean.getDriverName()+"成功");
                    drivers.add(dbPropertyBean.getDriverName());
                }catch (ClassNotFoundException e){
                    log.error("未找到JDBC驱动"+dbPropertyBean.getDriverName()+",请引入相关包");
                    e.printStackTrace();
                }
            }

            TDBConnectionPool.IConnectionPool cp= TDBConnectionPool.ConnectionPool.CreateConnectionPool(dbPropertyBean);
            if(cp!=null){
                pools.put(str_nodename,cp);
                cp.checkPool();
                log.info("创建"+str_nodename+"数据库连接池成功");
            }else {
                log.info("创建"+str_nodename+"数据库连接池失败");
            }


        }
    }


    /**
     * 获得单例
     * @return DBConnectionManager 单例
     */

    public synchronized static ConnectionManager getInstance(){
        if(dbm==null){
            dbm=new ConnectionManager();
        }

        return dbm;
    }

    /**
     * 从指定连接池中获取可用丽娜姐
     *
     * @param poolName 要获取连接的连接池名称
     * @return连接池中的一个可用连接或null
     */

    public Connection getConnection(String poolName){
        TDBConnectionPool.IConnectionPool pool=pools.get(poolName);
        return pool.getConnection();
    }

    /**
     * 回收指定连接池的连接
     *
     * @param poolName 连接池名称
     * @return conn 要回收的连接
     */

    public void closeConnection(String poolName,Connection conn)throws SQLException{
        TDBConnectionPool.IConnectionPool pool =pools.get(poolName);

        if(pool!=null){
            try{
                pool.releaseConn(conn);
            }catch (SQLException e){
                log.error("回收"+poolName+"池中的连接失败");
                throw new SQLException(e);
            }
        }else{
            log.error("找不到"+poolName+"连接池，无法回收");
        }
    }

    /**
     * 关闭所有连接，撤销驱动器的注册
     *
     */

    public void destroy(){
        for(Map.Entry<String, TDBConnectionPool.IConnectionPool>poolEntry:pools.entrySet()){
            TDBConnectionPool.IConnectionPool pool=poolEntry.getValue();
            pool.destroy();
        }
        log.info("已关闭所有连接");
    }
}
