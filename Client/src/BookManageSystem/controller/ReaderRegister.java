package BookManageSystem.controller;

import BookManageSystem.tools.SimpleTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ReaderRegister {

    @FXML
    public TextField user_name;
    @FXML
    public TextField password;
    @FXML
    public TextField password2;
    @FXML
    public TextField address;
    @FXML
    public TextField birthday;
    @FXML
    public TextField phone;
    @FXML
    public RadioButton male;
    @FXML
    public ToggleGroup sex;
    @FXML
    public RadioButton female;
    @FXML
    public Button registerButton;
    @FXML
    public TextField name;

    @FXML
    private SimpleTools simpleTools=new SimpleTools();
    @FXML
    private Stage stage;


    public void setStage(Stage stage){
        this.stage=stage;
    }
    public void do_registerButton_event(ActionEvent actionEvent) {
        boolean isOK=simpleTools.isRegistered(user_name,name,password,password2,address,birthday,phone,male,female);
        if(isOK){
            stage.close();
        }
    }


}
