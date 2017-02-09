package lpsmin.randsode.tasks;

import android.view.View;
import android.widget.FrameLayout;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.tv.TvSeries;

public abstract  class OneTask<T> extends Task<T> {

    protected final int serieId;
    private Closure closure;

    public OneTask(int serieId, FrameLayout loader, View list, Closure closure) {
        super(loader, list, null);

        this.serieId = serieId;
        this.closure = closure;
    }

    @Override
    protected void process(T data) {
        closure.go(data);
    }
}