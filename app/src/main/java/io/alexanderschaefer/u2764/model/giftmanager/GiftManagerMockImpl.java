package io.alexanderschaefer.u2764.model.giftmanager;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import io.alexanderschaefer.u2764.model.pojo.Challenge;
import io.alexanderschaefer.u2764.model.pojo.Gift;
import io.alexanderschaefer.u2764.util.DefaultEventEmitter;

import static java.util.stream.Collectors.toSet;

class GiftManagerMockImpl extends DefaultEventEmitter<GiftManager.GiftManagerListener> implements GiftManager {
    private static final String SHARED_PREF_KEY = "gift_manager_mock_impl_shared_pref_key";
    private static final String GIVEN_ANSWER_KEY1 = "given_answer_key1";
    private static final String GIVEN_ANSWER_KEY2 = "given_answer_key2";
    private static final String STATE_KEY = "state_key";
    private Context context;

    GiftManagerMockImpl(Context context) {
        this.context = context;
    }

    private Gift loadGift() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        String answer1 = sharedPreferences.getString(GIVEN_ANSWER_KEY1, "");
        String answer2 = sharedPreferences.getString(GIVEN_ANSWER_KEY2, "");
        int state = sharedPreferences.getInt(STATE_KEY, 0);
        Gift.GiftState giftState = Gift.GiftState.NEW;
        if (state == 1)
            giftState = Gift.GiftState.OPEN;
        else if (state == 2)
            giftState = Gift.GiftState.REDEEMED;
        Challenge challenge = new Challenge("How old am I?", Stream.of("20", "twenty").collect(toSet()), !answer1.isEmpty(), answer1);
        Challenge challenge2 = new Challenge("Where do I live?", Stream.of("Germany", "Bruchsal", "BW").collect(toSet()), !answer2.isEmpty(), answer2);
        return new Gift("0", "Lunch", "The food's on me!", Arrays.asList(challenge, challenge2), giftState);
    }

    private void saveGift(Gift gift) {
        int newState = 0;
        if (gift.getState() == Gift.GiftState.OPEN)
            newState = 1;
        else if (gift.getState() == Gift.GiftState.REDEEMED)
            newState = 2;

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(STATE_KEY, newState);
        editor.putString(GIVEN_ANSWER_KEY1, gift.getChallenges().get(0).getGivenAnswer());
        editor.putString(GIVEN_ANSWER_KEY2, gift.getChallenges().get(1).getGivenAnswer());
        editor.apply();
    }

    @Override
    public void fetchGifts() {
        List<Gift> gifts = new ArrayList<>();
        gifts.add(loadGift());

        forEachListener((listener) -> listener.onGiftsFetched(gifts));
    }

    @Override
    public void fetchGift(String id) {
        List<Gift> gifts = new ArrayList<>();
        gifts.add(loadGift());

        forEachListener((listener) -> listener.onGiftsFetched(gifts));
    }

    @Override
    public void openGift(String id, List<String> answers) {
        Gift gift = loadGift();

        for (int i = 0; i < gift.getChallenges().size(); i++) {
            Challenge challengeToTry = gift.getChallenges().get(i);
            if (answers.size() > i && answers.get(i) != null) {
                String answer = answers.get(i);
                for (String solution : challengeToTry.getAnswers()) {
                    if (answer.toLowerCase().contains(solution.toLowerCase())) {
                        challengeToTry.setAnswered(true);
                        challengeToTry.setGivenAnswer(answer);
                    }
                }
            }
        }

        boolean open = true;
        for (Challenge challengeToVerify : gift.getChallenges()) {
            if (!challengeToVerify.isAnswered())
                open = false;
        }
        if (open && gift.getState().equals(Gift.GiftState.NEW)) {
            gift.setState(Gift.GiftState.OPEN);
        }

        saveGift(gift);
        forEachListener((listener) -> listener.onGiftOpened(gift));
    }

    @Override
    public void redeemGift(String id) {
        Gift gift = loadGift();

        if (gift.getState().equals(Gift.GiftState.OPEN)) {
            gift.setState(Gift.GiftState.REDEEMED);
        }

        saveGift(gift);

        forEachListener((listener) -> listener.onGiftOpened(gift));
    }
}
