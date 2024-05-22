package ymc.UI;

import ymc.basicelements.Word;
import ymc.basicelements.WordBook;

public class ArticleProcessor {
    private WordBook wordBook;

    public ArticleProcessor(WordBook wordBook) {
        this.wordBook = wordBook;
    }

    public String processArticle(String article) {
        for (Word word : wordBook.getWords()) {
            String regex = "\\b" + word.getEnglish() + "\\b";
            article = article.replaceAll(regex, "<a href='" + word.getEnglish() + "'><b>" + word.getEnglish() + "</b></a>");
        }
        return "<html><body>" + article.replace("\n", "<br>") + "</body></html>";
    }

    public Word getWordDetails(String word) {
        for (Word w : wordBook.getWords()) {
            if (w.getEnglish().equalsIgnoreCase(word)) {
                return w;
            }
        }
        return null;
    }
}
