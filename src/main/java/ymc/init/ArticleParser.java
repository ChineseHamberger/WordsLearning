package ymc.init;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ArticleParser {

    private ConcurrentLinkedQueue<String> articleLinks;
    private final int NUM_THREADS = 5;  // 可以根据需要调整线程数

    public ArticleParser(ConcurrentLinkedQueue<String> articleLinks) {
        this.articleLinks = articleLinks;
    }

    public void startParsing() {
        for (int i = 0; i < NUM_THREADS; i++) {
            Thread parserThread = new Thread(new ParserWorker(articleLinks));
            parserThread.start();
        }
    }

    private static class ParserWorker implements Runnable {
        private ConcurrentLinkedQueue<String> articleLinks;
        private Set<String> seenContent;

        public ParserWorker(ConcurrentLinkedQueue<String> articleLinks) {
            this.articleLinks = articleLinks;
            this.seenContent = new HashSet<>();
        }

        @Override
        public void run() {
            while (!articleLinks.isEmpty()) {
                String url = articleLinks.poll();
                if (url != null) {
                    parseArticle(url);
                }
            }
        }

        private void parseArticle(String url) {
            try {
                Document articleDoc = Jsoup.connect(url).get();
                Element articleElement = articleDoc.selectFirst("article");

                if (articleElement != null) {
                    String title = articleElement.selectFirst("h1").text();  // 只提取h1的文本内容
                    Element contentElement = articleElement.selectFirst("div[class=lg:max-w-4xl max-w-2xl md:mx-auto]");

                    // 移除包含<blockquote>的内容
                    Elements blockquotes = contentElement.select("blockquote");
                    for (Element blockquote : blockquotes) {
                        blockquote.remove();
                    }

                    StringBuilder content = new StringBuilder();
                    content.append("# ").append(title).append("\n\n");

                    extractContent(contentElement, content);

                    // 创建 articles 目录
                    File articlesDir = new File("articles");
                    if (!articlesDir.exists()) {
                        articlesDir.mkdir();
                    }

                    // 将内容写入Markdown文件
                    String fileName = title.replaceAll("[^a-zA-Z0-9\\s]", "").replaceAll("\\s+", "_") + ".md";
                    File file = new File(articlesDir, fileName);
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write(content.toString());
//                        System.out.println("文章已保存为：" + file.getPath());
                    }
                } else {

//                    System.out.println("未找到文章内容：" + url);
                }
            } catch (IOException e) {
//                System.out.println("无法解析文章：" + url);
                e.printStackTrace();
            }
        }

        private void extractContent(Element element, StringBuilder content) {
            // 处理当前元素的文本内容
            processElementText(element, content);

            // 遍历当前元素的子元素
            for (Element child : element.children()) {
                // 递归处理子元素
                extractContent(child, content);
            }
        }

        private void processElementText(Element element, StringBuilder content) {
            String tagName = element.tagName();

            // 处理不同标签
            if (tagName.equals("h2")) {
                // 提取标题
                content.append("## ").append(element.text().trim()).append("\n\n");
            } else if (tagName.equals("div") || tagName.equals("p") || tagName.equals("span")) {
                // 提取段落内容
                String sectionText = element.text().trim();
                if (!sectionText.isEmpty() && !seenContent.contains(sectionText)) {
                    content.append(sectionText).append("\n\n");
                    seenContent.add(sectionText);
                }
            }
        }

    }
}
