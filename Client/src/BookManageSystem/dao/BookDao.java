package BookManageSystem.dao;

import BookManageSystem.beans.BookBean;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

        private static final Logger logger=Logger.getLogger(BookDao.class);






    /**
     * 操作结果：根据参数sql获取数据库记录数据
     *
     * @param sql SQL语句
     * @return List 返回包含记录Records对象的集合
     */
    public List getRecordsDataBySql(String sql) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        //ConnectionManager cm=ConnectionManager.getInstance();
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
                BookBean bookBean = new BookBean();
                // 索引是从1开始的
                bookBean.setBookId(rs.getInt(1));
                bookBean.setBookName(rs.getString(2));
                bookBean.setBookAuthor(rs.getString(3));
                bookBean.setBookAuthorSex(rs.getString(4));
                bookBean.setBookPrice(rs.getFloat(5));
                bookBean.setBookDescription(rs.getString(6));
                bookBean.setBookTypeId(rs.getString(7));
                bookBean.setBookNum(rs.getInt("bNum"));
                list.add(bookBean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            JDBCUtils.release(rs, stmt, conn);
            /*
            try{
                cm.closeConnection("default",conn);
            }catch (SQLException e){
                e.printStackTrace();
            }

             */
        }
        return list;
    }

    public static BookBean getBookInfo(int book_id){
        Connection conn=null;
        PreparedStatement cmd=null;
        ResultSet rt=null;
        String sql="SELECT * FROM tb_book WHERE bid="+book_id;
        BookBean bookBean=new BookBean();
        try{
            conn=JDBCUtils.getConnection();
            cmd=conn.prepareStatement(sql);


            rt=cmd.executeQuery(sql);
            while(rt.next()){
                bookBean.setBookId(rt.getInt(1));
                bookBean.setBookName(rt.getString(2));
                bookBean.setBookAuthor(rt.getString(3));
                bookBean.setBookAuthorSex(rt.getString(4));
                bookBean.setBookPrice(rt.getFloat(5));
                bookBean.setBookDescription(rt.getString(6));
                bookBean.setBookTypeId(rt.getString(7));
                bookBean.setBookNum(rt.getInt("bNum"));
            }
        }catch (SQLException | ClassNotFoundException e){
            logger.error(e.getMessage());
        }
        return bookBean;
    }
}
