package ymc.UI;

import ymc.LocalStorage.LocalStorage;
import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.basicelements.WordBook;
import ymc.init.ArticleFetcher;
import ymc.config.UserConfig;

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

    public void start() {
        SwingUtilities.invokeLater(() -> {

            // 加载配置和进度
            UserConfig config = storage.loadUserConfig();
            UserProgress progress = storage.loadUserProgress();

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
                int defaultLearningQuota = 10;
                int defaultReviewQuota = 5;

                String selectedWordBook = (String) wordBookComboBox.getSelectedItem();
                int dailyLearningQuota = learningQuotaField.getText().isEmpty() ? defaultLearningQuota :Integer.parseInt(learningQuotaField.getText());
                int dailyReviewQuota = reviewQuotaField.getText().isEmpty() ? defaultReviewQuota :Integer.parseInt(reviewQuotaField.getText());

                UserConfig config = new UserConfig(selectedWordBook, dailyLearningQuota, dailyReviewQuota);
                storage.saveUserConfig(config);

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

        JButton exitButton = new JButton("退出");
        exitButton.addActionListener(e -> {
            storage.saveUserProgress(progress);
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
                storage.saveUserConfig(config);

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

        List<Word> wordsToLearn = articleProcessor.getWordsForLearning(config.getDailyLearningQuota(), progress);
        List<Word> wordsToReview = articleProcessor.getWordsForReview(config.getDailyReviewQuota(), progress);

        JLabel learningLabel = new JLabel("学习新单词：");
        panel.add(learningLabel);

        for (Word word : wordsToLearn) {
            JButton wordButton = new JButton(word.getEnglish());
            wordButton.addActionListener(e -> {
                // 展示单词详细信息
                displayWordDetails(word);
                // 更新学习进度
                progress.learnWord(word);
                storage.saveUserProgress(progress);
            });
            panel.add(wordButton);
        }

        JLabel reviewLabel = new JLabel("复习单词：");
        panel.add(reviewLabel);

        for (Word word : wordsToReview) {
            JButton wordButton = new JButton(word.getEnglish());
            wordButton.addActionListener(e -> {
                // 展示单词详细信息
                displayWordDetails(word);
                // 更新复习进度
                progress.reviewWord(word);
                storage.saveUserProgress(progress);
            });
            panel.add(wordButton);
        }

        frame.getContentPane().add(panel);
        frame.setVisible(true);
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
}
