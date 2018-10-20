package io.alexanderschaefer.u2764.presenter.fragments.implementations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

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
import io.alexanderschaefer.u2764.view.formatter.FormattedGift;
import io.alexanderschaefer.u2764.view.formatter.FormattedGiftFactory;
import io.alexanderschaefer.u2764.view.giftitemview.GiftItemView;
import io.alexanderschaefer.u2764.view.overviewfragmentview.OverviewFragmentView;

public class OverviewFragment extends DefaultFragment implements OverviewFragmentView.OverviewFragmentViewListener, GiftManager.GiftManagerListener, GiftItemView.GiftItemViewListener {

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

    private OverviewFragmentView overviewFragmentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        overviewFragmentView = viewFactory.newInstance(OverviewFragmentView.class, container);
        return overviewFragmentView.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        overviewFragmentView.registerListener(this);
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
        overviewFragmentView.unregisterListener(this);
        giftManager.unregisterListener(this);
    }

    @Override
    public void onRefresh() {
        overviewFragmentView.showProgress();
        giftManager.fetchGifts();
    }

    @Override
    public EncapsulatedFragmentView getEncapsulatedView() {
        return overviewFragmentView;
    }

    @Override
    public void onGiftsFetched(List<Gift> gifts) {
        overviewFragmentView.bind(formattedGiftFactory.from(gifts));
        overviewFragmentView.hideProgress();
    }

    @Override
    public void onGiftSelected(FormattedGift gift) {
        navigateTo(GiftDetailFragment.newInstance(gift.getId()), true, false, true);
    }

    @Override
    public void onGiftAction(FormattedGift gift) {
        if (gift.getState() == Gift.GiftState.NEW) {
            DialogFragment dialogFragment = OpenGiftDialog.newInstance(gift.getId());
            dialogManager.showDialogDismissingCurrent(dialogFragment);
        } else if (gift.getState() == Gift.GiftState.OPEN) {
            dialogUtil.showConfirmDialog(getString(R.string.redeem_dialog_message), (dialog, which) -> {
                overviewFragmentView.showProgress();
                giftManager.redeemGift(gift.getId());
            });
        }
    }

    @Override
    public void onGiftFetched(Gift gift) {

    }

    @Override
    public void onGiftManagerError(String error) {
        overviewFragmentView.hideProgress();
        Snackbar.make(overviewFragmentView.getRootView(), R.string.gift_manager_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onGiftOpened(Gift gift) {
        onRefresh();
    }

    @Override
    public void onGiftRedeemed(Gift gift) {
        onRefresh();
    }
}
