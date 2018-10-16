package io.alexanderschaefer.u2764.view;

import android.view.ViewGroup;

public interface ItemViewSupplier<ItemType> {
    ItemView<ItemType> supply(ViewGroup parent);
}
