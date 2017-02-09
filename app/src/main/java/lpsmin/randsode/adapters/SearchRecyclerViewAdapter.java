package lpsmin.randsode.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.movito.themoviedbapi.model.tv.TvSeries;
import lpsmin.randsode.R;
import lpsmin.randsode.adapters.holders.SerieHolder;

public class SearchRecyclerViewAdapter extends RecyclerViewAdapter<TvSeries> {

    public SearchRecyclerViewAdapter(Context context, List<TvSeries> list) {
        super(context, list, R.layout.holder_serie, SerieHolder.class);
    }
}