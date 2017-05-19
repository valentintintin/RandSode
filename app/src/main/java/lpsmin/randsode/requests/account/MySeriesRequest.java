package lpsmin.randsode.requests.account;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;

import lpsmin.randsode.models.Result;
import lpsmin.randsode.models.database.Serie;
import lpsmin.randsode.models.database.Serie_Table;
import lpsmin.randsode.requests.SeriesArrayRequest;

public class MySeriesRequest extends SeriesArrayRequest {

    // f75598b9a9225166eaf0b4e96c78e8c304cf4679

    public MySeriesRequest(String sessionId) {
        super("https://api.themoviedb.org/3/account/0/favorite/tv?session_id=" + sessionId, null, null, null, null);
    }

    @Override
    protected void otherThing(Result response) {
        super.otherThing(response);

        ArrayList<Serie> toAddLocal = new ArrayList<>();
        ArrayList<Serie> toAddDistant = new ArrayList<>();

        for (Serie sMD : response.getResults()) {
            if (!SQLite.select(Serie_Table.id).from(Serie.class).where(Serie_Table.id.eq(sMD.getId())).query().moveToFirst()) {
                System.out.println("To add SQlite: " + sMD.getName());
                toAddLocal.add(sMD);
            }
        }

        int i;
        for (Serie s : SQLite.select(Serie_Table.id).from(Serie.class).queryList()) {
            i = 0;
            while (i < response.getResults().size() && response.getResults().get((i)).getId() != s.getId()) {
                i++;
            }
            if (i == response.getResults().size()) {
                System.out.println("To add MovieDB:" + s.getName());
                toAddDistant.add(s);
            }
        }
    }
}