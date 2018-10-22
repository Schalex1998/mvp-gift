package io.alexanderschaefer.u2764.model;

import android.content.SharedPreferences;
import android.icu.util.Calendar;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.alexanderschaefer.u2764.common.AppExecutors;
import io.alexanderschaefer.u2764.model.database.Challenge;
import io.alexanderschaefer.u2764.model.database.Gift;
import io.alexanderschaefer.u2764.model.database.GiftDao;
import io.alexanderschaefer.u2764.model.database.GiftWithChallenges;
import io.alexanderschaefer.u2764.model.network.GiftManager;

public class GiftRepository implements GiftManager.GiftManagerListener {

    private static final String KEY_LAST_FETCHED = "key_last_fetched";

    private final GiftDao giftDao;
    private final GiftManager giftManager;
    private final AppExecutors executors;
    private final SharedPreferences sharedPreferences;
    private boolean mInitialized = false;

    public GiftRepository(GiftDao giftDao, GiftManager giftManager, AppExecutors executors, SharedPreferences sharedPreferences) {
        this.giftDao = giftDao;
        this.giftManager = giftManager;
        this.executors = executors;
        this.sharedPreferences = sharedPreferences;
        giftManager.registerListener(this);
        giftManager.getGifts().observeForever(this::onGiftsFetched);
    }

    private synchronized void initializeData() {
        if (mInitialized) return;
        mInitialized = true;

        if (isFetchNeeded())
            refreshGifts();
    }

    private boolean isFetchNeeded() {
        long lastFetched = sharedPreferences.getLong(KEY_LAST_FETCHED, 0);
        return Calendar.getInstance().getTimeInMillis() - lastFetched > 60000;
    }

    public LiveData<List<GiftWithChallenges>> getGifts() {
        initializeData();
        return giftDao.loadAll();
    }

    public LiveData<GiftWithChallenges> getGift(String id) {
        initializeData();
        return giftDao.load(id);
    }

    public void openGift(String id, List<String> answers) {
        giftManager.openGift(id, answers);
    }

    public void redeemGift(String id) {
        giftManager.redeemGift(id);
    }

    public void refreshGift(String id) {
        giftManager.fetchGift(id);
    }

    public void refreshGifts() {
        giftManager.fetchGifts();
    }

    private void onGiftsFetched(Gift[] gifts) {
        executors.diskIO().execute(() -> {
            // TODO: 22.10.2018 delete all other gifts
            giftDao.deleteAllChallenges();
            giftDao.insertAll(gifts);
            for (Gift gift : gifts) {
                saveChallenges(gift);
            }
        });

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(KEY_LAST_FETCHED, Calendar.getInstance().getTimeInMillis());
        editor.apply();
    }

    private void saveChallenges(Gift gift) {
        List<Challenge> challenges = gift.getChallenges();
        for (Challenge challenge : challenges) {
            challenge.setGiftId(gift.getId());
        }
        Challenge[] challengeArray = new Challenge[challenges.size()];
        challengeArray = challenges.toArray(challengeArray);
        giftDao.insertAll(challengeArray);
    }

    @Override
    public void onGiftManagerError(String error) {
        // TODO: 22.10.2018 handle
    }
}
