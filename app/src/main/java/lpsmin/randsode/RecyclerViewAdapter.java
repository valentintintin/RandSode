package lpsmin.randsode;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import info.movito.themoviedbapi.model.tv.TvSeries;

public class RecyclerViewAdapter extends RecyclerView.Adapter<CardHolder> {

    private Context context;
    private List<TvSeries> list = Collections.emptyList();

    public RecyclerViewAdapter(Context context, List<TvSeries> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new CardHolder(v, this.context);
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void resetAndAdd(List<TvSeries> data) {
        this.list.clear();
        this.list.addAll(data);
        this.notifyDataSetChanged();
    }
}