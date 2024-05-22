package ymc.basicelements;

import java.io.Serializable;

public class Sentence implements Serializable {
    private String sContent;
    private String sCn;
    public Sentence(String sContent, String sCn){
        this.sContent = sContent;
        this.sCn = sCn;

    }
    public String getEnglish() {
        return sContent;
    }
    public String getChinese() {
        return sCn;
    }

    public void showInfo() {
        System.out.println("*Sentence: " + sContent + " " + sCn);
    }
}
