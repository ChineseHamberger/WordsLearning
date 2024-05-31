package modules.main;

import effects.MyButton;
import effects.MyScrollPane;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import tools.AudioKit;
import ymc.basicelements.*;

import effects.MyScrollPane;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import ymc.basicelements.*;

public class InfoBox extends MyScrollPane {
    public InfoBox(Word word) {
        VBox contentBox = new VBox();
        contentBox.setPadding(new Insets(10));
        contentBox.setSpacing(10);

        // 添加单词基本信息
        contentBox.getChildren().addAll(
                createLabelWithText("单词：" + word.getEnglish()),
                createLabelWithText("*音标：" + word.getPhone())
        );

        HBox speechButtonBox = new HBox();
        if (!word.getUSSpeech().equals("[not found]")){
            speechButtonBox.getChildren().add(createButtonWithText("美式发音",word.getUSSpeech()));
        }
        if (!word.getUKSpeech().equals("[not found]")){
            speechButtonBox.getChildren().add(createButtonWithText("英式发音",word.getUKSpeech()));
        }
        contentBox.getChildren().add(speechButtonBox);

        // 添加相关翻译
        for (Tran tran : word.getTrans()) {
            contentBox.getChildren().add(createLabelWithText(tran.info().toString()));
        }

        // 添加关联单词
        for (RelWord rel : word.getRels()) {
            contentBox.getChildren().add(createLabelWithText(rel.info().toString()));
        }

        // 添加同义词
        for (Syno syno : word.getSynos()) {
            contentBox.getChildren().add(createLabelWithText(syno.info().toString()));
        }

        // 添加短语
        for (Phrase phrase : word.getPhrases()) {
            contentBox.getChildren().add(createLabelWithText(phrase.info().toString()));
        }

        // 添加句子
        for (Sentence sentence : word.getSentences()) {
            contentBox.getChildren().add(createLabelWithText(sentence.info().toString()));
        }

        contentBox.getChildren().add(createLabelWithText("*来自词书: " + word.getBookId() + " 序号: " + word.getWordRank()));

        // 添加考试相关
//        for (Exam exam : word.getExams()) {
//            contentBox.getChildren().add(createLabelWithText(exam.info().toString()));
//        }

        // 设置ScrollPane
        setContent(contentBox);
        setFitToWidth(true);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    private Node createButtonWithText(String text, String param) {
        MyButton button = new MyButton(text);
        button.setFont(Font.font("Microsoft YaHei UI", 14));
        // 如果需要响应点击事件，可以添加以下代码：
        button.setOnAction(event -> {
            // 在这里添加点击事件处理逻辑
            AudioKit.playParam(param);
        });
        return button;
    }

    private Node createLabelWithText(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Microsoft YaHei UI", 14));
        return label;
    }
}
