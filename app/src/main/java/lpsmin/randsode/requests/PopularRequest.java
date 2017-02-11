package lpsmin.randsode.requests;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import lpsmin.randsode.adapters.RecyclerViewAdapter;

public class PopularRequest extends SeriesArrayRequest {

    public PopularRequest(FrameLayout loader, RecyclerViewAdapter listAdapter, View list, TextView noData) {
        super("https://api.themoviedb.org/3/tv/popular?", loader, listAdapter, list, noData);
    }
}