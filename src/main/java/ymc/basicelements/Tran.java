package ymc.basicelements;

import java.io.Serializable;

public class Tran implements Serializable {
    private String pos;
    private String tranCn;
    private String tranEng;
    public Tran(String pos, String tranCn, String tranEng){
        this.pos = pos;
        this.tranCn = tranCn;
        this.tranEng = tranEng;
    }
    public String getPos() {
        return pos;
    }
    public String getTranCn() {
        return tranCn;
    }
    public String getTranEng() {
        return tranEng;
    }

    public void showInfo() {
        System.out.print("*Tran: "+pos + " " + tranCn + " " + tranEng);
    }
}