package BookManageSystem.controller;

import BookManageSystem.MainApp;
import BookManageSystem.beans.*;
import BookManageSystem.dao.BookDao;
import BookManageSystem.dao.BookTypeDao;
import BookManageSystem.dao.ReaderDao;
import BookManageSystem.dao.l_rDao;
import BookManageSystem.tools.SimpleTools;
import Client.ClientThread;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.net.Socket;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class BookBorrowFrame {

    public static boolean isOk1;

    private static final Logger logger=Logger.getLogger(BookBorrowFrame.class);
    @FXML
    public TextField bookNameTextField;
    @FXML
    public TextField bookAuthorTextField;
    @FXML
    public ComboBox bookTypeComBox;
    @FXML
    public Button ckeckButton;
    @FXML
    public Button resetButton;
    @FXML
    private TableView<BookBeanTableData> bookManageTableView;

    @FXML
    public TextField bookNameTextField2;
    @FXML
    public TextField bookAuthorTextField2;
    @FXML
    public RadioButton maleRadioButton;
    @FXML
    public RadioButton femaleRadioButton;
    @FXML
    public ComboBox bookTypeComboBox2;
    @FXML
    public TextArea bookDescriptionTextArea1;
    @FXML
    public TextField bookNumber;
    @FXML
    public Button borrowButton;
    @FXML
    public Button resetButton2;
    @FXML
    public TextField idTextField;
    @FXML
    public TextField priceTextField;
    @FXML
    private TableColumn<BookBeanTableData, String> idTableColumn;
    @FXML
    private TableColumn<BookBeanTableData, String> bookNameTableColumn;

    @FXML
    private TableColumn<BookBeanTableData, String> authorSexTableColumn;

    @FXML
    private TableColumn<BookBeanTableData, String> bookPriceTableColumn;
    @FXML
    private TableColumn<BookBeanTableData, String> bookDescriptionTableColumn;
    @FXML
    private TableColumn<BookBeanTableData, String> bookTypeTableColumn;
    @FXML
    private TableColumn<BookBeanTableData, String> bookNumTableColumn;
    @FXML
    private TableColumn<BookBeanTableData, String> bookAuthorTableColumn;
    @FXML
    private Stage stage;
    public BookBean bookBean=new BookBean();






    public ReaderBean readerBean;

    private SimpleTools simpleTools = new SimpleTools();


    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }


    public void initialize() {
        // 批量为按钮添加图标
        simpleTools.setLabeledImage(new Labeled[]{borrowButton, resetButton2}, new String[]{"src/BookManageSystem/images/edit" +
                ".png",
                "src/BookManageSystem/images/reset.png"});
        // 设置显示id号的文本框不可编辑
        idTextField.setDisable(true);
        bookNameTextField2.setDisable(true);
        priceTextField.setDisable(true);
        bookAuthorTextField2.setDisable(true);
        maleRadioButton.setDisable(true);
        femaleRadioButton.setDisable(true);
        bookTypeComboBox2.setDisable(true);
        //bookDescriptionTextArea1.setDisable(true);
        bookNumber.setDisable(true);

        // 查询图书信息的SQL语句
        String sql = "select bId,bBookName,bAuthor,bSex,bPrice,bBookDescription,btName,bNum from tb_book,tb_booktype where tb_book.btId=tb_booktype.btId;";
        // 填充表格数据，初始化表格视图
        simpleTools.setBookTableViewData(bookManageTableView
                , simpleTools.getBookTableViewData(sql)
                , idTableColumn
                , bookNameTableColumn
                , bookAuthorTableColumn
                , authorSexTableColumn
                , bookPriceTableColumn
                , bookDescriptionTableColumn
                , bookTypeTableColumn
                , bookNumTableColumn
        );

        // 查询图书类别的SQL语句
        String getBookTypeSQL = "select * from tb_booktype";
        List bookTypeList = new BookTypeDao().getRecordsDataBySql(getBookTypeSQL);
        String[] typeNames = new String[bookTypeList.size()];
        for (int i = 0; i < bookTypeList.size(); i++) {
            BookTypeBean bookTypeBean = (BookTypeBean) bookTypeList.get(i);
            typeNames[i] = bookTypeBean.getBookTypeName();
        }
        // 为下拉列表框填充选项
        simpleTools.addComboBoxItems(bookTypeComBox, typeNames);
        simpleTools.addComboBoxItems(bookTypeComboBox2, typeNames);

        // 为表格注册事件监听器
        bookManageTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showBookDetails(newValue));


        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    ObservableList<BookBeanTableData>data=ReFreshTableView();
                    if(!simpleTools.isEmpty(idTextField.getText())){
                        for (BookBeanTableData datum : data) {
                            if (datum.getBookId().equals(idTextField.getText())) {
                                bookNumber.setText(datum.getBookNum());
                            }
                        }
                    }
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        logger.error(e.getMessage());
                    }

                }
            }
        }).start();
    }



    public ObservableList<BookBeanTableData> ReFreshTableView(){
        String sql = "select bId,bBookName,bAuthor,bSex,bPrice,bBookDescription,btName,bNum from tb_book,tb_booktype where tb_book.btId=tb_booktype.btId;";
        ObservableList<BookBeanTableData> data=simpleTools.getBookTableViewData(sql);
        simpleTools.setBookTableViewData(bookManageTableView
                , data
                , idTableColumn
                , bookNameTableColumn
                , bookAuthorTableColumn
                , authorSexTableColumn
                , bookPriceTableColumn
                , bookDescriptionTableColumn
                , bookTypeTableColumn
                , bookNumTableColumn
        );
        return data;

        //bookBean.setBookNum(Integer.parseInt(bookNumTableColumn.getText()));

    }
    public void ReFreshTableView(BookBean bookBean) {
        String sql = "select bId,bBookName,bAuthor,bSex,bPrice,bBookDescription,btName,bNum from tb_book,tb_booktype where tb_book.btId=tb_booktype.btId;";
        simpleTools.setBookTableViewData(bookManageTableView
                , simpleTools.getBookTableViewData(sql)
                , idTableColumn
                , bookNameTableColumn
                , bookAuthorTableColumn
                , authorSexTableColumn
                , bookPriceTableColumn
                , bookDescriptionTableColumn
                , bookTypeTableColumn
                , bookNumTableColumn
        );
        bookNumber.setText(String.valueOf(bookBean.getBookNum()));

    }

    public void showBookDetails(BookBeanTableData bookBeanTableData) {
        // 判断用户是否选中表格中的某一行
        if (bookManageTableView.getSelectionModel().getSelectedIndex() < 0) {
            return;
        } else {
            // 如果选中表格中的某一行，则将选中行的数据显示在下面的文本框中
            idTextField.setText(bookBeanTableData.getBookId());
            bookNameTextField2.setText(bookBeanTableData.getBookName());
            if (bookBeanTableData.getBookAuthorSex().equals("男")) {
                maleRadioButton.setSelected(true);
            } else if (bookBeanTableData.getBookAuthorSex().equals("女")) {
                femaleRadioButton.setSelected(true);
            }
            priceTextField.setText(bookBeanTableData.getBookPrice());
            bookAuthorTextField2.setText(bookBeanTableData.getBookAuthor());
            // 设置分类
            String str = bookBeanTableData.getBookType();
            int index = 0;
            List inputList = FXCollections.observableArrayList(bookTypeComBox.getItems());
            for (int i = 0; i < inputList.size(); i++) {
                if (str.equals(inputList.get(i))) {
                    index = i;
                }
            }
            bookTypeComboBox2.getSelectionModel().select(index);
            bookDescriptionTextArea1.setText(bookBeanTableData.getBookDescription());
            bookNumber.setText(bookBeanTableData.getBookNum());
        }
    }

    public void do_checkButton_event(ActionEvent actionEvent) {
        // 查询SQL语句
        String sql = "select bId,bBookName,bAuthor,bSex,bPrice,bBookDescription,btName,bNum from tb_book,tb_booktype where" +
                " tb_book.btId=tb_booktype.btId ";
        // 判断用户是否输入图书名称，模糊查询
        if (!simpleTools.isEmpty(bookNameTextField.getText())) {
            sql += " and bBookName like '%" + bookNameTextField.getText() + "%'";
        }
        // 判断用户是否输入作者名称，模糊查询
        if (!simpleTools.isEmpty(bookAuthorTextField.getText())) {
            sql += " and bAuthor like '%" + bookAuthorTextField.getText() + "%'";
        }
        // 判断用户是否选择图书类别
        String booktype = (String) bookTypeComBox.getSelectionModel().selectedItemProperty().getValue();
        if (!simpleTools.isEmpty(booktype)) {
            sql += " and btName='" + booktype + "';";
        }
        // 通过SQL语句查询到的数据重新填充表格，刷新表格显示的数据
        simpleTools.setBookTableViewData(bookManageTableView
                , simpleTools.getBookTableViewData(sql)
                , idTableColumn
                , bookNameTableColumn
                , bookAuthorTableColumn
                , authorSexTableColumn
                , bookPriceTableColumn
                , bookDescriptionTableColumn
                , bookTypeTableColumn
                , bookNumTableColumn
        );

    }

    public void do_resetButton1_event(ActionEvent actionEvent) {
        // 清空用户并刷新表格
        simpleTools.clearTextField(bookNameTextField, bookAuthorTextField);
        simpleTools.clearSelectedComboBox(bookTypeComBox);

    }

    public void do_borrowButton_event(ActionEvent actionEvent) {
        LendList lendList = new LendList();
        if (simpleTools.isEmpty(idTextField.getText())) {
            simpleTools.informationDialog(Alert.AlertType.ERROR, "提示", "错误", "请先选择要借阅的书");
        }
        BookBean bookBean=new BookBean();
        bookBean.setBookId(Integer.parseInt(idTextField.getText()));
        bookBean.setBookNum(Integer.parseInt(bookNumber.getText()));

        if(bookBean.getBookNum()==0){
            simpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "此书已借完！");
        }
        else{
            lendList.setBook_id(Integer.parseInt(idTextField.getText()));
            lendList.setReader_id(readerBean.getReader_id());
            lendList.setName(readerBean.getName());
            lendList.setBookname(bookNameTextField2.getText());
            Calendar c = Calendar.getInstance();
            Date LendDate = new Date(c.getTimeInMillis());
            lendList.setLend_date(LendDate);
            c.add(Calendar.DATE, 60);
            Date LimitedDate = new Date(c.getTimeInMillis());
            lendList.setLimited_date(LimitedDate);


            Socket client = MainApp.getClient();
            Thread thread = new Thread(new ClientThread(client, lendList, 1));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }

            if (StateBean.isOk1) {
                bookBean.setBookNum(bookBean.getBookNum() - 1);
                simpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "借阅成功!");
                logger.info("读者："+readerBean.getReader_id()+"借阅："+lendList.getBookname()+"成功！");
                readerBean.setNum(readerBean.getNum() + 1);
                Thread thread1 = new Thread(new ClientThread(client, readerBean, 2));
                thread1.start();
                try {
                    thread1.join();

                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }

                if (StateBean.isOk2) {
                    logger.info("读者借阅书目更新成功！");

                } else {
                    logger.error("读者借阅书目更新失败！");
                }

                Thread thread2 = new Thread(new ClientThread(client, bookBean, 3));
                thread2.start();
                try {
                    thread2.join();
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }

                if (StateBean.isOk3) {
                    logger.info("图书数量更新成功！");
                } else {
                    logger.info("图书数量更新失败！");
                }


            } else{
                simpleTools.informationDialog(Alert.AlertType.ERROR, "提示", "错误", "您已借阅过此书！");
                logger.info("已借阅过"+lendList.getBookname()+"！");
            }

            ReFreshTableView(bookBean);
        }



    }

    public void do_resetButton2_event(ActionEvent actionEvent) {
        // 清空用户输入
        simpleTools.clearTextField(idTextField, bookNameTextField2, priceTextField, bookAuthorTextField2,
                bookDescriptionTextArea1);
        simpleTools.clearSelectedRadioButton(femaleRadioButton, maleRadioButton);
        simpleTools.clearSelectedComboBox(bookTypeComboBox2);
    }

    public void setReaderBean(ReaderBean readerBean) {
        this.readerBean = readerBean;
    }
}
