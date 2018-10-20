package io.alexanderschaefer.u2764.presenter.dialog;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class DialogManager {

    private boolean isDialogShown = false;

    public void showConfirmDialog(String message, DialogInterface.OnClickListener onConfirm, Context context) {
        if (!isDialogShown) {
            new AlertDialog.Builder(context)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.yes, onConfirm)
                    .setOnDismissListener(dialog -> isDialogShown = false)
                    .setNegativeButton(android.R.string.no, null).show();
            isDialogShown = true;
        }
    }

}
