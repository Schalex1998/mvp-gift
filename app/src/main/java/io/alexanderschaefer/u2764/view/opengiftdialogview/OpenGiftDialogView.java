package io.alexanderschaefer.u2764.view.opengiftdialogview;


import io.alexanderschaefer.u2764.view.EncapsulatedFragmentView;
import io.alexanderschaefer.u2764.view.ProgressView;
import io.alexanderschaefer.u2764.view.formatter.FormattedGift;

public interface OpenGiftDialogView extends ProgressView, EncapsulatedFragmentView {

    String VIEW_STATE_ANSWERS = "answers";

    void bind(FormattedGift gift, boolean initial);
}
