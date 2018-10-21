package io.alexanderschaefer.u2764.construction.presentation;

import dagger.Subcomponent;
import io.alexanderschaefer.u2764.screens.opengift.OpenGiftDialog;
import io.alexanderschaefer.u2764.screens.giftdetail.GiftDetailFragment;
import io.alexanderschaefer.u2764.screens.overview.OverviewFragment;

@Subcomponent(modules = {
        PresentationModule.class,
        ViewModelModule.class
})
public interface PresentationComponent {

    void inject(GiftDetailFragment giftDetailFragment);

    void inject(OverviewFragment overviewFragment);

    void inject(OpenGiftDialog openGiftDialog);
}
