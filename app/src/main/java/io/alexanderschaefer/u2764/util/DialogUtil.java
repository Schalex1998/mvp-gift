package io.alexanderschaefer.u2764.util;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class DialogUtil {

    public static void showConfirmDialog(String message, DialogInterface.OnClickListener onConfirm, Context context) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, onConfirm)
                .setNegativeButton(android.R.string.no, null).show();
    }

}
