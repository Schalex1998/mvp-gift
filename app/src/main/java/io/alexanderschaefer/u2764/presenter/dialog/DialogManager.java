package io.alexanderschaefer.u2764.presenter.dialog;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

@UiThread
public class DialogManager {

    private static final String DIALOG_FRAGMENT_TAG = "DIALOG_FRAGMENT_TAG";

    private FragmentManager fragmentManager;
    private DialogFragment currentlyShownDialog;

    public DialogManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        Fragment fragmentWithDialogTag = fragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG);
        if (fragmentWithDialogTag != null && DialogFragment.class.isAssignableFrom(fragmentWithDialogTag.getClass())) {
            currentlyShownDialog = (DialogFragment) fragmentWithDialogTag;
        }
    }

    @Nullable
    public DialogFragment getCurrentlyShownDialog() {
        return currentlyShownDialog;
    }

    public void dismissCurrentlyShownDialog() {
        if (currentlyShownDialog != null) {
            currentlyShownDialog.dismissAllowingStateLoss();
            currentlyShownDialog = null;
        }
    }

    public void showDialogDismissingCurrent(DialogFragment dialog) {
        dismissCurrentlyShownDialog();
        showDialog(dialog);
    }

    private void showDialog(DialogFragment dialog) {
        fragmentManager.beginTransaction()
                .add(dialog, DIALOG_FRAGMENT_TAG)
                .commitAllowingStateLoss();
        currentlyShownDialog = dialog;
    }

}