package BookManageSystem.controller;

import BookManageSystem.MainApp;
import BookManageSystem.beans.ReaderBean;
import BookManageSystem.beans.StateBean;
import BookManageSystem.tools.SimpleTools;
import Client.ClientThread;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import org.apache.log4j.Logger;


public class EditPassword {
    private static final Logger logger=Logger.getLogger(EditPassword.class);
    @FXML
    public Button resetButton;
    @FXML
    public PasswordField current_password;
    @FXML
    public PasswordField new_password1;
    @FXML
    public PasswordField new_password2;
    @FXML
    private ReaderBean readerbean;
    public SimpleTools simpleTools=new SimpleTools();

    public void do_resetButton_event(ActionEvent actionEvent) {
        if (simpleTools.isEmpty(current_password.getText())) {
            simpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "密码不能为空!");
            logger.info("读者："+readerbean.getName()+"密码不能为空！");
        }else if (simpleTools.isEmpty(new_password1.getText())) {
            simpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "密码不能为空!");
            logger.info("读者："+readerbean.getName()+"密码不能为空！");
        } else if (simpleTools.isEmpty(new_password2.getText())) {
            simpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "请再次输入密码!");
            logger.info("读者："+readerbean.getName()+"请再次输入密码！");
        } else if (!current_password.getText().equals(readerbean.getPassword())) {
            simpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "原密码不正确!");
            logger.info("读者："+readerbean.getName()+"原密码不正确！");
        }else if(!new_password1.getText().equals(new_password2.getText())){
            simpleTools.informationDialog(Alert.AlertType.WARNING,"提示","警告","两次输入密码不一致!");
            logger.info("读者："+readerbean.getName()+"两次输入的密码不一致！");
        }else {
            readerbean.setPassword(new_password1.getText());
            Thread thread=new Thread(new ClientThread(MainApp.getClient(),readerbean,7));
            thread.start();
            try{
                thread.join();
            }catch (InterruptedException e){
                logger.error(e.getMessage());
            }

            if(StateBean.isOk1){
                simpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "修改密码成功！");
                logger.info("读者："+readerbean.getName()+"修改密码成功");
            }
        }
    }

    public void setReaderBean(ReaderBean readerBean) {
        this.readerbean=readerBean;
    }
}
