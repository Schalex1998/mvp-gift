package io.alexanderschaefer.u2764.construction.presentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import javax.inject.Provider;

import androidx.lifecycle.ViewModel;
import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.alexanderschaefer.u2764.model.giftmanager.GiftManager;
import io.alexanderschaefer.u2764.presenter.viewmodel.GiftDetailViewModel;
import io.alexanderschaefer.u2764.presenter.viewmodel.OpenGiftViewModel;
import io.alexanderschaefer.u2764.presenter.viewmodel.OverviewViewModel;
import io.alexanderschaefer.u2764.presenter.viewmodel.ViewModelFactory;

@Module
public class ViewModelModule {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @Provides
    ViewModelFactory provideViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }

    @Provides
    @IntoMap
    @ViewModelKey(GiftDetailViewModel.class)
    ViewModel provideGiftDetailViewModel(GiftManager giftManager) {
        return new GiftDetailViewModel(giftManager);
    }

    @Provides
    @IntoMap
    @ViewModelKey(OverviewViewModel.class)
    ViewModel provideOverviewViewModel(GiftManager giftManager) {
        return new OverviewViewModel(giftManager);
    }

    @Provides
    @IntoMap
    @ViewModelKey(OpenGiftViewModel.class)
    ViewModel provideOpenGiftViewModel(GiftManager giftManager) {
        return new OpenGiftViewModel(giftManager);
    }
}
