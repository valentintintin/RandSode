package lpsmin.randsode.requests.account;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.LinkedList;

import lpsmin.randsode.R;
import lpsmin.randsode.models.FavoriteReturn;
import lpsmin.randsode.models.database.Serie;
import lpsmin.randsode.shared.Closure;
import lpsmin.randsode.shared.JSONPostRequest;

public class AddFavoriteSerieRequest extends JSONPostRequest<FavoriteReturn> {

    public AddFavoriteSerieRequest(final String sessionId, final LinkedList<Serie> series, final boolean add, final Activity activity, final Closure<Nullable> next) {
        super("https://api.themoviedb.org/3/account/0/favorite?session_id=" + sessionId, FavoriteReturn.class, new Response.Listener<FavoriteReturn>() {
            @Override
            public void onResponse(FavoriteReturn response) {
                if (!series.isEmpty()) {
                    new AddFavoriteSerieRequest(sessionId, series, add, activity, next);
                } else {
                    if (next != null) next.execute(null);
                    else
                        Toast.makeText(activity, activity.getString(R.string.sychro_executed), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Closure<VolleyError>() {
            @Override
            public void execute(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(activity.getString(R.string.sychro_executed_error))
                        .setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.create().show();
            }
        });

        if (!series.isEmpty()) {
            try {
                this.paramsJson.put("media_type", new String("tv"));
                this.paramsJson.put("media_id", series.pop().getId());
                this.paramsJson.put("favorite", add);
            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }
        }
    }
}