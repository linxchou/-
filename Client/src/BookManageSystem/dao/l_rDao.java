package BookManageSystem.dao;


import BookManageSystem.beans.LendList;
import BookManageSystem.beans.ReaderBean;

import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class l_rDao {

    private static final Logger logger=Logger.getLogger(l_rDao.class);



    public static List getLendData(ReaderBean readerBean,String db) {
        Connection conn = null;
        PreparedStatement cmd = null;
        ResultSet rs = null;
        //ConnectionManager cm=ConnectionManager.getInstance();
        String sql="SELECT * FROM "+ db+" WHERE reader_id="+readerBean.getReader_id();
        List list = new ArrayList();
        try {
            //获得数据的连接
            conn = JDBCUtils.getConnection();
            //conn=cm.getConnection("default");
            //获得Statement对象
            cmd=conn.prepareStatement(sql);
            //cmd.setInt(1,readerBean.getReader_id());

            //发送SQL语句
            rs = cmd.executeQuery(sql);
            while (rs.next()) {
                LendList lendList=new LendList();

                // 索引是从1开始的
                if(db.equals("l_r")){
                    lendList.setBook_id(rs.getInt(1));
                    lendList.setReader_id(rs.getInt(2));
                    lendList.setName(rs.getString(3));
                    lendList.setBookname(rs.getString(4));
                    lendList.setLend_date(rs.getDate(5));
                    lendList.setLimited_date(rs.getDate(6));
                }else if(db.equals("borrowrecords")){
                    lendList.setBook_id(rs.getInt(2));
                    lendList.setReader_id(rs.getInt(3));
                    lendList.setName(rs.getString(4));
                    lendList.setBookname(rs.getString(5));
                    lendList.setLend_date(rs.getDate(6));
                    lendList.setLimited_date(rs.getDate(7));
                    lendList.setBack_date(rs.getDate(8));
                }


                list.add(lendList);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            JDBCUtils.release(rs, cmd, conn);
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

}
