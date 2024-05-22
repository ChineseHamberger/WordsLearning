package ymc.basicelements;

import java.io.Serializable;

public class Choice implements Serializable {
    private int choiceIndex;
    private String choice;
    public Choice(int choiceIndex, String choice){
        this.choiceIndex = choiceIndex;
        this.choice = choice;
    }
    public int getChoiceIndex() {
        return choiceIndex;
    }
    public String getChoice() {
        return choice;
    }
}
