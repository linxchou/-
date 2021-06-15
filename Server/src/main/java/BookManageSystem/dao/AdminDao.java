package BookManageSystem.dao;

import BookManageSystem.beans.AdminBean;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminDao {
    private static final Logger logger=Logger.getLogger(AdminDao.class);

    public synchronized static boolean insertAdmin(AdminBean adminBean){
        String sql="insert into admin(admin_id,name,sex,address,phone,user_name,password)"+
                "values(?,?,?,?,?,?,?)";

        Connection conn=null;
        PreparedStatement cmd=null;

        try{
            conn=JDBCUtils.getConnection();
            cmd=conn.prepareStatement(sql);
            cmd.setInt(1,adminBean.getAdmin_id());
            cmd.setString(2,adminBean.getName());
            cmd.setByte(3,adminBean.getSex());
            cmd.setString(4,adminBean.getAddress());
            cmd.setString(5,adminBean.getPhone());
            cmd.setString(6,adminBean.getUser_name());
            cmd.setString(7,adminBean.getPassword());

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

    public static boolean admin_passwordChange(AdminBean adminBean){
        Connection conn=null;
        PreparedStatement cmd=null;

        String sql="UPDATE admin SET password=? WHERE admin_id=?";
        try{
            conn=JDBCUtils.getConnection();
            cmd=conn.prepareStatement(sql);
            cmd.setString(1,adminBean.getPassword());
            cmd.setInt(2,adminBean.getAdmin_id());

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
