package ymc.basicelements;

import java.io.Serializable;
import java.util.List;

public class Exam implements Serializable {
    private int examType;
    private String question;
    private String answer;
    private int rightIndex;
    private List<Choice> choices;
    public Exam(int examType, String question, String answer, int rightIndex, List<Choice> choices){
        this.examType = examType;
        this.question = question;
        this.answer = answer;
        this.rightIndex = rightIndex;
        this.choices = choices;
    }
    public int getExamType() {
        return examType;
    }
    public String getQuestion() {
        return question;
    }
    public String getAnswer() {
        return answer;
    }
    public int getRightIndex() {
        return rightIndex;
    }
    public List<Choice> getChoices() {
        return choices;
    }

    public void showInfo() {
        System.out.println("Exam Type: " + examType);
        System.out.println("Question: " + question);
        System.out.println("Answer: " + answer);
        for (int i = 0; i < choices.size(); i++) {
            System.out.println("Choice " + (i + 1) + ": " + choices.get(i).getChoiceIndex()+ " " + choices.get(i).getChoice());
        }
        System.out.println("Right Index: " + rightIndex);
    }
}
