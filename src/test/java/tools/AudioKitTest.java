package tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudioKitTest {
    String urlString = "https://dict.youdao.com/dictvoice?audio=plastic&type=1";
    String savePath = "audio\\test.mp3";

    @Test
    void downloadAndSaveMP3() {
        //AudioKit.downloadAndSaveMP3(urlString, savePath);
    }

    @Test
    void playMP3fromURL() {
        //AudioKit.playMP3fromURL(urlString);
    }

    @Test
    void playMP3fromFile() {
        //AudioKit.playMP3fromFile(savePath);
    }

    @Test
    void audioTest() {
        AudioKit.playMP3fromURL(urlString);
    }
}