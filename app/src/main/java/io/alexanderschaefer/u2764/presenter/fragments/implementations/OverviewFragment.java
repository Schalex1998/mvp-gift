package io.alexanderschaefer.u2764.presenter.fragments.implementations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.model.giftmanager.GiftManager;
import io.alexanderschaefer.u2764.model.pojo.Gift;
import io.alexanderschaefer.u2764.model.viewmodel.GiftViewModel;
import io.alexanderschaefer.u2764.presenter.adapter.DefaultItemAdapter;
import io.alexanderschaefer.u2764.presenter.adapter.ItemAdapter;
import io.alexanderschaefer.u2764.presenter.dialog.implementations.OpenGiftDialog;
import io.alexanderschaefer.u2764.presenter.fragments.DefaultFragment;
import io.alexanderschaefer.u2764.presenter.dialog.DialogManager;
import io.alexanderschaefer.u2764.view.EncapsulatedFragmentView;
import io.alexanderschaefer.u2764.view.ViewFactory;
import io.alexanderschaefer.u2764.view.giftitemview.GiftItemView;
import io.alexanderschaefer.u2764.view.giftitemview.GiftItemViewSupplier;
import io.alexanderschaefer.u2764.view.overviewfragmentview.OverviewFragmentView;

public class OverviewFragment extends DefaultFragment implements OverviewFragmentView.OverviewFragmentViewListener, GiftManager.GiftManagerListener, GiftItemView.GiftItemViewListener {

    @Inject
    GiftManager giftManager;

    @Inject
    DialogManager dialogManager;

    @Inject
    ViewFactory viewFactory;

    private OverviewFragmentView overviewFragmentView;
    private ItemAdapter<GiftViewModel> giftItemAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);
        giftItemAdapter = new DefaultItemAdapter<>(new GiftItemViewSupplier(this));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        overviewFragmentView = viewFactory.newInstance(OverviewFragmentView.class, container);
        return overviewFragmentView.getRootView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        overviewFragmentView.setAdapter(giftItemAdapter.getAdapter());
    }

    @Override
    public void onStart() {
        super.onStart();
        overviewFragmentView.registerListener(this);
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
        overviewFragmentView.unregisterListener(this);
        giftManager.unregisterListener(this);
    }

    @Override
    public void onRefresh() {
        overviewFragmentView.showProgress();
        giftManager.fetchGifts();
    }

    @Override
    public void onGiftsFetched(List<Gift> gifts) {
        giftItemAdapter.setItems(GiftViewModel.from(gifts, getContext()));
        overviewFragmentView.hideProgress();
    }

    @Override
    public void onGiftOpened(Gift gift) {

    }

    @Override
    public EncapsulatedFragmentView getEncapsulatedView() {
        return overviewFragmentView;
    }

    @Override
    public void onGiftSelected(Gift gift) {
        navigateTo(GiftDetailFragment.newInstance(gift.getId()), true, false, true);
    }

    @Override
    public void onGiftAction(Gift gift) {
        if (gift.getState() == Gift.GiftState.NEW) {
            OpenGiftDialog.display(getFragmentManager(), gift.getId());
        } else if (gift.getState() == Gift.GiftState.OPEN) {
            dialogManager.showConfirmDialog(getString(R.string.redeem_dialog_message), (dialog, which) -> {
                giftManager.redeemGift(gift.getId());
                onRefresh();
            }, getContext());
        }
    }
}
