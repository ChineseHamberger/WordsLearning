package modules.main;

import effects.MyButton;
import effects.MyVbox;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import settings.GlobalSetting;
import tools.WordKit;
import ymc.basicelements.Sentence;
import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.basicelements.WordBook;
import ymc.LocalStorage.LocalStorage;

import java.util.*;

public class ProblemBox extends MyVbox {

    private final WordBook wordBook;
    private final Word word;
    private final Random random;
    private UserProgress progress;
    private LocalStorage storage;
    private String username;

    private int flag = 0;
    private List<String> options;
    private GlobalSetting setting;

    private BooleanProperty isValidReview = new SimpleBooleanProperty(false);

    public ProblemBox(WordBook selectedWordBook, Word word, UserProgress progress, String username, GlobalSetting globalSetting) {
        this.wordBook = selectedWordBook;
        this.word = word;
        this.random = new Random();
        this.progress = progress;
        this.storage = new LocalStorage();
        this.username = username;
        this.setting = globalSetting;
        initialize();
    }

    private void initialize() {
        setPadding(new Insets(10));
        setSpacing(10);

        int questionType = random.nextInt(4);
        if (questionType ==2 && word.getUKSpeech().equals("[not found]") && word.getUSSpeech().equals("[not found]")){
            questionType = 3;
        }
        String question;
        String correctAnswer;

        switch (questionType) {
            case 0:
                // English to Chinese
                question = "这个单词 " + word.getEnglish() + " 的中文解释是: ";
                correctAnswer = word.getTranChinese();
                generateOptions(correctAnswer, true);
                break;
            case 1:
                // Chinese to English
                question = "哪个选项更接近\""+word.getTranChinese()+"\"的含义: ";
                correctAnswer = word.getEnglish();
                generateOptions(correctAnswer, false);
                break;
            case 2:
                // Listening
                question = "你听到的词的含义是：";
                correctAnswer = word.getTranChinese();
                generateOptions(correctAnswer, true);
                break;
            case 3:
                // Fill in the blank
                if (word.getSentences() != null && !word.getSentences().isEmpty()) {
                    Sentence sentence = word.getSentences().get(random.nextInt(word.getSentences().size()));
                    question = sentence.getEnglish().replaceAll(word.getEnglish(), "__________");
                    correctAnswer = word.getEnglish();
                    generateOptions(correctAnswer, false);
                } else {
                    // If there are no sentences, fallback to English to Chinese
                    question = "这个单词 " + word.getEnglish() + " 的含义是:";
                    correctAnswer = word.getTranChinese();
                    generateOptions(correctAnswer, true);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + questionType);
        }

        Label questionLabel = new Label(question);
        getChildren().add(questionLabel);

        if (questionType == 2) {
            WordKit.playOneSpeech(word,setting.getPlayUSSpeechFirst());
        }

        for (String option : options) {
            MyButton optionButton = new MyButton(option);
            optionButton.setOnAction(e -> handleOptionSelection(option, correctAnswer));
            getChildren().add(optionButton);
        }

//        MyButton restButton = new MyButton("休息一会儿");
//        restButton.setOnAction(e -> {
//            flag = 2;
//            showAlert("错误","回答错误",Alert.AlertType.ERROR);
//        });
//        getChildren().add(restButton);
    }

    private void generateOptions(String correctAnswer, boolean isChinese) {
        options = new ArrayList<>();
        options.add(correctAnswer);
        List<Word> words = wordBook.getWords();
        while (options.size() < 4) {
            Word randomWord = words.get(random.nextInt(words.size()));
            String incorrectAnswer = isChinese ? randomWord.getTranChinese() : randomWord.getEnglish();
            if (!options.contains(incorrectAnswer)) {
                options.add(incorrectAnswer);
            }
        }
        Collections.shuffle(options);
    }

    private void handleOptionSelection(String selectedOption, String correctAnswer) {
        if (Objects.equals(selectedOption, correctAnswer)) {
            flag = 1;
            showAlert("正确","回答正确", Alert.AlertType.CONFIRMATION);
            progress.reviewWord(wordBook.getName(),word);
            storage.saveUserProgress(username, progress, wordBook.getName());
            isValidReview.set(true);
        } else {
            showAlert("错误","回答错误",Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public BooleanProperty getIsValidReview(){
        return isValidReview;
    }

}
