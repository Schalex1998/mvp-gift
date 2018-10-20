package io.alexanderschaefer.u2764.construction.application;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.alexanderschaefer.u2764.model.giftmanager.GiftManager;
import io.alexanderschaefer.u2764.model.giftmanager.GiftManagerMockImpl;

@Module
class NetworkingModule {

    @Singleton
    @Provides
    GiftManager provideGiftManager(Context context) {
        return new GiftManagerMockImpl(context);
    }
}
