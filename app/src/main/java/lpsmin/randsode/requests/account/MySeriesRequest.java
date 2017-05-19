package lpsmin.randsode.requests.account;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import lpsmin.randsode.models.Result;
import lpsmin.randsode.models.database.Serie;
import lpsmin.randsode.models.database.Serie_Table;
import lpsmin.randsode.requests.SeriesArrayRequest;

public class MySeriesRequest extends SeriesArrayRequest {

    // f75598b9a9225166eaf0b4e96c78e8c304cf4679

    private int typeSynchro = 2; // Both

    public MySeriesRequest(String sessionId, int type) {
        super("https://api.themoviedb.org/3/account/0/favorite/tv?session_id=" + sessionId, null, null, null, null);

        this.typeSynchro = type;
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
        ArrayList<Serie> toAddLocal = new ArrayList<>();

        for (Serie sMD : seriesMovieDB) {
            if (!SQLite.select(Serie_Table.id).from(Serie.class).where(Serie_Table.id.eq(sMD.getId())).query().moveToFirst()) {
                System.out.println("To add SQlite: " + sMD.getName());
                toAddLocal.add(sMD);
            }
        }
    }

    private void exportFromApplication(List<Serie> seriesMovieDB) {
        ArrayList<Serie> toAddDistant = new ArrayList<>();
        int i;

        for (Serie s : SQLite.select(Serie_Table.id, Serie_Table.name).from(Serie.class).queryList()) {
            i = 0;
            while (i < seriesMovieDB.size() && seriesMovieDB.get((i)).getId() != s.getId()) {
                i++;
            }
            if (i == seriesMovieDB.size()) {
                System.out.println("To add MovieDB: " + s.getName());
                toAddDistant.add(s);
            }
        }
    }
}