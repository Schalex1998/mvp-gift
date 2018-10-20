package io.alexanderschaefer.u2764.view.overviewfragmentview;


import java.util.List;

import io.alexanderschaefer.u2764.common.EventEmitter;
import io.alexanderschaefer.u2764.view.EncapsulatedFragmentView;
import io.alexanderschaefer.u2764.view.ProgressView;
import io.alexanderschaefer.u2764.view.formatter.FormattedGift;
import io.alexanderschaefer.u2764.view.giftitemview.GiftItemView;

public interface OverviewFragmentView extends ProgressView, EncapsulatedFragmentView, EventEmitter<OverviewFragmentView.OverviewFragmentViewListener> {

    void bind(List<FormattedGift> gifts);

    interface OverviewFragmentViewListener extends GiftItemView.GiftItemViewListener {
        void onRefresh();
    }
}
