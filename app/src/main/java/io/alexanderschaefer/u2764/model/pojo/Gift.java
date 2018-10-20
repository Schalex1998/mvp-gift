package io.alexanderschaefer.u2764.model.pojo;

import java.util.List;

public class Gift {
    private String id;
    private String name;
    private String description;
    private List<Challenge> challenges;
    private GiftState state;

    public Gift(String id, String name, String description, List<Challenge> challenges, GiftState state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.challenges = challenges;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public GiftState getState() {
        return state;
    }

    public void setState(GiftState state) {
        this.state = state;
    }

    public enum GiftState {
        NEW,
        OPEN,
        REDEEMED
    }
}
