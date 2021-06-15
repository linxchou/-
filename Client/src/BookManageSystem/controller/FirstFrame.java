package BookManageSystem.controller;

import BookManageSystem.MainApp;
import BookManageSystem.tools.SimpleTools;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;


public class FirstFrame {
    public Button exitButton;
    private SimpleTools simpleTools = new SimpleTools();
    @FXML
    public Button userloginButton;
    @FXML
    public Button adminloginButton;

    @FXML
    private Stage stage;
    private Socket client;
    private static final Logger logger= Logger.getLogger(FirstFrame.class);

    public void initialize(){
        userloginButton.requestFocus();
    }
    public void setStage(Stage primaryStage) {
        this.stage=primaryStage;
    }


    public void do_user_login(MouseEvent mouseEvent) {
        stage.close();
        //new Thread(new ClientThread(MainApp.getClient())).start();
        logger.info("切换到读者登陆页面！");
        new MainApp().initUserloginFrame();
    }

    public void do_admin_login(MouseEvent mouseEvent) {
        stage.close();
        logger.info("切换到管理员登陆页面！");
        new MainApp().initLogUpFrame();
    }

    public void setSocket(Socket client) {
        this.client=client;
    }

    public void do_exitButton_event(ActionEvent actionEvent) {
        try{
            MainApp.getClient().close();
            logger.info("退出系统！");
            System.exit(0);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
