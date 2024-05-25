package ymc.UI;

import ymc.basicelements.Word;
import ymc.basicelements.WordBook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArticleProcessor {
    private WordBook wordBook;
    private boolean boldLearningWords;

    public ArticleProcessor(WordBook wordBook) {
        this.wordBook = wordBook;
    }

    public String processArticle(String article) {
        StringBuilder processedArticle = new StringBuilder();

        // 正则表达式匹配文章中的单词，并添加链接和样式
        Matcher matcher = Pattern.compile("\\b\\w+\\b").matcher(article);
        int lastEnd = 0;
        while (matcher.find()) {
            int start = matcher.start();
            if (start > lastEnd) {
                // 添加单词之前的空格
                processedArticle.append(article.substring(lastEnd, start));
            }
            String word = matcher.group();
            // 检查是否需要加粗处理，并且单词存在于词书中
            if (boldLearningWords && wordBook.searchWordInEng(word) != null) {
                processedArticle.append("<b>");
                processedArticle.append("<a href=\"").append(word).append("\" style=\"text-decoration: none; color: inherit;\">").append(word).append("</a>");
                processedArticle.append("</b>");
            } else {
                processedArticle.append("<a href=\"").append(word).append("\" style=\"text-decoration: none; color: inherit;\">").append(word).append("</a>");
            }
            lastEnd = matcher.end();
        }
        // 添加最后一个单词之后的内容
        if (lastEnd < article.length()) {
            processedArticle.append(article.substring(lastEnd));
        }

        return "<html><body>" + processedArticle.toString().replace("\n", "<br>") + "</body></html>";
    }

    public Word getWordDetails(String word) {
        for (Word w : wordBook.getWords()) {
            if (w.getEnglish().equalsIgnoreCase(word)) {
                return w;
            }
        }
        return null;
    }

    public void setBoldLearningWords(boolean boldLearningWords) {
        this.boldLearningWords = boldLearningWords;
    }
}
