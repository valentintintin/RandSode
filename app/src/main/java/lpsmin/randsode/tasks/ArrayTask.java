package lpsmin.randsode.tasks;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import info.movito.themoviedbapi.TvResultsPage;
import lpsmin.randsode.adapters.SerieRecyclerViewAdapter;

public abstract class ArrayTask extends Task<TvResultsPage> {

    protected SerieRecyclerViewAdapter listAdapter;

    public ArrayTask(FrameLayout loader, SerieRecyclerViewAdapter listAdapter, RecyclerView list, TextView noData) {
        super(loader, list, noData);

        this.listAdapter = listAdapter;
    }

    protected void process(TvResultsPage data) {
        this.listAdapter.resetAndAdd(data.getResults());
    }
}