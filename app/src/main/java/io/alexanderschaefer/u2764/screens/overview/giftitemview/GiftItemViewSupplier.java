package io.alexanderschaefer.u2764.screens.overview.giftitemview;

import android.view.ViewGroup;

import io.alexanderschaefer.u2764.common.DefaultEventEmitter;
import io.alexanderschaefer.u2764.screens.common.view.ItemView;
import io.alexanderschaefer.u2764.screens.common.view.ItemViewSupplier;
import io.alexanderschaefer.u2764.model.formatter.FormattedGift;

public class GiftItemViewSupplier extends DefaultEventEmitter<GiftItemView.GiftItemViewListener> implements ItemViewSupplier<FormattedGift> {

    private GiftItemView.GiftItemViewListener listener;

    public GiftItemViewSupplier(GiftItemView.GiftItemViewListener listener) {
        this.listener = listener;
    }

    @Override
    public ItemView<FormattedGift> supply(ViewGroup parent) {
        GiftItemView itemView = new GiftItemViewImpl(parent);
        itemView.registerListener(listener);
        return itemView;
    }
}
