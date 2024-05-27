package tools;

import java.util.regex.Pattern;
public class StringKit {
    private StringKit() {}
    // 定义一个正则表达式，匹配不允许出现在文件名中的字符
    private static final Pattern INVALID_FILENAME_CHARS_PATTERN = Pattern.compile("[\\\\/:*?\"<>|\\s]");

    public static String sanitizeForFileName(String username) {
        // 移除不合法的文件名字符
        String sanitizedUsername = INVALID_FILENAME_CHARS_PATTERN.matcher(username).replaceAll("");
        // 去除首尾空白，确保用户名不是纯空白
        sanitizedUsername = sanitizedUsername.trim();
        // 如果处理后用户名变为空，则赋予默认值或其他处理方式
        if (sanitizedUsername.isEmpty()) {
            sanitizedUsername = "defaultUser";
        }
        return sanitizedUsername;
    }
}
