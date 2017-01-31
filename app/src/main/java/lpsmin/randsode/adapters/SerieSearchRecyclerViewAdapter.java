package lpsmin.randsode.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import info.movito.themoviedbapi.model.tv.TvSeries;
import lpsmin.randsode.R;
import lpsmin.randsode.adapters.holders.SerieHolder;

public class SerieSearchRecyclerViewAdapter extends SerieRecyclerViewAdapter {

    public SerieSearchRecyclerViewAdapter(Context context, List<TvSeries> list) {
        super(context, list);
    }

    @Override
    public SerieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_search, parent, false);
        return new SerieHolder(v, this.context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SerieHolder)holder).bind(list.get(position));
    }
}