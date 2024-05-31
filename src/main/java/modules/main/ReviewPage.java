package modules.main;

import effects.MyVbox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import settings.GlobalSetting;
import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.basicelements.WordBook;
import ymc.config.UserConfig;

import java.util.List;

public class ReviewPage extends BorderPane {
    boolean isFinished;
    GlobalSetting setting;
    int currentIndex = 0;
    public ReviewPage(String username, WordBook wordBook, UserConfig config, UserProgress progress, List<Word> wordsToReview, GlobalSetting globalSetting)
    {
        setting = globalSetting;
        List<String> reviewNames = wordsToReview.stream().map(Word::getEnglish).toList();
        NavigationPane wordsNavigationPane = new NavigationPane(120,600,reviewNames,false);
        setRight(wordsNavigationPane);

        MyVbox centerVbox = new MyVbox();
        wordsNavigationPane.getSelectedProperty().addListener((observable, oldValue, newValue) -> {
            currentIndex = newValue.intValue();
            Word word = wordsToReview.get(currentIndex);
            System.out.println("Selected word: " + word.getEnglish());
            ProblemBox problemBox = new ProblemBox(wordBook, word, progress, username, setting);

            // 添加对 isValidReview 属性的监听
            problemBox.getIsValidReview().addListener((observable1, oldValue1, newValue1) -> {
                if (newValue1) {
                    System.out.println("The review is now valid.");
                    // 这里执行当 isValidReview 变为 true 时的操作
                    InfoBox infoBox = new InfoBox(word);
                    centerVbox.getChildren().clear();
                    centerVbox.getChildren().add(infoBox);
                }
            });

            centerVbox.getChildren().clear();
            centerVbox.getChildren().add(problemBox);
            setCenter(centerVbox);
        });


        String text;
//        if (wordsToReview.isEmpty()){
//            text = "今日任务已完成，积少成多别贪心！";
//        } else {
//            // 弹出学习单词窗口
//            System.out.println("今日复习单词：");
//            for (Word word : wordsToReview) {
//                System.out.println("Selected word is "+word.getEnglish());
//            }
//        }

        if (isFinished) {
            text = "恭喜你完成今日学习任务！";
        } else {
            text = "坚持就是胜利，休息之后要回来继续学哦！";
        }
    }
}
