package BookManageSystem.dao;

import BookManageSystem.beans.LendList;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class l_rDao {
    private static final Logger logger=Logger.getLogger(l_rDao.class);

    public static boolean borrowBook(LendList lendList){
        String sql="insert into l_r(bid,reader_id,name,bBookName,lendDate,limitedDate)"+
                "values(?,?,?,?,?,?)";

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

            int result=cmd.executeUpdate();
            if(result==1){
                return true;
            }
            return false;
        }catch (SQLException | ClassNotFoundException e){
            logger.info("重复借书！");
        }finally {
            JDBCUtils.release(cmd,conn);
        }
        return false;
    }

    public static boolean deleteRecord(LendList lendList)  {
        Connection conn=null;
        PreparedStatement cmd=null;
        String sql="DELETE FROM l_r WHERE bid=? AND reader_id=?";
        try{
            conn=JDBCUtils.getConnection();
            cmd=conn.prepareStatement(sql);
            cmd.setInt(1,lendList.getBook_id());
            cmd.setInt(2,lendList.getReader_id());
            int result=cmd.executeUpdate();
            return result == 1;
        }catch (SQLException | ClassNotFoundException e){
            logger.error(e.getMessage());
        }
        return false;
    }
}
