package BookManageSystem.beans;

import javafx.beans.property.SimpleStringProperty;

public class LendListTableData {

    private SimpleStringProperty book_id;
    private SimpleStringProperty reader_id;
    private SimpleStringProperty name;
    private SimpleStringProperty bookname;
    private SimpleStringProperty lend_date;
    private SimpleStringProperty limited_date;
    private SimpleStringProperty return_date;


    public LendListTableData(){}

    public LendListTableData(String book_id, String reader_id, String name, String bookname, String lend_date, String limited_date, String return_date) {
        this.book_id = new SimpleStringProperty(book_id);
        this.reader_id = new SimpleStringProperty(reader_id);
        this.name = new SimpleStringProperty(name);
        this.bookname = new SimpleStringProperty(bookname);
        this.lend_date = new SimpleStringProperty(lend_date);
        this.limited_date = new SimpleStringProperty(limited_date);
        this.return_date = new SimpleStringProperty(return_date);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setBook_id(String book_id) {
        this.book_id.set(book_id);
    }

    public void setBookname(String bookname) {
        this.bookname.set(bookname);
    }

    public void setLend_date(String lend_date) {
        this.lend_date.set(lend_date);
    }

    public void setLimited_date(String limited_date) {
        this.limited_date.set(limited_date);
    }

    public void setReader_id(String reader_id) {
        this.reader_id.set(reader_id);
    }

    public void setReturn_date(String return_date) {
        this.return_date.set(return_date);
    }

    public SimpleStringProperty book_idProperty() {
        return book_id;
    }

    public SimpleStringProperty booknameProperty() {
        return bookname;
    }

    public SimpleStringProperty lend_dateProperty() {
        return lend_date;
    }

    public SimpleStringProperty limited_dateProperty() {
        return limited_date;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty reader_idProperty() {
        return reader_id;
    }

    public SimpleStringProperty return_dateProperty() {
        return return_date;
    }

    public String getBook_id() {
        return book_id.get();
    }

    public String getBookname() {
        return bookname.get();
    }

    public String getLend_date() {
        return lend_date.get();
    }

    public String getName() {
        return name.get();

    }

    public String getLimited_date() {
        return limited_date.get();
    }

    public String getReader_id() {
        return reader_id.get();
    }

    public String getReturn_date() {
        return return_date.get();
    }

}
