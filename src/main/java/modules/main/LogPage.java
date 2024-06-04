package modules.main;

import effects.MyButton;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LogPage extends BorderPane {
    BooleanProperty isOver = new SimpleBooleanProperty(false);
    private final TextArea logTextArea;

    public LogPage(File file) {
        VBox vBox = new VBox();

        // 创建一个TextArea
        logTextArea = new TextArea();
        logTextArea.setEditable(false); // 设置为只读模式

        MyButton endButton = new MyButton("返回上一级");
        endButton.setOnAction(event -> {
            isOver.set(true);
        });

        vBox.getChildren().addAll(logTextArea, endButton);
        this.setCenter(vBox);

        // 在后台线程中读取文件，以避免阻塞UI线程
        Platform.runLater(() -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                StringBuilder contentBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    contentBuilder.append(line).append("\n");
                }
                logTextArea.setText(contentBuilder.toString());
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        });

    }
    public BooleanProperty isOverProperty() {
        return isOver;
    }
}

