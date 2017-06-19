package lpsmin.randsode.requests;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lpsmin.randsode.adapters.RecyclerViewAdapter;
import lpsmin.randsode.models.ResultSeries;
import lpsmin.randsode.shared.ArrayJSONRequest;

public class SeriesArrayRequest extends ArrayJSONRequest<ResultSeries> {

    public SeriesArrayRequest(String url, ViewGroup loader, RecyclerViewAdapter listAdapter, View list, TextView noData) {
        super(url, ResultSeries.class, loader, listAdapter, list, noData);
    }

    protected void addToListAdapter(ResultSeries response) {
        this.listAdapter.resetAndAdd(response.getResults());
    }

    protected boolean isResultEmpty(ResultSeries response) {
        return response.getResults().isEmpty();
    }
}