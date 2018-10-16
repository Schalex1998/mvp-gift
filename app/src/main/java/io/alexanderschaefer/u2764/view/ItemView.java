package io.alexanderschaefer.u2764.view;

public interface ItemView<ItemType> extends EncapsulatedView {
    void bind(ItemType item);
}
