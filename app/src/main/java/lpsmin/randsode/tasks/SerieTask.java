package lpsmin.randsode.tasks;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.tv.TvSeries;
import lpsmin.randsode.adapters.SerieRecyclerViewAdapter;
import lpsmin.randsode.adapters.SerieSearchRecyclerViewAdapter;

public abstract class SerieTask extends AsyncTask<Void, Void, TvResultsPage> {

    private final SerieRecyclerViewAdapter listAdapter;
    private final RecyclerView list;
    private final FrameLayout loader;
    private final TextView noData;

    public SerieTask(FrameLayout loader, SerieRecyclerViewAdapter listAdapter, RecyclerView list, TextView noData) {
        this.listAdapter = listAdapter;
        this.loader = loader;
        this.list = list;
        this.noData = noData;

        this.loader.setVisibility(View.VISIBLE);
        this.list.setVisibility(View.GONE);
        this.noData.setVisibility(View.GONE);
    }

    protected void onPostExecute(TvResultsPage result) {
        this.loader.setVisibility(View.GONE);

        if (result.getResults().isEmpty()) this.noData.setVisibility(View.VISIBLE);
        else {
            this.noData.setVisibility(View.GONE);
            this.list.setVisibility(View.VISIBLE);

            this.listAdapter.resetAndAdd(result.getResults());
        }
    }
}