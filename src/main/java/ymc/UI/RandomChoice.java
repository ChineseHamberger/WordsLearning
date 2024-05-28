package ymc.UI;

import ymc.LocalStorage.LocalStorage;
import ymc.basicelements.Sentence;
import ymc.basicelements.UserProgress;
import ymc.basicelements.Word;
import ymc.basicelements.WordBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RandomChoice {
    private final WordBook wordBook;
    private final Word word;
    private final Random random;
    private UserProgress progress;
    private LocalStorage storage;
    private String username;

    private int flag = 0;
    private List<String> options;

    public RandomChoice(WordBook selectedWordBook, Word word, UserProgress progress, String username) {
        this.wordBook = selectedWordBook;
        this.word = word;
        this.random = new Random();
        this.progress = progress;
        this.storage = new LocalStorage();
        this.username = username;
    }

    public int show() {
        // Decide question type: 0 for "English to Chinese", 1 for "Chinese to English", 2 for "Listening", 3 for "Fill in the blank"
        int questionType = random.nextInt(4);
        String question;
        String correctAnswer;
        options = new ArrayList<>();

        switch (questionType) {
            case 0:
                // English to Chinese
                question = "The meaning of " + word.getEnglish() + " is:";
                correctAnswer = word.getTranChinese();
                generateOptions(correctAnswer, true);
                break;
            case 1:
                // Chinese to English
                question = word.getTranChinese() + "的含义是：";
                correctAnswer = word.getEnglish();
                generateOptions(correctAnswer, false);
                break;
            case 2:
                // Listening
                question = "你听到的词的含义是：";
                correctAnswer = word.getTranChinese();
                generateOptions(correctAnswer, true);
                break;
            case 3:
                // Fill in the blank
                if (word.getSentences() != null && !word.getSentences().isEmpty()) {
                    Sentence sentence = word.getSentences().get(random.nextInt(word.getSentences().size()));
                    question = sentence.getEnglish().replaceAll(word.getEnglish(), "__________");
                    correctAnswer = word.getEnglish();
                    generateOptions(correctAnswer, false);
                } else {
                    // If there are no sentences, fallback to English to Chinese
                    question = "The meaning of " + word.getEnglish() + " is:";
                    correctAnswer = word.getTranChinese();
                    generateOptions(correctAnswer, true);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + questionType);
        }

        displayQuestion(questionType, question, correctAnswer);
        return flag;
    }

    private void generateOptions(String correctAnswer, boolean isChinese) {
        options.add(correctAnswer);
        List<Word> words = wordBook.getWords();
        while (options.size() < 4) {
            Word randomWord = words.get(random.nextInt(words.size()));
            String incorrectAnswer = isChinese ? randomWord.getTranChinese() : randomWord.getEnglish();
            if (!options.contains(incorrectAnswer)) {
                options.add(incorrectAnswer);
            }
        }
        java.util.Collections.shuffle(options);
    }

    private void displayQuestion(int questionType, String question, String correctAnswer) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1));

        JLabel questionLabel = new JLabel(question);
        panel.add(questionLabel);

        if (questionType == 2) {
            JButton playSoundButton = new JButton("听音");
            playSoundButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    word.playUSSpeech();
                }
            });
            panel.add(playSoundButton);
        }

        for (String option : options) {
            JButton optionButton = new JButton(option);
            optionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Window window = SwingUtilities.getWindowAncestor(panel);
                    if (Objects.equals(option, correctAnswer)) {
                        flag = 1;
                        JOptionPane.showMessageDialog(panel, "答对了！");
                        progress.reviewWordWithProblem(word);
                        storage.saveUserProgress(username, progress, wordBook.getName());
                    } else {
                        JOptionPane.showMessageDialog(panel, "答错了，正确答案是：" + correctAnswer);
                    }
                    if (window != null) {
                        window.dispose();
                    }
                }
            });
            panel.add(optionButton);
        }

        JButton restButton = new JButton("休息一会儿");
        restButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = 2;
                Window window = SwingUtilities.getWindowAncestor(panel);
                if (window != null) {
                    window.dispose();
                }
            }
        });
        panel.add(restButton);

        JOptionPane.showMessageDialog(null, panel, "Review Question", JOptionPane.PLAIN_MESSAGE);
    }
}
