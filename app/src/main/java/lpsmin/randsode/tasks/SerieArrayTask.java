package lpsmin.randsode.tasks;

import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.TextView;

import info.movito.themoviedbapi.TvResultsPage;
import lpsmin.randsode.adapters.RecyclerViewAdapter;
import lpsmin.randsode.tasks.models.ArrayTask;

public abstract class SerieArrayTask extends ArrayTask<TvResultsPage> {

    public SerieArrayTask(FrameLayout loader, RecyclerViewAdapter listAdapter, RecyclerView list, TextView noData) {
        super(loader, listAdapter, list, noData);
    }

    protected void process(TvResultsPage data) {
        this.listAdapter.resetAndAdd(data.getResults());
    }
}