package BookManageSystem.dao;

import BookManageSystem.beans.BookTypeBean;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookTypeDao {
        private  static final Logger logger= Logger.getLogger(BookTypeDao.class);



    /**
     * 操作结果：根据参数sql获取数据库记录数据
     *
     * @param sql SQL语句
     * @return List 返回包含记录Records对象的集合
     */
    public List getRecordsDataBySql(String sql) {
        Connection conn = null;
        Statement stmt = null;
        //ConnectionManager cm=ConnectionManager.getInstance();
        ResultSet rs = null;
        List list = new ArrayList();
        try {
            //获得数据的连接
            conn = JDBCUtils.getConnection();
            //conn=cm.getConnection("default");
            //获得Statement对象
            stmt = conn.createStatement();
            //发送SQL语句
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                BookTypeBean bookTypeBean=new BookTypeBean();
                bookTypeBean.setBookTypeId(rs.getInt(1));
                bookTypeBean.setBookTypeName(rs.getString(2));
                bookTypeBean.setBookTypeDescription(rs.getString(3));
                list.add(bookTypeBean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            JDBCUtils.release(rs, stmt, conn);

        }
        return list;
    }
}
