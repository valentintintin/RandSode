package lpsmin.randsode.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import info.movito.themoviedbapi.model.tv.TvSeries;
import lpsmin.randsode.R;
import lpsmin.randsode.adapters.SeriePopularRecyclerViewAdapter;
import lpsmin.randsode.adapters.SerieSearchRecyclerViewAdapter;
import lpsmin.randsode.tasks.PopularTask;

public class MainActivity extends AppCompatActivity { // implements SearchView.OnQueryTextListener

    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ArrayList<TvSeries> series = new ArrayList<>();

        this.list = (RecyclerView) findViewById(R.id.main_list);
        this.list.addItemDecoration(new DividerItemDecoration(this.list.getContext(), DividerItemDecoration.VERTICAL));
        final SeriePopularRecyclerViewAdapter listAdapter = new SeriePopularRecyclerViewAdapter(this, series);
        this.list.setAdapter(listAdapter);
        this.list.setLayoutManager(new LinearLayoutManager(this));

        if (series.isEmpty()) {
            PopularTask task = new PopularTask((FrameLayout) findViewById(R.id.main_load), listAdapter, this.list, (TextView) findViewById(R.id.main_no_data));
            task.execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.i("b", b + "");
                if (b) {
                    list.setVisibility(View.GONE);
                } else {
                    list.setVisibility(View.VISIBLE);
                }
            }
        });

//        searchView.setOnQueryTextListener(this);

        return true;
    }
}
