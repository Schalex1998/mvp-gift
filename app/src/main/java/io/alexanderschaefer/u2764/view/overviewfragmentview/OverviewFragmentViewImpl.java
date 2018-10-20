package io.alexanderschaefer.u2764.view.overviewfragmentview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.presenter.adapter.DefaultItemAdapter;
import io.alexanderschaefer.u2764.presenter.adapter.ItemAdapter;
import io.alexanderschaefer.u2764.view.DefaultEncapsulatedFragmentView;
import io.alexanderschaefer.u2764.view.formatter.FormattedGift;
import io.alexanderschaefer.u2764.view.giftitemview.GiftItemView;
import io.alexanderschaefer.u2764.view.giftitemview.GiftItemViewSupplier;

public class OverviewFragmentViewImpl extends DefaultEncapsulatedFragmentView<OverviewFragmentView.OverviewFragmentViewListener> implements OverviewFragmentView, SwipeRefreshLayout.OnRefreshListener, GiftItemView.GiftItemViewListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ItemAdapter<FormattedGift> itemAdapter;

    public OverviewFragmentViewImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.generic_swipetorefresh_recyclerview, container, false));
        initialize();

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemAdapter = new DefaultItemAdapter<>(new GiftItemViewSupplier(this));
        recyclerView.setAdapter(itemAdapter.getAdapter());
    }

    private void initialize() {
        swipeRefreshLayout = findViewById(R.id.srl);
        recyclerView = findViewById(R.id.rv);
    }

    @Override
    public Bundle getViewState() {
        return null;
    }

    @Override
    public String getTitle() {
        return getContext().getString(R.string.fragment_overview_title);
    }

    @Override
    public int getOptionsMenu() {
        return R.menu.fragment_overview_toolbar;
    }

    @Override
    public void onRefresh() {
        forEachListener(OverviewFragmentViewListener::onRefresh);
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGiftSelected(FormattedGift gift) {
        forEachListener((listener) -> listener.onGiftSelected(gift));
    }

    @Override
    public void onGiftAction(FormattedGift gift) {
        forEachListener((listener) -> listener.onGiftAction(gift));
    }

    @Override
    public void bind(List<FormattedGift> gifts) {
        itemAdapter.setItems(gifts);
    }
}
