package lpsmin.randsode.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

import info.movito.themoviedbapi.model.tv.TvSeries;
import lpsmin.randsode.R;
import lpsmin.randsode.adapters.SerieRecyclerViewAdapter;
import lpsmin.randsode.tasks.SearchTask;

public class SearchActivity extends AppCompatActivity {

    private SerieRecyclerViewAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final ArrayList<TvSeries> series = new ArrayList<>();

        RecyclerView list = (RecyclerView) findViewById(R.id.search_list);
        list.addItemDecoration(new DividerItemDecoration(list.getContext(), DividerItemDecoration.VERTICAL));

        this.listAdapter = new SerieRecyclerViewAdapter(this, series);
        list.setAdapter(listAdapter);
        list.setLayoutManager(new LinearLayoutManager(this));

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            setTitle(getResources().getString(R.string.search_activity_title_caption) + " " + query);

            search(query);
        }
    }

    private void search(String query) {
        if (query.length() > 0) {
            SearchTask task = new SearchTask(query, this.listAdapter);
            task.execute();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
