package ymc.basicelements;

import java.io.Serializable;

public class Phrase implements Serializable {
    private String pContent;
    private String pCn;
    public Phrase(String pContent, String pCn){
        this.pContent = pContent;
        this.pCn = pCn;
    }
    public String getPContent() {
        return pContent;
    }
    public String getPCn() {
        return pCn;
    }

    public void showInfo() {
        System.out.println("*Phrase: " + pContent + " " + pCn);
    }
}
