package io.alexanderschaefer.u2764.presenter.viewmodel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.alexanderschaefer.u2764.model.giftmanager.GiftManager;
import io.alexanderschaefer.u2764.model.pojo.Gift;

public class GiftDetailViewModel extends ViewModel implements GiftManager.GiftManagerListener {

    private MutableLiveData<Gift> gift;

    private final GiftManager giftManager;

    public GiftDetailViewModel(GiftManager giftManager) {
        this.giftManager = giftManager;
        gift = new MutableLiveData<>();
        giftManager.registerListener(this);
    }

    public void fetchGift(String id) {
        if (gift.getValue() == null)
            giftManager.fetchGift(id);
        else {
            setGift(gift.getValue());
        }
    }

    public void redeemGift(String id) {
        giftManager.redeemGift(id);
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
    }

    @Override
    public void onGiftRedeemed(Gift gift) {
        setGift(gift);
    }
}
