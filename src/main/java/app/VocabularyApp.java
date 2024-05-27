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
import modules.primary.StartPane;
import org.example.wordslearning.HelloApplication;
import ymc.LocalStorage.LocalStorage;
import ymc.UI.ArticleProcessor;

import java.io.IOException;

public class VocabularyApp extends Application {

    private LocalStorage localStorage = new LocalStorage();
    private String username = "test";

    private String[] words = {"Hello", "World", "Java", "FX", "Vocabulary"}; // 示例单词列表
    private int currentWordIndex = 0;

    @Override
    public void start(Stage primayStage) throws IOException {
        primayStage.setTitle("Login");

        StartPane startPane = new StartPane();
        startPane.setStyle("-fx-background-color: white");
        startPane.setEffect(Shadows.WINDOW_SHADOW);

        Scene scene = new Scene(startPane,600,500);
        primayStage.setScene(scene);
        primayStage.show();

        startPane.startProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                primayStage.close();
                showLoginStage();
            }
        });

    }

    public void showLoginStage(){
        LoginPane loginPane = new LoginPane();

    }

    public static void main(String[] args) {
        launch();
    }
}