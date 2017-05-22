package lpsmin.randsode.shared;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class HttpSingleton {

    public final static String END_URL = "&api_key=6eea0576c85e5ebf9fd8e438a8d8b316&language=en-US";

    private static HttpSingleton instance;
    private final ImageLoader imageLoader;
    private RequestQueue requestQueue;

    private HttpSingleton(Context context) {
        this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        this.imageLoader = new ImageLoader(this.requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(50);

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

    public static synchronized HttpSingleton getInstance() {
        if (instance == null) throw new NullPointerException();
        return instance;
    }

    public static synchronized HttpSingleton createInstance(Context context) {
        if (instance == null) {
            instance = new HttpSingleton(context);
        }
        return instance;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        requestQueue.add(req);
    }

    public ImageLoader getImageLoader() {
        return this.imageLoader;
    }
}
