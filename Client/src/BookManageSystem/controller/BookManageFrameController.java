package BookManageSystem.controller;

import BookManageSystem.MainApp;
import BookManageSystem.beans.AdminBean;
import BookManageSystem.beans.BookBeanTableData;
import BookManageSystem.beans.BookTypeBean;
import BookManageSystem.beans.StateBean;
import BookManageSystem.dao.BookDao;
import BookManageSystem.dao.BookTypeDao;
import BookManageSystem.tools.SimpleTools;
import Client.ClientThread;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.log4j.Logger;

import java.util.List;

public class BookManageFrameController {

    @FXML
    private TextField bookNumberTextField;
    private SimpleTools simpleTools = new SimpleTools();
    private BookDao bookDao = new BookDao();
    private static final Logger logger=Logger.getLogger(BookManageFrameController.class);

    @FXML
    private TextField idTextField;

    @FXML
    private Button alterButton;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private TextField bookAuthorTextField2;

    @FXML
    private ComboBox bookTypeComboBox2;

    @FXML
    private TableColumn<BookBeanTableData, String> idTableColumn;

    @FXML
    private TableColumn<BookBeanTableData, String> authorSexTableColumn;

    @FXML
    private TableColumn<BookBeanTableData, String> bookPriceTableColumn;

    @FXML
    private TableColumn<BookBeanTableData,String> bookNumTableColumn;

    @FXML
    private ComboBox<?> bookTypeComboBox;

    @FXML
    private Button checkButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button resetButton2;

    @FXML
    private TableColumn<BookBeanTableData, String> bookAuthorTableColumn;

    @FXML
    private TableView<BookBeanTableData> bookManageTableView;

    @FXML
    private TextArea bookDescriptionTextArea;

    @FXML
    private TextField bookAuthorTextField;

    @FXML
    private TableColumn<BookBeanTableData, String> bookNameTableColumn;

    @FXML
    private TableColumn<BookBeanTableData, String> bookDescriptionTableColumn;

    @FXML
    private TextField bookNameTextField2;

    @FXML
    private TextField priceTextField;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField bookNameTextField;

    @FXML
    private TableColumn<BookBeanTableData, String> bookTypeTableColumn;
    private AdminBean admin;

