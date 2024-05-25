package ymc.algo;

import ymc.basicelements.ReviewData;
import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.basicelements.WordBook;
import ymc.config.UserConfig;

import java.util.*;

import java.time.LocalDateTime;
import java.time.Duration;

public class WordSelectionAlgorithm {

    //根据艾宾浩斯记忆曲线制定：
    //记忆周期：
    //1． 第一个记忆周期：5分钟
    //2． 第二个记忆周期：30分钟
    //3． 第三个记忆周期：12小时
    //4． 第四个记忆周期：1天
    //5． 第五个记忆周期：2天
    //6． 第六个记忆周期：4天
    //7． 第七个记忆周期：7天
    //8． 第八个记忆周期：15天
    private WordSelectionAlgorithm() {};

    //x=reviewCount, y=weight
    //x=0, y=1
    //x=MaxReviewCount, y=1/pivot
    //根据y=exp(-ax)拟合
    public static double weightOf(ReviewData reviewData){
        int pivot = 100;
        double a = reviewData.getMaxReviewCount() * Math.log(pivot);
        double weight = Math.exp(-a * reviewData.getReviewCount());
        Duration duration = Duration.between(reviewData.getFirstLearnTime(), LocalDateTime.now());
        int days = (int) (duration.toDays() + 1);
        //1,2,4,7,15天 这几个关键节点增加权重
        if (days==1||days==2||days==4||days==7||days==15){
            weight *= 10;
        }
        return weight;
    }

    public static List<Word> getWordsForLearning(WordBook wordBook, UserProgress userProgress, UserConfig userConfig) {
        int cnt = 0, dailyLearningQuota = userConfig.getDailyLearningQuota();
        List<Word> allWords = wordBook.getWords();
        List<Word> wordsForLearning = new ArrayList<Word>();
        for (Word word : allWords){
            if (cnt >= dailyLearningQuota) {
                break;
            }
            if (!userProgress.isWordLearned(wordBook.getName(), word)) {
                wordsForLearning.add(word);
            }
        }
        return wordsForLearning;
    }

    public static List<Word> getWordsForReview(WordBook wordBook, UserProgress progress, UserConfig config) {
        String bookName = wordBook.getName();
        double allReviewWeight = 0;
        int dailyReviewQuota = config.getDailyReviewQuota();

        Map<Word,ReviewData> reviewCounts = progress.getReviewCounts(bookName);
        Map<Word,Double> weights = new HashMap<>();

        System.out.println("<Begin>" + bookName);
        System.out.println("There are " + reviewCounts.size() + " words to review");

        // 计算总权重并为每个单词分配权重
        for (Word learnedWord : reviewCounts.keySet()) {
            ReviewData reviewData = reviewCounts.get(learnedWord);
            System.out.println("Word: " + learnedWord.getEnglish() + ", ReviewCount: " + reviewData.getReviewCount());
            double reviewWeight = weightOf(reviewData);
            System.out.println("ReviewWeight: " + reviewWeight);
            // 权重可以是直接的reviewCount，也可以根据需求调整为其他公式，比如reviewCount的平方等
            allReviewWeight += reviewWeight;
            weights.put(learnedWord, (double) reviewWeight);
        }

        System.out.println("AllReviewWeight: " + allReviewWeight);

        // 如果没有单词需要复习，直接返回空列表
        if (allReviewWeight == 0 || dailyReviewQuota <= 0) {
            return Collections.emptyList();
        }

        // 根据权重选择单词
        List<Word> selectedWords = new ArrayList<>();
        Random random = new Random();
        while (selectedWords.size() < dailyReviewQuota && !reviewCounts.isEmpty()) {
            // 生成一个基于总权重的随机数
            double randomWeight = random.nextDouble(allReviewWeight);
            double currentWeight = 0;
            Word chosenWord = null;

            // 遍历单词，根据其权重确定被选中的单词
            for (Word word : reviewCounts.keySet()){
                ReviewData reviewData = reviewCounts.get(word);
                double reviewWeight = weightOf(reviewData);
                currentWeight += reviewWeight;
                if (randomWeight < currentWeight) {
                    chosenWord = word;
                    break;
                }
            }

            // 确保选择了单词且未超过所需数量
            if (chosenWord != null) {
                selectedWords.add(chosenWord);
                // 减去已选单词的权重，防止重复选择
                allReviewWeight -= weights.remove(chosenWord);
            }
        }

        return selectedWords;
    }



}
