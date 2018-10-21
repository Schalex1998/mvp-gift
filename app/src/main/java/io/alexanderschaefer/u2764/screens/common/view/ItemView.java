package io.alexanderschaefer.u2764.screens.common.view;

public interface ItemView<ItemType> extends EncapsulatedView {

    void bind(ItemType item);
}
