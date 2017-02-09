package lpsmin.randsode.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.w3c.dom.Text;

import info.movito.themoviedbapi.model.tv.TvSeries;
import lpsmin.randsode.R;
import lpsmin.randsode.shared.HttpSingleton;
import lpsmin.randsode.tasks.Populate;
import lpsmin.randsode.tasks.SerieTask;

public class SerieActivity extends AppCompatActivity {

    private TvSeries serie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView summary = (TextView) findViewById(R.id.serie_summary);
        NetworkImageView image = (NetworkImageView) findViewById(R.id.serie_image);
        final TextView seasons = (TextView) findViewById(R.id.serie_number_seasons);
        final TextView episodes = (TextView) findViewById(R.id.serie_number_episodes);
        Button random = (Button) findViewById(R.id.serie_random);
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        this.serie = (TvSeries) getIntent().getSerializableExtra("serie");
        setTitle(serie.getName());
        summary.setText(serie.getOverview());

        if (serie.getBackdropPath() != null)
            image.setImageUrl("https://image.tmdb.org/t/p/w342/" + serie.getBackdropPath(), HttpSingleton.getInstance(getApplicationContext()).getImageLoader());
        else if (serie.getPosterPath() != null)
            image.setImageUrl("https://image.tmdb.org/t/p/w342/" + serie.getPosterPath(), HttpSingleton.getInstance(getApplicationContext()).getImageLoader());

        SerieTask task = new SerieTask(this.serie.getId(), (FrameLayout) findViewById(R.id.serie_load), (LinearLayout) findViewById(R.id.serie_infos__toload), new Populate() {

            @Override
            public void go(TvSeries data) {
                serie = data;

                seasons.setText(serie.getNumberOfSeasons() + "");
                episodes.setText(serie.getNumberOfEpisodes() + "");
            }
        });
        task.execute();
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
