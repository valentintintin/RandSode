package lpsmin.randsode.requests.account;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import lpsmin.randsode.R;
import lpsmin.randsode.models.FavoriteReturn;
import lpsmin.randsode.models.database.Serie;
import lpsmin.randsode.shared.Closure;
import lpsmin.randsode.shared.JSONPostRequest;

public class AddFavoriteSerieRequest extends JSONPostRequest<FavoriteReturn> {

    public AddFavoriteSerieRequest(String sessionId, final Serie serie, final boolean add, final Activity activity) {
        super("https://api.themoviedb.org/3/account/0/favorite?session_id=" + sessionId, FavoriteReturn.class, new Response.Listener<FavoriteReturn>() {
            @Override
            public void onResponse(FavoriteReturn response) {
                Toast.makeText(activity, serie.getName() + " " + activity.getString(R.string.has_been) + " " + (add ? activity.getString(R.string.added) : activity.getString(R.string.deleted)), Toast.LENGTH_SHORT).show();
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

        try {
            this.paramsJson.put("media_type", new String("tv"));
            this.paramsJson.put("media_id", serie.getId());
            this.paramsJson.put("favorite", add);
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }
}