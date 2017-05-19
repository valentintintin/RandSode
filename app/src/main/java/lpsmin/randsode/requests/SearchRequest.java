package lpsmin.randsode.requests;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Iterator;

import lpsmin.randsode.adapters.RecyclerViewAdapter;
import lpsmin.randsode.models.Result;
import lpsmin.randsode.models.database.Serie;

public class SearchRequest extends SeriesArrayRequest {

    public SearchRequest(String query, FrameLayout loader, RecyclerViewAdapter listAdapter, View list, TextView noData) {
        super("https://api.themoviedb.org/3/search/tv?query=" + query, loader, listAdapter, list, noData);
    }

    protected void addToListAdapter(Result response) {
        for (Iterator<Serie> iter = response.getResults().listIterator(); iter.hasNext(); ) {
            Serie s = iter.next();
            if (s.getOverview().length() == 0) iter.remove();
        }

        super.addToListAdapter(response);
    }

}