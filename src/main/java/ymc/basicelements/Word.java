package ymc.basicelements;

import tools.AudioKit;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.time.Duration;
import java.time.LocalDateTime;


public class Word implements Serializable {

    private String bookId;
    private int wordRank;
    private final String english;

    private String tranChinese;
    private String tranEnglish;

    private List<Exam> exams;
    private List<Sentence> sentences;
    private List<Syno> synos;
    private List<Phrase> phrases;
    private List<RelWord> rels;
    private List<Tran> trans;

    private String usphone, ukphone;
    private String ukspeech,usspeech;


    public Word(String bookId, int wordRank, String english, String chinese, String tranEnglish, List<Exam> exams, List<Sentence> sentences, List<Syno> synos, List<Phrase> phrases, List<RelWord> rels, List<Tran> trans, String usphone, String ukphone, String ukspeech, String usspeech){
        this.bookId = bookId;
        this.wordRank = wordRank;
        this.english = english;
        this.tranChinese = chinese;
        this.tranEnglish = tranEnglish;
        this.exams = exams;
        this.sentences = sentences;
        this.synos = synos;
        this.phrases = phrases;
        this.rels = rels;
        this.trans = trans;
        this.usphone = usphone;
        this.ukphone = ukphone;
        this.ukspeech = ukspeech;
        this.usspeech = usspeech;
    }
    public StringBuilder info(){
        StringBuilder info = new StringBuilder();
        info.append("**单词：").append(english).append("\n");
        info.append("*中文释义：").append(tranChinese).append("\n");
        info.append("*英文释义：").append(tranEnglish).append("\n");
        info.append("*音标：").append(getPhone()).append("\n");
        info.append("*来自词书: ").append(bookId).append("序号: ").append(wordRank).append("\n");
        info.append("*发音参数: ").append(ukspeech).append(" ").append(usspeech).append("\n");

        for(Tran tran:trans){
            info.append(tran.info());
        }
        for(RelWord rel:rels){
            info.append(rel.info());
        }
        for(Syno syno:synos){
            info.append(syno.info());
        }

        for (Phrase phrase:phrases){
            info.append(phrase.info());
        }
        for(Sentence sentence:sentences){
            info.append(sentence.info());
        }
        for(Exam exam:exams){
            info.append(exam.info());
        }

        return info;

    }

    public void showInfo(){
        System.out.println(info());
    }

    public String getEnglish() {
        return english;
    }

    public String getTranChinese() {
        return tranChinese;
    }

    public List<Sentence> getSentences() { return sentences; }

    public String getTranEnglish() {
        return tranEnglish;
    }

    public String getBookId() {
        return bookId;
    }

    public int getWordRank() {
        return wordRank;
    }

    public String getPhone(){
        return usphone + " " + ukphone;
    }


    public void playUSSpeech(){
        if(!usspeech.equals("[not found]")){
            String mp3Url = AudioKit.getYoudaoApi()+usspeech;
            //System.out.println(mp3Url);
            AudioKit.playMP3fromURL(mp3Url);
        }
    }

    public void playUKSpeech(){
        if(!ukspeech.equals("[not found]")){
            String mp3Url = AudioKit.getYoudaoApi()+ukspeech;
            //System.out.println(mp3Url);
            AudioKit.playMP3fromURL(mp3Url);
        }
    }

    public String getUSSpeech() {
        return usspeech;
    }
    public String getUKSpeech() {
        return ukspeech;
    }

    public List<Exam> getExams() {
        return exams;
    }
    public List<Phrase> getPhrases() {
        return phrases;
    }
    public List<RelWord> getRels() {
        return rels;
    }
    public List<Syno> getSynos() {
        return synos;
    }
    public List<Tran> getTrans() {
        return trans;
    }

}
