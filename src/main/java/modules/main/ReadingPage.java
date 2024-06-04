package modules.main;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
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
    private ArticleProcessor articleProcessor;
    private CheckBox boldCheckBox;

    public ReadingPage(ArticleProcessor articleProcessor) {

        ArticleFetcher.fetchArticles();

        this.articleProcessor = articleProcessor;

        List<File> articles = loadArticles();

        if (articles.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No articles found.");
            alert.showAndWait();
            return;
        }

        VBox articleButtons = new VBox(10);
        articleButtons.setAlignment(Pos.CENTER);

        for (File article : articles) {
            Button articleButton = new Button(article.getName());
            articleButton.setOnAction(e -> displayArticle(article));
            articleButtons.getChildren().add(articleButton);
        }

        boldCheckBox = new CheckBox("加粗正在学习的单词");
        boldCheckBox.setSelected(false);

        ScrollPane scrollPane = new ScrollPane(articleButtons);
        scrollPane.setFitToWidth(true);
        VBox vbox = new VBox(scrollPane, boldCheckBox);
        setCenter(vbox);
    }

    private List<File> loadArticles() {
        List<File> articles = new ArrayList<>();
        try {
            Files.list(Paths.get("articles"))
                    .filter(path -> path.toString().toLowerCase().endsWith(".md"))
                    .forEach(path -> articles.add(path.toFile()));
        } catch (IOException e) {
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

            setCenter(webView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
