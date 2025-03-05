package com.example.myapplication.Domain;

import java.util.ArrayList;

public class AnswerDomain {
    String answer;
    int correct;

    public AnswerDomain(String answer, int correct) {
        this.answer = answer;
        this.correct = correct;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }
}
