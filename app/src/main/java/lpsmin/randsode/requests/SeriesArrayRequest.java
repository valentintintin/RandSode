package lpsmin.randsode.requests;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lpsmin.randsode.adapters.RecyclerViewAdapter;
import lpsmin.randsode.models.Result;
import lpsmin.randsode.shared.ArrayJSONRequest;

public class SeriesArrayRequest extends ArrayJSONRequest<Result> {

    public SeriesArrayRequest(String url, ViewGroup loader, RecyclerViewAdapter listAdapter, View list, TextView noData) {
        super(url, Result.class, loader, listAdapter, list, noData);
    }

    protected void addToListAdapter(Result response) {
        this.listAdapter.resetAndAdd(response.getResults());
    }

    protected boolean isResultEmpty(Result response) {
        return response.getResults().isEmpty();
    }
}