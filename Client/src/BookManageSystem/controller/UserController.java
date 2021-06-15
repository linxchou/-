package BookManageSystem.controller;

import BookManageSystem.MainApp;
import BookManageSystem.beans.ReaderBean;
import BookManageSystem.tools.SimpleTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserController {
    @FXML
    public Label userNameLabel;
    @FXML
    public TextField userNameTextField;
    @FXML
    public Label passwordLabel;
    @FXML
    public PasswordField passwordTextField;
    @FXML
    public Button logupButton;
    @FXML
    public Button resetButton;
    @FXML
    public Label systemLabel;
    @FXML
    public Button registerButton;
    @FXML
    public Button goBackButton;
    @FXML
    private Stage stage;

    private SimpleTools simpleTools = new SimpleTools();

    public void initialize(){

    }

    public void setStage(Stage stage) {
        this.stage=stage;
    }

    public void logupButtonEvent(ActionEvent actionEvent) {

        ReaderBean isOK = simpleTools.isReaderLogIn(userNameTextField, passwordTextField);
        if (isOK!=null) {
            // 如果登录成功则跳转到主界面
            this.stage.close();
            new MainApp().initMainFrame2(isOK);
        }
    }

    public void resetButtonEvent(ActionEvent actionEvent) {
        userNameTextField.clear();
        passwordTextField.clear();
    }

    public void do_readerRegisterButton_event(ActionEvent actionEvent) {
        new MainApp().initReaderRegisterFrame();
    }

    public void do_goBackButton_event(ActionEvent actionEvent) {
        new MainApp().initFirstFrame();
    }
}
