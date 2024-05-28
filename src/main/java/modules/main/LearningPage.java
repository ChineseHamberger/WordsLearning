package modules.main;

import javafx.scene.layout.BorderPane;
import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.config.UserConfig;
import java.util.ArrayList;
import java.util.List;

public class LearningPage extends BorderPane {
    boolean isFinished = false;
    public LearningPage(UserConfig config, UserProgress progress, List<Word> wordsToLearn)
    {
        List<String> learnNames = new ArrayList<>();
        for (Word word : wordsToLearn) {
            learnNames.add(word.getEnglish());
        }
        NavigationPane wordsNavigationPane = new NavigationPane(120,600,learnNames,false);
        setRight(wordsNavigationPane);

        String text;
        if (wordsToLearn.isEmpty()){
            text = "今日任务已完成，积少成多别贪心！";
        } else {
            // 弹出学习单词窗口
            System.out.println("今日学习单词：");
            for (Word word : wordsToLearn) {
                System.out.println(word.getEnglish());
            }
        }

        if (isFinished) {
            text = "恭喜你完成今日学习任务！";
        } else {
            text = "坚持就是胜利，休息之后要回来继续学哦！";
        }
    }
}

