package ymc.basicelements;

import java.io.Serializable;
import java.util.List;

public class RelWord implements Serializable {
    private String pos;
    List<String> hwds;
    List<String> trans;

    public RelWord(String pos, List<String> hwds, List<String> trans){
        this.pos = pos;
        this.hwds = hwds;
        this.trans = trans;
    }
    public String getPos() {
        return pos;
    }
    public List<String> getHwds() {
        return hwds;
    }
    public List<String> getTrans() {
        return trans;
    }

    public void showInfo() {
        System.out.println("*RelWord: " + pos);
        for (int i = 0; i < hwds.size(); i++) {
            System.out.println("hwd: " + hwds.get(i) + " tran: " + trans.get(i));
        }
    }
}
