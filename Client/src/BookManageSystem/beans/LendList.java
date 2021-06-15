package BookManageSystem.beans;

import java.io.Serializable;
import java.sql.Date;

public class LendList implements Serializable {
    private int reader_id;
    private int  book_id;
    private String name;
    private String bookname;
    private Date lend_date;
    private Date limited_date;
    private Date back_date=null;


    public LendList(int reader_id, int book_id,String name, String bookname, Date lend_date, Date limited_date) {
        this.reader_id = reader_id;
        this.book_id = book_id;
        this.name=name;
        this.bookname = bookname;
        this.lend_date = lend_date;
        this.limited_date = limited_date;
    }
    public LendList(){

    }

    public int getReader_id(){
        return reader_id;
    }
    public String getBookname(){
        return bookname;
    }

    public Date getBack_date() {

        return back_date;
    }
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public void setReader_id(int reader_id){this.reader_id=reader_id;}
    public void setBook_id(int book_id){this.book_id=book_id;}
    public void setBookname(String bookname){this.bookname=bookname;}
    public void setLend_date(Date lend_date){this.lend_date=lend_date;}


    public Date getLend_date() {
        return lend_date;
    }

    public Date getLimited_date() {
        return limited_date;
    }

    public int getBook_id() {
        return book_id;
    }
    public void setLimited_date(Date limited_date){
        this.limited_date=limited_date;
    }

    public void setBack_date(Date back_date){
        this.back_date=back_date;
    }
}
