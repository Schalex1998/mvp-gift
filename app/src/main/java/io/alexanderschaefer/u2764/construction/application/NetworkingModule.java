package io.alexanderschaefer.u2764.construction.application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.alexanderschaefer.u2764.model.giftmanager.GiftManager;
import io.alexanderschaefer.u2764.model.giftmanager.GiftManagerMockImpl;

@Module
class NetworkingModule {

    @Singleton
    @Provides
    GiftManager provideGiftManager() {
        return new GiftManagerMockImpl();
    }
}
