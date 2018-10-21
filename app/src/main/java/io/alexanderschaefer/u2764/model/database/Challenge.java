package io.alexanderschaefer.u2764.model.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "challenges")
public class Challenge {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String giftId;

    private String question;
    private String givenAnswer;
    private String answer;

    public Challenge(int id, String giftId, String question, String givenAnswer, String answer) {
        this.id = id;
        this.giftId = giftId;
        this.question = question;
        this.givenAnswer = givenAnswer;
        this.answer = answer;
    }

    @Ignore
    public Challenge(String question, String givenAnswer, String answer) {
        this.question = question;
        this.givenAnswer = givenAnswer;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
