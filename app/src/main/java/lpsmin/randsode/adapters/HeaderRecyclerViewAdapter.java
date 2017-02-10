package lpsmin.randsode.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lpsmin.randsode.adapters.holders.HeaderHolder;
import lpsmin.randsode.adapters.holders.Holder;

public class HeaderRecyclerViewAdapter<T> extends RecyclerViewAdapter<T> {

    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1;

    private final int holderHeaderRessource;
    private final Class holderHeaderClass;

    public HeaderRecyclerViewAdapter(Context context, List<T> list, int holderRessource, Class holderClass, int holderHeaderRessource, Class holderHeaderClass) {
        super(context, list, holderRessource, holderClass);

        this.holderHeaderRessource = holderHeaderRessource;
        this.holderHeaderClass = holderHeaderClass;
    }

    @Override
    public Holder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(this.holderHeaderRessource, parent, false);
            return this.getHolder(this.holderHeaderClass, v);
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderHolder) ((HeaderHolder) holder).bind(list.isEmpty());
        else ((Holder)holder).bind(list.get(position - 1)); //header = 0
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1; //Header
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) return TYPE_HEADER;
        return TYPE_ITEM;
    }
}