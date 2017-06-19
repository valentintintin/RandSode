package lpsmin.randsode.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;

import lpsmin.randsode.R;
import lpsmin.randsode.adapters.RecyclerViewAdapter;
import lpsmin.randsode.adapters.holders.SerieHolder;
import lpsmin.randsode.models.database.Serie;
import lpsmin.randsode.models.database.Serie_Table;

public class MySeriesListFragment extends Fragment {

    private static MySeriesListFragment instance = null;

    private RecyclerViewAdapter listAdapter;
    private TextView noData;
    private RecyclerView list;

    public static void update() {
        if (MySeriesListFragment.instance != null) MySeriesListFragment.instance.refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_series, container, false);

        list = (RecyclerView) v.findViewById(R.id.my_series_list);
        noData = (TextView) v.findViewById(R.id.my_series_no_data);

        final ArrayList<Serie> series = new ArrayList<>();
        listAdapter = new RecyclerViewAdapter(this.getActivity(), series, R.layout.holder_serie, SerieHolder.class);
        list.setAdapter(listAdapter);
        list.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        refresh();

        MySeriesListFragment.instance = this;

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        refresh();
    }

    private void refresh() {
        listAdapter.resetAndAdd(SQLite.select().from(Serie.class).orderBy(Serie_Table.date_added, false).queryList());

        if (listAdapter.getItemCount() > 0) {
            noData.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
        }
    }
}