    /**
     * 初始化图书维护界面控件
     */
    public void initialize() {
        // 批量为按钮添加图标
        simpleTools.setLabeledImage(new Labeled[]{alterButton, deleteButton, resetButton2}, new String[]{"src/BookManageSystem/images/edit" +
                ".png",
                "src/BookManageSystem/images/delete.png", "src/BookManageSystem/images/reset.png"});
        // 设置显示id号的文本框不可编辑
        idTextField.setDisable(true);
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
                ,bookNumTableColumn
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
        simpleTools.addComboBoxItems(bookTypeComboBox, typeNames);
        simpleTools.addComboBoxItems(bookTypeComboBox2, typeNames);

        // 为表格注册事件监听器
        bookManageTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showBookDetails(newValue));
    }

    // 【查询】按钮的事件监听器
    public void do_checkButton_event(ActionEvent event) {
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
        String booktype = (String) bookTypeComboBox.getSelectionModel().selectedItemProperty().getValue();
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
                ,bookNumTableColumn
        );
    }

    // 【修改】按钮的事件监听器
    public void do_alterButton_event(ActionEvent event) {
        // 获取用户输入的修改数据
        String id = idTextField.getText();
        String bookName = bookNameTextField2.getText();
        String authorSex = "";
        if (maleRadioButton.isSelected()) {
            authorSex = maleRadioButton.getText();
        } else if (femaleRadioButton.isSelected()) {
            authorSex = femaleRadioButton.getText();
        }
        String price = priceTextField.getText();
        String bookAuthor = bookAuthorTextField2.getText();
        String bookType = (String) bookTypeComboBox2.getSelectionModel().selectedItemProperty().getValue();
        String description = bookDescriptionTextArea.getText();
        // 组装SQL语句
        String bookTypeSQL = "select * from tb_booktype where btName='" + bookType + "';";
        List bookTypeList = new BookTypeDao().getRecordsDataBySql(bookTypeSQL);
        BookTypeBean bookTypeBean = (BookTypeBean) bookTypeList.get(0);
        // 获取图书类别id
        int bookTypeId = bookTypeBean.getBookTypeId();
        // 组装修改SQL语句
        String alterSQL =
                "update tb_book set bBookName='" + bookName + "',bAuthor='" + bookAuthor + "',bSex='" + authorSex +
                        "',bPrice=" + price + ",bBookDescription='" + description + "',btId=" + bookTypeId + " where " +
                        "bId=" + id + ";";
        // 执行SQL语句并返回结果

        Thread thread=new Thread(new ClientThread(MainApp.getClient(),alterSQL,5));
        thread.start();
        try{
            thread.join();
        }catch (InterruptedException e){
            logger.error(e.getMessage());
        }

        //boolean isOK = bookDao.dataChange(alterSQL);
        // 对结果进行判断
        if (StateBean.isOk1) {
            // 修改成功则重新刷新表格，并重置用户输入
            initialize();
            do_resetButton2_event(null);
            simpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "修改成功！");
            logger.info("管理员："+admin.getAdmin_id()+"修改书籍信息成功！");
        } else {
            // 修改失败则弹出提示框
            simpleTools.informationDialog(Alert.AlertType.ERROR, "提示", "错误", "修改失败！");
            logger.info("管理员："+admin.getAdmin_id()+"修改书籍信息失败！");
        }
    }

    // 【删除】按钮的事件监听器
    public void do_deleteButton_event(ActionEvent event) {
        // 获取id文本框的值
        String id = idTextField.getText();
        // 组装SQL语句，通过id来删除记录
        String deleteSQL = "delete from tb_book where bId=" + id + ";";
        // 执行删除操作

        Thread thread=new Thread(new ClientThread(MainApp.getClient(),deleteSQL,5));
        thread.start();
        try{
            thread.join();
        }catch (InterruptedException e){
            logger.error(e.getMessage());
        }
        //boolean isOK = bookDao.dataChange(deleteSQL);
        // 对结果进行判断处理
        if (StateBean.isOk1) {
            // 删除成功则刷新表格
            initialize();
            do_resetButton2_event(null);
            simpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "删除成功！");
            logger.info("管理员："+admin.getAdmin_id()+"删除书籍成功！");
        } else {
            // 删除失败弹出提示框
            simpleTools.informationDialog(Alert.AlertType.ERROR, "提示", "错误", "删除失败！");
            logger.info("管理员："+admin.getAdmin_id()+"删除书籍失败！");
        }
    }

    // 【重置】按钮的事件监听器
    public void do_resetButton2_event(ActionEvent event) {
        // 清空用户输入
        simpleTools.clearTextField(idTextField, bookNameTextField2, priceTextField, bookAuthorTextField2,
                bookDescriptionTextArea);
        simpleTools.clearSelectedRadioButton(femaleRadioButton, maleRadioButton);
        simpleTools.clearSelectedComboBox(bookTypeComboBox2);
    }

    // 【重置】按钮的事件监听器
    public void do_resetButton_event(ActionEvent event) {
        // 清空用户并刷新表格
        simpleTools.clearTextField(bookNameTextField, bookAuthorTextField);
        simpleTools.clearSelectedComboBox(bookTypeComboBox);
        initialize();
    }

    // 选中某行后行内容显示在下面文本框中
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
            List inputList = FXCollections.observableArrayList(bookTypeComboBox.getItems());
            for (int i = 0; i < inputList.size(); i++) {
                if (str.equals(inputList.get(i))) {
                    index = i;
                }
            }
            bookTypeComboBox2.getSelectionModel().select(index);
            bookDescriptionTextArea.setText(bookBeanTableData.getBookDescription());
            bookNumberTextField.setText(bookBeanTableData.getBookNum());
        }
    }

    public void setAdmin(AdminBean adminBean) {
        this.admin=adminBean;
    }
}
