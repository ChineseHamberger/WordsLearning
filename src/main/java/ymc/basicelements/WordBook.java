package ymc.basicelements;

import java.io.Serializable;
import java.util.List;
import java.util.TreeSet;

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

    public Word searchWordInEng(String english) {
        for (Word word : words){
            if (word.getEnglish().equals(english)){
                System.out.println("Word found" + word.getEnglish() +" "+ word.getTranChinese());
                return word;
            }
        }
        System.out.println("Word not found");
        return null;
    }

    public Word searchWordInCh(String chinese) {
        for (Word word : words){
            if (word.getTranChinese().equals(chinese)){
                System.out.println("Word found" + word.getEnglish() +" "+ word.getTranChinese());
                return word;
            }
        }
        System.out.println("Word not found");
        return null;
    }


}