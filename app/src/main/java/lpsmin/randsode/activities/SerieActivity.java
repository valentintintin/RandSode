package lpsmin.randsode.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import info.movito.themoviedbapi.model.tv.TvSeries;
import lpsmin.randsode.R;
import lpsmin.randsode.shared.HttpSingleton;

public class SerieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TvSeries serie = (TvSeries) getIntent().getSerializableExtra("serie");

        TextView summary = (TextView) findViewById(R.id.serie_summary);
        setTitle(serie.getName());

        NetworkImageView image = (NetworkImageView) findViewById(R.id.serie_image);
        if (serie.getBackdropPath() != null) image.setImageUrl("https://image.tmdb.org/t/p/w185/" + serie.getBackdropPath(), HttpSingleton.getInstance(this).getImageLoader());
        else if (serie.getPosterPath() != null) image.setImageUrl("https://image.tmdb.org/t/p/w185/" + serie.getPosterPath(), HttpSingleton.getInstance(this).getImageLoader());

        summary.setText(serie.getOverview());
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
