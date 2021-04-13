package com.sakthi.gktrivia.model;

public class Question {
    public String answer;
    public boolean answeropt;

    public Question() {
    }

    public Question(String answer, boolean answeropt) {
        this.answer = answer;
        this.answeropt = answeropt;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isAnsweropt() {
        return answeropt;
    }

    public void setAnsweropt(boolean answeropt) {
        this.answeropt = answeropt;
    }

    @Override
    public String toString() {
        return "Question{" +
                "answer='" + answer + '\'' +
                ", answeropt=" + answeropt +
                '}';
    }
}
