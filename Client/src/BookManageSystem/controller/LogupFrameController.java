package BookManageSystem.controller;

import BookManageSystem.MainApp;
import BookManageSystem.beans.AdminBean;
import BookManageSystem.tools.SimpleTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;

public class LogupFrameController {
    @FXML
    public Button adminRegisterButton;
    @FXML
    public Button goBackButton;
    private SimpleTools simpleTools = new SimpleTools();

    @FXML
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button logupButton;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label systemLabel;

    @FXML
    private Button resetButton;

    @FXML
    private Label passwordLabel;
    private static final Logger logger= Logger.getLogger(LogupFrameController.class);

    public void initialize() {
        // 给组件添加图标
        Labeled[] labeleds = new Labeled[]{systemLabel, userNameLabel, passwordLabel, logupButton, resetButton};
        String[] imagePaths = new String[]{"src/BookManageSystem/images/logo.png", "src/BookManageSystem/images/userName.png",
                "src/BookManageSystem/images/password.png",
                "src/BookManageSystem/images/login.png", "src/BookManageSystem/images/reset.png"};
        simpleTools.setLabeledImage(labeleds, imagePaths);
    }

    // “登录”按钮的事件监听器方法
    public void logupButtonEvent(ActionEvent event) {
        // 关闭登录界面

        // 判断用户名和密码是否匹配
        AdminBean isOK = simpleTools.isAdminLogIn(userNameTextField, passwordTextField);
        if (isOK!=null) {
            // 如果登录成功则跳转到主界面
            stage.close();
            logger.info("切换到图书管理页面！");
            new MainApp().initMainFrame(isOK);
        }
    }

    // “重置”按钮的事件监听器方法
    public void resetButtonEvent(ActionEvent event) {
        // 重置将清空文本框
        userNameTextField.setText("");
        passwordTextField.setText("");
    }

    public void do_adminRegisterButton_event(ActionEvent actionEvent) {
        new MainApp().initAdminRegisterFrame();
    }

    public void do_goBackButton_event(ActionEvent actionEvent) {
        new MainApp().initFirstFrame();
    }
}
