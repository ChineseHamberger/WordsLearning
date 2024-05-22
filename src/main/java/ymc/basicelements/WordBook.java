package ymc.basicelements;

import java.io.Serializable;
import java.util.List;

public class WordBook implements Serializable {
    private final String name;
    private List<Word> words;


    public WordBook(String name, List<Word> words) {
        this.name = name;
        this.words = words;
    }

    public void addWord(Word word) {
        words.add(word);
    }
    public List<Word> getWords() {
        return words;
    }

    public String getName() {
        return name;
    }

}