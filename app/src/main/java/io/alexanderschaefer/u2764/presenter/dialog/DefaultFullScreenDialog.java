package io.alexanderschaefer.u2764.presenter.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.view.EncapsulatedFragmentView;

public abstract class DefaultFullScreenDialog extends DialogFragment {

    public static String TAG = "default_full_screen_dialog";
    private Toolbar toolbar;
    private FrameLayout frameLayoutContainer;

    protected abstract boolean handleMenuItemClick(int itemId);

    protected abstract EncapsulatedFragmentView getEncapsulatedView();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getEncapsulatedView() != null) {
            toolbar.setTitle(getEncapsulatedView().getTitle());
            if (getEncapsulatedView().shouldShowActionBar()) {
                toolbar.setVisibility(View.VISIBLE);
            } else {
                toolbar.setVisibility(View.GONE);
            }
            if (getEncapsulatedView().getOptionsMenu() != 0) {
                toolbar.inflateMenu(getEncapsulatedView().getOptionsMenu());
                toolbar.setOnMenuItemClickListener(item -> handleMenuItemClick(item.getItemId()));
            }
            frameLayoutContainer.addView(getEncapsulatedView().getRootView());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_default_fullscreen, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> dismiss());

        frameLayoutContainer = view.findViewById(R.id.container);

        return view;
    }
}
