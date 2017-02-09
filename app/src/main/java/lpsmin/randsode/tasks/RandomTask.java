package lpsmin.randsode.tasks;

import android.view.View;
import android.widget.FrameLayout;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.tv.TvEpisode;
import info.movito.themoviedbapi.model.tv.TvSeries;

public class RandomTask extends OneTask<TvEpisode> {

    private final int season, episode;

    public RandomTask(int serieId, int season, int episode, FrameLayout loader, View list, Closure closure) {
        super(serieId, loader, list, closure);

        this.season = season;
        this.episode = episode;
    }

    @Override
    protected TvEpisode doInBackground(Void... voids) {
        TmdbApi tmdb = new TmdbApi("6eea0576c85e5ebf9fd8e438a8d8b316");
        return tmdb.getTvEpisodes().getEpisode(this.serieId, this.season, this.episode, "en-UK");
    }
}