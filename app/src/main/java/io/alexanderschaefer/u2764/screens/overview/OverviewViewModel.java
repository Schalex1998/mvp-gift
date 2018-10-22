package io.alexanderschaefer.u2764.screens.overview;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import io.alexanderschaefer.u2764.model.GiftRepository;
import io.alexanderschaefer.u2764.model.database.GiftWithChallenges;

public class OverviewViewModel extends ViewModel {

    private final GiftRepository giftRepository;
    private LiveData<List<GiftWithChallenges>> gifts;

    public OverviewViewModel(GiftRepository giftRepository) {
        this.giftRepository = giftRepository;
    }

    public void init() {
        if (this.gifts != null) {
            return;
        }
        gifts = giftRepository.getGifts();
    }

    void redeemGift(String id) {
        giftRepository.redeemGift(id);
    }

    void refresh() {
        giftRepository.refreshGifts();
    }

    LiveData<List<GiftWithChallenges>> getGifts() {
        return gifts;
    }
}
