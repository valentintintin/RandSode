package lpsmin.randsode.tasks;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TvResultsPage;
import lpsmin.randsode.adapters.SerieSearchRecyclerViewAdapter;

public class SearchTask extends SerieTask {

    private final String searchQuery;

    public SearchTask(String searchQuery, FrameLayout loader, SerieSearchRecyclerViewAdapter listAdapter, RecyclerView list, TextView noData) {
        super(loader, listAdapter, list, noData);

        this.searchQuery = searchQuery;
    }

    @Override
    protected TvResultsPage doInBackground(Void... params) {
        TmdbApi tmdb = new TmdbApi("6eea0576c85e5ebf9fd8e438a8d8b316");
        return tmdb.getSearch().searchTv(this.searchQuery, "en-UK", 1);
    }
}