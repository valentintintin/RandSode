package lpsmin.randsode;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import info.movito.themoviedbapi.model.tv.TvSeries;

public class MainActivity extends AppCompatActivity { // implements SearchView.OnQueryTextListener

    private RecyclerView list;
    private RecyclerViewAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ArrayList<TvSeries> series = new ArrayList<>();

        this.list = (RecyclerView) findViewById(R.id.main_list);
        this.listAdapter = new RecyclerViewAdapter(this, series);
        this.list.setAdapter(listAdapter);
        this.list.setLayoutManager(new LinearLayoutManager(this));
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
