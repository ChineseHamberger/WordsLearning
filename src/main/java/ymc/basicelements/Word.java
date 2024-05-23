package ymc.basicelements;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

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

    private int familiarity = 0; //熟悉,掌握程度评分
    private int rareness = 0; //稀有程度评分

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
        info.append("**单词：" + english +"\n");
        info.append("*中文释义：" + tranChinese+"\n");
        info.append("*英文释义：" + tranEnglish+"\n");
        info.append("*音标：" + getPhone()+"\n");
        info.append("*来自词书: "+ bookId + "序号: "+ wordRank+"\n");
        info.append("*发音参数: "+ukspeech+" "+usspeech+"\n");

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

}
