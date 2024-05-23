package org.example.wordslearning;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VocabularyApp extends Application {

    private String[] words = {"Hello", "World", "Java", "FX", "Vocabulary"}; // 示例单词列表
    private int currentWordIndex = 0;

    @Override
    public void start(Stage primaryStage) {
        // 创建UI元素
        Label wordLabel = new Label(words[currentWordIndex]);
        Button nextButton = new Button("Next Word");

        // 添加按钮的点击事件处理器
        nextButton.setOnAction(event -> {
            currentWordIndex = (currentWordIndex + 1) % words.length; // 循环回到数组开头
            wordLabel.setText(words[currentWordIndex]);
        });

        // 布局容器
        VBox layout = new VBox(10);
        layout.getChildren().addAll(wordLabel, nextButton);

        // 场景与舞台设置
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setTitle("Simple Vocabulary App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}