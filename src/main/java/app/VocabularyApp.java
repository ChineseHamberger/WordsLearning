package app;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(VocabularyApp.class.getResource("VocabularyApp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 680, 600);
        stage.setTitle("Words Learning");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}