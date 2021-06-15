package BookManageSystem.dao;


import BookManageSystem.beans.ReaderBean;
import org.apache.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReaderDao {
    private static final Logger logger=Logger.getLogger(ReaderDao.class);
    public static ReaderBean login(String user_name,String password){
        ReaderBean readerBean=null;
        String sql="SELECT * FROM reader WHERE user_name = ? AND password = ?";
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
                readerBean=new ReaderBean();
                readerBean.setReader_id(resultSet.getInt("reader_id"));
                readerBean.setUser_name(resultSet.getString("user_name"));
                readerBean.setName(resultSet.getString("name"));
                readerBean.setSex(resultSet.getByte("sex"));
                readerBean.setAddress(resultSet.getString("address"));
                readerBean.setBirthday(resultSet.getDate("birthday"));
                readerBean.setPhone(resultSet.getString("phone"));
                readerBean.setPassword(resultSet.getString("password"));
                readerBean.setNum(resultSet.getInt("book_num"));
            }
            return readerBean;
        }catch (Exception e){
            logger.error("数据库连接失败，可能：1. 用户名/密码/连接字符串不正确(请修改工程目录下的/conf/db.properties)。2. 没有为当前工程加载MySQL驱动器。");
        }finally {
            JDBCUtils.release(resultSet, cmd, conn);
        }
        return null;
    }

    public static boolean isUserExisted(String user_name){
        String sql="SELECT * FROM reader WHERE user_name=?";

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



}
