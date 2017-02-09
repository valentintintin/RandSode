package lpsmin.randsode.tasks;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.tv.TvSeries;
import lpsmin.randsode.adapters.SerieRecyclerViewAdapter;

public class SerieTask extends Task<TvSeries> {

    private final int serieId;
    private Populate populate;

    public SerieTask(int serieId, FrameLayout loader, View list, Populate populate) {
        super(loader, list, null);

        this.serieId = serieId;
        this.populate = populate;
    }

    @Override
    protected void process(TvSeries data) {
        populate.go(data);
    }

    @Override
    protected TvSeries doInBackground(Void... voids) {
        TmdbApi tmdb = new TmdbApi("6eea0576c85e5ebf9fd8e438a8d8b316");
        return tmdb.getTvSeries().getSeries(this.serieId, "en-UK");
    }
}