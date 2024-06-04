package modules.main;

import effects.MyButton;
import effects.MyVbox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import tools.WordKit;
import ymc.LocalStorage.LocalStorage;
import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.basicelements.WordBook;
import ymc.config.UserConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LearningPage extends BorderPane {
    boolean isFinished = false;
    Map<Word, Boolean> wordsPassed = new HashMap<>();
    List<InfoBox> infoBoxes = new ArrayList<>();
    int currentIndex = 0;
    public LearningPage(String username,WordBook wordBook, UserConfig config, UserProgress progress, List<Word> wordsToLearn,double height)
    {
        LocalStorage storage = new LocalStorage();
        List<String> learnNames = new ArrayList<>();
        String bookName = wordBook.getName();
        for (Word word : wordsToLearn) {
            System.out.println(word.getEnglish());
            learnNames.add(word.getEnglish());
            if (progress.isWordLearned(wordBook.getName(), word)) {
                wordsPassed.put(word, true);
            } else {
                wordsPassed.put(word, false);
            }
            infoBoxes.add(new InfoBox(word));
        }
        MyButton learnButton = new MyButton("添加到复习列表");
        learnButton.setOnAction(e -> {
            Word curWord = wordsToLearn.get(currentIndex);
            if (!wordsPassed.get(curWord)){
                wordsPassed.put(curWord, true);
                progress.learnWord(bookName, wordsToLearn.get(currentIndex));
                System.out.println("Learned word: " + wordsToLearn.get(currentIndex).getEnglish());
                storage.saveUserProgress(username, progress, bookName);
            }
        });


        NavigationPane wordsNavigationPane = new NavigationPane(120,height,learnNames,false);
        setRight(wordsNavigationPane);

        MyVbox centerVbox = new MyVbox();
        centerVbox.getChildren().add(learnButton);

        wordsNavigationPane.getSelectedProperty().addListener((observable, oldValue, newValue) -> {
            currentIndex = newValue.intValue();
            Word word = wordsToLearn.get(currentIndex);
            System.out.println("Selected word: " + word.getEnglish());
            WordKit.playOneSpeech(word,true);
            centerVbox.getChildren().clear();
            centerVbox.getChildren().add(infoBoxes.get(currentIndex));
            // 检查当前单词是否已被学习过
            if (!wordsPassed.getOrDefault(word, false)) {
                // 如果单词已被学习，隐藏learnButton
                centerVbox.getChildren().add(learnButton);
            }
            setCenter(centerVbox);
        });



        String text;
        if (wordsToLearn.isEmpty()){
            text = "今日任务已完成，积少成多别贪心！";
        } else {
            // 弹出学习单词窗口
            System.out.println("今日学习单词：");
        }

        if (isFinished) {
            text = "恭喜你完成今日学习任务！";
        } else {
            text = "坚持就是胜利，休息之后要回来继续学哦！";
        }
    }
}

