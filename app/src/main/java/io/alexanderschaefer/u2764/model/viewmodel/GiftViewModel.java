package io.alexanderschaefer.u2764.model.viewmodel;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.model.pojo.Gift;

public class GiftViewModel {
    private Gift gift;
    private Context context;

    public GiftViewModel(Gift gift, Context context) {
        this.gift = gift;
        this.context = context;
    }

    public static List<GiftViewModel> from(List<Gift> gifts, Context context) {
        List<GiftViewModel> giftViewModels = new ArrayList<>();
        for (Gift gift : gifts) {
            giftViewModels.add(new GiftViewModel(gift, context));
        }
        return giftViewModels;
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

    public List<ChallengeViewModel> getChallenges() {
        return ChallengeViewModel.from(gift.getChallenges(), context);
    }

    public Gift.GiftState getState() {
        return gift.getState();
    }

    public Gift getGift() {
        return gift;
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
