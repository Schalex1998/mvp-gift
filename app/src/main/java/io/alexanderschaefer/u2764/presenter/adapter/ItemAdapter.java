package io.alexanderschaefer.u2764.presenter.adapter;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemAdapter<ItemType> {

    void setItems(List<ItemType> items);

    RecyclerView.Adapter getAdapter();
}
