package io.alexanderschaefer.u2764.view.giftdetailfragmentview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.model.pojo.Gift;
import io.alexanderschaefer.u2764.view.formatter.FormattedChallenge;
import io.alexanderschaefer.u2764.view.formatter.FormattedGift;
import io.alexanderschaefer.u2764.view.DefaultEncapsulatedFragmentView;

public class GiftDetailFragmentViewImpl extends DefaultEncapsulatedFragmentView<GiftDetailFragmentView.GiftDetailFragmentViewListener> implements GiftDetailFragmentView, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView textViewDescription;
    private TextView textViewStatus;
    private TextView textViewId;
    private TextView textViewChallenges;
    private LinearLayout linearLayoutChallenges;
    private MaterialButton buttonAction;
    private String title = " ";

    public GiftDetailFragmentViewImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.fragment_gift_detail, container, false));
        initialize();

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setEnabled(false);
    }

    private void initialize() {
        swipeRefreshLayout = findViewById(R.id.srl);
        textViewDescription = findViewById(R.id.tv_description);
        textViewStatus = findViewById(R.id.tv_status);
        textViewId = findViewById(R.id.tv_id);
        textViewChallenges = findViewById(R.id.tv_challenges);
        linearLayoutChallenges = findViewById(R.id.ll_challenges);
        buttonAction = findViewById(R.id.b_action);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }

    @Override
    public int getOptionsMenu() {
        return R.menu.fragment_gift_detail_toolbar;
    }

    @Override
    public void onRefresh() {
        forEachListener(GiftDetailFragmentViewListener::onRefresh);
    }

    @Override
    public void bind(FormattedGift gift) {
        title = gift.getName();
        buttonAction.setVisibility(View.VISIBLE);
        buttonAction.setText(gift.getActionText());
        textViewId.setText(gift.getId());
        textViewStatus.setText(gift.getStatusText());
        textViewDescription.setText(gift.getDescription());
        textViewChallenges.setText(String.valueOf(gift.getChallenges().size()));
        if (gift.getState() == Gift.GiftState.REDEEMED)
            buttonAction.setVisibility(View.GONE);

        linearLayoutChallenges.removeAllViews();
        for (FormattedChallenge formattedChallenge : gift.getChallenges()) {
            addChallengePropertyItem(formattedChallenge);
        }

        buttonAction.setOnClickListener(v -> forEachListener(GiftDetailFragmentViewListener::onGiftAction));
    }

    private void addChallengePropertyItem(FormattedChallenge formattedChallenge) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (inflater != null) {
            View view = inflater.inflate(R.layout.generic_property_item, linearLayoutChallenges, false);
            TextView textViewLabel = view.findViewById(R.id.tv_label);
            TextView textViewProperty = view.findViewById(R.id.tv_property);
            textViewLabel.setText(formattedChallenge.getQuestion());
            textViewProperty.setText(formattedChallenge.getGivenAnswer());
            linearLayoutChallenges.addView(view);
        }
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
