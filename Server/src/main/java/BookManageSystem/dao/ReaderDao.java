package BookManageSystem.dao;


import BookManageSystem.beans.ReaderBean;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ReaderDao {

    private static final Logger logger=Logger.getLogger(ReaderDao.class);
    public synchronized static boolean book_numChange(ReaderBean readerBean){
        Connection conn=null;
        PreparedStatement cmd=null;

        String sql="UPDATE reader SET book_num=? WHERE reader_id=?";
        try{
            conn=JDBCUtils.getConnection();
            cmd=conn.prepareStatement(sql);
            cmd.setInt(1,readerBean.getNum());
            cmd.setInt(2,readerBean.getReader_id());

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

    public static boolean reader_passwordChange(ReaderBean readerBean){
        Connection conn=null;
        PreparedStatement cmd=null;

        String sql="UPDATE reader SET password=? WHERE reader_id=?";
        try{
            conn=JDBCUtils.getConnection();
            cmd=conn.prepareStatement(sql);
            cmd.setString(1,readerBean.getPassword());
            cmd.setInt(2,readerBean.getReader_id());

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

    public synchronized static boolean insertReader(ReaderBean readerBean){
        String sql="insert into reader (reader_id,user_name,name,sex,address,birthday,phone,password)"+
                "values(?,?,?,?,?,?,?,?)";

        Connection conn=null;
        PreparedStatement cmd=null;

        try{
            conn=JDBCUtils.getConnection();
            cmd=conn.prepareStatement(sql);
            cmd.setInt(1,readerBean.getReader_id());
            cmd.setString(2,readerBean.getUser_name());
            cmd.setString(3,readerBean.getName());
            cmd.setByte(4,readerBean.getSex());
            cmd.setString(5,readerBean.getAddress());
            cmd.setDate(6,readerBean.getBirthday());
            cmd.setString(7,readerBean.getPhone());
            cmd.setString(8,readerBean.getPassword());

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
