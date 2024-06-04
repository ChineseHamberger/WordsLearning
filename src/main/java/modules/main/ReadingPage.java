package modules.main;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import ymc.UI.ArticleProcessor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadingPage extends BorderPane {
    private ArticleProcessor articleProcessor;

    public ReadingPage(ArticleProcessor articleProcessor) {
        this.articleProcessor = articleProcessor;

        // Load articles from the disk
        List<File> articles = loadArticles();

        if (articles.isEmpty()) {
            // If no articles found, show an alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No articles found.");
            alert.showAndWait();
            return;
        }

        VBox articleButtons = new VBox(10);
        articleButtons.setAlignment(Pos.CENTER);

        // Create buttons for each article
        for (File article : articles) {
            Button articleButton = new Button(article.getName());
            articleButton.setOnAction(e -> displayArticle(article));
            articleButtons.getChildren().add(articleButton);
        }

        ScrollPane scrollPane = new ScrollPane(articleButtons);
        scrollPane.setFitToWidth(true);
        setCenter(scrollPane);
    }

    private List<File> loadArticles() {
        List<File> articles = new ArrayList<>();
        try {
            // Assuming the articles are stored in a folder named "articles"
            Files.list(Paths.get("articles"))
                    .filter(path -> path.toString().toLowerCase().endsWith(".md"))
                    .forEach(path -> articles.add(path.toFile()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }

    private void displayArticle(File articleFile) {
        try {
            String content = new String(Files.readAllBytes(articleFile.toPath()));
            String processedContent = articleProcessor.processArticle(content);

            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            webEngine.loadContent(processedContent);

            setCenter(webView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
