package io.alexanderschaefer.u2764.model.giftmanager;

import java.util.List;

import io.alexanderschaefer.u2764.model.pojo.Gift;
import io.alexanderschaefer.u2764.common.EventEmitter;

public interface GiftManager extends EventEmitter<GiftManager.GiftManagerListener> {
    void fetchGifts();

    void fetchGift(String id);

    void openGift(String id, List<String> answers);

    void redeemGift(String id);

    interface GiftManagerListener {
        void onGiftsFetched(List<Gift> gifts);

        void onGiftOpened(Gift gift);
    }
}

