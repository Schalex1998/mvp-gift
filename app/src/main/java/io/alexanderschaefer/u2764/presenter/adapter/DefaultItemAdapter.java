package io.alexanderschaefer.u2764.presenter.adapter;

import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import io.alexanderschaefer.u2764.view.ItemView;
import io.alexanderschaefer.u2764.view.ItemViewSupplier;

public class DefaultItemAdapter<ItemType> extends RecyclerView.Adapter<DefaultItemAdapter.ViewHolder> implements ItemAdapter<ItemType> {

    private ItemViewSupplier<ItemType> itemViewSupplier;
    private List<ItemType> items;

    public DefaultItemAdapter(ItemViewSupplier<ItemType> itemViewSupplier) {
        this.itemViewSupplier = itemViewSupplier;
    }

    @Override
    public void setItems(List<ItemType> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    @Override
    public DefaultItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DefaultItemAdapter.ViewHolder(itemViewSupplier.supply(parent));
    }

    @Override
    public void onBindViewHolder(DefaultItemAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemView<ItemType> itemView;

        ViewHolder(ItemView<ItemType> itemView) {
            super(itemView.getRootView());
            this.itemView = itemView;
        }

        void bind(ItemType item) {
            itemView.bind(item);
        }
    }
}
