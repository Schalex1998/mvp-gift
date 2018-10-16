package io.alexanderschaefer.u2764.view;

public abstract class DefaultEncapsulatedFragmentView<ListenerType> extends DefaultEncapsulatedView<ListenerType> implements EncapsulatedFragmentView {

    @Override
    public String getTitle() {
        return " ";
    }

    @Override
    public boolean shouldShowActionBar() {
        return true;
    }

    @Override
    public int getOptionsMenu() {
        return 0;
    }
}
