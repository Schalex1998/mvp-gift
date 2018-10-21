package io.alexanderschaefer.u2764.model.database;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "gifts")
public class Gift {

    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    private String description;
    private GiftState state;

    @Ignore
    private List<Challenge> challenges;

    public Gift(@NonNull String id, String name, String description, GiftState state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
    }

    @Ignore
    public Gift(@NonNull String id, String name, String description, GiftState state, List<Challenge> challenges) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
        this.challenges = challenges;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
