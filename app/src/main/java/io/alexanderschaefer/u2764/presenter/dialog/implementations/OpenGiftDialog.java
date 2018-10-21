package io.alexanderschaefer.u2764.presenter.dialog.implementations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.model.pojo.Gift;
import io.alexanderschaefer.u2764.presenter.dialog.DefaultFullScreenDialog;
import io.alexanderschaefer.u2764.presenter.dialog.DialogManager;
import io.alexanderschaefer.u2764.presenter.viewmodel.OpenGiftViewModel;
import io.alexanderschaefer.u2764.presenter.viewmodel.ViewModelFactory;
import io.alexanderschaefer.u2764.view.EncapsulatedFragmentView;
import io.alexanderschaefer.u2764.view.ViewFactory;
import io.alexanderschaefer.u2764.view.formatter.FormattedGift;
import io.alexanderschaefer.u2764.view.formatter.FormattedGiftFactory;
import io.alexanderschaefer.u2764.view.opengiftdialogview.OpenGiftDialogView;

public class OpenGiftDialog extends DefaultFullScreenDialog {

    private static final String ARG_ID = "arg_id";

    @Inject
    ViewFactory viewFactory;
    @Inject
    DialogManager dialogManager;
    @Inject
    FormattedGiftFactory formattedGiftFactory;
    @Inject
    ViewModelFactory viewModelFactory;

    private OpenGiftDialogView openGiftDialogView;
    private OpenGiftViewModel openGiftViewModel;
    private String giftId;
    private boolean initialBinding;

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
        giftId = Objects.requireNonNull(getArguments()).getString(ARG_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        openGiftDialogView = viewFactory.newInstance(OpenGiftDialogView.class, container);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        openGiftViewModel = ViewModelProviders.of(this, viewModelFactory).get(OpenGiftViewModel.class);
        openGiftViewModel.getGift().observe(this, this::onGiftFetched);
    }

    @Override
    public void onStart() {
        super.onStart();
        initialBinding = true;
        openGiftDialogView.showProgress();
        openGiftViewModel.fetchGift(giftId);
    }

    @Override
    protected boolean handleMenuItemClick(int itemId) {
        if (itemId == R.id.action_submit) {
            onSubmit();
            return true;
        }
        return false;
    }

    @Override
    protected EncapsulatedFragmentView getEncapsulatedView() {
        return openGiftDialogView;
    }

    private void onSubmit() {
        Bundle bundle = openGiftDialogView.getViewState();
        if (bundle != null) {
            ArrayList<String> answers = bundle.getStringArrayList(openGiftDialogView.VIEW_STATE_ANSWERS);
            openGiftDialogView.showProgress();
            openGiftViewModel.openGift(giftId, answers);
        }
    }

    private void onGiftFetched(Gift gift) {
        FormattedGift formattedGift = formattedGiftFactory.from(gift);

        if (formattedGift.getState() == Gift.GiftState.OPEN) {
            dialogManager.dismissCurrentlyShownDialog();
            return;
        }

        openGiftDialogView.bind(formattedGift, initialBinding);
        openGiftDialogView.hideProgress();
        initialBinding = false;
    }

//  private void onGiftManagerError(String error) {
//      openGiftDialogView.hideProgress();
//      Snackbar.make(openGiftDialogView.getRootView(), R.string.gift_manager_error, Snackbar.LENGTH_SHORT).show();
//  }
}
