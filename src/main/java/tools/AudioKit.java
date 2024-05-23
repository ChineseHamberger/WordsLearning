package tools;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class AudioKit {


    public static void downloadAndSaveMP3(String urlString, String savePath) {
        try {
            // 创建URL对象
            URL url = new URL(urlString);

            // 打开连接
            InputStream in = url.openStream();

            // 创建文件输出流指向本地文件
            FileOutputStream fileOutputStream = new FileOutputStream(savePath);

            // 使用BufferedInputStream和BufferedOutputStream提高读写效率
            BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            // 使用Channels转移数据，这通常比直接读写更快
            ReadableByteChannel rbc = Channels.newChannel(bufferedInputStream);
            fileOutputStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

            // 关闭资源
            bufferedOutputStream.close();
            fileOutputStream.close();
            bufferedInputStream.close();
            in.close();
            System.out.println("MP3文件下载完成并保存至：" + savePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("下载文件时发生错误：" + e.getMessage());
        }
    }
    public static void playMP3fromURL(String mp3Url) {
        try {
            URL url = new URL(mp3Url);
            InputStream inputStream = url.openStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            Player player = new Player(bufferedInputStream);
            player.play();
        }
        catch (IOException | JavaLayerException e) {
            System.err.println("Error playing MP3: "+e.getMessage());
        }

    }
    public static void playMP3fromFile(String mp3FilePath) {
        try {

            FileInputStream inputStream = new FileInputStream(mp3FilePath);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            Player player = new Player(bufferedInputStream);
            player.play();
        } catch (IOException | JavaLayerException e) {
            System.err.println("Error playing MP3: " + e.getMessage());
        }
    }

    public static void AudioTest() {
    }
}
