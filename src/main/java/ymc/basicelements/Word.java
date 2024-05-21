package ymc.basicelements;

import java.io.Serializable;

public class Word implements Serializable {
    private final String english;
    private final String definition;
    private final String exampleSentence;

    public Word(String english, String definition, String exampleSentence) {
        this.english = english;
        this.definition = definition;
        this.exampleSentence = exampleSentence;
    }
    public String getEnglish() {
        return english;
    }
    public String getDefinition() {
        return definition;
    }
    public String getExampleSentence() {
        return exampleSentence;
    }
}
