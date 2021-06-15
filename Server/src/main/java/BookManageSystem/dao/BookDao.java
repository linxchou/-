package BookManageSystem.dao;

import BookManageSystem.beans.BookBean;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class BookDao {
    private static final Logger logger=Logger.getLogger(BookDao.class);

    public synchronized static boolean bookNumChange(BookBean bookBean){
        Connection conn=null;
        PreparedStatement cmd=null;

        String sql="UPDATE tb_book SET bNum=? WHERE bid=?";
        try{
            conn=JDBCUtils.getConnection();
            cmd=conn.prepareStatement(sql);
            cmd.setInt(1,bookBean.getBookNum());
            cmd.setInt(2,bookBean.getBookId());

            int result=cmd.executeUpdate();
            if(result==1){
                return true;
            }
            return false;
        }catch (SQLException | ClassNotFoundException e){
            logger.error(e.getMessage());
        }finally {
            JDBCUtils.release(cmd,conn);
        }
        return false;

    }


}
