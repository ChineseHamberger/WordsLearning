package app;
import effects.Shadows;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import modules.login.LoginPane;


import java.io.IOException;

public class VocabularyApp extends Application {

    private LocalStorage localStorage = new LocalStorage();
    private String username = "test";

    private String[] words = {"Hello", "World", "Java", "FX", "Vocabulary"}; // 示例单词列表
    private int currentWordIndex = 0;

    @Override

    public void start(Stage primayStage) throws IOException {
        primayStage.setTitle("Login");
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: white");
        root.setEffect(Shadows.WINDOW_SHADOW);

        Scene scene = new Scene(root,600,500);

        LoginPane loginPane = new LoginPane();
        root.setCenter(loginPane);
        primayStage.setScene(scene);
        primayStage.show();



    public static void main(String[] args) {
        launch();
    }
}