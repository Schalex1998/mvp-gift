package io.alexanderschaefer.u2764.view.overviewfragmentview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.view.DefaultEncapsulatedFragmentView;

class OverviewFragmentViewImpl extends DefaultEncapsulatedFragmentView<OverviewFragmentView.OverviewFragmentViewListener> implements OverviewFragmentView, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    OverviewFragmentViewImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.generic_swipetorefresh_recyclerview, container, false));
        initialize();

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
