package io.alexanderschaefer.u2764.view.opengiftdialogview;


import io.alexanderschaefer.u2764.view.formatter.FormattedGift;
import io.alexanderschaefer.u2764.common.EventEmitter;
import io.alexanderschaefer.u2764.view.EncapsulatedFragmentView;
import io.alexanderschaefer.u2764.view.ProgressView;

public interface OpenGiftDialogView extends ProgressView, EncapsulatedFragmentView, EventEmitter<OpenGiftDialogView.OpenGiftDialogViewListener> {

    String VIEW_STATE_ANSWERS = "answers";

    void bind(FormattedGift gift, boolean initial);

    interface OpenGiftDialogViewListener {
        void onOpenGift();
    }
}
