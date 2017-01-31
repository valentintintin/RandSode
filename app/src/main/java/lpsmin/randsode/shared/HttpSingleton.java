package lpsmin.randsode.shared;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Valentin on 30/01/2017.
 */

public class HttpSingleton {

    private static HttpSingleton instance;
    private RequestQueue requestQueue;
    private final ImageLoader imageLoader;
    private static Context context;

    private HttpSingleton(Context context) {
        HttpSingleton.context = context;
        this.requestQueue = getRequestQueue();
        this.imageLoader = new ImageLoader(this.requestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
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
