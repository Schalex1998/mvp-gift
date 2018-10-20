package io.alexanderschaefer.u2764.presenter.dialog.implementations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.model.giftmanager.GiftManager;
import io.alexanderschaefer.u2764.model.pojo.Gift;
import io.alexanderschaefer.u2764.presenter.dialog.DefaultFullScreenDialog;
import io.alexanderschaefer.u2764.presenter.dialog.DialogManager;
import io.alexanderschaefer.u2764.view.EncapsulatedFragmentView;
import io.alexanderschaefer.u2764.view.ViewFactory;
import io.alexanderschaefer.u2764.view.formatter.FormattedGift;
import io.alexanderschaefer.u2764.view.formatter.FormattedGiftFactory;
import io.alexanderschaefer.u2764.view.opengiftdialogview.OpenGiftDialogView;

public class OpenGiftDialog extends DefaultFullScreenDialog implements GiftManager.GiftManagerListener, OpenGiftDialogView.OpenGiftDialogViewListener {

    private static final String ARG_ID = "arg_id";

    @Inject
    GiftManager giftManager;

    @Inject
    ViewFactory viewFactory;

    @Inject
    DialogManager dialogManager;

    @Inject
    FormattedGiftFactory formattedGiftFactory;

    private OpenGiftDialogView openGiftDialogView;
    private String id;

    public static OpenGiftDialog newInstance(String giftId) {
        OpenGiftDialog openGiftDialog = new OpenGiftDialog();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_ID, giftId);
        openGiftDialog.setArguments(bundle);
        return openGiftDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);
        id = Objects.requireNonNull(getArguments()).getString(ARG_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        openGiftDialogView = viewFactory.newInstance(OpenGiftDialogView.class, container);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        giftManager.registerListener(this);

        openGiftDialogView.showProgress();
        giftManager.fetchGift(id);
    }

    @Override
    public void onStop() {
        super.onStop();
        giftManager.unregisterListener(this);
    }

    @Override
    protected boolean handleMenuItemClick(int itemId) {
        if (itemId == R.id.action_submit) {
            onSubmit();
            return true;
        } else {
            return false;
        }
    }

    private void onSubmit() {
        Bundle bundle = openGiftDialogView.getViewState();
        if (bundle != null) {
            ArrayList<String> answers = bundle.getStringArrayList(openGiftDialogView.VIEW_STATE_ANSWERS);
            openGiftDialogView.showProgress();
            giftManager.openGift(id, answers);
        }
    }

    @Override
    protected EncapsulatedFragmentView getEncapsulatedView() {
        return openGiftDialogView;
    }

    @Override
    public void onGiftsFetched(List<Gift> gifts) {

    }

    @Override
    public void onGiftFetched(Gift gift) {
        openGiftDialogView.bind(formattedGiftFactory.from(gift), true);
        openGiftDialogView.hideProgress();
    }

    @Override
    public void onGiftManagerError(String error) {
        openGiftDialogView.hideProgress();
        Snackbar.make(openGiftDialogView.getRootView(), R.string.gift_manager_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onGiftOpened(Gift gift) {
        FormattedGift formattedGift = formattedGiftFactory.from(gift);

        if (formattedGift.getState() == Gift.GiftState.OPEN) {
            dialogManager.dismissCurrentlyShownDialog();
            return;
        }

        openGiftDialogView.bind(formattedGift, false);
        openGiftDialogView.hideProgress();
    }

    @Override
    public void onGiftRedeemed(Gift gift) {

    }
}
