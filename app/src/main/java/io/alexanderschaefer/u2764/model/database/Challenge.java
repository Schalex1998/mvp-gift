package io.alexanderschaefer.u2764.model.database;

import java.util.Set;

public class Challenge {
    private String question;
    private Set<String> answers;
    private String givenAnswer;

    public Challenge(String question, Set<String> answers, String givenAnswer) {
        this.question = question;
        this.answers = answers;
        this.givenAnswer = givenAnswer;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public Set<String> getAnswers() {
        return answers;
    }
}
