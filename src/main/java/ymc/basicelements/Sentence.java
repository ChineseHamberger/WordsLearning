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
        System.out.println(info());
    }

    public StringBuilder info() {
        StringBuilder sb = new StringBuilder();
        sb.append("*Sentence: ").append(sContent).append(" ").append(sCn).append("\n");
        return sb;
    }
}
