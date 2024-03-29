package lpsmin.randsode.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import lpsmin.randsode.R;
import lpsmin.randsode.adapters.RecyclerViewAdapter;
import lpsmin.randsode.adapters.holders.SerieHolder;
import lpsmin.randsode.models.database.Serie;
import lpsmin.randsode.requests.PopularRequest;

public class PopularListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_popular, container, false);

        final ArrayList<Serie> series = new ArrayList<>();
        final RecyclerView list = (RecyclerView) v.findViewById(R.id.popular_list);
        final RecyclerViewAdapter listAdapter = new RecyclerViewAdapter(this.getActivity(), series, R.layout.holder_serie, SerieHolder.class);
        list.setAdapter(listAdapter);
        list.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        new PopularRequest((FrameLayout) v.findViewById(R.id.popular_load), listAdapter, list, (TextView) v.findViewById(R.id.popular_no_data));

        return v;
    }
}
