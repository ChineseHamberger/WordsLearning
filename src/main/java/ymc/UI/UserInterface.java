package ymc.UI;

import ymc.LocalStorage.LocalStorage;
import ymc.algo.WordSelectionAlgorithm;
import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.basicelements.WordBook;
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
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.text.*;
import javax.swing.text.html.*;

public class UserInterface {
    private LocalStorage storage = new LocalStorage();
    private ArticleProcessor articleProcessor;
    private String username = "a";
    public void start() {
        SwingUtilities.invokeLater(() -> {

            // 加载配置和进度
            UserConfig config = storage.loadUserConfig(username);
            UserProgress progress = config != null? storage.loadUserProgress(username, config.getSelectedWordBook()): null;

            // 如果没有配置，则初始化
            if (config == null) {
                showInitialSetupDialog();
            } else {
                // 如果没有进度，则初始化
                if (progress == null) {
                    progress = new UserProgress();
                }

                // 获取用户选择的单词书
                WordBook wordBook = storage.loadWordBook(config.getSelectedWordBook());
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
                storage.saveUserConfig(username, config);

                WordBook wordBook = storage.loadWordBook(config.getSelectedWordBook());
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
            storage.saveUserProgress(username ,progress, config.getSelectedWordBook());
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
                System.out.println("submit");
                String selectedWordBook = (String) wordBookComboBox.getSelectedItem();
                int dailyLearningQuota = Integer.parseInt(learningQuotaField.getText());
                int dailyReviewQuota = Integer.parseInt(reviewQuotaField.getText());

                config.setSelectedWordBook(selectedWordBook);
                config.setDailyLearningQuota(dailyLearningQuota);
                config.setDailyReviewQuota(dailyReviewQuota);
                storage.saveUserConfig(username, config);

                WordBook wordBook = storage.loadWordBook(config.getSelectedWordBook());
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

        boolean finished = true;
        List<Word> wordsToLearn = null;
        List<Word> wordsToReview = null;
        String selectedWordBook = config.getSelectedWordBook();

        if (progress.IsTodaySet()) {
            wordsToLearn = progress.getWordsToLearn();
            wordsToReview = progress.getWordsToReview();
        } else {
            // 加载选定的单词书
            WordBook wordBook = storage.loadWordBook(selectedWordBook);
            wordsToLearn = WordSelectionAlgorithm.getWordsForLearning(wordBook, progress, config);
            wordsToReview = WordSelectionAlgorithm.getWordsForReview(wordBook, progress, config);
            progress.setWordsToLearn(wordsToLearn);
            progress.setWordsToReview(wordsToReview);
            progress.updateLastLearningDate();
        }
        String text;
        if (wordsToLearn.isEmpty() && wordsToReview.isEmpty()){
            text = "今日任务已完成，积少成多别贪心！";
        } else {
            // 弹出学习单词窗口
            ShowWords:
            while (! (wordsToLearn.isEmpty() && wordsToReview.isEmpty())) {
                deleteWord:{
                    Random random = new Random();
                    int randomNumber = random.nextInt(2) + 1;
                    if (randomNumber == 1) {
                        for (Word word : wordsToLearn) {
                            int flag = showWordDialog(frame, word, progress, selectedWordBook, true);
                            if (flag == 2) {
                                finished = false;
                                break ShowWords;
                            } else if (flag == 1) {
                                wordsToLearn.remove(word);
                                break deleteWord;
                            }
                        }
                    } else {
                        // 弹出复习单词窗口
                        for (Word word : wordsToReview) {
                            int flag = showWordDialog(frame, word, progress, selectedWordBook, false);
                            if (flag == 2) {
                                finished = false;
                                break ShowWords;
                            } else if (flag == 1) {
                                wordsToReview.remove(word);
                                break deleteWord;
                            }
                        }
                    }
                }
            }

            if (finished) {
                text = "恭喜你完成今日学习任务！";
            } else {
                text = "坚持就是胜利，休息之后要回来继续学哦！";
            }
        }
        JLabel finishLabel = new JLabel(text);
        panel.add(finishLabel);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private int showWordDialog(JFrame frame, Word word, UserProgress progress, String selectedWordBook, boolean isLearning) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel messageLabel = new JLabel("你认识这个单词吗？\n" + word.getEnglish());
        panel.add(messageLabel, BorderLayout.CENTER);

        AtomicInteger rest = new AtomicInteger(0);
        JPanel buttonPanel = new JPanel();
        JButton yesButton = new JButton("是");
        JButton ignoreButton = new JButton("太简单了");
        JButton noButton = new JButton("否");
        JButton restButton = new JButton("休息一会儿");

        if (isLearning) {
            buttonPanel.add(ignoreButton);
        }
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        buttonPanel.add(restButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JDialog dialog = new JDialog(frame, "单词测试", true);
        dialog.setContentPane(panel);
        dialog.pack();

        ignoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progress.ignoreWord(selectedWordBook, word);
                storage.saveUserProgress(username, progress, selectedWordBook);
                dialog.dispose();
                showWordDetails(word);
                rest.set(1);
            }
        });

        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isLearning) {
                    progress.learnWord(selectedWordBook, word);
                } else {
                    progress.reviewWord(selectedWordBook, word);
                }
                storage.saveUserProgress(username, progress, selectedWordBook);
                dialog.dispose();
                showWordDetails(word);
                rest.set(1);
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                showWordDetails(word);
            }
        });

        restButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                storage.saveUserProgress(username, progress, selectedWordBook);
                rest.set(2);
                dialog.dispose();
            }
        });

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        return rest.get();
    }

    private void showWordDetails(Word word) {
        word.playUSSpeech();
        JOptionPane.showMessageDialog(null, getWordDetailMessage(word), "单词详情", JOptionPane.INFORMATION_MESSAGE);
    }

    private String getWordDetailMessage(Word word) {
        return "单词详细信息：\n英文: " + word.info();
    }

    private void readArticles() {
        // 生成 articles 文件夹和文章文件
        ArticleFetcher.fetchArticles();
        File folder = new File("articles");
        File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".md"));

        if (listOfFiles != null && listOfFiles.length > 0) {
            // 弹出对话框询问用户是否要加粗正在学习的单词
            int choice = JOptionPane.showConfirmDialog(null, "是否加粗正在学习的单词?", "选择", JOptionPane.YES_NO_OPTION);
            boolean boldLearningWords = (choice == JOptionPane.YES_OPTION);
            articleProcessor.setBoldLearningWords(boldLearningWords);

            JFrame frame = new JFrame("选择文章");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(400, 300);

            JPanel panel = new JPanel(new GridLayout(0, 1));

            for (File listOfFile : listOfFiles) {
                JButton articleButton = new JButton(listOfFile.getName());
                articleButton.addActionListener(e -> {
                    frame.dispose();
                    displayArticle(listOfFile);
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

            JTextPane textPane = new JTextPane();
            textPane.setContentType("text/html");
            textPane.setText(processedArticle);
            textPane.setEditable(false);
            textPane.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int pos = textPane.viewToModel2D(e.getPoint());
                    Document doc = textPane.getDocument();
                    if (doc instanceof HTMLDocument htmlDoc) {
                        Element elem = htmlDoc.getCharacterElement(pos);
                        AttributeSet as = elem.getAttributes();
                        String href = (String) as.getAttribute(HTML.Attribute.HREF);
//                        System.out.println(href);
                        String word;
                        if (href != null) {
                            word = href.substring(href.lastIndexOf('/') + 1);
                        } else {
                            try {
                                int startOffset = elem.getStartOffset();
                                int endOffset = elem.getEndOffset();
                                word = doc.getText(startOffset, endOffset - startOffset).trim();
                            } catch (BadLocationException ex) {
                                ex.printStackTrace();
                                return;
                            }
                        }
                        translateAndDisplay(word);
                    }
                }
            });


            JScrollPane scrollPane = new JScrollPane(textPane);

            JFrame frame = new JFrame("文章阅读");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 600);
            frame.getContentPane().add(scrollPane);
            frame.setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "无法读取文章：" + e.getMessage());
        }
    }

    private void translateAndDisplay(String word) {
//        System.out.println("Translating word: " + word);
        String translation = translator.translate(2, word); // 中译英
        JOptionPane.showMessageDialog(null, "单词 \"" + word + "\" 的翻译是：" + translation);
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
                if (doc instanceof HTMLDocument htmlDoc) {
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
