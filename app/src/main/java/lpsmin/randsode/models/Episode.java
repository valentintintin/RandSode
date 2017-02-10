package lpsmin.randsode.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import info.movito.themoviedbapi.model.tv.TvEpisode;

@Table(database = AppDatabase.class)
public class Episode extends TvEpisode {

    @PrimaryKey
    private int id;

    @Column(name = "show_id")
    private int seriesId;

    @Column
    private String name;

    @Column(name = "season_number")
    private int seasonNumber;

    @Column(name = "episode_number")
    private int episodeNumber;

    @Column(name = "still_path")
    private String stillPath;

    @Column(name = "vote_average")
    private float voteAverage;

    @Column(name = "vote_count", defaultValue = "0")
    private int voteCount;

    @Column(name = "date_added", defaultValue = "Date('now')")
    private long dateAdded;

    @Column
    private boolean watched;

    public Episode() {
        super();
    }

    public Episode(TvEpisode episode, boolean watched) {
        this.id = episode.getId();
        this.seriesId = episode.getSeriesId();
        this.name = episode.getName();
        this.seasonNumber = episode.getSeasonNumber();
        this.episodeNumber = episode.getEpisodeNumber();
        this.stillPath = episode.getStillPath();
        this.voteAverage = episode.getVoteAverage();
        this.voteCount = episode.getVoteCount();
        this.watched = watched;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }
}
