package lpsmin.randsode.shared;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class JSONGetRequest<T> extends com.android.volley.Request<T> {

    private final Gson gson;
    private final Class<T> jsonType;
    private final Response.Listener<T> listener;
    private final ViewGroup loader;
    private final View view;
    private final TextView noData;

    public JSONGetRequest(String url, Class<T> jsonType, Response.Listener<T> listener) {
        this(url, jsonType, listener, null, null, null);
    }

    public JSONGetRequest(String url, Class<T> jsonType, Response.Listener<T> listener, final ViewGroup loader, final View view, TextView noData) {
        this(url, jsonType, listener, loader, view, noData, null);
    }

    public JSONGetRequest(String url, Class<T> jsonType, Response.Listener<T> listener, final ViewGroup loader, final View view, TextView noData, final Closure<VolleyError> errorListener) {
        super(Method.GET, url + HttpSingleton.END_URL, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                Log.e("http", new String(error.networkResponse.data, StandardCharsets.UTF_8));

                if (loader != null && errorListener == null) { // To have the context ...
                    Snackbar.make(loader, "Network error code: " + error.networkResponse.statusCode, Snackbar.LENGTH_LONG).show();
                } else if (errorListener != null) errorListener.execute(error);

                if (loader != null) loader.setVisibility(View.GONE);
                if (view != null) view.setVisibility(View.VISIBLE);
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
