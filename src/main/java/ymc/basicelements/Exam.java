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
        System.out.println(info());
    }

    public StringBuilder info() {
        StringBuilder sb = new StringBuilder();
        sb.append("*Exam:").append("\n");
        sb.append("Exam Type: ").append(examType).append("\n");
        sb.append("Question: ").append(question).append("\n");
        sb.append("Answer: ").append(answer).append("\n");
        for (int i = 0; i < choices.size(); i++) {
            sb.append("Choice ").append(i + 1).append(": ").append(choices.get(i).getChoiceIndex()).append(" ").append(choices.get(i).getChoice()).append("\n");
        }
        sb.append("Right Index: ").append(rightIndex);
        return sb;
    }
}
