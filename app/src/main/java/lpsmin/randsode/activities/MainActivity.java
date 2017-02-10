package lpsmin.randsode.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.model.tv.TvSeries;
import lpsmin.randsode.R;
import lpsmin.randsode.adapters.HeaderRecyclerViewAdapter;
import lpsmin.randsode.adapters.holders.HeaderHolder;
import lpsmin.randsode.adapters.holders.SerieHolder;
import lpsmin.randsode.models.Serie;
import lpsmin.randsode.tasks.PopularTask;

public class MainActivity extends AppCompatActivity {

    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FrameLayout loader = (FrameLayout) findViewById(R.id.main_load);
        this.list = (RecyclerView) findViewById(R.id.main_list);

        final List<Serie> series = SQLite.select().from(Serie.class).queryList();

        final HeaderRecyclerViewAdapter listAdapter = new HeaderRecyclerViewAdapter(this, series, R.layout.holder_serie, SerieHolder.class, R.layout.holder_header, HeaderHolder.class);
        this.list.setAdapter(listAdapter);
        this.list.setLayoutManager(new LinearLayoutManager(this));

        if (series.isEmpty()) {
            PopularTask task = new PopularTask(loader, listAdapter, this.list, (TextView) findViewById(R.id.main_no_data));
            task.execute();
        } else {
            this.list.setVisibility(View.VISIBLE);
            loader.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) list.setVisibility(View.GONE);
                else list.setVisibility(View.VISIBLE);
            }
        });

        return true;
    }
}
