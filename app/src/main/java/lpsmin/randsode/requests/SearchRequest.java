package lpsmin.randsode.requests;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import lpsmin.randsode.adapters.RecyclerViewAdapter;
import lpsmin.randsode.models.Result;
import lpsmin.randsode.models.Serie;

public class SearchRequest extends SeriesArrayRequest {

    public SearchRequest(String query, FrameLayout loader, RecyclerViewAdapter listAdapter, View list, TextView noData) {
        super("https://api.themoviedb.org/3/search/tv?query=" + query, loader, listAdapter, list, noData);
    }

    protected void addToListAdapter(Result response) {
        for(Serie s : response.getResults()) {
            if (s.getOverview().length() == 0) response.getResults().remove(s);
        }

        super.addToListAdapter(response);
    }

}