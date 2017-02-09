package lpsmin.randsode.tasks;

import android.view.View;
import android.widget.FrameLayout;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.tv.TvSeries;

public class SerieTask extends OneTask<TvSeries> {

    public SerieTask(int serieId, FrameLayout loader, View list, Closure closure) {
        super(serieId, loader, list, closure);
    }

    @Override
    protected TvSeries doInBackground(Void... voids) {
        TmdbApi tmdb = new TmdbApi("6eea0576c85e5ebf9fd8e438a8d8b316");
        return tmdb.getTvSeries().getSeries(this.serieId, "en-UK");
    }
}