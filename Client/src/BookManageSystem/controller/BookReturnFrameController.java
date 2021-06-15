package BookManageSystem.controller;


import BookManageSystem.MainApp;
import BookManageSystem.beans.*;
import BookManageSystem.dao.BookDao;
import BookManageSystem.tools.SimpleTools;

import Client.ClientThread;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.log4j.Logger;

import java.net.Socket;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class BookReturnFrameController {

    private static final Logger logger=Logger.getLogger(BookReturnFrameController.class);
    @FXML
    public Button checkButton;
    @FXML
    public Button resetButton;
    @FXML
    public TextField idTextField;
    @FXML
    public Button returnButton;
    @FXML
    public Button resetButton2;
    @FXML
    public SimpleTools simpleTools = new SimpleTools();
    @FXML
    public ReaderBean readerBean;
    @FXML
    public TableView bookReturnTableView;
    @FXML
    public TableColumn<LendListTableData, String> bidTableColumn;
    @FXML
    public TableColumn<LendListTableData, String> reader_idTableColumn;
    @FXML
    public TableColumn<LendListTableData, String> reader_nameTableColumn;
    @FXML
    public TableColumn<LendListTableData, String> book_nameTableColumn;
    @FXML
    public TableColumn<LendListTableData, String> lendDateTableColumn;
    @FXML
    public TableColumn<LendListTableData, String> limitedDateTableColumn;
    @FXML
    public TableColumn<LendListTableData, String> returnDateTableColumn;
    @FXML
    public TextField reader_nameTextField;
    @FXML
    public TextField reader_idTextField;
    @FXML
    public TextField book_nameTextField;
    @FXML
    public TextField lendDateTextField;
    @FXML
    public Button checkButton2;
    @FXML
    public TextField limitedDateTextField;
    @FXML
    public TextField returnDateTextField;
    @FXML
    public BookBean bookBean;

    public void initialize() {
        bookReturnTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showLendDetails((LendListTableData) newValue));
        //reader_nameTextField.disabledProperty();
        idTextField.setDisable(true);
        reader_idTextField.setDisable(true);
        lendDateTextField.setDisable(true);
        reader_nameTextField.setDisable(true);
        book_nameTextField.setDisable(true);
        limitedDateTextField.setDisable(true);
        returnDateTextField.setDisable(true);

    }

    public void showLendDetails(LendListTableData lendListTableData) {
        // 判断用户是否选中表格中的某一行
        if (bookReturnTableView.getSelectionModel().getSelectedIndex() < 0) {
        } else {
            // 如果选中表格中的某一行，则将选中行的数据显示在下面的文本框中
            idTextField.setText(lendListTableData.getBook_id());
            reader_idTextField.setText(lendListTableData.getReader_id());
            reader_nameTextField.setText(lendListTableData.getName());
            book_nameTextField.setText(lendListTableData.getBookname());
            lendDateTextField.setText(lendListTableData.getLend_date());
            limitedDateTextField.setText(lendListTableData.getLimited_date());
            returnDateTextField.setText(lendListTableData.getReturn_date());


        }
    }

    public void do_checkButton_event(ActionEvent actionEvent) {
        returnButton.setVisible(true);
        resetButton2.setVisible(true);
        simpleTools.setLendTableViewData(bookReturnTableView
                , simpleTools.getLendListTableViewData(this.readerBean, "l_r")
                , bidTableColumn
                , reader_idTableColumn
                , reader_nameTableColumn
                , book_nameTableColumn
                , lendDateTableColumn
                , limitedDateTableColumn
                , returnDateTableColumn
        );

    }


    public void do_returnButton_event(ActionEvent actionEvent) {
        LendList lendList = new LendList();
        lendList.setBook_id(Integer.parseInt(idTextField.getText()));
        lendList.setReader_id(Integer.parseInt(reader_idTextField.getText()));
        lendList.setName(reader_nameTextField.getText());
        lendList.setBookname(book_nameTextField.getText());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d1 = null;
        java.util.Date d2 = null;
        try {
            d1 = format.parse(lendDateTextField.getText());
            d2 = format.parse(limitedDateTextField.getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert d1 != null;
        java.sql.Date date1 = new java.sql.Date(d1.getTime());
        assert d2 != null;
        java.sql.Date date2 = new java.sql.Date(d2.getTime());
        lendList.setLend_date(date1);
        lendList.setLimited_date(date2);
        Calendar c = Calendar.getInstance();
        Date ReturnDate = new Date(c.getTimeInMillis());
        lendList.setBack_date(ReturnDate);
        bookBean = BookDao.getBookInfo(lendList.getBook_id());

        bookBean.setBookNum(bookBean.getBookNum() + 1);
        readerBean.setNum(readerBean.getNum() - 1);

        Socket client = MainApp.getClient();
        Thread thread = new Thread(new ClientThread(client, lendList, 4));
        Thread thread1=new Thread(new ClientThread(client,bookBean,3));
        Thread thread2=new Thread(new ClientThread(client,readerBean,2));

        thread.start();
        try{
            thread.join();
        }catch (InterruptedException e){
            logger.error(e.getMessage());
        }

        thread1.start();
        try{
            thread1.join();
        }catch (InterruptedException e){
            logger.error(e.getMessage());
        }

        thread2.start();
        try{
            thread2.join();
        }catch (InterruptedException e){
            logger.error(e.getMessage());
        }




        if (StateBean.isOk1&& StateBean.isOk2&&StateBean.isOk3) {
            simpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "归还成功!");
            logger.info("读者："+readerBean.getReader_id()+"归还"+bookBean.getBookName()+"成功！");
        }
        ReFreshTableView();


    }

    public void do_resetButton2_event(ActionEvent actionEvent) {

    }


    public void setReaderBean(ReaderBean readerBean) {
        this.readerBean = readerBean;
    }

    public void do_checkButton2_event(ActionEvent actionEvent) {
        returnButton.setVisible(false);
        resetButton2.setVisible(false);
        simpleTools.setLendTableViewData(bookReturnTableView
                , simpleTools.getLendListTableViewData(this.readerBean, "borrowrecords")
                , bidTableColumn
                , reader_idTableColumn
                , reader_nameTableColumn
                , book_nameTableColumn
                , lendDateTableColumn
                , limitedDateTableColumn
                , returnDateTableColumn
        );
    }

    public void ReFreshTableView() {
        simpleTools.setLendTableViewData(bookReturnTableView
                , simpleTools.getLendListTableViewData(this.readerBean, "l_r")
                , bidTableColumn
                , reader_idTableColumn
                , reader_nameTableColumn
                , book_nameTableColumn
                , lendDateTableColumn
                , limitedDateTableColumn
                , returnDateTableColumn
        );
        idTextField.clear();
        reader_idTextField.clear();
        reader_nameTextField.clear();
        book_nameTextField.clear();
        lendDateTextField.clear();
        limitedDateTextField.clear();
        returnDateTextField.clear();
    }
}
