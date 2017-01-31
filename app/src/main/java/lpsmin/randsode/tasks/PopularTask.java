package lpsmin.randsode.tasks;

import android.os.AsyncTask;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TvResultsPage;
import lpsmin.randsode.adapters.SerieRecyclerViewAdapter;

public class PopularTask extends AsyncTask<Void, Void, TvResultsPage> {

    private final SerieRecyclerViewAdapter listAdapter;

    public PopularTask(SerieRecyclerViewAdapter listAdapter) {
        this.listAdapter = listAdapter;
    }

    @Override
    protected TvResultsPage doInBackground(Void... params) {
        TmdbApi tmdb = new TmdbApi("6eea0576c85e5ebf9fd8e438a8d8b316");
        return tmdb.getTvSeries().getPopular("en-UK", 1);
    }

    protected void onPostExecute(TvResultsPage result) {
        this.listAdapter.resetAndAdd(result.getResults());
    }
}