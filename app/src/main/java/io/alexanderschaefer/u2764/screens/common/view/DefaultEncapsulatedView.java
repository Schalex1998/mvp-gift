package io.alexanderschaefer.u2764.screens.common.view;

import android.content.Context;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import io.alexanderschaefer.u2764.common.DefaultEventEmitter;

public abstract class DefaultEncapsulatedView<ListenerType> extends DefaultEventEmitter<ListenerType> implements EncapsulatedView {

    private View rootView;

    @Override
    public View getRootView() {
        return rootView;
    }

    protected void setRootView(@NonNull View rootView) {
        this.rootView = rootView;
    }

    protected <T extends View> T findViewById(@IdRes int id) {
        return rootView.findViewById(id);
    }

    protected Context getContext() {
        return rootView.getContext();
    }
}
