package lpsmin.randsode.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;

import info.movito.themoviedbapi.model.tv.TvSeries;
import lpsmin.randsode.R;
import lpsmin.randsode.adapters.SerieRecyclerViewAdapter;
import lpsmin.randsode.tasks.PopularTask;

public class MainActivity extends AppCompatActivity { // implements SearchView.OnQueryTextListener

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ArrayList<TvSeries> series = new ArrayList<>();

        RecyclerView list = (RecyclerView) findViewById(R.id.main_list);
        list.addItemDecoration(new DividerItemDecoration(list.getContext(), DividerItemDecoration.VERTICAL));
        SerieRecyclerViewAdapter listAdapter = new SerieRecyclerViewAdapter(this, series);
        list.setAdapter(listAdapter);
        list.setLayoutManager(new LinearLayoutManager(this));

        if (series.isEmpty()) {
            PopularTask task = new PopularTask(listAdapter);
            task.execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setOnQueryTextListener(this);

        return true;
    }
}
