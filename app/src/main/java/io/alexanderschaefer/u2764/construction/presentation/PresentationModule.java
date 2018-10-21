package io.alexanderschaefer.u2764.construction.presentation;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import dagger.Module;
import dagger.Provides;
import io.alexanderschaefer.u2764.common.DialogUtil;
import io.alexanderschaefer.u2764.screens.common.dialog.DialogManager;
import io.alexanderschaefer.u2764.screens.ViewFactory;

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
    FragmentManager provideFragmentManager() {
        return activity.getSupportFragmentManager();
    }

    @Provides
    DialogUtil provideDialogUtil(Context context) {
        return new DialogUtil(context);
    }

    @Provides
    DialogManager provideDialogManager(FragmentManager fragmentManager) {
        return new DialogManager(fragmentManager);
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
