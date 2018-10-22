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
import io.alexanderschaefer.u2764.model.GiftRepository;
import io.alexanderschaefer.u2764.model.network.GiftManager;
import io.alexanderschaefer.u2764.screens.giftdetail.GiftDetailViewModel;
import io.alexanderschaefer.u2764.screens.opengift.OpenGiftViewModel;
import io.alexanderschaefer.u2764.screens.overview.OverviewViewModel;
import io.alexanderschaefer.u2764.screens.common.view.ViewModelFactory;

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
    ViewModel provideGiftDetailViewModel(GiftRepository giftRepository) {
        return new GiftDetailViewModel(giftRepository);
    }

    @Provides
    @IntoMap
    @ViewModelKey(OverviewViewModel.class)
    ViewModel provideOverviewViewModel(GiftRepository giftRepository) {
        return new OverviewViewModel(giftRepository);
    }

    @Provides
    @IntoMap
    @ViewModelKey(OpenGiftViewModel.class)
    ViewModel provideOpenGiftViewModel(GiftRepository giftRepository) {
        return new OpenGiftViewModel(giftRepository);
    }
}
