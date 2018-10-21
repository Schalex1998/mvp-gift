package io.alexanderschaefer.u2764.model.network;

import android.os.Handler;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import io.alexanderschaefer.u2764.common.DefaultEventEmitter;
import io.alexanderschaefer.u2764.model.database.Challenge;
import io.alexanderschaefer.u2764.model.database.Gift;

import static java.util.stream.Collectors.toSet;

public class GiftManagerMockImpl extends DefaultEventEmitter<GiftManager.GiftManagerListener> implements GiftManager {

    private static final int NUM_GIFTS = 30;
    private static final int NETWORK_TIME = 2000;
    private HashMap<String, Gift> gifts;
    private Handler handler;

    public GiftManagerMockImpl() {
        handler = new Handler();
        gifts = new HashMap<>();
        for (int i = 0; i < NUM_GIFTS; i++) {
            List<Challenge> challenges = new ArrayList<>();
            int numberOfChallenges = new Random().nextInt(5) + 1;
            for (int j = 0; j < numberOfChallenges; j++) {
                Challenge challenge = new Challenge("Question" + j, Stream.of("Answer" + j, String.valueOf(j)).collect(toSet()), null);
                challenges.add(challenge);
            }
            Gift gift = new Gift(String.valueOf(i), "Name" + i, "Description" + i, challenges, Gift.GiftState.NEW);
            gifts.put(String.valueOf(i), gift);
        }
    }

    @Override
    public void fetchGifts() {
        List<Gift> giftValues = new ArrayList<>(gifts.values());

        handler.postDelayed(() -> forEachListener((listener) -> listener.onGiftsFetched(giftValues)), NETWORK_TIME);
    }

    @Override
    public void fetchGift(String id) {
        Gift gift = gifts.get(id);

        if (gift == null) {
            forEachListener((listener) -> listener.onGiftManagerError("Gift " + id + " not found!"));
            return;
        }

        handler.postDelayed(() -> forEachListener((listener) -> listener.onGiftFetched(gift)), NETWORK_TIME);
    }

    @Override
    public void openGift(String id, List<String> answers) {
        Gift gift = gifts.get(id);

        if (gift == null) {
            forEachListener((listener) -> listener.onGiftManagerError("Gift " + id + " not found!"));
            return;
        }

        for (int i = 0; i < gift.getChallenges().size(); i++) {
            Challenge challengeToTry = gift.getChallenges().get(i);
            if (answers.size() > i && answers.get(i) != null) {
                String answer = answers.get(i);
                for (String solution : challengeToTry.getAnswers()) {
                    if (answer.toLowerCase().contains(solution.toLowerCase())) {
                        challengeToTry.setGivenAnswer(answer);
                    }
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

        handler.postDelayed(() -> forEachListener((listener) -> listener.onGiftOpened(gift)), NETWORK_TIME);
    }

    @Override
    public void redeemGift(String id) {
        Gift gift = gifts.get(id);

        if (gift == null) {
            forEachListener((listener) -> listener.onGiftManagerError("Gift " + id + " not found!"));
            return;
        }

        if (gift.getState().equals(Gift.GiftState.OPEN)) {
            gift.setState(Gift.GiftState.REDEEMED);
        }

        handler.postDelayed(() -> forEachListener((listener) -> listener.onGiftRedeemed(gift)), NETWORK_TIME);
    }
}
