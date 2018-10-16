package io.alexanderschaefer.u2764.view.opengiftdialogview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

public class OpenGiftDialogViewFactory {

    public static OpenGiftDialogView createInstance(LayoutInflater inflater, ViewGroup container) {
        return new OpenGiftDialogViewImpl(inflater, container);
    }
}
