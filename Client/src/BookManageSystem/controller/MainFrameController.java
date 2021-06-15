package BookManageSystem.controller;

import BookManageSystem.MainApp;
import BookManageSystem.beans.AdminBean;
import BookManageSystem.tools.SimpleTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

public class MainFrameController {

    public MenuItem editPassword;
    private SimpleTools simpleTools = new SimpleTools();
    @FXML
    private MenuItem bookManageMenuItem;

    @FXML
    public ImageView mainBookManageImageView;

    @FXML
    private AnchorPane mainFrameAnchorPane;

    @FXML
    private MenuItem bookAddMenuItem;

    @FXML
    private MenuItem exitMenuItem;

    @FXML
    private MenuItem bookTypeManageMenuItem;

    @FXML
    private MenuItem aboutSoftMenuItem;

    @FXML
    private MenuItem bookTypeAddMenuItem;
    private AdminBean admin;
    private static final Logger logger=Logger.getLogger(MainFrameController.class);

    /**
     * 初始化启动
     */
    public void initialize() {
        // 为菜单项添加图标
        simpleTools.setMenuItemImage(new MenuItem[]{bookTypeAddMenuItem, bookTypeManageMenuItem, bookAddMenuItem,
                bookManageMenuItem, exitMenuItem, editPassword}, new String[]{"src/BookManageSystem/images/add.png", "src/BookManageSystem/images/edit" +
                ".png", "src/BookManageSystem/images/add.png", "src/BookManageSystem/images/edit.png", "src/BookManageSystem/images/exit.png", "src/BookManageSystem/images/about.png"});
        // 设置图片
        mainBookManageImageView.setImage(new Image("file:src/BookManageSystem/images/bookmanagesystem.png"));
    }

    /**
     * “退出”菜单项的事件处理
     *
     * @param event 事件
     */
    public void do_exitMenuItem_vent(ActionEvent event) {
        // 退出菜单项的事件处理
        logger.info("退出系统！");
        System.exit(0);
    }

    /**
     * “图书类别添加”菜单项的事件处理
     *
     * @param event 事件
     */
    public void do_bookTypeAddMenuItem_event(ActionEvent event) {
        AnchorPane pane = new MainApp().initBookTypeAddFrame(admin);
        logger.info("切换到图书类型添加界面！");
        mainFrameAnchorPane.getChildren().clear();
        mainFrameAnchorPane.getChildren().add(pane);
    }

    /**
     * “图书类别维护”菜单项的事件处理
     *
     * @param event 事件
     */
    public void do_bookTypeManageMenuItem_event(ActionEvent event) {
        // 当点击“图书类别维护”菜单项后，加载图书类别维护面板
        AnchorPane pane = new MainApp().initBookTypeManageFrame(admin);
        // 清空界面上原有的控件
        logger.info("切换到图书类型维护界面！");
        mainFrameAnchorPane.getChildren().clear();
        // 将图书类别维护面板添加到界面上
        mainFrameAnchorPane.getChildren().add(pane);
    }

    /**
     * “图书添加”菜单项的事件处理
     * @param event 事件
     */
    public void do_bookAddMenuItem_event(ActionEvent event) {
        // 当点击“图书添加”菜单项后，加载图书添加面板
        AnchorPane pane = new MainApp().initBookAddFrame(admin);
        // 清空界面上原有的控件
        logger.info("切换到图书添加界面！");
        mainFrameAnchorPane.getChildren().clear();
        // 将图书添加面板添加到界面上
        mainFrameAnchorPane.getChildren().add(pane);
    }

    /**
     * “图书维护”菜单项的事件处理
     * @param event 事件
     */
    public void do_bookManageMenuItem_event(ActionEvent event) {
        // 当点击“图书维护”菜单项后，加载图书维护面板
        AnchorPane pane = new MainApp().initBookManageFrame(admin);
        // 清空界面上原有的控件
        logger.info("切换到图书维护界面！");
        mainFrameAnchorPane.getChildren().clear();
        // 将图书维护面板添加到界面上
        mainFrameAnchorPane.getChildren().add(pane);
    }



    public void setAdminBean(AdminBean adminBean) {
        this.admin=adminBean;
    }

    public void do_passwordEdit_event(ActionEvent actionEvent) {
        AnchorPane pane=new MainApp().initEditPasswordFrameAdmin(admin);
        logger.info("切换到管理员密码修改界面！");
        mainFrameAnchorPane.getChildren().clear();
        mainFrameAnchorPane.getChildren().add(pane);
    }
}
