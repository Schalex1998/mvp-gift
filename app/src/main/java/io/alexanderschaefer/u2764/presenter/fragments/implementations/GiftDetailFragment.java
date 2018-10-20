package io.alexanderschaefer.u2764.presenter.fragments.implementations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.common.DialogUtil;
import io.alexanderschaefer.u2764.model.giftmanager.GiftManager;
import io.alexanderschaefer.u2764.model.pojo.Gift;
import io.alexanderschaefer.u2764.presenter.dialog.DialogManager;
import io.alexanderschaefer.u2764.presenter.dialog.implementations.OpenGiftDialog;
import io.alexanderschaefer.u2764.presenter.fragments.DefaultFragment;
import io.alexanderschaefer.u2764.view.EncapsulatedFragmentView;
import io.alexanderschaefer.u2764.view.ViewFactory;
import io.alexanderschaefer.u2764.view.formatter.FormattedGiftFactory;
import io.alexanderschaefer.u2764.view.giftdetailfragmentview.GiftDetailFragmentView;

public class GiftDetailFragment extends DefaultFragment implements GiftDetailFragmentView.GiftDetailFragmentViewListener, GiftManager.GiftManagerListener {

    private static final String ARG_ID = "arg_id";

    @Inject
    GiftManager giftManager;

    @Inject
    DialogUtil dialogUtil;

    @Inject
    DialogManager dialogManager;

    @Inject
    ViewFactory viewFactory;

    @Inject
    FormattedGiftFactory formattedGiftFactory;

    private GiftDetailFragmentView giftDetailFragmentView;
    private String id;
    private Gift gift;

    static GiftDetailFragment newInstance(String id) {
        GiftDetailFragment giftDetailFragment = new GiftDetailFragment();

        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        giftDetailFragment.setArguments(args);

        return giftDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);
        id = Objects.requireNonNull(getArguments()).getString(ARG_ID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        giftDetailFragmentView = viewFactory.newInstance(GiftDetailFragmentView.class, container);
        return giftDetailFragmentView.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        giftDetailFragmentView.registerListener(this);
        giftManager.registerListener(this);

        onRefresh();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                onRefresh();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        giftDetailFragmentView.unregisterListener(this);
        giftManager.unregisterListener(this);
    }

    @Override
    public void onRefresh() {
        giftDetailFragmentView.showProgress();
        giftManager.fetchGift(id);
    }

    @Override
    public void onGiftsFetched(List<Gift> gifts) {

    }

    @Override
    public void onGiftAction() {
        if (gift.getState() == Gift.GiftState.NEW) {
            DialogFragment dialogFragment = OpenGiftDialog.newInstance(gift.getId());
            dialogManager.showDialogDismissingCurrent(dialogFragment);
        } else if (gift.getState() == Gift.GiftState.OPEN) {
            dialogUtil.showConfirmDialog(getString(R.string.redeem_dialog_message), (dialog, which) -> {
                giftDetailFragmentView.showProgress();
                giftManager.redeemGift(id);
            });
        }
    }

    @Override
    public void onGiftFetched(Gift gift) {
        this.gift = gift;
        giftDetailFragmentView.bind(formattedGiftFactory.from(gift));
        giftDetailFragmentView.hideProgress();
        invalidateActionBar();
    }

    @Override
    public void onGiftManagerError(String error) {
        giftDetailFragmentView.hideProgress();
        Snackbar.make(giftDetailFragmentView.getRootView(), R.string.gift_manager_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onGiftOpened(Gift gift) {
        onRefresh();
    }

    @Override
    public void onGiftRedeemed(Gift gift) {
        onRefresh();
    }

    @Override
    public EncapsulatedFragmentView getEncapsulatedView() {
        return giftDetailFragmentView;
    }
}
