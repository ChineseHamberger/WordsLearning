package modules.main;

import javafx.scene.layout.Pane;
import ymc.LocalStorage.LocalStorage;
import ymc.algo.WordSelectionAlgorithm;
import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.basicelements.WordBook;
import ymc.config.UserConfig;

import java.util.List;
import java.util.Random;

public class LearningPage extends Pane {
    boolean isFinished = false;
    public LearningPage(UserConfig config, UserProgress progress, List<Word> wordsToLearn)
    {

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

