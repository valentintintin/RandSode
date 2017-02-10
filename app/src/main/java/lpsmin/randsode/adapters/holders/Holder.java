package lpsmin.randsode.adapters.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class Holder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected final Context context;

    public Holder(View itemView, Context context) {
        super(itemView);

        this.context = context;
    }

    public abstract void bind(T data);

    @Override
    public void onClick(View view) {
    }
}
