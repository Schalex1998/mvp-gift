package io.alexanderschaefer.u2764.screens.opengift;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import io.alexanderschaefer.u2764.model.GiftRepository;
import io.alexanderschaefer.u2764.model.database.GiftWithChallenges;

public class OpenGiftViewModel extends ViewModel {

    private final GiftRepository giftRepository;
    private LiveData<GiftWithChallenges> gift;
    private String giftId;

    public OpenGiftViewModel(GiftRepository giftRepository) {
        this.giftRepository = giftRepository;
    }

    public void init(String id) {
        if (this.gift != null) {
            return;
        }
        giftId = id;
        gift = giftRepository.getGift(id);
    }

    void openGift(List<String> answers) {
        giftRepository.openGift(giftId, answers);
    }

    public LiveData<GiftWithChallenges> getGift() {
        return gift;
    }
}
