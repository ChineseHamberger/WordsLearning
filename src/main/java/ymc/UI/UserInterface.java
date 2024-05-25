package ymc.UI;

import ymc.LocalStorage.LocalStorage;
import ymc.algo.WordSelectionAlgorithm;
import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.basicelements.WordBook;
import ymc.basicelements.WordBookLoader;
import ymc.init.ArticleFetcher;
import ymc.config.UserConfig;
import ymc.translator.translator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javax.swing.text.*;
import javax.swing.text.html.*;

public class UserInterface {
    private LocalStorage storage = new LocalStorage();
    private ArticleProcessor articleProcessor;
    private ArticleFetcher articleFetcher = new ArticleFetcher();

    private String username = "user";

    public void start() {
        SwingUtilities.invokeLater(() -> {

            // 加载配置和进度
            UserConfig config = storage.loadUserConfig(username);
            UserProgress progress = storage.loadUserProgress(username);

            // 如果没有配置，则初始化
            if (config == null) {
                showInitialSetupDialog();
            } else {
                // 如果没有进度，则初始化
                if (progress == null) {
                    progress = new UserProgress();
                }

                // 获取用户选择的单词书
                WordBook wordBook = storage.getWordBook(config.getSelectedWordBook());
                articleProcessor = new ArticleProcessor(wordBook);

                showMainMenu(config, progress);
            }
        });
    }

    private void showInitialSetupDialog() {
        JFrame frame = new JFrame("初始化设置");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(0, 1));

        JLabel label = new JLabel("请选择一个单词书：");
        panel.add(label);

        JComboBox<String> wordBookComboBox = new JComboBox<>();
        List<String> wordBooks = storage.listWordBooks();
        for (String wordBook : wordBooks) {
            wordBookComboBox.addItem(wordBook);
        }
        panel.add(wordBookComboBox);

        JLabel learningQuotaLabel = new JLabel("请输入每日学习量：");
        panel.add(learningQuotaLabel);

        JTextField learningQuotaField = new JTextField();
        panel.add(learningQuotaField);

        JLabel reviewQuotaLabel = new JLabel("请输入每日复习量：");
        panel.add(reviewQuotaLabel);

        JTextField reviewQuotaField = new JTextField();
        panel.add(reviewQuotaField);

        JButton submitButton = new JButton("提交");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selectedWordBook = (String) wordBookComboBox.getSelectedItem();
                int dailyLearningQuota = learningQuotaField.getText().isEmpty() ? UserConfig.getDefaultDailyLearningQuota() :Integer.parseInt(learningQuotaField.getText());
                int dailyReviewQuota = reviewQuotaField.getText().isEmpty() ? UserConfig.getDefaultDailyReviewQuota() :Integer.parseInt(reviewQuotaField.getText());

                UserConfig config = new UserConfig(selectedWordBook, dailyLearningQuota, dailyReviewQuota);
                storage.saveUserConfig(config,username);

                WordBook wordBook = storage.getWordBook(config.getSelectedWordBook());
                articleProcessor = new ArticleProcessor(wordBook);

