package io.alexanderschaefer.u2764.screens.giftdetail.giftdetailfragmentview;


import io.alexanderschaefer.u2764.common.EventEmitter;
import io.alexanderschaefer.u2764.screens.common.view.EncapsulatedFragmentView;
import io.alexanderschaefer.u2764.screens.common.view.ProgressView;
import io.alexanderschaefer.u2764.model.formatter.FormattedGift;

public interface GiftDetailFragmentView extends ProgressView, EncapsulatedFragmentView, EventEmitter<GiftDetailFragmentView.GiftDetailFragmentViewListener> {

    void bind(FormattedGift gift);

    interface GiftDetailFragmentViewListener {
        void onGiftAction();

        void onRefresh();
    }
}
