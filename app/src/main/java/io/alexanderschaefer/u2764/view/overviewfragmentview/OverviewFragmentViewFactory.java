package io.alexanderschaefer.u2764.view.overviewfragmentview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

public class OverviewFragmentViewFactory {

    public static OverviewFragmentView createInstance(LayoutInflater inflater, ViewGroup container) {
        return new OverviewFragmentViewImpl(inflater, container);
    }
}
