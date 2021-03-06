package io.alexanderschaefer.u2764.model.formatter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.alexanderschaefer.u2764.model.database.Challenge;
import io.alexanderschaefer.u2764.model.database.GiftWithChallenges;

public class FormattedGiftFactory {

    private Context context;

    public FormattedGiftFactory(Context context) {
        this.context = context;
    }

    public List<FormattedGift> from(List<GiftWithChallenges> gifts) {
        List<FormattedGift> formattedGifts = new ArrayList<>();
        if (gifts != null) {
            for (GiftWithChallenges gift : gifts) {
                formattedGifts.add(new FormattedGift(gift.getGift(), getChallenges(gift), context));
            }
        }
        return formattedGifts;
    }

    private List<FormattedChallenge> getChallenges(GiftWithChallenges gift) {
        List<FormattedChallenge> formattedChallenges = new ArrayList<>();
        if (gift.getChallenges() != null) {
            for (Challenge challenge : gift.getChallenges()) {
                formattedChallenges.add(new FormattedChallenge(challenge, context));
            }
        }
        return formattedChallenges;
    }

    public FormattedGift from(GiftWithChallenges gift) {
        return new FormattedGift(gift.getGift(), getChallenges(gift), context);
    }
}
