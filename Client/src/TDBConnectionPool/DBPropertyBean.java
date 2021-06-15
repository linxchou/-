package TDBConnectionPool;

public class DBPropertyBean {

    private String nodeName;
    //数据库连接驱动
    private String driverName;
    //数据库连接url
    private String url;
    //数据库连接username
    private String username;
    //数据库连接密码
    private String password;
    //连接池最大连接数
    private int maxConnections;
    //连接池最小连接数
    private int minConnections;

    //连接池初始连接数
    private int initConnections;
    //重连间隔时间，单位毫秒
    private int conninterval;
    //获取连接超时时间，单位毫秒，0用不超时
    private int timeout;

    public DBPropertyBean(){
        super();
    }

    public String getNodeName(){
        return nodeName;
    }

    public void setNodeName(String nodeName){
        this.nodeName=nodeName;
    }

    public String getDriverName(){
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *获取重连间隔时间，单位毫秒

     */
    public int getConninterval() {
        return conninterval;
    }

    public void setConninterval(int conninterval) {
        this.conninterval = conninterval;
    }

    public int getInitConnections() {
        return initConnections;
    }

    public void setInitConnections(int initConnections) {
        this.initConnections = initConnections;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getMinConnections() {
        return minConnections;
    }

    public void setMinConnections(int minConnections) {
        this.minConnections = minConnections;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}

