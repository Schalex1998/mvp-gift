package io.alexanderschaefer.u2764.view.giftitemview;


import io.alexanderschaefer.u2764.model.pojo.Gift;
import io.alexanderschaefer.u2764.view.formatter.FormattedGift;
import io.alexanderschaefer.u2764.common.EventEmitter;
import io.alexanderschaefer.u2764.view.ItemView;

public interface GiftItemView extends ItemView<FormattedGift>, EventEmitter<GiftItemView.GiftItemViewListener> {

    interface GiftItemViewListener {
        void onGiftSelected(FormattedGift gift);

        void onGiftAction(FormattedGift gift);
    }
}
