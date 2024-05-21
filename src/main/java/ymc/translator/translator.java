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
        System.out.print("请选择模式（1，中译英；2，英译中）: ");
        int mode = Integer.parseInt(scanner.nextLine());
        System.out.print("请输入要翻译的单词: ");
        String query = scanner.nextLine();
        scanner.close();

        try {
            String translation = translate(query, mode);
            System.out.println("翻译结果: " + translation);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("翻译失败");
        }
    }

    private static String translate(String query, int mode) throws IOException {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url;
        if (mode == 1) { // 中译英
            url = "http://www.youdao.com/w/zh-CHS/" + encodedQuery + "/#keyfrom=dict2.index";
        } else { // 英译中
            url = "http://www.youdao.com/w/eng/" + encodedQuery + "/#keyfrom=dict2.index";
        }

        Document doc = Jsoup.connect(url).get();
        Element translationElement;

        if (mode == 1) { // 中译英
            translationElement = doc.selectFirst(".wt-container > div:first-child");
        } else { // 英译中
            translationElement = doc.selectFirst(".trans-container > ul > li");
        }

        if (translationElement != null) {
            return translationElement.text();
        } else {
            return "未找到翻译结果";
        }
    }
}

