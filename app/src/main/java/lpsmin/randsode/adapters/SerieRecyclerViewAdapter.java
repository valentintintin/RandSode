package lpsmin.randsode.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import info.movito.themoviedbapi.model.tv.TvSeries;
import lpsmin.randsode.adapters.holders.SerieListHolder;
import lpsmin.randsode.R;

public class SerieRecyclerViewAdapter extends RecyclerView.Adapter<SerieListHolder> {

    private final Context context;
    private List<TvSeries> list = Collections.emptyList();

    public SerieRecyclerViewAdapter(Context context, List<TvSeries> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public SerieListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_search, parent, false);
        return new SerieListHolder(v, this.context);
    }

    @Override
    public void onBindViewHolder(SerieListHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void resetAndAdd(List<TvSeries> data) {
        for(int i = 0; i < data.size(); i++) {
            if (data.get(i).getOverview().length() == 0) data.remove(i);
        }
        this.list.clear();
        this.list.addAll(data);
        this.notifyDataSetChanged();
    }
}