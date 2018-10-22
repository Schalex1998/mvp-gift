package io.alexanderschaefer.u2764.screens.giftdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import io.alexanderschaefer.u2764.model.GiftRepository;
import io.alexanderschaefer.u2764.model.database.GiftWithChallenges;

public class GiftDetailViewModel extends ViewModel {

    private final GiftRepository giftRepository;
    private LiveData<GiftWithChallenges> gift;
    private String giftId;

    public GiftDetailViewModel(GiftRepository giftRepository) {
        this.giftRepository = giftRepository;
    }

    public void init(String id) {
        if (this.gift != null) {
            return;
        }
        giftId = id;
        gift = giftRepository.getGift(id);
    }

    void refresh() {
        giftRepository.refreshGift(giftId);
    }

    void redeemGift() {
        giftRepository.redeemGift(giftId);
    }

    public LiveData<GiftWithChallenges> getGift() {
        return gift;
    }
}
