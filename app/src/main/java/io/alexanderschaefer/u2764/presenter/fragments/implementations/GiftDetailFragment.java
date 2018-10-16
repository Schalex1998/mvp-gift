package io.alexanderschaefer.u2764.presenter.fragments.implementations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.model.giftmanager.GiftManager;
import io.alexanderschaefer.u2764.model.giftmanager.GiftManagerFactory;
import io.alexanderschaefer.u2764.model.pojo.Gift;
import io.alexanderschaefer.u2764.model.viewmodel.GiftViewModel;
import io.alexanderschaefer.u2764.presenter.dialog.implementations.OpenGiftDialog;
import io.alexanderschaefer.u2764.presenter.fragments.DefaultFragment;
import io.alexanderschaefer.u2764.util.DialogUtil;
import io.alexanderschaefer.u2764.view.EncapsulatedFragmentView;
import io.alexanderschaefer.u2764.view.giftdetailfragmentview.GiftDetailFragmentView;
import io.alexanderschaefer.u2764.view.giftdetailfragmentview.GiftDetailFragmentViewFactory;

public class GiftDetailFragment extends DefaultFragment implements GiftDetailFragmentView.GiftDetailFragmentViewListener, GiftManager.GiftManagerListener {
    private static final String ARG_ID = "arg_id";

    private GiftDetailFragmentView giftDetailFragmentView;
    private GiftManager giftManager;
    private String id;
    private Gift gift;

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
        giftManager = GiftManagerFactory.createInstance(getContext().getApplicationContext());
        id = getArguments().getString(ARG_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        giftDetailFragmentView = GiftDetailFragmentViewFactory.createInstance(inflater, container);
        return giftDetailFragmentView.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        giftDetailFragmentView.registerListener(this);
        giftManager.registerListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void onGiftAction() {
        if (gift.getState() == Gift.GiftState.NEW) {
            OpenGiftDialog.display(getFragmentManager(), gift.getId());
        } else if (gift.getState() == Gift.GiftState.OPEN) {
            DialogUtil.showConfirmDialog(getString(R.string.redeem_dialog_message), (dialog, which) -> {
                giftManager.redeemGift(id);
                onRefresh();
            }, getContext());
        }
    }

    @Override
    public void onRefresh() {
        giftDetailFragmentView.showProgress();
        giftManager.fetchGift(id);
    }

    @Override
    public void onGiftsFetched(List<Gift> gifts) {
        gift = gifts.get(0);
        giftDetailFragmentView.bind(new GiftViewModel(gift, getContext()));
        giftDetailFragmentView.hideProgress();
        invalidateActionBar();
    }

    @Override
    public void onGiftOpened(Gift gift) {

    }

    @Override
    public EncapsulatedFragmentView getEncapsulatedView() {
        return giftDetailFragmentView;
    }
}
