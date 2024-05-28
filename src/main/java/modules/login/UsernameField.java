package modules.login;

import effects.SelfDefinedColors;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class UsernameField extends BorderPane {
    HBox hBox;
    TextField textField = new TextField();
    Rectangle line;
    Label icon = new Label();
    BorderPane usernameField = new BorderPane();
    String username;
    /**
     * 构造一个用于输入用户名的字段，包括字段样式、事件处理和布局管理。
     * 该构造函数不接受参数，并且没有返回值。
     */
    public UsernameField(){
        // 设置用户名字段的最大大小，设置ID，并添加CSS样式表
        usernameField.setMaxSize(280,40);
        usernameField.setId("username-field");
        usernameField.getStylesheets().add(String.valueOf(getClass().getResource("/CSS/Style.css")));

        // 定义颜色变量
        String ColorBlueHex = SelfDefinedColors.ColorBlueHex;
        String ColorGreyHex = SelfDefinedColors.ColorGrayHex;

        // 鼠标进入文本字段时改变样式
        usernameField.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            if(!textField.isFocused()){
                usernameField.setStyle("-fx-border-color: " + ColorBlueHex);
                icon.setStyle("-fx-text-fill: " + ColorBlueHex);
                textField.setStyle("-fx-prompt-text-fill: " + ColorBlueHex);
            }
        });

        // 鼠标离开文本字段时改变样式
        usernameField.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            if(!textField.isFocused()){
                usernameField.setStyle("-fx-border-color: " + ColorGreyHex);
                icon.setStyle("-fx-text-fill: " + ColorGreyHex);
                textField.setStyle("-fx-prompt-text-fill: " + ColorGreyHex);
            }
        });

        // 加载字体并设置图标样式
        Font font = Font.loadFont(getClass().getResourceAsStream("/Icons/userIcon.ttf"),28);
        icon.setFont(font);
        icon.setText("\ue908");
        icon.setTextFill(SelfDefinedColors.GRAY);
        icon.setId("icon");
        icon.getStylesheets().add(String.valueOf(getClass().getResource("/CSS/Style.css")));

        // 设置分隔线样式
        line = new Rectangle(1.5,26);
        line.setFill(SelfDefinedColors.GRAY);
        line.fillProperty().bind(icon.textFillProperty());

        // 设置文本字段属性
        textField.setPrefHeight(40);
        textField.setPromptText("Press your username");
        textField.setId("text-field");
        textField.getStylesheets().add(String.valueOf(getClass().getResource("/CSS/Style.css")));

        // 监听文本字段的焦点变化，以改变样式
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                textField.setPromptText("");
                usernameField.setStyle("-fx-border-color: " + ColorBlueHex);
                icon.setStyle("-fx-text-fill: " + ColorBlueHex);
            } else{
                textField.setPromptText("Press your username");
                textField.setStyle("-fx-prompt-text-fill: " + ColorGreyHex);
                usernameField.setStyle("-fx-border-color: " + ColorGreyHex);
                icon.setStyle("-fx-text-fill: " + ColorGreyHex);
            }
        });

        // 构建并设置布局
        hBox = new HBox(icon,line,textField);
        hBox.setAlignment(Pos.CENTER_LEFT);
        usernameField.setCenter(hBox);

        Label label = new Label("Username(仅支持英文)");
        label.setId("label");
        VBox vBox = new VBox(label,usernameField);
        vBox.setAlignment(Pos.CENTER_LEFT);
        
        this.setMaxSize(280,50);
        this.setCenter(vBox);
    }
}
