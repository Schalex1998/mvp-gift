package io.alexanderschaefer.u2764.screens.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.common.DialogUtil;
import io.alexanderschaefer.u2764.model.database.Gift;
import io.alexanderschaefer.u2764.model.database.GiftWithChallenges;
import io.alexanderschaefer.u2764.model.formatter.FormattedGift;
import io.alexanderschaefer.u2764.model.formatter.FormattedGiftFactory;
import io.alexanderschaefer.u2764.screens.ViewFactory;
import io.alexanderschaefer.u2764.screens.common.dialog.DialogManager;
import io.alexanderschaefer.u2764.screens.common.fragment.DefaultFragment;
import io.alexanderschaefer.u2764.screens.common.view.EncapsulatedFragmentView;
import io.alexanderschaefer.u2764.screens.common.view.ViewModelFactory;
import io.alexanderschaefer.u2764.screens.giftdetail.GiftDetailFragment;
import io.alexanderschaefer.u2764.screens.opengift.OpenGiftDialog;
import io.alexanderschaefer.u2764.screens.overview.giftitemview.GiftItemView;
import io.alexanderschaefer.u2764.screens.overview.overviewfragmentview.OverviewFragmentView;

public class OverviewFragment extends DefaultFragment implements OverviewFragmentView.OverviewFragmentViewListener, GiftItemView.GiftItemViewListener {

    @Inject
    DialogUtil dialogUtil;
    @Inject
    DialogManager dialogManager;
    @Inject
    ViewFactory viewFactory;
    @Inject
    FormattedGiftFactory formattedGiftFactory;
    @Inject
    ViewModelFactory viewModelFactory;

    private OverviewViewModel overviewViewModel;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        overviewViewModel = ViewModelProviders.of(this, viewModelFactory).get(OverviewViewModel.class);
        overviewViewModel.init();
        overviewViewModel.getGifts().observe(this, this::onGiftsFetched);
    }

    @Override
    public void onStart() {
        super.onStart();
        overviewFragmentView.registerListener(this);
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
    }

    @Override
    public void onRefresh() {
        overviewFragmentView.showProgress();
        //overviewViewModel.fetchGifts();
    }

    @Override
    public EncapsulatedFragmentView getEncapsulatedView() {
        return overviewFragmentView;
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
                overviewViewModel.redeemGift(gift.getId());
            });
        }
    }

    private void onGiftsFetched(List<GiftWithChallenges> gifts) {
        overviewFragmentView.bind(formattedGiftFactory.fromGiftWithChallenges(gifts));
        overviewFragmentView.hideProgress();
    }

//  private void onGiftManagerError(String error) {
//      overviewFragmentView.hideProgress();
//      Snackbar.make(overviewFragmentView.getRootView(), R.string.gift_manager_error, Snackbar.LENGTH_SHORT).show();
//  }
}
