package modules.login;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class LoginButton extends Button {
    BooleanProperty loginProperty = new SimpleBooleanProperty(false);
    /**
     * 构造一个具有特定文本的登录按钮。
     * 这个构造函数初始化了一个登录按钮，设置了它的最大大小、ID、样式表，并添加了一个鼠标点击事件处理器。
     *
     * @param text 按钮上显示的文本。
     */
    public LoginButton(String text){
        super(text); // 使用父类构造函数初始化按钮文本
        this.setMaxSize(100,30); // 设置按钮的最大大小为宽度100，高度30
        this.setId("loginButton"); // 设置按钮的ID为"loginButton"
        // 添加CSS样式表到按钮，确保按钮的外观符合登录页面的样式要求
        this.getStylesheets().add(String.valueOf(getClass().getResource("/CSS/Login.css")));
        // 为按钮添加一个鼠标点击事件处理器，当按钮被点击时，将loginProperty的值设置为true
        this.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            loginProperty.set(true);
        });
    }
}

