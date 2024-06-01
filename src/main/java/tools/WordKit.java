package tools;

import ymc.basicelements.Word;

/**
 * WordKit工具类，提供操作单词发音的功能。
 * 本类不鼓励实例化，所有方法均为静态方法。
 */
public class WordKit {
    // 私有构造方法，防止实例化
    private WordKit(){}

    /**
     * 播放指定单词的发音。
     * @param word 要播放发音的单词对象。
     * @param isUSFirst 指定优先播放美式发音还是英式发音。true表示优先播放美式发音。
     */
    public static void playOneSpeech(Word word, boolean isUSFirst){
        // 当优先美式且美式发音存在时，播放美式发音
        if (isUSFirst && !word.getUSSpeech().equals("[not found]")){
            word.playUSSpeech();
        }
        // 当优先美式但美式发音不存在时，播放英式发音
        if (isUSFirst && word.getUSSpeech().equals("[not found]")){
            word.playUKSpeech();
        }
        // 当不优先美式且英式发音存在时，播放英式发音
        if (!isUSFirst && !word.getUKSpeech().equals("[not found]")){
            word.playUKSpeech();
        }
        // 当不优先美式且英式发音不存在时，播放美式发音
        if (!isUSFirst && word.getUKSpeech().equals("[not found]")){
            word.playUSSpeech();
        }
    }
}

