package io.alexanderschaefer.u2764.presenter.dialog.implementations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.fragment.app.FragmentManager;
import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.model.giftmanager.GiftManager;
import io.alexanderschaefer.u2764.model.pojo.Gift;
import io.alexanderschaefer.u2764.model.viewmodel.GiftViewModel;
import io.alexanderschaefer.u2764.presenter.dialog.DefaultFullScreenDialog;
import io.alexanderschaefer.u2764.view.EncapsulatedFragmentView;
import io.alexanderschaefer.u2764.view.ViewFactory;
import io.alexanderschaefer.u2764.view.opengiftdialogview.OpenGiftDialogView;

public class OpenGiftDialog extends DefaultFullScreenDialog implements GiftManager.GiftManagerListener, OpenGiftDialogView.OpenGiftDialogViewListener {

    private static final String TAG = "open_gift_dialog";
    private static final String ARG_ID = "arg_id";

    @Inject
    GiftManager giftManager;

    @Inject
    ViewFactory viewFactory;

    private OpenGiftDialogView openGiftDialogView;
    private String id;

    public static OpenGiftDialog display(FragmentManager fragmentManager, String giftId) {
        OpenGiftDialog openGiftDialog = new OpenGiftDialog();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_ID, giftId);
        openGiftDialog.setArguments(bundle);
        openGiftDialog.show(fragmentManager, TAG);
        return openGiftDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);
        id = getArguments().getString(ARG_ID);
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
        openGiftDialogView.registerListener(this);
        giftManager.registerListener(this);

        openGiftDialogView.showProgress();
        giftManager.fetchGift(id);
    }

    @Override
    public void onStop() {
        super.onStop();
        openGiftDialogView.unregisterListener(this);
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
        openGiftDialogView.showProgress();
        Bundle bundle = openGiftDialogView.getViewState();
        ArrayList<String> answers = bundle.getStringArrayList(openGiftDialogView.VIEW_STATE_ANSWERS);
        giftManager.openGift(id, answers);
    }

    @Override
    protected EncapsulatedFragmentView getEncapsulatedView() {
        return openGiftDialogView;
    }

    @Override
    public void onGiftsFetched(List<Gift> gifts) {
        openGiftDialogView.bind(new GiftViewModel(gifts.get(0), getContext()), true);
        openGiftDialogView.hideProgress();
    }

    @Override
    public void onGiftOpened(Gift gift) {
        openGiftDialogView.bind(new GiftViewModel(gift, getContext()), false);
        openGiftDialogView.hideProgress();
    }

    @Override
    public void onOpenGift() {
        openGiftDialogView.showProgress();
    }
}
