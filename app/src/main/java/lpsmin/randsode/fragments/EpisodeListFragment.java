package lpsmin.randsode.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import lpsmin.randsode.R;
import lpsmin.randsode.adapters.RecyclerViewAdapter;
import lpsmin.randsode.adapters.holders.EpisodeHolder;
import lpsmin.randsode.models.Episode;
import lpsmin.randsode.models.Serie;

public class EpisodeListFragment extends Fragment {

    private RecyclerViewAdapter listAdapter;
    private TextView noData;
    private RecyclerView list;

    private Serie serie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_episode, container, false);

        this.serie = (Serie) getArguments().getSerializable("serie");

        list = (RecyclerView) v.findViewById(R.id.episode_list);
        noData = (TextView) v.findViewById(R.id.episode_no_data);

        final ArrayList<Episode> episodes = new ArrayList<>();
        listAdapter = new RecyclerViewAdapter(this.getActivity(), episodes, R.layout.holder_episode, EpisodeHolder.class);
        list.setAdapter(listAdapter);
        list.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        refresh();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        refresh();
    }

    public void refresh() {
        listAdapter.resetAndAdd(serie.getEpisodes());

        if (listAdapter.getItemCount() > 0) {
            noData.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
        }
    }
}
