package BookManageSystem.beans;

import BookManageSystem.dao.ReaderDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class ReaderBean extends PersonBean implements ReaderBeanDao, Serializable {

    private int reader_id;
    private int num=0;
    private List<LendList> lendLists=new ArrayList<>();

    public ReaderBean(){
        super();

    }
    public ReaderBean(int reader_id, String name, byte sex, Date date, String address, String phone, String user_name, String password, int num){
        super(name,sex,date,address,phone,user_name,password);
        this.reader_id=reader_id;

        this.num=num;
    }



    public int  getReader_id(){
        return reader_id;
    }

    public void setReader_id(int id){
        this.reader_id=id;
    }


    public void setNum(int num){
        this.num=num;
    }
    public int getNum(){
        return num;
    }

    public void addLendList(LendList lendList){
        this.lendLists.add(lendList);
    }


    public static ReaderBean login(String vr1, String vr2){
        ReaderDao readerDao=new ReaderDao();
        ReaderBean readerBean=readerDao.login(vr1,vr2);

        return readerBean;
    }

    @Override
    public boolean updateReader(ReaderBean readerBean){
        return true;
    }
}
