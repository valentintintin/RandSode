package lpsmin.randsode.tasks.models;

import android.os.AsyncTask;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


public abstract class Task<T> extends AsyncTask<Void, Void, T> {

    private final View list;
    private final FrameLayout loader;
    protected final TextView noData;

    public Task(FrameLayout loader, View list, TextView noData) {
        this.loader = loader;
        this.list = list;
        this.noData = noData;

        this.list.setVisibility(View.GONE);
        if (this.noData != null) this.noData.setVisibility(View.GONE);
        this.loader.setVisibility(View.VISIBLE);
    }

    protected void onPostExecute(T result) {
        this.loader.setVisibility(View.GONE);

        if (result == null && this.noData != null) this.noData.setVisibility(View.VISIBLE);
        else {
            if (this.noData != null) this.noData.setVisibility(View.GONE);
            this.list.setVisibility(View.VISIBLE);

            this.process(result);
        }
    }

    protected abstract void process(T data);
}