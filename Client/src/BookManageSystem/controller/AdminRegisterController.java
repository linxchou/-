package BookManageSystem.controller;


import BookManageSystem.tools.SimpleTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AdminRegisterController {
    @FXML
    public TextField userNameTextField;
    @FXML
    public TextField passwordTextField;
    @FXML
    public TextField password2TextField;
    @FXML
    public TextField addressTextField;

    @FXML
    public TextField phoneTextField;
    @FXML
    public Button adminRegisterButton;
    @FXML
    public ToggleGroup sex;
    @FXML
    public TextField nameTextField;
    @FXML
    public RadioButton femaleRadioButton;
    @FXML
    public RadioButton maleRadioButton;

    private SimpleTools simpleTools=new SimpleTools();
    private Stage stage;

    public void initialize(){

    }

    /**
     *
     * @param actionEvent
     * 判断是否登陆
     */
    public void do_adminRegisterButton_event(ActionEvent actionEvent) {
        boolean isOK=simpleTools.isAdminRegistered(nameTextField,userNameTextField,passwordTextField,password2TextField,maleRadioButton,femaleRadioButton,addressTextField,phoneTextField);
        if(isOK){
            stage.close();
        }

    }

    public void setStage(Stage mainFrameStage) {
        this.stage=mainFrameStage;
    }
}
