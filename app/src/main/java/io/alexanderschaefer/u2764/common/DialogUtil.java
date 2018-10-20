package io.alexanderschaefer.u2764.common;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class DialogUtil {

    private boolean isDialogShown = false;
    private Context context;

    public DialogUtil(Context context) {
        this.context = context;
    }

    public void showConfirmDialog(String message, DialogInterface.OnClickListener onConfirm) {
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
