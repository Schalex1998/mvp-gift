package io.alexanderschaefer.u2764.construction.presentation;

import dagger.Subcomponent;
import io.alexanderschaefer.u2764.presenter.dialog.implementations.OpenGiftDialog;
import io.alexanderschaefer.u2764.presenter.fragments.implementations.GiftDetailFragment;
import io.alexanderschaefer.u2764.presenter.fragments.implementations.OverviewFragment;

@Subcomponent(modules = PresentationModule.class)
public interface PresentationComponent {

    void inject(GiftDetailFragment giftDetailFragment);

    void inject(OverviewFragment overviewFragment);

    void inject(OpenGiftDialog openGiftDialog);
}
