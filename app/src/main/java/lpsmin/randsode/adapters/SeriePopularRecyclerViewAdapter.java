package lpsmin.randsode.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.movito.themoviedbapi.model.tv.TvSeries;
import lpsmin.randsode.adapters.holders.HeaderHolder;
import lpsmin.randsode.R;
import lpsmin.randsode.adapters.holders.SerieHolder;

public class SeriePopularRecyclerViewAdapter extends SerieRecyclerViewAdapter {

    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1;


    public SeriePopularRecyclerViewAdapter (Context context, List<TvSeries> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_header, parent, false);
            return new HeaderHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_serie, parent, false);
            return new SerieHolder(v, this.context);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderHolder) ((HeaderHolder) holder).bind(list.isEmpty());
        else ((SerieHolder)holder).bind(list.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) return TYPE_HEADER; //TODO : not header, lost the first one !
        return TYPE_ITEM;
    }
}