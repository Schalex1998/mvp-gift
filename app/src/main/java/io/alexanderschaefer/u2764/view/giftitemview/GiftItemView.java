package io.alexanderschaefer.u2764.view.giftitemview;


import io.alexanderschaefer.u2764.model.pojo.Gift;
import io.alexanderschaefer.u2764.model.viewmodel.GiftViewModel;
import io.alexanderschaefer.u2764.util.EventEmitter;
import io.alexanderschaefer.u2764.view.ItemView;

public interface GiftItemView extends ItemView<GiftViewModel>, EventEmitter<GiftItemView.GiftItemViewListener> {

    interface GiftItemViewListener {
        void onGiftSelected(Gift gift);

        void onGiftAction(Gift gift);
    }
}