                frame.dispose();
                showMainMenu(config, new UserProgress());
            }
        });
        panel.add(submitButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void showMainMenu(UserConfig config, UserProgress progress) {
        JFrame frame = new JFrame("主菜单");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(0, 1));

        JButton startLearningButton = new JButton("开始学习");
        startLearningButton.addActionListener(e -> startLearning(config, progress));
        panel.add(startLearningButton);

        JButton updateConfigButton = new JButton("修改配置");
        updateConfigButton.addActionListener(e -> showUpdateConfigDialog(config));
        panel.add(updateConfigButton);

        JButton readArticlesButton = new JButton("阅读文章");
        readArticlesButton.addActionListener(e -> readArticles());
        panel.add(readArticlesButton);

        JButton translateButton = new JButton("查询单词");
        translateButton.addActionListener(e -> queryWord());
        panel.add(translateButton);


        JButton exitButton = new JButton("退出");
        exitButton.addActionListener(e -> {
            storage.saveUserProgress(progress,username);
            frame.dispose();
            System.exit(0);
        });
        panel.add(exitButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void showUpdateConfigDialog(UserConfig config) {
        JFrame frame = new JFrame("修改配置");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(0, 1));

        JLabel label = new JLabel("请选择一个单词书：");
        panel.add(label);

        JComboBox<String> wordBookComboBox = new JComboBox<>();
        List<String> wordBooks = storage.listWordBooks();
        for (String wordBook : wordBooks) {
            wordBookComboBox.addItem(wordBook);
        }
        wordBookComboBox.setSelectedItem(config.getSelectedWordBook());
        panel.add(wordBookComboBox);

        JLabel learningQuotaLabel = new JLabel("请输入每日学习量：");
        panel.add(learningQuotaLabel);

        JTextField learningQuotaField = new JTextField(String.valueOf(config.getDailyLearningQuota()));
        panel.add(learningQuotaField);

        JLabel reviewQuotaLabel = new JLabel("请输入每日复习量：");
        panel.add(reviewQuotaLabel);

        JTextField reviewQuotaField = new JTextField(String.valueOf(config.getDailyReviewQuota()));
        panel.add(reviewQuotaField);

        JButton submitButton = new JButton("提交");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedWordBook = (String) wordBookComboBox.getSelectedItem();
                int dailyLearningQuota = Integer.parseInt(learningQuotaField.getText());
                int dailyReviewQuota = Integer.parseInt(reviewQuotaField.getText());

                config.setSelectedWordBook(selectedWordBook);
                config.setDailyLearningQuota(dailyLearningQuota);
                config.setDailyReviewQuota(dailyReviewQuota);
                storage.saveUserConfig(config,username);

                WordBook wordBook = storage.getWordBook(config.getSelectedWordBook());
                articleProcessor = new ArticleProcessor(wordBook);

                frame.dispose();
            }
        });
        panel.add(submitButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void startLearning(UserConfig config, UserProgress progress) {
        JFrame frame = new JFrame("学习模式");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(0, 1));

        // 加载选定的单词书
        String selectedWordBook = config.getSelectedWordBook();
        WordBook wordBook = WordBookLoader.loadWordBook(selectedWordBook);

        // 获取今日需要学习和复习的单词
        List<Word> wordsToLearn = WordSelectionAlgorithm.getWordsForLearning(wordBook,progress,config);
        List<Word> wordsToReview = WordSelectionAlgorithm.getWordsForReview(wordBook,progress,config);

        // 弹出学习单词窗口
        for (Word word : wordsToLearn) {
            showWordDialog(word, progress, selectedWordBook, true);
        }

        // 弹出复习单词窗口
        for (Word word : wordsToReview) {
            showWordDialog(word, progress, selectedWordBook, false);
        }

        JLabel finishLabel = new JLabel("恭喜你完成今日学习任务");
        panel.add(finishLabel);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void showWordDialog(Word word, UserProgress progress, String selectedWordBook, boolean isLearning) {
        int option = JOptionPane.showConfirmDialog(null, "你认识这个单词吗？\n" + word.getEnglish(), "单词测试", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            if (isLearning) {
                progress.learnWord(selectedWordBook, word);
            } else {
                progress.reviewWord(selectedWordBook, word);
            }
            storage.saveUserProgress(progress,username);
            //progress.saveProgress("username"); // 保存进度，假设用户名为"username"
        }

        // 显示单词详细信息
        word.playUSSpeech();
        JOptionPane.showMessageDialog(null, getWordDetailMessage(word), "单词详情", JOptionPane.INFORMATION_MESSAGE);
    }

    private String getWordDetailMessage(Word word) {
        return "单词详细信息：\n英文: " + word.info();
    }



    private void readArticles() {
        articleFetcher.fetchArticles();  // 生成 articles 文件夹和文章文件
        File folder = new File("articles");
        File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".md"));

        if (listOfFiles != null && listOfFiles.length > 0) {
            JFrame frame = new JFrame("选择文章");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(400, 300);

            JPanel panel = new JPanel(new GridLayout(0, 1));


            for (int i = 0; i < listOfFiles.length; i++) {
                JButton articleButton = new JButton(listOfFiles[i].getName());
                File file = listOfFiles[i];
                articleButton.addActionListener(e -> {
                    frame.dispose();
                    displayArticle(file);
                });
                panel.add(articleButton);
            }

            frame.getContentPane().add(panel);
            frame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "没有找到文章。");
        }
    }

    private void displayArticle(File file) {
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            StringBuilder content = new StringBuilder();
            for (String line : lines) {
                content.append(line).append("\n");
            }
            String processedArticle = articleProcessor.processArticle(content.toString());

            SwingUtilities.invokeLater(() -> createAndShowArticleGUI(processedArticle));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "无法读取文章：" + e.getMessage());
        }

    }

    private void createAndShowArticleGUI(String content) {
        JFrame frame = new JFrame("文章阅读");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText(content);
        textPane.setEditable(false);
        textPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int pos = textPane.viewToModel2D(e.getPoint());
                Document doc = textPane.getDocument();
                if (doc instanceof HTMLDocument) {
                    HTMLDocument htmlDoc = (HTMLDocument) doc;
                    Element elem = htmlDoc.getCharacterElement(pos);
                    AttributeSet as = elem.getAttributes();
                    String word = (String) as.getAttribute(HTML.Attribute.HREF);
                    if (word != null) {
                        Word wordDetails = articleProcessor.getWordDetails(word);
                        if (wordDetails != null) {
                            displayWordDetails(wordDetails);
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(textPane);
        frame.getContentPane().add(scrollPane);
        frame.setVisible(true);
    }

    private void displayWordDetails(Word word) {
        JFrame frame = new JFrame("单词详情");
        frame.setSize(400, 300);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(word.info().toString());

        frame.getContentPane().add(new JScrollPane(textArea));
        frame.setVisible(true);

    }

    private void queryWord() {
        // 创建对话框
        JDialog dialog = new JDialog((Frame) null, "查询单词", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(4, 1));

        // 翻译模式选择
        JLabel modeLabel = new JLabel("选择翻译模式:");
        dialog.add(modeLabel);

        String[] modes = { "中译英", "英译中" };
        JComboBox<String> modeComboBox = new JComboBox<>(modes);
        dialog.add(modeComboBox);

        // 输入单词区域
        JLabel queryLabel = new JLabel("输入单词:");
        dialog.add(queryLabel);

        JTextField queryField = new JTextField();
        dialog.add(queryField);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        JButton translateButton = new JButton("翻译");
        buttonPanel.add(translateButton);

        JButton cancelButton = new JButton("取消");
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel);

        // 翻译按钮的动作监听器
        translateButton.addActionListener(e -> {
            int mode = modeComboBox.getSelectedIndex() + 1; // JComboBox索引从0开始，translate方法的mode从1开始
            String query = queryField.getText().trim();

            if (!query.isEmpty()) {
                String result = translator.translate(mode, query);
                JOptionPane.showMessageDialog(dialog, "翻译结果: " + result, "翻译结果", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialog, "请输入一个单词。", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 取消按钮的动作监听器
        cancelButton.addActionListener(e -> dialog.dispose());

        // 显示对话框
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

}
