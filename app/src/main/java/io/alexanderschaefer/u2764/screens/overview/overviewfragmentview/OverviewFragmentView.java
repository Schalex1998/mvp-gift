package io.alexanderschaefer.u2764.screens.overview.overviewfragmentview;


import java.util.List;

import io.alexanderschaefer.u2764.common.EventEmitter;
import io.alexanderschaefer.u2764.screens.common.view.EncapsulatedFragmentView;
import io.alexanderschaefer.u2764.screens.common.view.ProgressView;
import io.alexanderschaefer.u2764.model.formatter.FormattedGift;
import io.alexanderschaefer.u2764.screens.overview.giftitemview.GiftItemView;

public interface OverviewFragmentView extends ProgressView, EncapsulatedFragmentView, EventEmitter<OverviewFragmentView.OverviewFragmentViewListener> {

    void bind(List<FormattedGift> gifts);

    interface OverviewFragmentViewListener extends GiftItemView.GiftItemViewListener {
        void onRefresh();
    }
}
