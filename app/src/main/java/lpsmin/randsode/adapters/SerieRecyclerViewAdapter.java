package lpsmin.randsode.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import info.movito.themoviedbapi.model.tv.TvSeries;

/**
 * Created by Valentin on 31/01/2017.
 */

public abstract class SerieRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final Context context;
    protected List<TvSeries> list = Collections.emptyList();

    public SerieRecyclerViewAdapter (Context context, List<TvSeries> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void resetAndAdd(List<TvSeries> data) {
        this.list.clear();
        this.list.addAll(data);

        this.notifyDataSetChanged();
    }
}
