package lpsmin.randsode.shared;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class JSONRequest<T> extends com.android.volley.Request<T> {

    private final static String END_URL = "&api_key=6eea0576c85e5ebf9fd8e438a8d8b316&language=en-US";

    private final Gson gson;
    private final Class<T> jsonType;
    private final Response.Listener<T> listener;
    private final ViewGroup loader;
    private final View view;
    private final TextView noData;

    public JSONRequest(String url, Class<T> jsonType, Response.Listener<T> listener) {
        this(url, jsonType, listener, null, null, null);
    }

    public JSONRequest(String url, Class<T> jsonType, Response.Listener<T> listener, ViewGroup loader, View view, TextView noData) {
        super(Method.GET, url + END_URL, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getStackTrace();
            }
        });

        this.gson = new Gson();
        this.jsonType = jsonType;
        this.listener = listener;
        this.loader = loader;
        this.view = view;
        this.noData = noData;

        if (this.loader != null) this.loader.setVisibility(View.VISIBLE);
        if (this.view != null) this.view.setVisibility(View.GONE);

        HttpSingleton.getInstance().addToRequestQueue(this);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(gson.fromJson(json, this.jsonType), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        if (this.loader != null) this.loader.setVisibility(View.GONE);
        if (this.noData != null) {
            if (this.isResultEmpty(response)) this.noData.setVisibility(View.VISIBLE);
            else if (this.view != null) this.view.setVisibility(View.VISIBLE);
        } else if (this.view != null) this.view.setVisibility(View.VISIBLE);

        if (this.listener != null) this.listener.onResponse(response);
    }

    protected boolean isResultEmpty(T response) {
        return false;
    }
}
