package io.alexanderschaefer.u2764.construction.application;

import android.app.Application;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import io.alexanderschaefer.u2764.common.AppExecutors;
import io.alexanderschaefer.u2764.model.GiftRepository;
import io.alexanderschaefer.u2764.model.database.GiftDao;
import io.alexanderschaefer.u2764.model.database.GiftDatabase;
import io.alexanderschaefer.u2764.model.network.GiftManager;
import io.alexanderschaefer.u2764.model.network.GiftManagerMockImpl;

@Module
class DataModule {

    @Singleton
    @Provides
    GiftManager provideGiftManager() {
        return new GiftManagerMockImpl();
    }

    @Singleton
    @Provides
    GiftDatabase provideGiftDatabase(Application application) {
        return Room.databaseBuilder(application, GiftDatabase.class, "gift_database").build();
    }

    @Provides
    GiftDao provideGiftDao(GiftDatabase giftDatabase) {
        return giftDatabase.giftDao();
    }

    @Singleton
    @Provides
    GiftRepository provideGiftRepository(GiftDao giftDao, GiftManager giftManager, AppExecutors appExecutors, SharedPreferences sharedPreferences) {
        return new GiftRepository(giftDao, giftManager, appExecutors, sharedPreferences);
    }
}
