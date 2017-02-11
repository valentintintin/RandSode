package lpsmin.randsode.requests;

import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;

import lpsmin.randsode.models.Result;
import lpsmin.randsode.models.Serie;
import lpsmin.randsode.shared.JSONRequest;

public class SerieRequest extends JSONRequest<Serie> {

    public SerieRequest(int serieId, Response.Listener<Serie> listener, ViewGroup loader, View view) {
        super("https://api.themoviedb.org/3/tv/" + serieId + "?", Serie.class, listener, loader, view, null);
    }
}
