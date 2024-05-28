package modules.main;

import javafx.scene.layout.Pane;
import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.config.UserConfig;

import java.util.List;

public class ReviewPage extends Pane {
    boolean isFinished;
    public ReviewPage(UserConfig config, UserProgress progress, List<Word> wordsToReview)
    {
        String text;
        if (wordsToReview.isEmpty()){
            text = "今日任务已完成，积少成多别贪心！";
        } else {
            // 弹出学习单词窗口
            System.out.println("今日复习单词：");
            for (Word word : wordsToReview) {
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
