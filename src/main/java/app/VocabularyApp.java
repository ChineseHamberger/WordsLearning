package app;
import effects.Shadows;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modules.load.LoadingStage;
import modules.login.LoginPane;
import modules.primary.StartPane;
import org.example.wordslearning.HelloApplication;
import ymc.LocalStorage.LocalStorage;
import ymc.UI.ArticleProcessor;
import javafx.scene.text.Text;

import java.io.IOException;

public class VocabularyApp extends Application {

    private LocalStorage localStorage = new LocalStorage();
    private String username = "test";

    private String[] words = {"Hello", "World", "Java", "FX", "Vocabulary"}; // 示例单词列表
    private int currentWordIndex = 0;

//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Vocabulary Learning");
//
//        Label welcomeText = new Label("欢迎使用词汇学习");
//
//        // 创建"开始使用"按钮
//        Button startButton = new Button("开始使用");
//        startButton.setOnAction(event -> {
//            // 在这里添加点击按钮后的操作，例如调用上面拆分出的方法
//            // showLoadingStageAndProceed();
//        });
//
//        // 布局管理器
//        StackPane root = new StackPane();
//        root.getChildren().addAll(welcomeText, startButton);
//
//        // 设置场景并显示舞台
//        Scene scene = new Scene(root, 400, 300);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Vocabulary Learning");

        StartPane startPane = new StartPane();
        startPane.setStyle("-fx-background-color: white");
        startPane.setEffect(Shadows.WINDOW_SHADOW);

        Scene scene = new Scene(startPane,600,500);
        primaryStage.setScene(scene);
        primaryStage.show();

        startPane.startProperty().addListener(((observable, oldValue, newValue) -> {
            primaryStage.close();
            showLoginStage();
        }));

//        LoginPane loginPane = new LoginPane();
//        root.setCenter(loginPane);
//        primaryStage.setScene(scene);
//        primaryStage.show();

//        loginPane.loginProperty().addListener(((observable, oldValue, newValue) -> {
//            primaryStage.close();
//            LoadingStage loadingStage  = new LoadingStage(1000,750);
//            loadingStage.show();
//            loadingStage.isOver().addListener(((observable1, oldValue1, newValue1) -> {
//                if(newValue1) {
//                    loadingStage.close();
//                    MainStage mainStage = new MainStage(1000,750);
//                    mainStage.show();
//                }
//            }));
//        }));

    }

    public void showLoginStage(){
//        LoginStage loginStage = new LoginStage(1000,750);
//        loginStage.show();
//        loginStage.isOver().addListener(((observable, oldValue, newValue) -> {
//            if(newValue) {
//                loginStage.close();
//                MainStage mainStage = new MainStage(1000,750);
//                mainStage.show();
//            }
//        }));
    }

    public static void main(String[] args) {
        launch();
    }
}