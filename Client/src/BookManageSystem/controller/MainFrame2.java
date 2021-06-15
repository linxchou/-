package BookManageSystem.controller;

import BookManageSystem.MainApp;
import BookManageSystem.beans.ReaderBean;
import BookManageSystem.tools.SimpleTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MainFrame2 {

    @FXML
    public MenuItem bookBorrowMenuItem;
    @FXML
    public MenuItem bookReturnMenuItem;
    @FXML
    public MenuItem exitMenuItem;
    @FXML
    public MenuItem aboutSoftMenuItem;
    @FXML
    public ImageView mainBookManageImageView;
    @FXML
    public MenuItem passwordEditMenuItem;
    public MenuItem editpasswordMenuItem;
    @FXML
    private AnchorPane mainFrameAnchorPane;
    @FXML
    public ImageView imageView;

    private SimpleTools simpleTools = new SimpleTools();
    public ReaderBean readerBean;

    @FXML
    private Stage stage;
    @FXML
    private MenuItem bookTypeAddMenuItem;
    @FXML
    private MenuItem bookTypeManageMenuItem;
    @FXML
    private MenuItem bookAddMenuItem;
    @FXML
    private MenuItem bookManageMenuItem;
    @FXML
    public MenuItem editPassword;

    private static final Logger logger=Logger.getLogger(MainFrame2.class);

    /**
    public void initialize(){
        simpleTools.setMenuItemImage(new MenuItem[]{bookBorrowMenuItem, bookReturnMenuItem,  exitMenuItem, aboutSoftMenuItem}, new String[]{"src/BookManageSystem/images/add.png", "src/BookManageSystem/images/edit" +
                ".png", "src/BookManageSystem/images/exit.png", "src/BookManageSystem/images/about.png"});
        // 设置图片
        mainBookManageImageView.setImage(new Image("file:src/BookManageSystem/images/bookmanagesystem.png"));

    }
     */


    public void do_bookBorrowMenuItem_event(ActionEvent actionEvent) {
        AnchorPane pane = new MainApp().initBookBorrowFrame(readerBean);
        // 清空界面上原有的控件
        logger.info("切换到借书界面！");
        mainFrameAnchorPane.getChildren().clear();
        // 将图书维护面板添加到界面上
        mainFrameAnchorPane.getChildren().add(pane);
    }




    public void do_bookReturnItem_event(ActionEvent actionEvent) {
        AnchorPane pane = new MainApp().initReturnBooksFrame(readerBean);
        // 清空界面上原有的控件
        logger.info("切换到还书界面！");
        mainFrameAnchorPane.getChildren().clear();
        // 将图书维护面板添加到界面上
        mainFrameAnchorPane.getChildren().add(pane);
    }


    public void do_editPasswordMenuitem_event(ActionEvent actionEvent) {
        AnchorPane pane=new MainApp().initEditPasswordFrame(readerBean);
        logger.info("切换到读者修改密码界面！");
        mainFrameAnchorPane.getChildren().clear();
        mainFrameAnchorPane.getChildren().add(pane);
    }

    public void do_exitMenuItem_event(ActionEvent actionEvent) {
        try{
            MainApp.client.close();
        }catch (IOException e){
            logger.error(e.getMessage());
        }
        logger.info("客户端退出！");
        System.exit(0);
    }

    public void setReaderBean(ReaderBean readerBean) {
        this.readerBean=readerBean;
    }
}
