package io.alexanderschaefer.u2764.presenter.fragments;

import androidx.annotation.Nullable;
import io.alexanderschaefer.u2764.view.EncapsulatedFragmentView;

public interface BaseFragment {

    @Nullable
    EncapsulatedFragmentView getEncapsulatedView();
}
