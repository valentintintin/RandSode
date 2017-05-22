package lpsmin.randsode.shared;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class JSONPostRequest<T> extends com.android.volley.Request<T> {

    private final Gson gson;
    private final Class<T> jsonType;
    private final Response.Listener<T> listener;
    protected JSONObject paramsJson;

    public JSONPostRequest(String url, Class<T> jsonType, Response.Listener<T> listener, final Closure<VolleyError> errorListener) {
        super(Method.POST, url + HttpSingleton.END_URL, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                Log.e("http", new String(error.networkResponse.data, StandardCharsets.UTF_8));

                if (errorListener != null) errorListener.execute(error);
            }
        });

        this.paramsJson = new JSONObject();

        this.gson = new Gson();
        this.jsonType = jsonType;
        this.listener = listener;

        HttpSingleton.getInstance().addToRequestQueue(this);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return this.paramsJson.toString().getBytes();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json;charset=utf-8");
        return params;
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
        if (this.listener != null) this.listener.onResponse(response);
    }
}
