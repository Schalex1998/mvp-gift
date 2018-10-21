package io.alexanderschaefer.u2764.screens.opengift;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.alexanderschaefer.u2764.model.network.GiftManager;
import io.alexanderschaefer.u2764.model.database.Gift;

public class OpenGiftViewModel extends ViewModel implements GiftManager.GiftManagerListener {

    private MutableLiveData<Gift> gift;

    private final GiftManager giftManager;

    public OpenGiftViewModel(GiftManager giftManager) {
        this.giftManager = giftManager;
        gift = new MutableLiveData<>();
        giftManager.registerListener(this);
    }

    void fetchGift(String id) {
        if (gift.getValue() == null)
            giftManager.fetchGift(id);
        else {
            setGift(gift.getValue());
        }
    }

    void openGift(String id, List<String> answers) {
        giftManager.openGift(id, answers);
    }

    public LiveData<Gift> getGift() {
        return gift;
    }

    private void setGift(Gift gift) {
        this.gift.setValue(gift);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        giftManager.unregisterListener(this);
    }

    @Override
    public void onGiftsFetched(List<Gift> gifts) {
    }

    @Override
    public void onGiftFetched(Gift gift) {
        setGift(gift);
    }

    @Override
    public void onGiftManagerError(String error) {
        // TODO: 20/10/2018 propagate
    }

    @Override
    public void onGiftOpened(Gift gift) {
        setGift(gift);
    }

    @Override
    public void onGiftRedeemed(Gift gift) {
    }
}
