package io.alexanderschaefer.u2764.screens.opengift.opengiftdialogview;


import io.alexanderschaefer.u2764.common.EventEmitter;
import io.alexanderschaefer.u2764.screens.common.view.EncapsulatedFragmentView;
import io.alexanderschaefer.u2764.screens.common.view.ProgressView;
import io.alexanderschaefer.u2764.model.formatter.FormattedGift;

public interface OpenGiftDialogView extends ProgressView, EncapsulatedFragmentView, EventEmitter<OpenGiftDialogView.OpenGiftDialogViewListener> {

    String VIEW_STATE_ANSWERS = "answers";

    void bind(FormattedGift gift, boolean showErrors);

    interface OpenGiftDialogViewListener {
    }
}
