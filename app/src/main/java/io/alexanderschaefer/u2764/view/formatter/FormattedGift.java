package io.alexanderschaefer.u2764.view.formatter;

import android.content.Context;

import java.util.List;

import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.model.pojo.Gift;

public class FormattedGift {
    private Gift gift;
    private Context context;
    private List<FormattedChallenge> formattedChallenges;

    FormattedGift(Gift gift, List<FormattedChallenge> formattedChallenges, Context context) {
        this.gift = gift;
        this.context = context;
        this.formattedChallenges = formattedChallenges;
    }

    public String getId() {
        return gift.getId();
    }

    public String getName() {
        return gift.getState() == Gift.GiftState.NEW ? context.getString(R.string.new_gift_name) : gift.getName();
    }

    public String getDescription() {
        return gift.getState() == Gift.GiftState.NEW ? context.getString(R.string.new_gift_description) : gift.getDescription();
    }

    public List<FormattedChallenge> getChallenges() {
        return formattedChallenges;
    }

    public Gift.GiftState getState() {
        return gift.getState();
    }

    public String getStatusText() {
        switch (gift.getState()) {
            case NEW:
                return context.getString(R.string.status_gift_new);
            case OPEN:
                return context.getString(R.string.status_gift_open);
            case REDEEMED:
                return context.getString(R.string.status_gift_redeemed);
        }
        return "";
    }

    public String getActionText() {
        switch (gift.getState()) {
            case NEW:
                return context.getString(R.string.action_gift_open);
            case OPEN:
                return context.getString(R.string.action_gift_redeem);
            default:
                return "";
        }
    }
}
