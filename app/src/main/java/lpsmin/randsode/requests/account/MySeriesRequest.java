package lpsmin.randsode.requests.account;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.volley.Response;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.LinkedList;
import java.util.List;

import lpsmin.randsode.R;
import lpsmin.randsode.fragments.MySeriesListFragment;
import lpsmin.randsode.models.ResultSeries;
import lpsmin.randsode.models.database.Serie;
import lpsmin.randsode.models.database.Serie_Table;
import lpsmin.randsode.requests.SerieRequest;
import lpsmin.randsode.requests.SeriesArrayRequest;
import lpsmin.randsode.shared.Closure;
import lpsmin.randsode.shared.Synchro;

public class MySeriesRequest extends SeriesArrayRequest {

    // f75598b9a9225166eaf0b4e96c78e8c304cf4679

    private final Activity activity;
    private int typeSynchro = Synchro.TYPE_BOTH; // Both
    private String sessionId;

    public MySeriesRequest(String sessionId, int type, final Activity activity) {
        super("https://api.themoviedb.org/3/account/0/favorite/tv?session_id=" + sessionId, null, null, null, null);

        this.typeSynchro = type;
        this.sessionId = sessionId;
        this.activity = activity;

        Toast.makeText(activity, activity.getString(R.string.synchro_running), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void otherThing(ResultSeries response) {
        super.otherThing(response);

        if (this.typeSynchro == Synchro.TYPE_IMPORT) importFromMovieDB(response.getResults());
        else if (this.typeSynchro == Synchro.TYPE_EXPORT)
            exportFromApplication(response.getResults());
        else {
            importFromMovieDB(response.getResults());
            exportFromApplication(response.getResults());
        }
    }

    private void importFromMovieDB(List<Serie> seriesMovieDB) {
//        for (Serie sMD : seriesMovieDB) {
//            if (!SQLite.select(Serie_Table.id).from(Serie.class).where(Serie_Table.id.eq(sMD.getId())).query().moveToFirst()) {
//                System.out.println("To add SQlite: " + sMD.getName() + "/" + sMD.getId());
//            }
//        }

        SQLite.delete().from(Serie.class).execute();
        for (final Serie serie : seriesMovieDB) {
            new SerieRequest(serie.getId(), new Response.Listener<Serie>() {
                @Override
                public void onResponse(Serie response) {
                    if (response.getNumber_of_episodes() != 0 &&
                            response.getEpisodes().size() < response.getNumber_of_episodes()) {
                        response.save();

                        MySeriesListFragment.update();
                    }
                }
            }, null);
        }

        Toast.makeText(activity, activity.getString(R.string.sychro_executed) + " " + seriesMovieDB.size() + " series imported", Toast.LENGTH_SHORT).show();
    }

    private void exportFromApplication(List<Serie> seriesMovieDB) {
        final LinkedList<Serie> seriesServer = new LinkedList<>(seriesMovieDB);
        final LinkedList<Serie> seriesBDD = new LinkedList<>(SQLite.select(Serie_Table.id, Serie_Table.name).from(Serie.class).queryList());

        if (!seriesBDD.isEmpty()) { // local not empty
            if (seriesMovieDB.isEmpty()) { //ajout simple
                addMovieDB(seriesBDD);
            } else { // suppression movieDB puis ajout
                new AddFavoriteSerieRequest(this.sessionId, seriesServer, false, this.activity, new Closure<Nullable>() {
                    @Override
                    public void execute(Nullable data) {
                        addMovieDB(seriesBDD);
                    }
                });
            }
        } else { // suppression de tout
            new AddFavoriteSerieRequest(this.sessionId, seriesServer, false, this.activity, null);
        }

        Toast.makeText(activity, activity.getString(R.string.sychro_executed) + " " + seriesBDD.size() + " series exported", Toast.LENGTH_SHORT).show();
    }

    private void addMovieDB(List<Serie> seriesMovieDB) {
        final LinkedList<Serie> series = new LinkedList<>(seriesMovieDB);
        new AddFavoriteSerieRequest(sessionId, series, true, activity, null);
    }
}