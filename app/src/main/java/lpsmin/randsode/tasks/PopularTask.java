package lpsmin.randsode.tasks;

import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.TextView;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TvResultsPage;
import lpsmin.randsode.adapters.SeriePopularRecyclerViewAdapter;

public class PopularTask extends ArrayTask {

    public PopularTask(FrameLayout loader, SeriePopularRecyclerViewAdapter listAdapter, RecyclerView list, TextView noData) {
        super(loader, listAdapter, list, noData);
    }

    @Override
    protected TvResultsPage doInBackground(Void... params) {
        TmdbApi tmdb = new TmdbApi("6eea0576c85e5ebf9fd8e438a8d8b316");
        return tmdb.getTvSeries().getPopular("en-UK", 1);
    }
}