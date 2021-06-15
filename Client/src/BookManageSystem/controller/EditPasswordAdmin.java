package BookManageSystem.controller;

import BookManageSystem.MainApp;
import BookManageSystem.beans.AdminBean;
import BookManageSystem.beans.StateBean;
import BookManageSystem.tools.SimpleTools;
import Client.ClientThread;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import org.apache.log4j.Logger;

public class EditPasswordAdmin {
    private static final Logger logger=Logger.getLogger(EditPasswordAdmin.class);
    @FXML
    public PasswordField current_password;
    @FXML
    public PasswordField new_password1;
    @FXML
    public PasswordField new_password2;
    @FXML
    public Button resetButton;
    public SimpleTools simpleTools=new SimpleTools();
    private AdminBean admin;

    public void do_resetButton_event(ActionEvent actionEvent) {
        if (simpleTools.isEmpty(current_password.getText())) {
            simpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "密码不能为空!");
            logger.info("管理员："+admin.getName()+"密码不能为空！");
        }else if (simpleTools.isEmpty(new_password1.getText())) {
            simpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "密码不能为空!");
            logger.info("管理员："+admin.getName()+"密码不能为空！");
        } else if (simpleTools.isEmpty(new_password2.getText())) {
            simpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "请再次输入密码!");
            logger.info("管理员："+admin.getName()+"请再次输入密码!");
        } else if (!current_password.getText().equals(admin.getPassword())) {
            simpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "原密码不正确!");
            logger.info("管理员："+admin.getName()+"原密码不正确!");
        }else if(!new_password1.getText().equals(new_password2.getText())){
            simpleTools.informationDialog(Alert.AlertType.WARNING,"提示","警告","两次输入密码不一致!");
            logger.info("管理员："+admin.getName()+"两次输入密码不一致!");
        }else {
            admin.setPassword(new_password1.getText());
            Thread thread=new Thread(new ClientThread(MainApp.getClient(),admin,9));
            thread.start();
            try{
                thread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            if(StateBean.isOk1){
                simpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "修改密码成功！");
                logger.info("管理员："+admin.getName()+"修改密码成功！");
            }
    }
}

    public void setAdmin(AdminBean adminBean) {
        this.admin=adminBean;
    }
    }
