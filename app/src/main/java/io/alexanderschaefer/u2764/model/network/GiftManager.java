package io.alexanderschaefer.u2764.model.network;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.alexanderschaefer.u2764.common.EventEmitter;
import io.alexanderschaefer.u2764.model.database.Gift;

public interface GiftManager extends EventEmitter<GiftManager.GiftManagerListener> {

    void fetchGifts();

    void fetchGift(String id);

    void openGift(String id, List<String> answers);

    void redeemGift(String id);

    LiveData<Gift[]> getGifts();

    interface GiftManagerListener {
        void onGiftManagerError(String error);
    }
}

