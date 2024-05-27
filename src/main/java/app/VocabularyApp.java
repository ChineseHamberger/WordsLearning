package app;
import effects.Shadows;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modules.loading.LoadingPane;
import modules.login.LoginPane;
import modules.main.NavigationPane;
import modules.start.StartPane;
import ymc.LocalStorage.LocalStorage;


import java.io.IOException;

public class VocabularyApp extends Application {
    private int width = 600,height = 500;

    private LocalStorage localStorage = new LocalStorage();
    private String username = "test";

    private String[] words = {"Hello", "World", "Java", "FX", "Vocabulary"}; // 示例单词列表
    private int currentWordIndex = 0;

    @Override
    public void start(Stage primayStage) throws IOException {
        primayStage.setTitle("Start");

        StartPane startPane = new StartPane();
        startPane.setStyle("-fx-background-color: white");
        startPane.setEffect(Shadows.WINDOW_SHADOW);

        Scene scene = new Scene(startPane, width, height);
        primayStage.setScene(scene);
        primayStage.show();

        startPane.startProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                primayStage.close();
                showLoginStage();
            }
        });

    }

    public void showLoginStage() {
        LoginPane loginPane = new LoginPane();
        Scene scene = new Scene(loginPane, width, height);
        Stage loginStage = new Stage();
        loginStage.setScene(scene);
        loginPane.loginProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                username = loginPane.getUsername();
                loginStage.close();
                showLoadingStage();
            }
        });
        loginStage.show();
    }

    public void showLoadingStage() {
        System.out.println("showLoadingStage");

        Stage loadingStage = new Stage();
        loadingStage.setTitle("Loading");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: white");
        root.setEffect(Shadows.WINDOW_SHADOW);

        LoadingPane loadingPane = new LoadingPane();
        root.setCenter(loadingPane);


        Scene scene = new Scene(root, width, height);
        loadingStage.setScene(scene);
        loadingStage.show();
    }

    public void showMainStage() {
        System.out.println("username="+username);
        System.out.println("showMainStage");

        Stage mainStage = new Stage();
        mainStage.setTitle("Main");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: white");
        root.setEffect(Shadows.WINDOW_SHADOW);

        NavigationPane navigationPane = new NavigationPane("Home","User","Support");
        root.setLeft(navigationPane);

        Scene scene = new Scene(root,width,height);
        mainStage.setScene(scene);
        mainStage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}