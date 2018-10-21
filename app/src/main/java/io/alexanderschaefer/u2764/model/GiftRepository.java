package io.alexanderschaefer.u2764.model;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.alexanderschaefer.u2764.common.AppExecutors;
import io.alexanderschaefer.u2764.model.database.Challenge;
import io.alexanderschaefer.u2764.model.database.Gift;
import io.alexanderschaefer.u2764.model.database.GiftDao;
import io.alexanderschaefer.u2764.model.database.GiftWithChallenges;
import io.alexanderschaefer.u2764.model.network.GiftManager;

public class GiftRepository implements GiftManager.GiftManagerListener {

    private final GiftDao giftDao;
    private final GiftManager giftManager;
    private final AppExecutors executors;

    private MutableLiveData<List<GiftWithChallenges>> gifts;

    public GiftRepository(GiftDao giftDao, GiftManager giftManager, AppExecutors executors) {
        this.giftDao = giftDao;
        this.giftManager = giftManager;
        this.executors = executors;
        giftManager.registerListener(this);
        gifts = new MutableLiveData<>();
    }

    public LiveData<List<GiftWithChallenges>> getGifts() {
        refreshGifts();
        return giftDao.loadAll();
    }

    public LiveData<GiftWithChallenges> getGift(String id) {
        refreshGift(id);
        return giftDao.load(id);
    }

    private void refreshGift(String id) {
        giftManager.fetchGift(id);
    }

    private void refreshGifts() {
        giftManager.fetchGifts();
    }

    @Override
    public void onGiftsFetched(List<Gift> gifts) {
        executors.diskIO().execute(() -> {
            saveGifts(gifts);
            for (Gift gift : gifts) {
                saveChallenges(gift.getChallenges());
            }
        });
    }

    @Override
    public void onGiftFetched(Gift gift) {
        executors.diskIO().execute(() -> {
            giftDao.insert(gift);
            saveChallenges(gift.getChallenges());
        });
    }

    private void saveChallenges(List<Challenge> challenges) {
        Challenge[] challengeArray = new Challenge[challenges.size()];
        challengeArray = challenges.toArray(challengeArray);
        giftDao.insertAll(challengeArray);
    }

    private void saveGifts(List<Gift> gifts) {
        Gift[] giftArray = new Gift[gifts.size()];
        giftArray = gifts.toArray(giftArray);
        giftDao.insertAll(giftArray);
    }

    @Override
    public void onGiftManagerError(String error) {

    }

    @Override
    public void onGiftOpened(Gift gift) {

    }

    @Override
    public void onGiftRedeemed(Gift gift) {

    }
}
