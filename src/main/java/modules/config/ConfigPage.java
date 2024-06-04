package modules.config;

import effects.MyButton;
import effects.MyLabel;
import effects.MyTextField;
import effects.Shadows;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ymc.LocalStorage.LocalStorage;
import ymc.config.UserConfig;

import java.util.List;

public class ConfigPage extends BorderPane {
    private final LocalStorage storage = new LocalStorage();
    BooleanProperty isOver = new SimpleBooleanProperty(false);
    public ConfigPage(String username){
        VBox dialogRoot = new VBox(10);
        dialogRoot.setPadding(new Insets(10));

        dialogRoot.setStyle("-fx-background-color: white;");
        dialogRoot.setEffect(Shadows.WINDOW_SHADOW);

        MyLabel label = new MyLabel("请选择一个单词书：");

        ComboBox<String> wordBookComboBox = new ComboBox<>();
        List<String> wordBooks = storage.listWordBooks();
        wordBooks.forEach(wordBookComboBox.getItems()::add);

        MyLabel learningQuotaLabel = new MyLabel("请输入每日学习量(最大为20)：");
        MyTextField learningQuotaField = new MyTextField();
        learningQuotaField.setMaxSize(100,20);
        MyLabel reviewQuotaLabel = new MyLabel("请输入每日复习量(最大为20)：");
        MyTextField reviewQuotaField = new MyTextField();
        reviewQuotaField.setMaxSize(100,20);

        MyButton submitButton = new MyButton("提交");

        submitButton.setOnAction(e -> {
            String selectedWordBook = wordBookComboBox.getValue()==null ? wordBooks.get(0) : wordBookComboBox.getValue();
            int dailyLearningQuota = learningQuotaField.getText().isEmpty() ? UserConfig.getDefaultDailyLearningQuota() : Integer.parseInt(learningQuotaField.getText());
            int dailyReviewQuota = reviewQuotaField.getText().isEmpty() ? UserConfig.getDefaultDailyReviewQuota() : Integer.parseInt(reviewQuotaField.getText());

            UserConfig newConfig = new UserConfig(selectedWordBook, dailyLearningQuota, dailyReviewQuota);
            storage.saveUserConfig(username, newConfig);
            isOver.set(true);
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().add(submitButton);
        buttonBox.setAlignment(Pos.CENTER);

        dialogRoot.getChildren().addAll(label, wordBookComboBox, learningQuotaLabel, learningQuotaField, reviewQuotaLabel, reviewQuotaField, buttonBox);
        dialogRoot.setAlignment(Pos.CENTER);

        this.setCenter(dialogRoot);
    }
    public BooleanProperty isOverProperty()
    {
        return isOver;
    }
}
