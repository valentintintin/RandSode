package lpsmin.randsode.requests;

import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;

import lpsmin.randsode.models.Episode;
import lpsmin.randsode.models.Serie;
import lpsmin.randsode.shared.JSONRequest;

public class EpisodeRequest extends JSONRequest<Episode> {

    public EpisodeRequest(int serieId, int season, int episode, Response.Listener<Episode> listener, ViewGroup loader, View view) {
        super("https://api.themoviedb.org/3/tv/" + serieId + "/season/" + season + "/episode/" + episode + "?", Episode.class, listener, loader, view, null);
    }
}