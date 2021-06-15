package BookManageSystem.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Statement;


public class BookTypeDao {
    private static final Logger logger= Logger.getLogger(BookTypeDao.class);

    /**
     * 操作结果：根据SQL语句执行数据库的增删改操作
     *
     * @param sql SQL语句
     * @return boolean 如果操作数据库成功返回true，否则返回false
     */
    public synchronized boolean dataChange(String sql) {
        Connection conn = null;
        Statement stmt = null;
        //ConnectionManager cm=ConnectionManager.getInstance();
        try {
            //获得数据的连接
            conn = JDBCUtils.getConnection();
            //conn=cm.getConnection("default");
            //获得Statement对象
            stmt = conn.createStatement();
            //发送SQL语句
            int num = stmt.executeUpdate(sql);
            return num > 0;
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {

            JDBCUtils.release(stmt, conn);



        }
        return false;
    }


}
