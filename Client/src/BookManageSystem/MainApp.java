package BookManageSystem;

import BookManageSystem.beans.AdminBean;
import BookManageSystem.beans.LendList;
import BookManageSystem.beans.ReaderBean;
import BookManageSystem.controller.*;
import Client.MyClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

public class MainApp extends Application {


    private Stage primaryStage;
    public MyClient myClient;
    public static Socket client;
    private static final Logger logger=Logger.getLogger(MainApp.class);
    @Override
    public void start(Stage primaryStage) {
        myClient=new MyClient();
        client=myClient.initClient();
        initFirstFrame();
    }

    public static void main(String[] args) {
        try{
            launch(args);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

    }


    public static Socket getClient(){
        return client;
    }


    /**
     * 首页面
     */
    public void initFirstFrame(){
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation((getClass().getResource("view/firstFrame.fxml")));
            AnchorPane root=loader.load();


            Stage primaryStage=new Stage();
            Scene scene=new Scene(root);
            primaryStage.setTitle("XXX图书馆系统");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);

            FirstFrame firstFrame=loader.getController();
            firstFrame.setStage(primaryStage);
            firstFrame.setSocket(client);

            primaryStage.show();
        }catch (IOException e){
            logger.error(e.getMessage());
        }
    }

    /**
     * 读者注册页面
     */
    public void initReaderRegisterFrame(){
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("view/ReaderRegister.fxml"));
            AnchorPane root=loader.load();

            Stage mainFrameStage=new Stage();
            mainFrameStage.setTitle("读者注册");
            mainFrameStage.setResizable(true);
            mainFrameStage.setAlwaysOnTop(false);
            mainFrameStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(root);
            mainFrameStage.setScene(scene);

            ReaderRegister readerRegister=loader.getController();
            readerRegister.setStage(mainFrameStage);

            mainFrameStage.showAndWait();
        }catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 管理员注册页面
     */
    public void initAdminRegisterFrame(){
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("view/AdminRegister.fxml"));
            AnchorPane root=loader.load();

            Stage mainFrameStage=new Stage();
            mainFrameStage.setTitle("管理员者注册");
            mainFrameStage.setResizable(true);
            mainFrameStage.setAlwaysOnTop(false);
            mainFrameStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(root);
            mainFrameStage.setScene(scene);

            AdminRegisterController adminRegisterController=loader.getController();
            adminRegisterController.setStage(mainFrameStage);

            mainFrameStage.showAndWait();
        }catch (IOException e) {
            logger.error(e.getMessage());
        }

    }


    /**
     *
     * @param readerBean 读者登陆信息
     * 加载读者页面
     */
    public void initMainFrame2(ReaderBean readerBean){
        try {
            // 加载主界面
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/mainFrame2.fxml"));
            AnchorPane root = loader.load();

            // 设置stage舞台的属性
            Stage mainFrameStage = new Stage();
            mainFrameStage.setTitle("用户主界面");
            mainFrameStage.setResizable(true);
            mainFrameStage.setAlwaysOnTop(false);
            mainFrameStage.initModality(Modality.APPLICATION_MODAL);
            mainFrameStage.initOwner(primaryStage);
            MainFrame2 mainFrame2=loader.getController();
            mainFrame2.setReaderBean(readerBean);

            Scene scene = new Scene(root);
            mainFrameStage.setScene(scene);

            mainFrameStage.showAndWait();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 借书界面
     */
    public AnchorPane initBookBorrowFrame(ReaderBean readerBean){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/bookBorrowFrame.fxml"));
            AnchorPane root = loader.load();
            BookBorrowFrame bookBorrowFrame=loader.getController();
            bookBorrowFrame.setReaderBean(readerBean);
            return root;
        }catch (IOException e){
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 还书界面
     */
    public AnchorPane initReturnBooksFrame(ReaderBean readerBean){
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("view/ReturnBooks.fxml"));
            AnchorPane root=loader.load();
            BookReturnFrameController bookReturnFrameController=loader.getController();
            bookReturnFrameController.setReaderBean(readerBean);

            return root;
        }catch (IOException e){
            logger.error(e.getMessage());
        }
        return null;
    }

    public AnchorPane initEditPasswordFrame(ReaderBean readerBean){
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("view/editPassword.fxml"));
            AnchorPane root=loader.load();
            EditPassword editPassword=loader.getController();
            editPassword.setReaderBean(readerBean);
            return root;
        }catch (IOException e){
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 用户登陆界面
     */
    public void initUserloginFrame(){
        try {
            // 加载登录界面的fxml文件
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/Userlogin.fxml"));
            AnchorPane root = loader.load();

            Stage mainFrameStage = new Stage();
            mainFrameStage.setTitle("用户登陆登陆");
            mainFrameStage.setResizable(true);
            mainFrameStage.setAlwaysOnTop(false);
            mainFrameStage.initModality(Modality.APPLICATION_MODAL);
            mainFrameStage.initOwner(primaryStage);

            UserController userController=loader.getController();
            userController.setStage(mainFrameStage);

            Scene scene = new Scene(root);
            mainFrameStage.setScene(scene);

            mainFrameStage.show();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 管理员登录界面
     */
    public void initLogUpFrame() {
        try {
            // 加载登录界面的fxml文件
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/logupFrame.fxml"));
            AnchorPane root = loader.load();

            Stage mainFrameStage = new Stage();
            mainFrameStage.setTitle("管理员登陆");
            mainFrameStage.setResizable(true);
            mainFrameStage.setAlwaysOnTop(false);
            mainFrameStage.initModality(Modality.APPLICATION_MODAL);
            mainFrameStage.initOwner(primaryStage);

            LogupFrameController logupFrameController=loader.getController();
            logupFrameController.setStage(mainFrameStage);

            Scene scene = new Scene(root);
            mainFrameStage.setScene(scene);

            mainFrameStage.show();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 主界面
     */
    public void initMainFrame(AdminBean adminBean) {
        try {
            // 加载主界面
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/mainFrame.fxml"));
            AnchorPane root = loader.load();

            // 设置stage舞台的属性
            Stage mainFrameStage = new Stage();
            mainFrameStage.setTitle("图书管理系统主界面");
            mainFrameStage.setResizable(true);
            mainFrameStage.setAlwaysOnTop(false);
            mainFrameStage.initModality(Modality.APPLICATION_MODAL);
            mainFrameStage.initOwner(primaryStage);

            MainFrameController mainFrameController=loader.getController();
            mainFrameController.setAdminBean(adminBean);

            Scene scene = new Scene(root);
            mainFrameStage.setScene(scene);

            mainFrameStage.showAndWait();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 图书类别添加界面
     *
     * @return 返回一个AnchorPane便于其他控件调用
     */
    public AnchorPane initBookTypeAddFrame(AdminBean adminBean) {
        try {
            // 加载FXML布局文件
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/bookTypeAddFrame.fxml"));
            AnchorPane root = loader.load();
            BookTypeAddFrameController bookTypeAddFrameController=loader.getController();
            bookTypeAddFrameController.setAdmin(adminBean);
            return root;
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 图书类别维护界面
     * @return 返回一个AnchorPane便于其他控件调用
     */
    public AnchorPane initBookTypeManageFrame(AdminBean adminBean) {
        try {
            // 加载图书类别维护界面
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/bookTypeManageFrame.fxml"));
            AnchorPane root = loader.load();
            BookTypeManageFrameController bookTypeManageFrameController=loader.getController();
            bookTypeManageFrameController.setAdmin(adminBean);
            return root;
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }



    /**
     * 图书添加界面
     * @return 返回一个AnchorPane便于其他控件调用
     */
    public AnchorPane initBookAddFrame(AdminBean adminBean) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/bookAddFrame.fxml"));
            AnchorPane pane = loader.load();
            BookAddFrameController bookAddFrameController=loader.getController();
            bookAddFrameController.setAdmin(adminBean);
            return pane;
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 图书维护界面
     * @return 返回一个AnchorPane便于其他控件调用
     */
    public AnchorPane initBookManageFrame(AdminBean adminBean) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/bookManageFrame.fxml"));
            AnchorPane root = loader.load();
            BookManageFrameController bookManageFrameController=loader.getController();
            bookManageFrameController.setAdmin(adminBean);
            return root;
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public AnchorPane initEditPasswordFrameAdmin(AdminBean adminBean){
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("view/editPasswordadmin.fxml"));
            AnchorPane root=loader.load();

            EditPasswordAdmin editPasswordAdmin=loader.getController();
            editPasswordAdmin.setAdmin(adminBean);

            return root;
        }catch (IOException e){
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 关于软件弹出框界面
     * @return 返回Scene
     */
    public Scene initAboutSoftFrame() {
        try {
            // 加载关于软件界面
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/softInformationFrame.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage mainFrameStage = new Stage();
            mainFrameStage.setTitle("关于软件");
            mainFrameStage.setResizable(true);
            mainFrameStage.setAlwaysOnTop(false);
            mainFrameStage.initModality(Modality.APPLICATION_MODAL);
            mainFrameStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            mainFrameStage.setScene(scene);

            SoftInformationFrameController controller = loader.getController();
            controller.setDialogStage(mainFrameStage);

            mainFrameStage.showAndWait();
            return scene;
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}
