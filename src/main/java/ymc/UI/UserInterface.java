package ymc.UI;

import ymc.LocalStorage.LocalStorage;
import ymc.algo.WordSelectionAlgorithm;
import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.basicelements.WordBook;
import ymc.config.UserConfig;

import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;
import java.util.List;


public class UserInterface {
    private Scanner scanner = new Scanner(System.in);
    private LocalStorage storage = new LocalStorage();
    private WordSelectionAlgorithm algorithm = new WordSelectionAlgorithm();

    public LocalStorage getStorage() {
        return storage;
    }

    public void start() {
        System.out.println("欢迎使用背单词应用！");

        // 加载配置和进度
        UserConfig config = storage.loadUserConfig();
        UserProgress progress = storage.loadUserProgress();

        // 如果没有配置，则初始化
        if (config == null) {
            System.out.println("首次使用，请设置您的配置：");
            config = initialSetup();
            storage.saveUserConfig(config);
        }

        // 如果没有进度，则初始化
        if (progress == null) {
            progress = new UserProgress();
        }

        // 主循环
        while (true) {
            System.out.println("请选择操作：1. 开始学习 2. 修改配置 3. 退出");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                startLearning(config, progress);
            } else if (choice == 2) {
                config = updateConfig();
                storage.saveUserConfig(config);
            } else if (choice == 3) {
                storage.saveUserProgress(progress);
                System.out.println("退出程序，再见！");
                break;
            } else {
                System.out.println("无效选择，请重新输入。");
            }
        }
    }

    private UserConfig initialSetup() {
        System.out.print("请输入要学习的单词书名称：");
        String wordBookName = scanner.nextLine();
        System.out.print("请输入每日学习量：");
        int dailyLearningQuota = Integer.parseInt(scanner.nextLine());
        System.out.print("请输入每日复习量：");
        int dailyReviewQuota = Integer.parseInt(scanner.nextLine());
        return new UserConfig(wordBookName, dailyLearningQuota, dailyReviewQuota);
    }

    private UserConfig updateConfig() {
        System.out.print("请输入新的单词书名称：");
        String wordBookName = scanner.nextLine();
        System.out.print("请输入新的每日学习量：");
        int dailyLearningQuota = Integer.parseInt(scanner.nextLine());
        System.out.print("请输入新的每日复习量：");
        int dailyReviewQuota = Integer.parseInt(scanner.nextLine());
        return new UserConfig(wordBookName, dailyLearningQuota, dailyReviewQuota);
    }

    private void startLearning(UserConfig config, UserProgress progress) {
        WordBook wordBook = storage.getWordBook(config.getSelectedWordBook());
        if (wordBook == null) {
            System.out.println("单词书不存在，请先配置正确的单词书。");
            return;
        }

        List<Word> wordsForToday = algorithm.getWordsForToday(progress, config, wordBook);
        for (Word word : wordsForToday) {
            displayWord(word);
            boolean known = getUserResponse();
            updateUserProgress(word.getEnglish(), known, progress);
        }
    }

    private void displayWord(Word word) {
        System.out.println("单词：" + word.getEnglish());
        System.out.println("释义：" + word.getDefinition());
        System.out.println("例句：" + word.getExampleSentence());
    }

    private boolean getUserResponse() {
        System.out.print("你认识这个单词吗？(y/n): ");
        String response = scanner.nextLine();
        return response.equalsIgnoreCase("y");
    }

    private void updateUserProgress(String word, boolean known, UserProgress progress) {
        Map<String, Integer> familiarity = progress.getWordFamiliarity();
        if (known) {
            familiarity.put(word, familiarity.getOrDefault(word, 0) + 1);
        } else {
            familiarity.put(word, familiarity.getOrDefault(word, 0) - 1);
        }
        progress.getLastReviewed().put(word, LocalDate.now());
    }
}
