package ymc.translator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Translator {

    public static String translate(int mode, String query) {
        try {
            return translate(query, mode);
        } catch (Exception e) {
            e.printStackTrace();
            return "翻译失败";
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

