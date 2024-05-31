package app;
import effects.MyButton;
import effects.MyLabel;
import effects.MyTextField;
import effects.Shadows;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VocabularyApp extends Application {
    private int width = 1000,height = 600;
    private GlobalSetting globalSetting;

    private LocalStorage storage = new LocalStorage();
    private ArticleProcessor articleProcessor;
    private UserConfig config;
    private UserProgress progress;

    private WordBook wordBook;
    private String username;

    private String[] words = {"Hello", "World", "Java", "FX", "Vocabulary"}; // 示例单词列表
    private int currentWordIndex = 0;
    private List<Word> wordsToLearnCopy;
    private List<Word> wordsToReviewCopy;

    @Override
    public void start(Stage primayStage) throws IOException {
        primayStage.setTitle("开始");

        StartPane startPane = new StartPane();
        startPane.setStyle("-fx-background-color: white");
        startPane.setEffect(Shadows.WINDOW_SHADOW);

        Scene scene = new Scene(startPane, width, height);
        primayStage.setScene(scene);
        primayStage.show();

        globalSetting = storage.loadGlobalSettings();
        System.out.println("globalSetting="+globalSetting.getPlayUSSpeechFirst());

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
                System.out.println("username="+username);
                loginStage.close();
                showLoadingStage();;
            }
        });
        loginStage.show();
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

        config = storage.loadUserConfig(username);
        progress = config != null? storage.loadUserProgress(username, config.getSelectedWordBook()): null;
        if (config == null) {
            loadingStage.close();
            showInitialSetupDialog();
        }
        else{
            // 运行后台线程
            Platform.runLater(() -> {
                loadingTask.setOnSucceeded(event -> {
                    System.out.println("loadingTask.setOnSucceeded");
                    loadingStage.close();
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

    public void showInitialSetupDialog() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("初始化设置");

        VBox dialogRoot = new VBox(10);
        dialogRoot.setPadding(new Insets(10));
        dialogRoot.setStyle("-fx-background-color: white;");
        dialogRoot.setEffect(Shadows.WINDOW_SHADOW);

        MyLabel label = new MyLabel("请选择一个单词书：");

        ComboBox<String> wordBookComboBox = new ComboBox<>();
        List<String> wordBooks = storage.listWordBooks();
        wordBooks.forEach(wordBookComboBox.getItems()::add);

        MyLabel learningQuotaLabel = new MyLabel("请输入每日学习量：");
        MyTextField learningQuotaField = new MyTextField();
        MyLabel reviewQuotaLabel = new MyLabel("请输入每日复习量：");
        MyTextField reviewQuotaField = new MyTextField();

        MyButton submitButton = new MyButton("提交");

        submitButton.setOnAction(e -> {
            String selectedWordBook = wordBookComboBox.getValue()==null ? wordBooks.get(0) : wordBookComboBox.getValue();
            int dailyLearningQuota = learningQuotaField.getText().isEmpty() ? UserConfig.getDefaultDailyLearningQuota() : Integer.parseInt(learningQuotaField.getText());
            int dailyReviewQuota = reviewQuotaField.getText().isEmpty() ? UserConfig.getDefaultDailyReviewQuota() : Integer.parseInt(reviewQuotaField.getText());

            UserConfig config = new UserConfig(selectedWordBook, dailyLearningQuota, dailyReviewQuota);
            storage.saveUserConfig(username, config);

            Platform.runLater(() -> {
                dialogStage.close();
                showLoadingStage();
            });
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().add(submitButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        dialogRoot.getChildren().addAll(label, wordBookComboBox, learningQuotaLabel, learningQuotaField, reviewQuotaLabel, reviewQuotaField, buttonBox);

        Scene dialogScene = new Scene(dialogRoot, 400, 300);
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }


    public void showMainStage() {
        System.out.println("username="+username);
        System.out.println("showMainStage");

        Stage mainStage = new Stage();
        mainStage.setTitle("Main");

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
                    contentPane.getChildren().setAll(new LearningPage(username,wordBook,config,progress,wordsToLearnCopy));
                    System.out.println("LearningPane showed");
                    break;
                case 1:
                    contentPane.getChildren().setAll(new ReviewPage(username,wordBook,config,progress,wordsToReviewCopy,globalSetting));
                    System.out.println("ReviewPage showed");
                    break;
                case 2:
                    contentPane.getChildren().setAll(new ReadingPage());
                    System.out.println("ReadingPage showed");
                    break;
                case 3:
                    contentPane.getChildren().setAll(new QueryBox());
                    System.out.println("DictionaryPage showed");
                    break;
                case 4:
                    contentPane.getChildren().setAll(new SettingsPage(globalSetting));
                    System.out.println("SettingsPage showed");
                    break;
                }
        });
        root.setLeft(navigationPane);

        Scene scene = new Scene(root,width,height);
        mainStage.setScene(scene);

        mainStage.setOnCloseRequest(event -> {
            storage.saveUserConfig(username, config);
            storage.saveUserProgress(username, progress, config.getSelectedWordBook());
        });
        mainStage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}