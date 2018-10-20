package io.alexanderschaefer.u2764.view.giftitemview;

import android.view.ViewGroup;

import io.alexanderschaefer.u2764.view.formatter.FormattedGift;
import io.alexanderschaefer.u2764.view.ItemView;
import io.alexanderschaefer.u2764.view.ItemViewSupplier;

public class GiftItemViewSupplier implements ItemViewSupplier<FormattedGift> {

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
