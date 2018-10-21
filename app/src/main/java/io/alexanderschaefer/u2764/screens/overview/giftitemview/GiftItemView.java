package io.alexanderschaefer.u2764.screens.overview.giftitemview;


import io.alexanderschaefer.u2764.common.EventEmitter;
import io.alexanderschaefer.u2764.screens.common.view.ItemView;
import io.alexanderschaefer.u2764.model.formatter.FormattedGift;

public interface GiftItemView extends ItemView<FormattedGift>, EventEmitter<GiftItemView.GiftItemViewListener> {

    interface GiftItemViewListener {
        void onGiftSelected(FormattedGift gift);

        void onGiftAction(FormattedGift gift);
    }
}
