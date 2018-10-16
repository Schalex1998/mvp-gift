package io.alexanderschaefer.u2764.view.giftdetailfragmentview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

public class GiftDetailFragmentViewFactory {

    public static GiftDetailFragmentView createInstance(LayoutInflater inflater, ViewGroup container) {
        return new GiftDetailFragmentViewImpl(inflater, container);
    }
}
