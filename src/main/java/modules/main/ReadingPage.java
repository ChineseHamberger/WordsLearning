package modules.main;

import effects.MyButton;
import effects.MyVbox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import ymc.UI.ArticleProcessor;
import ymc.init.ArticleFetcher;
import ymc.translator.Translator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadingPage extends BorderPane {
    private VBox vbox;
    private ArticleProcessor articleProcessor;
    private CheckBox boldCheckBox;
    private final ListView<File> fileList = new ListView<>();
//    public ArticlePage(ArticleProcessor articleProcessor) {
//        this.articleProcessor = articleProcessor;
//
//        readArticles();
//        int a = fileList.getItems().size();
//        System.out.println(a);
//
//
//        MyButton chooseArticleButton = new MyButton("选择其他路径下的文章添加到如上列表");
//        chooseArticleButton.setOnAction(event -> selectAndListArticles());
//
//        VBox vbox = new VBox(10, fileList, chooseArticleButton);
//        vbox.setPadding(new Insets(10));
//
//        getChildren().add(vbox);
//
//    }

    public ReadingPage(ArticleProcessor articleProcessor) {

        this.articleProcessor = articleProcessor;

        if (!new File("articles").exists()) {
            new File("articles").mkdir();
        }



        List<File> articles = loadArticles();
        for (File article : articles){
            fileList.getItems().add(article);
        }
        fileList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                File selectedFile = fileList.getSelectionModel().getSelectedItem();
                if (selectedFile != null) {
                    displayArticle(selectedFile);
                }
            }
        });
        VBox articlesVbox = new VBox(10,fileList);
        articlesVbox.setAlignment(Pos.CENTER);
        articlesVbox.setMaxSize(1000,600);

        if (articles.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No articles found.");
            alert.showAndWait();
            return;
        }

        boldCheckBox = new CheckBox("加粗正在学习的单词");
        boldCheckBox.setSelected(false);
        boldCheckBox.setOnAction(e -> {
            articleProcessor.setBoldLearningWords(boldCheckBox.isSelected());
            System.out.println("boldLearningWords: " + boldCheckBox.isSelected());
        });


        // 使用HBox包裹boldCheckBox，并设置对其方式为底部，减少其占用的垂直空间
        HBox checkBoxContainer = new HBox(boldCheckBox);
        checkBoxContainer.setAlignment(Pos.BOTTOM_LEFT);
        checkBoxContainer.setPadding(new Insets(5, 0, 0, 0)); // 可根据需要调整内边距

        vbox = new VBox(articlesVbox, checkBoxContainer);
        vbox.setPadding(new Insets(0, 0, 20, 0));
        setCenter(vbox);
    }


    private List<File> loadArticles() {

        List<File> articles = new ArrayList<>();
        try {
            Files.list(Paths.get("articles"))
                    .filter(path -> path.toString().toLowerCase().endsWith(".md"))
                    .forEach(path -> articles.add(path.toFile()));
        } catch (IOException e) {
            System.out.println("Error loading articles.");
            e.printStackTrace();
        }
        return articles;
    }

    private void displayArticle(File articleFile) {
        try {
            String content = new String(Files.readAllBytes(articleFile.toPath()));
            articleProcessor.setboldLearningWords(boldCheckBox.isSelected());
            String processedContent = articleProcessor.processArticle(content);

            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            webEngine.loadContent(processedContent);

            webView.setOnMouseClicked(event -> {
                int x = (int) event.getX();
                int y = (int) event.getY();

                String script = String.format(
                        "document.elementFromPoint(%d, %d).innerText.trim();",
                        x, y);
                Object result = webEngine.executeScript(script);

                if (result instanceof String) {
                    String word = (String) result;
                    String translation = Translator.translate(2, word); // 中译英
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("单词翻译");
                    alert.setHeaderText(null);
                    alert.setContentText("单词 \"" + word + "\" 的翻译是：" + translation);
                    alert.showAndWait();
                }
            });

            MyButton backButton = new MyButton("返回上一级");
            backButton.setOnAction(event -> {
                setCenter(vbox);
            });
            
            VBox webViewVbox = new VBox(webView, backButton);
            webViewVbox.setPadding(new Insets(0, 0, 10, 0)); // 添加适当的间距

            setCenter(webViewVbox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
