package io.alexanderschaefer.u2764.construction.presentation;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.fragment.app.FragmentActivity;
import dagger.Module;
import dagger.Provides;
import io.alexanderschaefer.u2764.presenter.dialog.DialogManager;
import io.alexanderschaefer.u2764.view.ViewFactory;

@Module
public class PresentationModule {

    private final FragmentActivity activity;

    PresentationModule(FragmentActivity activity) {
        this.activity = activity;
    }

    @Provides
    LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(activity);
    }

    @Provides
    DialogManager provideDialogManager() {
        return new DialogManager();
    }

    @Provides
    Context provideContext() {
        return activity;
    }

    @Provides
    ViewFactory provideViewFactory(LayoutInflater layoutInflater) {
        return new ViewFactory(layoutInflater);
    }
}
