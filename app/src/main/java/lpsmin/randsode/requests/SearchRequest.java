package lpsmin.randsode.requests;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import lpsmin.randsode.adapters.RecyclerViewAdapter;

public class SearchRequest extends SeriesArrayRequest {

    public SearchRequest(String query, FrameLayout loader, RecyclerViewAdapter listAdapter, View list, TextView noData) {
        super("https://api.themoviedb.org/3/search/tv/?query=" + query, loader, listAdapter, list, noData);
    }
}