package lpsmin.randsode;

import android.os.AsyncTask;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TvResultsPage;

public class SearchTask extends AsyncTask<Void, Void, TvResultsPage> {

    private String searchQuery;
    private RecyclerViewAdapter listAdapter;

    public SearchTask(String searchQuery, RecyclerViewAdapter listAdapter) {
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