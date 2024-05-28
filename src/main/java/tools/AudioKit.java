package tools;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class AudioKit {

    private static final String YOUDAO_API = "https://dict.youdao.com/dictvoice?audio=";
    /**
     * 获取有道API的链接地址。
     *
     * @return 返回一个字符串，代表有道API的链接地址。
     */
    public static String getYoudaoApi(){
        return YOUDAO_API;
    }
    /**
     * 从指定URL下载MP3文件并保存到本地。
     *
     * @param urlString MP3文件的URL地址。
     * @param savePath 保存MP3文件的本地路径。
     */
    public static void downloadAndSaveMP3(String urlString, String savePath) {
        try {
            // 创建URL对象，指定MP3文件的网络地址
            URI uri = new URI(urlString);
            URL url = uri.toURL();

            // 打开连接，获取输入流
            InputStream in = url.openStream();

            // 创建文件输出流，指定保存MP3文件的本地路径
            FileOutputStream fileOutputStream = new FileOutputStream(savePath);

            // 使用BufferedInputStream和BufferedOutputStream提高数据读写效率
            BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            // 使用Channels进行数据传输，通常比直接读写更高效
            ReadableByteChannel rbc = Channels.newChannel(bufferedInputStream);
            fileOutputStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

            // 关闭所有资源，包括输入输出流和通道
            bufferedOutputStream.close();
            fileOutputStream.close();
            bufferedInputStream.close();
            in.close();
            // 下载完成后的提示信息
            System.out.println("MP3文件下载完成并保存至：" + savePath);
        } catch (IOException e) {
            // 捕获并处理IO异常
            e.printStackTrace();
            System.err.println("下载文件时发生错误：" + e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 在新线程中从给定的URL播放MP3文件。
     * @param mp3Url MP3文件的URL地址。
     * 该方法不返回任何值。
     */
    public static void playMP3fromURL(String mp3Url) {
        // 在新线程中启动播放逻辑，以避免阻塞主线程
        new Thread( ()-> {
            try {
                // 创建URL对象并打开输入流
                URI uri = new URI(mp3Url);
                URL url = uri.toURL();
                InputStream inputStream = url.openStream();
                // 使用BufferedInputStream增强输入流读取性能
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                // 实例化Player对象以播放音频
                Player player = new Player(bufferedInputStream);
                // 播放音频
                player.play();
            }
            catch (IOException | JavaLayerException e) {
                // 捕获并处理可能的异常，输出错误信息
                System.err.println("Error playing MP3: "+e.getMessage());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }
    public static void playUSSpeech(String wordName){
        playMP3fromURL(getYoudaoApi()+wordName+"&type=2");
    }
    public static void playUKSpeech(String wordName){
        playMP3fromURL(getYoudaoApi()+wordName+"&type=1");
    }
    /**
     * 从指定文件播放MP3。
     *
     * @param mp3FilePath 指定的MP3文件路径。
     */
    public static void playMP3fromFile(String mp3FilePath) {
        try {
            // 创建FileInputStream以读取MP3文件
            FileInputStream inputStream = new FileInputStream(mp3FilePath);
            // 使用BufferedInputStream提高读取效率
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            // 使用JavaLayer的Player类播放音频
            Player player = new Player(bufferedInputStream);
            player.play(); // 播放音频
        } catch (IOException | JavaLayerException e) {
            // 处理异常，输出错误信息
            System.err.println("Error playing MP3: " + e.getMessage());
        }
    }

}
