package lpsmin.randsode.tasks;

import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.TextView;

import info.movito.themoviedbapi.TvResultsPage;
import lpsmin.randsode.adapters.RecyclerViewAdapter;

public abstract class ArrayTask extends Task<TvResultsPage> {

    protected RecyclerViewAdapter listAdapter;

    public ArrayTask(FrameLayout loader, RecyclerViewAdapter listAdapter, RecyclerView list, TextView noData) {
        super(loader, list, noData);

        this.listAdapter = listAdapter;
    }

    protected void process(TvResultsPage data) {
        this.listAdapter.resetAndAdd(data.getResults());
    }
}