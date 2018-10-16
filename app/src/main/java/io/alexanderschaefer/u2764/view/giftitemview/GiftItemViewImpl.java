package io.alexanderschaefer.u2764.view.giftitemview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.model.pojo.Gift;
import io.alexanderschaefer.u2764.model.viewmodel.GiftViewModel;
import io.alexanderschaefer.u2764.view.DefaultEncapsulatedView;

class GiftItemViewImpl extends DefaultEncapsulatedView<GiftItemView.GiftItemViewListener> implements GiftItemView {

    private TextView textViewName;
    private TextView textViewStatus;
    private TextView textViewDescription;
    private Button buttonAction;
    private LinearLayout linearLayoutMain;

    GiftItemViewImpl(ViewGroup parent) {
        setRootView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_gift, parent, false));
        initialize();
    }

    private void initialize() {
        textViewName = findViewById(R.id.tv_name);
        textViewStatus = findViewById(R.id.tv_status);
        textViewDescription = findViewById(R.id.tv_description);
        buttonAction = findViewById(R.id.b_action);
        linearLayoutMain = findViewById(R.id.ll_main);
    }

    @Override
    public void bind(GiftViewModel item) {
        buttonAction.setVisibility(View.VISIBLE);
        buttonAction.setText(item.getActionText());
        textViewStatus.setTextColor(getContext().getColor(android.R.color.black));
        textViewStatus.setText(item.getStatusText());
        textViewName.setText(item.getName());
        textViewDescription.setText(item.getDescription());
        if (item.getState() == Gift.GiftState.REDEEMED) {
            textViewStatus.setTextColor(getContext().getColor(R.color.redeemed_gift));
            buttonAction.setVisibility(View.GONE);
        }
        linearLayoutMain.setOnClickListener(v -> forEachListener((listener) -> listener.onGiftSelected(item.getGift())));
        buttonAction.setOnClickListener(v -> forEachListener((listener) -> listener.onGiftAction(item.getGift())));
    }
}
