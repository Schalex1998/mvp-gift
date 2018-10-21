package io.alexanderschaefer.u2764.presenter.viewmodel;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.alexanderschaefer.u2764.model.giftmanager.GiftManager;
import io.alexanderschaefer.u2764.model.pojo.Gift;

public class OverviewViewModel extends ViewModel implements GiftManager.GiftManagerListener {

    private MutableLiveData<List<Gift>> gifts;

    private final GiftManager giftManager;

    public OverviewViewModel(GiftManager giftManager) {
        this.giftManager = giftManager;
        gifts = new MutableLiveData<>();
        giftManager.registerListener(this);
    }

    public void fetchGifts() {
        if (gifts.getValue() == null || gifts.getValue().isEmpty())
            giftManager.fetchGifts();
        else {
            setGifts(gifts.getValue());
        }
    }

    public void redeemGift(String id) {
        giftManager.redeemGift(id);
    }

    public MutableLiveData<List<Gift>> getGifts() {
        return gifts;
    }

    private void setGifts(List<Gift> gifts) {
        this.gifts.setValue(gifts);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        giftManager.unregisterListener(this);
    }

    @Override
    public void onGiftsFetched(List<Gift> gifts) {
        setGifts(gifts);
    }

    @Override
    public void onGiftFetched(Gift gift) {
    }

    @Override
    public void onGiftManagerError(String error) {
        // TODO: 20/10/2018 propagate
    }

    @Override
    public void onGiftOpened(Gift gift) {
    }

    @Override
    public void onGiftRedeemed(Gift redeemedGift) {
        List<Gift> giftList = gifts.getValue();
        giftList.removeIf(gift -> gift.getId().equals(redeemedGift.getId()));
        giftList.add(redeemedGift);
        setGifts(giftList);
    }
}
