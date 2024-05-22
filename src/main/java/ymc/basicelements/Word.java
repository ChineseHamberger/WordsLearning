package ymc.basicelements;

import java.io.Serializable;
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


    public void showInfo(){
        System.out.println("**单词：" + english);
        System.out.println("*中文释义：" + tranChinese);
        System.out.println("*英文释义：" + tranEnglish);
        System.out.println("*音标：" + getPhone());
        System.out.println("*来自词书: "+ bookId + "序号: "+ wordRank);
        System.out.println("*发音参数: "+ukspeech+" "+usspeech); //待实现发音的流
        for(Exam exam:exams){
            exam.showInfo();
        }
        for(Sentence sentence:sentences){
            sentence.showInfo();
        }
        for(Syno syno:synos){
            syno.showInfo();
        }
        for(Phrase phrase:phrases){
            phrase.showInfo();
        }
        for(RelWord rel:rels){
            rel.showInfo();
        }
        for(Tran tran:trans){
            tran.showInfo();
        }


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
    public String getExampleSentence() {
        return " ";
    }
}
