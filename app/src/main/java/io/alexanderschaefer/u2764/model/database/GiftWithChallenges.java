package io.alexanderschaefer.u2764.model.database;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class GiftWithChallenges {

    @Embedded
    private Gift gift;

    @Relation(entity = Challenge.class, entityColumn = "giftId", parentColumn = "id")
    private List<Challenge> challenges;

    public GiftWithChallenges(Gift gift, List<Challenge> challenges) {
        this.gift = gift;
        this.challenges = challenges;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }
}
