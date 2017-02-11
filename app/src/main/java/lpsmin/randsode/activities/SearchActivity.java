package lpsmin.randsode.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import lpsmin.randsode.R;
import lpsmin.randsode.adapters.RecyclerViewAdapter;
import lpsmin.randsode.adapters.holders.SerieHolder;
import lpsmin.randsode.models.Serie;
import lpsmin.randsode.requests.SearchRequest;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView list;
    private RecyclerViewAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final ArrayList<Serie> series = new ArrayList<>();

        this.list = (RecyclerView) findViewById(R.id.search_list);
        this.list.addItemDecoration(new DividerItemDecoration(list.getContext(), DividerItemDecoration.VERTICAL));

        this.listAdapter = new RecyclerViewAdapter(this, series, R.layout.holder_serie, SerieHolder.class);
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
            new SearchRequest(query, (FrameLayout) findViewById(R.id.search_load), this.listAdapter, this.list, (TextView) findViewById(R.id.search_no_data));
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
