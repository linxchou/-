package BookManageSystem.dao;

import BookManageSystem.beans.AdminBean;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {

    private static final Logger logger=Logger.getLogger(AdminDao.class);

    public static boolean isUserExisted(String user_name){
        String sql="SELECT * FROM admin WHERE user_name=?";

        Connection conn=null;
        PreparedStatement cmd=null;
        ResultSet resultSet=null;

        try{
            conn=JDBCUtils.getConnection();
            cmd=conn.prepareStatement(sql);
            cmd.setString(1,user_name);
            resultSet=cmd.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }

        }catch (SQLException | ClassNotFoundException e){
            logger.error(e.getMessage());
        }finally {
            JDBCUtils.release(resultSet,cmd,conn);
        }
        return false;
    }


    public static AdminBean login(String user_name,String password){
        AdminBean adminBean=null;
        String sql="SELECT * FROM admin WHERE user_name = ? AND password = ?";
        Connection conn=null;
        PreparedStatement cmd=null;
        ResultSet resultSet=null;

        try{
            conn=JDBCUtils.getConnection();
            cmd=conn.prepareStatement(sql);
            cmd.setString(1,user_name);
            cmd.setString(2,password);
            resultSet=cmd.executeQuery();
            if(resultSet.next()){
                adminBean=new AdminBean();
                adminBean.setAdmin_id(resultSet.getInt("admin_id"));
                adminBean.setName(resultSet.getString("name"));
                adminBean.setSex(resultSet.getByte("sex"));
                adminBean.setAddress(resultSet.getString("address"));
                adminBean.setPhone(resultSet.getString("phone"));
                adminBean.setUser_name(resultSet.getString("user_name"));
                adminBean.setPassword(resultSet.getString("password"));

            }
            return adminBean;
        }catch (Exception e){
            logger.error("数据库连接失败，可能：1. 用户名/密码/连接字符串不正确(请修改工程目录下的/conf/db.properties)。2. 没有为当前工程加载MySQL驱动器。");
        }finally {
            JDBCUtils.release(resultSet, cmd, conn);
        }
        return null;
    }
}
