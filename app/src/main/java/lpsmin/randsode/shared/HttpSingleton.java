package lpsmin.randsode.shared;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class HttpSingleton {

    private static HttpSingleton instance;
    private RequestQueue requestQueue;
    private final ImageLoader imageLoader;
    private static Context context;

    private HttpSingleton(Context context) {
        HttpSingleton.context = context;
        this.requestQueue = getRequestQueue();
        this.imageLoader = new ImageLoader(this.requestQueue, new LruBitmapCache());
    }

    public static synchronized HttpSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new HttpSingleton(context);
        }
        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (this.requestQueue == null) {
            this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return this.imageLoader;
    }
}
