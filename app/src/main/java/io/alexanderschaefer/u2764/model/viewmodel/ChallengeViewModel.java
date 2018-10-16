package io.alexanderschaefer.u2764.model.viewmodel;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.model.pojo.Challenge;

public class ChallengeViewModel {

    private Challenge challenge;
    private Context context;

    public ChallengeViewModel(Challenge challenge, Context context) {
        this.challenge = challenge;
        this.context = context;
    }

    public static List<ChallengeViewModel> from(List<Challenge> challenges, Context context) {
        List<ChallengeViewModel> challengeViewModels = new ArrayList<>();
        for (Challenge challenge : challenges) {
            challengeViewModels.add(new ChallengeViewModel(challenge, context));
        }
        return challengeViewModels;
    }

    public boolean isAnswered() {
        return challenge.isAnswered();
    }

    public String getGivenAnswer() {
        return challenge.isAnswered() ? challenge.getGivenAnswer() : context.getString(R.string.challenge_not_answered);
    }

    public String getQuestion() {
        return challenge.getQuestion();
    }

    public Challenge getChallenge() {
        return challenge;
    }
}
