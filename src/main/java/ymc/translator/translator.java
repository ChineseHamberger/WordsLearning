package ymc.translator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class translator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入要翻译的单词: ");
        String query = scanner.nextLine();
        scanner.close();

        try {
            String translation = translate(query);
            System.out.println("翻译结果: " + translation);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("翻译失败");
        }
    }

    private static String translate(String query) throws IOException {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = "http://www.youdao.com/w/eng/" + encodedQuery + "/#keyfrom=dict2.index";

        Document doc = Jsoup.connect(url).get();
        Element translationElement = doc.selectFirst(".trans-container > ul > li");

        if (translationElement != null) {
            return translationElement.text();
        } else {
            return "未找到翻译结果";
        }
    }
}
