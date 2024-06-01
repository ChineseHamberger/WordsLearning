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
        sb.append("*近义词:").append("\n");
        sb.append("词性: ").append(pos).append("\n");
        sb.append("中文: ").append(tran).append("\n");
        for (String hwd : hwds) {
            sb.append("词根: ").append(hwd).append("\n");
        }
        return sb;
    }
}
