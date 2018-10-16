package io.alexanderschaefer.u2764.model.pojo;

import java.util.Set;

public class Challenge {
    private String question;
    private Set<String> answers;
    private boolean isAnswered;
    private String givenAnswer;

    public Challenge(String question, Set<String> answers, boolean isAnswered, String givenAnswer) {
        this.question = question;
        this.answers = answers;
        this.isAnswered = isAnswered;
        this.givenAnswer = givenAnswer;
    }

    public Challenge() {
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<String> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<String> answers) {
        this.answers = answers;
    }
}
