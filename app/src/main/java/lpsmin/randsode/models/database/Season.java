package lpsmin.randsode.models.database;

public class Season {

    private int id;
    private int season_number;
    private int episode_count;
    private String air_date;
    private String poster_path;

    public Season() {}

    public int getId() {
        return id;
    }

    public int getSeasonNumber() {
        return season_number;
    }

    public int getEpisodeCount() {
        return episode_count;
    }

    public String getAirDate() {
        return air_date;
    }

    public String getPosterPath() {
        return poster_path;
    }
}