package lpsmin.randsode.shared;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;

import lpsmin.randsode.adapters.RecyclerViewAdapter;

public class ArrayJSONRequest<T> extends JSONRequest<T> {

    protected RecyclerViewAdapter listAdapter;

    public ArrayJSONRequest(String url, Class<T> jsonType, ViewGroup loader, RecyclerViewAdapter listAdapter, View list, TextView noData) {
        super(url, jsonType, null, loader, list, noData);

        this.listAdapter = listAdapter;
    }

    public ArrayJSONRequest(String url, Class<T> jsonType, Response.Listener<T> listener, ViewGroup loader, View list) {
        super(url, jsonType, listener, loader, list, null);
    }

    @Override
    protected void deliverResponse(T response) {
        super.deliverResponse(response);

        if (this.listAdapter != null) addToListAdapter(response);
    }

    protected void addToListAdapter(T response) {}
}
