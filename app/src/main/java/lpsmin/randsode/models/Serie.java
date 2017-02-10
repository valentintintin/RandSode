package lpsmin.randsode.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import info.movito.themoviedbapi.model.tv.TvSeries;

@Table(database = AppDatabase.class)
public class Serie extends TvSeries {

    @PrimaryKey
    private int id;

    @Column
    private String name;

    @Column
    private String overview;

    @Column(name = "first_air_date")
    private String firstAirDate;

    @Column(name = "backdrop_path")
    private String backdropPath;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(name = "number_of_seasons")
    private int numberOfSeasons;

    @Column(name = "number_of_episodes")
    private int numberOfEpisodes;

    @Column(name = "vote_average")
    private float voteAverage;

    @Column(name = "vote_count", defaultValue = "0")
    private int voteCount;

    @Column(name = "date_added", defaultValue = "Date('now')")
    private long dateAdded;

    @Column(name = "last_watched")
    private long lastWatched;

    public Serie() {
        super();
    }

    public Serie(TvSeries serie) {
        this.id = serie.getId();
        this.name = serie.getName();
        this.overview = serie.getOverview();
        this.firstAirDate = serie.getFirstAirDate();
        this.backdropPath = serie.getBackdropPath();
        this.posterPath = serie.getPosterPath();
        this.numberOfSeasons = serie.getNumberOfSeasons();
        this.numberOfEpisodes = serie.getNumberOfEpisodes();
        this.voteAverage = serie.getVoteAverage();
        this.voteCount = serie.getVoteCount();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public long getLastWatched() {
        return lastWatched;
    }

    public void setLastWatched(long lastWatched) {
        this.lastWatched = lastWatched;
    }
}
