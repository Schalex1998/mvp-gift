package io.alexanderschaefer.u2764.view.opengiftdialogview;


import io.alexanderschaefer.u2764.model.viewmodel.GiftViewModel;
import io.alexanderschaefer.u2764.util.EventEmitter;
import io.alexanderschaefer.u2764.view.EncapsulatedFragmentView;
import io.alexanderschaefer.u2764.view.ProgressView;

public interface OpenGiftDialogView extends ProgressView, EncapsulatedFragmentView, EventEmitter<OpenGiftDialogView.OpenGiftDialogViewListener> {

    String VIEW_STATE_ANSWERS = "answers";

    void bind(GiftViewModel gift, boolean initial);

    interface OpenGiftDialogViewListener {
        void onOpenGift();
    }
}
