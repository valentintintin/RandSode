package lpsmin.randsode.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.List;

@Table(database = AppDatabase.class)
public class Serie extends BaseModel implements Serializable {

    @PrimaryKey
    private int id;

    @Column
    private String name;

    @Column
    private String overview;

    @Column
    private String first_air_date;

    @Column
    private String backdrop_path;

    @Column
    private String poster_path;

    @Column
    private int number_of_seasons;

    @Column
    private int number_of_episodes;

    @Column
    private float vote_average;

    @Column(defaultValue = "0")
    private int vote_count;

    @Column(defaultValue = "Date('now')")
    private long date_added;

    @Column
    private long last_watched;

    List<Episode> episodes;

    private List<Season> seasons;

    public Serie() {
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "episodes")
    public List<Episode> getEpisodes() {
        episodes = SQLite.select()
                .from(Episode.class)
                .where(Episode_Table.serie_id.eq(id))
                .orderBy(Episode_Table.date_added, false)
                .queryList();
        return episodes;
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

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public long getDate_added() {
        return date_added;
    }

    public void setDate_added(long date_added) {
        this.date_added = date_added;
    }

    public long getLast_watched() {
        return last_watched;
    }

    public void setLast_watched(long last_watched) {
        this.last_watched = last_watched;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }
}
