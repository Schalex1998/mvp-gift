package io.alexanderschaefer.u2764.model.formatter;

import android.content.Context;
import android.text.TextUtils;

import java.util.Objects;

import androidx.annotation.Nullable;
import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.model.database.Challenge;

public class FormattedChallenge {

    private Challenge challenge;
    private Context context;

    FormattedChallenge(Challenge challenge, Context context) {
        this.challenge = challenge;
        this.context = context;
    }

    public String getGivenAnswer() {
        return isAnswered() ? challenge.getGivenAnswer() : context.getString(R.string.challenge_not_answered);
    }

    public boolean isAnswered() {
        return !TextUtils.isEmpty(challenge.getGivenAnswer());
    }

    public String getQuestion() {
        return challenge.getQuestion();
    }
}
