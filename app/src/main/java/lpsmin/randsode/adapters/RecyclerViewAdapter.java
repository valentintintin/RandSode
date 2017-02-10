package lpsmin.randsode.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import lpsmin.randsode.adapters.holders.Holder;

public class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final int holderRessource;
    protected final Class holderClass;

    protected final Context context;
    protected List<T> list = Collections.emptyList();

    public RecyclerViewAdapter(Context context, List<T> list, int holderRessource, Class holderClass) {
        this.context = context;
        this.list = list;
        this.holderRessource = holderRessource;
        this.holderClass = holderClass;
    }

    @Override
    public Holder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(this.holderRessource, parent, false);
        return this.getHolder(this.holderClass, v);
    }

    protected Holder<T> getHolder(Class holderClass, View v) {
        try {
            return (Holder<T>) holderClass.getConstructor(View.class, Context.class).newInstance(v, this.context);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Holder)holder).bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void resetAndAdd(List<T> data) {
        this.list.clear();
        this.list.addAll(data);

        this.notifyDataSetChanged();
    }
}
