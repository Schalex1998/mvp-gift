package io.alexanderschaefer.u2764.screens;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import io.alexanderschaefer.u2764.screens.common.view.EncapsulatedView;
import io.alexanderschaefer.u2764.screens.giftdetail.giftdetailfragmentview.GiftDetailFragmentView;
import io.alexanderschaefer.u2764.screens.giftdetail.giftdetailfragmentview.GiftDetailFragmentViewImpl;
import io.alexanderschaefer.u2764.screens.opengift.opengiftdialogview.OpenGiftDialogView;
import io.alexanderschaefer.u2764.screens.opengift.opengiftdialogview.OpenGiftDialogViewImpl;
import io.alexanderschaefer.u2764.screens.overview.overviewfragmentview.OverviewFragmentView;
import io.alexanderschaefer.u2764.screens.overview.overviewfragmentview.OverviewFragmentViewImpl;

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
