package lpsmin.randsode.shared;

import android.os.AsyncTask;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TvResultsPage;
import lpsmin.randsode.adapters.SerieSearchRecyclerViewAdapter;

public class SearchTask extends AsyncTask<Void, Void, TvResultsPage> {

    private final String searchQuery;
    private final SerieSearchRecyclerViewAdapter listAdapter;

    public SearchTask(String searchQuery, SerieSearchRecyclerViewAdapter listAdapter) {
        this.searchQuery = searchQuery;
        this.listAdapter = listAdapter;
    }

    @Override
    protected TvResultsPage doInBackground(Void... params) {
        TmdbApi tmdb = new TmdbApi("6eea0576c85e5ebf9fd8e438a8d8b316");
        return tmdb.getSearch().searchTv(this.searchQuery, "en-UK", 1);
    }

    protected void onPostExecute(TvResultsPage result) {
        this.listAdapter.resetAndAdd(result.getResults());
    }
}