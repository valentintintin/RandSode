package lpsmin.randsode.requests.account;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.LinkedList;
import java.util.List;

import lpsmin.randsode.R;
import lpsmin.randsode.fragments.MySeriesListFragment;
import lpsmin.randsode.models.Result;
import lpsmin.randsode.models.database.Serie;
import lpsmin.randsode.models.database.Serie_Table;
import lpsmin.randsode.requests.SeriesArrayRequest;
import lpsmin.randsode.shared.Closure;

public class MySeriesRequest extends SeriesArrayRequest {

    // f75598b9a9225166eaf0b4e96c78e8c304cf4679

    private final Activity activity;
    private int typeSynchro = 2; // Both
    private String sessionId;

    public MySeriesRequest(String sessionId, int type, final Activity activity) {
        super("https://api.themoviedb.org/3/account/0/favorite/tv?session_id=" + sessionId, null, null, null, null);

        this.typeSynchro = type;
        this.sessionId = sessionId;
        this.activity = activity;
    }

    @Override
    protected void otherThing(Result response) {
        super.otherThing(response);

        if (this.typeSynchro == 0) importFromMovieDB(response.getResults());
        else if (this.typeSynchro == 1) exportFromApplication(response.getResults());
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
        for (Serie s : seriesMovieDB) s.save();

        MySeriesListFragment.observable.notifyObservers();

        Toast.makeText(activity, activity.getString(R.string.sychro_executed), Toast.LENGTH_SHORT).show();
    }

    private void exportFromApplication(List<Serie> seriesMovieDB) {
        final LinkedList<Serie> series = new LinkedList<>(seriesMovieDB);
//        int i;

//        for (Serie s : SQLite.select(Serie_Table.id, Serie_Table.name).from(Serie.class).queryList()) {
//            i = 0;
//            while (i < seriesMovieDB.size() && seriesMovieDB.get((i)).getId() != s.getId()) {
//                i++;
//            }
//            if (i == seriesMovieDB.size()) {
//                System.out.println("To add MovieDB: " + s.getName() + "/" + s.getId());
//                new AddFavoriteSerieRequest(this.sessionId, s, true, this.activity);
//            }
//        }

        if (seriesMovieDB.isEmpty())
            addMovieDB(SQLite.select(Serie_Table.id, Serie_Table.name).from(Serie.class).queryList());
        else {
            new AddFavoriteSerieRequest(this.sessionId, series, false, this.activity, new Closure<Nullable>() {
                @Override
                public void execute(Nullable data) {
                    addMovieDB(SQLite.select(Serie_Table.id, Serie_Table.name).from(Serie.class).queryList());
                }
            });
        }
    }

    private void addMovieDB(List<Serie> seriesMovieDB) {
        final LinkedList<Serie> series = new LinkedList<>(seriesMovieDB);
        new AddFavoriteSerieRequest(sessionId, series, true, activity, null);
    }
}