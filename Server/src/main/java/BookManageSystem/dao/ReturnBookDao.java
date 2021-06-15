package BookManageSystem.dao;

import BookManageSystem.beans.LendList;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReturnBookDao {
    private static final Logger logger=Logger.getLogger(ReturnBookDao.class);
    public static boolean returnBook(LendList lendList){
        String sql="insert into borrowrecords(bid,reader_id,name,bBookName,lendDate,limitedDate,returnDate)"+
                "values(?,?,?,?,?,?,?)";

        Connection conn=null;
        PreparedStatement cmd=null;
        try{
            conn=JDBCUtils.getConnection();
            cmd=conn.prepareStatement(sql);
            cmd.setInt(1,lendList.getBook_id());
            cmd.setInt(2,lendList.getReader_id());
            cmd.setString(3,lendList.getName());
            cmd.setString(4,lendList.getBookname());
            cmd.setDate(5,lendList.getLend_date());
            cmd.setDate(6,lendList.getLimited_date());
            cmd.setDate(7,lendList.getBack_date());
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
