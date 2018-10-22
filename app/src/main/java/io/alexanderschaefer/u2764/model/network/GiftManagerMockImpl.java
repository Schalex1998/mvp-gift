package io.alexanderschaefer.u2764.model.network;

import android.os.Handler;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.alexanderschaefer.u2764.common.DefaultEventEmitter;
import io.alexanderschaefer.u2764.model.database.Challenge;
import io.alexanderschaefer.u2764.model.database.Gift;

public class GiftManagerMockImpl extends DefaultEventEmitter<GiftManager.GiftManagerListener> implements GiftManager {

    private static final int NUM_GIFTS = 30;
    private static final int NETWORK_TIME = 2000;
    private HashMap<String, Gift> giftMap;
    private Handler handler;

    private final MutableLiveData<Gift[]> gifts;

    public GiftManagerMockImpl() {
        gifts = new MutableLiveData<>();
        handler = new Handler();
        giftMap = new HashMap<>();
        for (int i = 0; i < NUM_GIFTS; i++) {
            List<Challenge> challenges = new ArrayList<>();
            int numberOfChallenges = new Random().nextInt(5) + 1;
            for (int j = 0; j < numberOfChallenges; j++) {
                Challenge challenge = new Challenge("Question" + j, null, String.valueOf(j));
                challenges.add(challenge);
            }
            Gift gift = new Gift(String.valueOf(i), "Name" + i, "Description" + i, Gift.GiftState.NEW, challenges);
            giftMap.put(String.valueOf(i), gift);
        }
    }

    @Override
    public LiveData<Gift[]> getGifts() {
        return gifts;
    }

    @Override
    public void fetchGifts() {
        handler.postDelayed(() -> gifts.postValue(giftMap.values().toArray(new Gift[giftMap.values().size()])), NETWORK_TIME);
    }

    @Override
    public void fetchGift(String id) {
        Gift gift = giftMap.get(id);

        giftMap.put(gift.getId(), gift);

        fetchGifts();
    }

    @Override
    public void openGift(String id, List<String> answers) {
        Gift gift = giftMap.get(id);

        if (gift == null) {
            forEachListener((listener) -> listener.onGiftManagerError("Gift " + id + " not found!"));
            return;
        }

        for (int i = 0; i < gift.getChallenges().size(); i++) {
            Challenge challengeToTry = gift.getChallenges().get(i);
            if (answers.size() > i && answers.get(i) != null) {
                String answer = answers.get(i);
                if (answer.toLowerCase().contains(challengeToTry.getAnswer())) {
                    challengeToTry.setGivenAnswer(answer);
                }
            }
        }

        boolean open = true;
        for (Challenge challengeToVerify : gift.getChallenges()) {
            if (TextUtils.isEmpty(challengeToVerify.getGivenAnswer()))
                open = false;
        }
        if (open && gift.getState().equals(Gift.GiftState.NEW)) {
            gift.setState(Gift.GiftState.OPEN);
        }

        giftMap.put(gift.getId(), gift);

        fetchGifts();
    }

    @Override
    public void redeemGift(String id) {
        Gift gift = giftMap.get(id);

        if (gift == null) {
            forEachListener((listener) -> listener.onGiftManagerError("Gift " + id + " not found!"));
            return;
        }

        if (gift.getState().equals(Gift.GiftState.OPEN)) {
            gift.setState(Gift.GiftState.REDEEMED);
        }

        giftMap.put(gift.getId(), gift);

        fetchGifts();
    }
}
