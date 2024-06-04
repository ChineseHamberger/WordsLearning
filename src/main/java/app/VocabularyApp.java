package app;
import effects.MyButton;
import effects.MyLabel;
import effects.MyTextField;
import effects.Shadows;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import modules.config.ConfigPage;
import modules.loading.LoadingPane;
import modules.login.LoginPane;
import modules.main.*;
import modules.start.StartPane;
import settings.GlobalSetting;
import ymc.LocalStorage.LocalStorage;
import ymc.UI.ArticleProcessor;
import ymc.algo.WordSelectionAlgorithm;
import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.basicelements.WordBook;
import ymc.config.UserConfig;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import ymc.init.ArticleFetcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VocabularyApp extends Application {
    private Stage UIStage;
    private double width = 1000,height = 600;
    private GlobalSetting globalSetting;

    private LocalStorage storage = new LocalStorage();
    private ArticleProcessor articleProcessor;
    private UserConfig config;
    private UserProgress progress;

    private WordBook wordBook;
    private String username;

    private List<Word> wordsToLearnCopy;
    private List<Word> wordsToReviewCopy;

    @Override
    public void start(Stage pirmaryStage) throws IOException {
        UIStage = pirmaryStage;
        globalSetting = storage.loadGlobalSettings();
        if (globalSetting.getFullScreen()){
            Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
            width = screenSize.getWidth();
            height = screenSize.getHeight()-30;
            System.out.println("fullScreen");
            System.out.println(width);
            System.out.println(height);
        }

        UIStage.setTitle("WordsLearning");

        StartPane startPane = new StartPane(globalSetting);

        Scene scene = new Scene(startPane, width, height);
        UIStage.setScene(scene);
        UIStage.show();

        System.out.println("globalSetting="+globalSetting.getPlayUSSpeechFirst());

        startPane.startProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                //UIStage.close();
                showLoginStage();
            }
        });

    }

    public void showLoginStage() {
        LoginPane loginPane = new LoginPane();
        Scene scene = new Scene(loginPane, width, height);
        UIStage.setScene(scene);
        loginPane.loginProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                username = loginPane.getUsername();
                System.out.println("username="+username);
                //loginStage.close();
                showLoadingStage();;
            }
        });
    }

    public void showLoadingStage() {
        System.out.println("showLoadingStage");

        Task<Void> loadingTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                config = storage.loadUserConfig(username);
                progress = config != null? storage.loadUserProgress(username, config.getSelectedWordBook()): null;

                // 如果没有进度，则初始化
                if (progress == null) {
                    progress = new UserProgress();
                }
                // 获取用户选择的单词书
                wordBook = storage.loadWordBook(config.getSelectedWordBook());
                articleProcessor = new ArticleProcessor(wordBook);
                new Thread(ArticleFetcher::fetchArticles).start();


                // 如果今天已经学习过单词，则从进度中获取
//                if (progress.IsTodaySet()) {
//                    wordsToLearn = progress.getWordsToLearn();
//                    wordsToReview = progress.getWordsToReview();
//                } else {
//                    wordsToLearn = WordSelectionAlgorithm.getWordsForLearning(wordBook, progress, config);
//                    wordsToReview = WordSelectionAlgorithm.getWordsForReview(wordBook, progress, config);
//                    progress.setWordsToLearn(wordsToLearn);
//                    progress.setWordsToReview(wordsToReview);
//                    progress.updateLastLearningDate();
//                }
                // 测试时使用
                List<Word> wordsToLearn = WordSelectionAlgorithm.getWordsForLearning(wordBook, progress, config);
                wordsToLearnCopy = new ArrayList<>(wordsToLearn);
                Collections.copy(wordsToLearnCopy, wordsToLearn);
                List<Word> wordsToReview = WordSelectionAlgorithm.getWordsForReview(wordBook, progress, config);
                wordsToReviewCopy = new ArrayList<>(wordsToReview);
                Collections.copy(wordsToReviewCopy, wordsToReview);
                progress.setWordsToLearn(wordsToLearn);
                progress.setWordsToReview(wordsToReview);
                progress.updateLastLearningDate();

                return null;
            }
        };

        BorderPane root = new BorderPane();
        root.setEffect(Shadows.WINDOW_SHADOW);

        LoadingPane loadingPane = new LoadingPane();
        root.setCenter(loadingPane);

        Scene scene = new Scene(root, width, height);
        UIStage.setScene(scene);

        config = storage.loadUserConfig(username);
        progress = config != null? storage.loadUserProgress(username, config.getSelectedWordBook()): null;
        if (config == null) {
            showConfigStage();
            //showInitialSetupDialog();
        }
        else{
            // 运行后台线程
            Platform.runLater(() -> {
                loadingTask.setOnSucceeded(event -> {
                    System.out.println("loadingTask.setOnSucceeded");
                    showMainStage();
                });

                loadingTask.setOnFailed(event -> {
                    System.out.println("loadingTask.setOnFailed");
                    loadingTask.getException().printStackTrace();
                    System.exit(-1);
                });

                loadingTask.run();
            });
        }

    }
    public void showConfigStage() {
        ConfigPage configPage = new ConfigPage(username);
        Scene scene = new Scene(configPage, width, height);
        configPage.isOverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                Platform.runLater(
                        () -> {
                            showLoadingStage();
                        }
                );
            }
        });
        UIStage.setScene(scene);
    }

    public void showMainStage() {
        System.out.println("username="+username);
        System.out.println("showMainStage");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: white");
        root.setEffect(Shadows.WINDOW_SHADOW);

        StackPane contentPane = new StackPane();
        root.setCenter(contentPane);

        List<String> menuNames = new ArrayList<>();
        menuNames.add("学习新单词");
        menuNames.add("复习单词");
        menuNames.add("读文章");
        menuNames.add("查单词");
        menuNames.add("设置");
        NavigationPane navigationPane = new NavigationPane(200,600,menuNames,true);
        navigationPane.getSelectedProperty ().addListener((observable, oldValue, newValue) -> {
            int newIndex = newValue.intValue();
            switch (newIndex) {
                case 0:
                    contentPane.getChildren().setAll(new LearningPage(username,wordBook,config,progress,wordsToLearnCopy,height));
                    System.out.println("LearningPane showed");
                    break;
                case 1:
                    contentPane.getChildren().setAll(new ReviewPage(username,wordBook,config,progress,wordsToReviewCopy,globalSetting,height));
                    System.out.println("ReviewPage showed");
                    break;
                case 2:
                    ArticleProcessor articleProcessor = new ArticleProcessor(wordBook);
                    ReadingPage readingPage = new ReadingPage(articleProcessor, width, height);
                    contentPane.getChildren().setAll(readingPage);
                    System.out.println("ReadingPage showed");
                    break;
                case 3:
                    contentPane.getChildren().setAll(new QueryBox());
                    System.out.println("DictionaryPage showed");
                    break;
                case 4:
                    contentPane.getChildren().setAll(new SettingsPage(username,globalSetting));
                    System.out.println("SettingsPage showed");
                    break;
                }
        });
        root.setLeft(navigationPane);

        Scene scene = new Scene(root,width,height);
        UIStage.setScene(scene);

        UIStage.setOnCloseRequest(event -> {
            storage.saveUserConfig(username, config);
            storage.saveUserProgress(username, progress, config.getSelectedWordBook());
        });
    }

    public static void main(String[] args) {
        launch();
    }

}