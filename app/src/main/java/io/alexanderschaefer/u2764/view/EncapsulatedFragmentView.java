package io.alexanderschaefer.u2764.view;

import android.os.Bundle;

import androidx.annotation.MenuRes;
import androidx.annotation.Nullable;

public interface EncapsulatedFragmentView extends EncapsulatedView {

    @Nullable
    Bundle getViewState();

    String getTitle();

    boolean shouldShowActionBar();

    @MenuRes
    int getOptionsMenu();
}
