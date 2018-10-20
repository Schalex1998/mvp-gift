package io.alexanderschaefer.u2764.view.giftdetailfragmentview;


import io.alexanderschaefer.u2764.view.formatter.FormattedGift;
import io.alexanderschaefer.u2764.common.EventEmitter;
import io.alexanderschaefer.u2764.view.EncapsulatedFragmentView;
import io.alexanderschaefer.u2764.view.ProgressView;

public interface GiftDetailFragmentView extends ProgressView, EncapsulatedFragmentView, EventEmitter<GiftDetailFragmentView.GiftDetailFragmentViewListener> {

    void bind(FormattedGift gift);

    interface GiftDetailFragmentViewListener {
        void onGiftAction();

        void onRefresh();
    }
}
