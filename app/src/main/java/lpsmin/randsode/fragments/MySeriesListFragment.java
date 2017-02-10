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

import java.util.List;

import lpsmin.randsode.R;
import lpsmin.randsode.adapters.RecyclerViewAdapter;
import lpsmin.randsode.adapters.holders.SerieHolder;
import lpsmin.randsode.models.Serie;

public class MySeriesListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_series, container, false);

        final RecyclerView list = (RecyclerView) v.findViewById(R.id.my_series_list);
        final TextView noData = (TextView) v.findViewById(R.id.my_series_no_data);

        final List<Serie> series = SQLite.select().from(Serie.class).queryList();
        final RecyclerViewAdapter listAdapter = new RecyclerViewAdapter(this.getActivity(), series, R.layout.holder_serie, SerieHolder.class);
        list.setAdapter(listAdapter);
        list.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        if (series.isEmpty()) noData.setVisibility(View.VISIBLE);

        return v;
    }
}
