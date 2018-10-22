package io.alexanderschaefer.u2764.screens.giftdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.common.DialogUtil;
import io.alexanderschaefer.u2764.model.database.Gift;
import io.alexanderschaefer.u2764.model.database.GiftWithChallenges;
import io.alexanderschaefer.u2764.model.formatter.FormattedGiftFactory;
import io.alexanderschaefer.u2764.screens.ViewFactory;
import io.alexanderschaefer.u2764.screens.common.dialog.DialogManager;
import io.alexanderschaefer.u2764.screens.common.fragment.DefaultFragment;
import io.alexanderschaefer.u2764.screens.common.view.EncapsulatedFragmentView;
import io.alexanderschaefer.u2764.screens.common.view.ViewModelFactory;
import io.alexanderschaefer.u2764.screens.giftdetail.giftdetailfragmentview.GiftDetailFragmentView;
import io.alexanderschaefer.u2764.screens.opengift.OpenGiftDialog;

public class GiftDetailFragment extends DefaultFragment implements GiftDetailFragmentView.GiftDetailFragmentViewListener {

    private static final String ARG_ID = "arg_id";

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

    private GiftDetailViewModel giftDetailViewModel;
    private GiftDetailFragmentView giftDetailFragmentView;

    private String giftId;
    private GiftWithChallenges gift;

    public static GiftDetailFragment newInstance(String id) {
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
        giftId = Objects.requireNonNull(getArguments()).getString(ARG_ID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        giftDetailFragmentView = viewFactory.newInstance(GiftDetailFragmentView.class, container);
        return giftDetailFragmentView.getRootView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        giftDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(GiftDetailViewModel.class);
        giftDetailViewModel.init(giftId);
        giftDetailViewModel.getGift().observe(this, this::onGiftFetched);
    }

    @Override
    public void onStart() {
        super.onStart();
        giftDetailFragmentView.registerListener(this);
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
    }

    @Override
    public void onRefresh() {
        giftDetailFragmentView.showProgress();
        giftDetailViewModel.refresh();
    }

    @Override
    public void onGiftAction() {
        if (gift.getGift().getState() == Gift.GiftState.NEW) {
            DialogFragment dialogFragment = OpenGiftDialog.newInstance(giftId);
            dialogManager.showDialogDismissingCurrent(dialogFragment);
        } else if (gift.getGift().getState() == Gift.GiftState.OPEN) {
            dialogUtil.showConfirmDialog(getString(R.string.redeem_dialog_message), (dialog, which) -> {
                giftDetailFragmentView.showProgress();
                giftDetailViewModel.redeemGift();
            });
        }
    }

    @Override
    public EncapsulatedFragmentView getEncapsulatedView() {
        return giftDetailFragmentView;
    }

    private void onGiftFetched(GiftWithChallenges gift) {
        this.gift = gift;
        giftDetailFragmentView.bind(formattedGiftFactory.from(gift));
        giftDetailFragmentView.hideProgress();
        invalidateActionBar();
    }

//  private void onGiftManagerError(String error) {
//      giftDetailFragmentView.hideProgress();
//      Snackbar.make(giftDetailFragmentView.getRootView(), R.string.gift_manager_error, Snackbar.LENGTH_SHORT).show();
//  }
}
