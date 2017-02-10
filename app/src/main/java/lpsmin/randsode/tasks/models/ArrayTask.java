package lpsmin.randsode.tasks.models;

import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.TextView;

import lpsmin.randsode.adapters.RecyclerViewAdapter;

public abstract class ArrayTask<T> extends Task<T> {

    protected final RecyclerViewAdapter listAdapter;

    public ArrayTask(FrameLayout loader, RecyclerViewAdapter listAdapter, RecyclerView list, TextView noData) {
        super(loader, list, noData);

        this.listAdapter = listAdapter;
    }
}