package BookManageSystem.controller;

import BookManageSystem.MainApp;
import BookManageSystem.beans.AdminBean;
import BookManageSystem.beans.StateBean;
import BookManageSystem.tools.SimpleTools;
import Client.ClientThread;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.log4j.Logger;

public class BookTypeAddFrameController {
    private SimpleTools simpleTools = new SimpleTools();
    private static final Logger logger=Logger.getLogger(BookTypeAddFrameController.class);

    @FXML
    private TextField bookTypeNameTextField;

    @FXML
    private Button addButton;

    @FXML
    private TextArea bookTypeDescriptionTextArea;

    @FXML
    private Button resetButton;
    private AdminBean admin;

    public void initialize() {
        // 初始化按钮的图标
        simpleTools.setLabeledImage(new Labeled[]{addButton, resetButton}, new String[]{"src/BookManageSystem/images/add.png",
                "src/BookManageSystem/images/reset.png"});
    }

    // “添加”按钮的事件监听器方法
    public void do_addButton_event(ActionEvent event) {
        // 获取图书类别名称
        String bookTypeName = bookTypeNameTextField.getText();
        // 获取图书类别描述
        String bookTypeDescription = bookTypeDescriptionTextArea.getText();
        // 组装插入SQL语句
        String sql =
                "insert into tb_bookType (btName, btDescription) values ('" + bookTypeName + "','" + bookTypeDescription + "');";
        // 执行添加操作并返回操作结果
        Thread thread=new Thread(new ClientThread(MainApp.getClient(),sql,5));
        thread.start();
        try{
            thread.join();
        }catch (InterruptedException e){
            logger.error(e.getMessage());
        }
        //boolean isOK = new BookTypeDao().dataChange(sql);
        // 对操作结果进行判断
        if (StateBean.isOk1) {
            // 添加成功则弹出提示框并清空文本框内容
            simpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "添加成功！");
            logger.info("管理员："+admin.getAdmin_id()+"图书类型添加成功！");
            bookTypeNameTextField.setText("");
            bookTypeDescriptionTextArea.setText("");
        } else {
            // 添加失败则弹出提示框
            simpleTools.informationDialog(Alert.AlertType.ERROR, "提示", "错误", "添加失败！");
            logger.info("管理员："+admin.getAdmin_id()+"图书类型添加失败！");
        }


    }

    // “重置”按钮的事件监听器方法
    public void do_resetButton_event(ActionEvent event) {
        // 重置即清空用户输入的内容
        simpleTools.clearTextField(bookTypeNameTextField, bookTypeDescriptionTextArea);
    }

    public void setAdmin(AdminBean adminBean) {
        this.admin=adminBean;
    }
}
