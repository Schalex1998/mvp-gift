package io.alexanderschaefer.u2764.view.overviewfragmentview;


import androidx.recyclerview.widget.RecyclerView;
import io.alexanderschaefer.u2764.util.EventEmitter;
import io.alexanderschaefer.u2764.view.EncapsulatedFragmentView;
import io.alexanderschaefer.u2764.view.ProgressView;

public interface OverviewFragmentView extends ProgressView, EncapsulatedFragmentView, EventEmitter<OverviewFragmentView.OverviewFragmentViewListener> {

    void setAdapter(RecyclerView.Adapter adapter);

    interface OverviewFragmentViewListener {
        void onRefresh();
    }
}
