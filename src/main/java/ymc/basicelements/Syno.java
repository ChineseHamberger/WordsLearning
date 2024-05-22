package ymc.basicelements;

import java.io.Serializable;
import java.util.List;

public class Syno implements Serializable {
    private String pos;
    private String tran;
    private List<String> hwds;
    public Syno(String pos, String tran, List<String> hwds) {
        this.pos = pos;
        this.tran = tran;
        this.hwds = hwds;
    }
    public String getPos() {
        return pos;
    }
    public String getTrans() {
        return tran;
    }
    public List<String> getHwds() {
        return hwds;
    }

    public void showInfo() {
        System.out.println("*Syno:");
        System.out.println("pos: " + pos);
        System.out.println("tran: " + tran);
        for (String hwd : hwds) {
            System.out.println("hwd: " + hwd);
        }

    }

}
