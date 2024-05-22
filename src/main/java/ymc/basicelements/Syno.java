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
        System.out.println(info());
    }

    public StringBuilder info() {
        StringBuilder sb = new StringBuilder();
        sb.append("*Syno:").append("\n");
        sb.append("pos: ").append(pos).append("\n");
        sb.append("tran: ").append(tran).append("\n");
        for (String hwd : hwds) {
            sb.append("hwd: ").append(hwd).append("\n");
        }
        return sb;
    }
}
