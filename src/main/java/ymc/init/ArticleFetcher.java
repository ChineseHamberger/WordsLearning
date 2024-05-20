package ymc.init;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ArticleFetcher {

    private static ConcurrentLinkedQueue<String> articleLinks = new ConcurrentLinkedQueue<>();

    public static void fetchArticles() {
        String url = "https://www.readersdigest.co.uk/";
        try {
            // 使用Jsoup连接到指定的URL
            Document doc = Jsoup.connect(url).get();

            // 获取所有带有href属性的<a>标签
            Elements allLinks = doc.select("a[href]");

            // 遍历每个链接
            for (Element link : allLinks) {
                String href = link.attr("href");

                // 检查链接是否符合你的条件
                if (countOccurrences(href, '-') >= 3) {
                    String articleUrl = link.absUrl("href");
//                    System.out.println("发现链接：" + articleUrl);
                    articleLinks.add(articleUrl);
                }
            }

            // 打印所有收集到的链接
//            System.out.println("收集到的链接数：" + articleLinks.size());

            // 启动文章解析爬虫
            ArticleParser parser = new ArticleParser(articleLinks);
            parser.startParsing();

        } catch (UnknownHostException e) {
            System.out.println("无法连接到网络，请检查您的网络连接。");
        } catch (IOException e) {
            System.out.println("发生IO异常：" + e.getMessage());
            e.printStackTrace();
        }
    }

    // 计算字符串中某个字符出现的次数
    private static int countOccurrences(String str, char ch) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == ch) {
                count++;
            }
        }
        return count;
    }
}

