package io.alexanderschaefer.u2764.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import io.alexanderschaefer.u2764.view.giftdetailfragmentview.GiftDetailFragmentView;
import io.alexanderschaefer.u2764.view.giftdetailfragmentview.GiftDetailFragmentViewImpl;
import io.alexanderschaefer.u2764.view.opengiftdialogview.OpenGiftDialogView;
import io.alexanderschaefer.u2764.view.opengiftdialogview.OpenGiftDialogViewImpl;
import io.alexanderschaefer.u2764.view.overviewfragmentview.OverviewFragmentView;
import io.alexanderschaefer.u2764.view.overviewfragmentview.OverviewFragmentViewImpl;

public class ViewFactory {

    private final LayoutInflater mLayoutInflater;

    public ViewFactory(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

    public <T extends EncapsulatedView> T newInstance(Class<T> viewClass, @Nullable ViewGroup container) {

        EncapsulatedView encapsulatedView;

        if (viewClass == GiftDetailFragmentView.class) {
            encapsulatedView = new GiftDetailFragmentViewImpl(mLayoutInflater, container);
        } else if (viewClass == OverviewFragmentView.class) {
            encapsulatedView = new OverviewFragmentViewImpl(mLayoutInflater, container);
        } else if (viewClass == OpenGiftDialogView.class) {
            encapsulatedView = new OpenGiftDialogViewImpl(mLayoutInflater, container);
        } else {
            throw new IllegalArgumentException("unsupported view class " + viewClass);
        }

        //noinspection unchecked
        return (T) encapsulatedView;
    }
}
