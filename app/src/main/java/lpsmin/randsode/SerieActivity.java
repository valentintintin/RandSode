package lpsmin.randsode;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import info.movito.themoviedbapi.model.tv.TvSeries;

public class SerieActivity extends AppCompatActivity {

    TvSeries serie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.serie = (TvSeries) getIntent().getSerializableExtra("serie");

        TextView summary = (TextView) findViewById(R.id.serie_summary);
        setTitle(this.serie.getName());
        NetworkImageView image = (NetworkImageView) findViewById(R.id.serie_image);
        image.setImageUrl("https://image.tmdb.org/t/p/w185/" + serie.getPosterPath(), HttpSingleton.getInstance(this).getImageLoader());
        summary.setText(this.serie.getOverview());

        FloatingActionButton favorite = (FloatingActionButton) findViewById(R.id.fab_favorite);
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
