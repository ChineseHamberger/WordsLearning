package modules.primary;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class StartButton extends Button {
    BooleanProperty startProperty = new SimpleBooleanProperty(false);

    public StartButton(String text){
        super(text); // 使用父类构造函数初始化按钮文本
        this.setMaxSize(100,30); // 设置按钮的最大大小为宽度100，高度30
        this.setId("startButton"); // 设置按钮的ID为"loginButton"
        // 添加CSS样式表到按钮，确保按钮的外观符合登录页面的样式要求
        this.getStylesheets().add(String.valueOf(getClass().getResource("/CSS/Login/Login.css")));
        // 为按钮添加一个鼠标点击事件处理器，当按钮被点击时，将loginProperty的值设置为true
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            startProperty.set(true);
        });
    }

}
