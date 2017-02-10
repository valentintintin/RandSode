package lpsmin.randsode.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Random;

import info.movito.themoviedbapi.model.tv.TvEpisode;
import info.movito.themoviedbapi.model.tv.TvSeries;
import lpsmin.randsode.R;
import lpsmin.randsode.adapters.RecyclerViewAdapter;
import lpsmin.randsode.adapters.holders.SerieHolder;
import lpsmin.randsode.shared.HttpSingleton;
import lpsmin.randsode.tasks.Closure;
import lpsmin.randsode.tasks.RandomTask;
import lpsmin.randsode.tasks.SerieTask;

public class SerieActivity extends AppCompatActivity {

    private TvSeries serie;

    private FrameLayout loader;
    private FloatingActionButton random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.serie = (TvSeries) getIntent().getSerializableExtra("serie");

        this.loader = (FrameLayout) findViewById(R.id.serie_load);
        this.random = (FloatingActionButton) findViewById(R.id.serie_random);
        RecyclerView list = (RecyclerView) findViewById(R.id.serie_list);
        TextView summary = (TextView) findViewById(R.id.serie_summary);
        NetworkImageView image = (NetworkImageView) findViewById(R.id.serie_image);
        final TextView seasons = (TextView) findViewById(R.id.serie_number_seasons);
        final TextView episodes = (TextView) findViewById(R.id.serie_number_episodes);
        final FloatingActionButton favorite = (FloatingActionButton) findViewById(R.id.serie_favorite);
        final FloatingActionMenu fabs = (FloatingActionMenu) findViewById(R.id.serie_fabs);

        final ArrayList<TvEpisode> episodesList = new ArrayList<>();
        final RecyclerViewAdapter listAdapter = new RecyclerViewAdapter(this, episodesList, R.layout.holder_serie, SerieHolder.class);
        list.setAdapter(listAdapter);
        list.setLayoutManager(new LinearLayoutManager(this));

        setTitle(serie.getName());
        summary.setText(serie.getOverview());

        if (serie.getBackdropPath() != null)
            image.setImageUrl("https://image.tmdb.org/t/p/w342/" + serie.getBackdropPath(), HttpSingleton.getInstance(getApplicationContext()).getImageLoader());
        else if (serie.getPosterPath() != null)
            image.setImageUrl("https://image.tmdb.org/t/p/w342/" + serie.getPosterPath(), HttpSingleton.getInstance(getApplicationContext()).getImageLoader());


        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giveRandomEpisode();
                fabs.close(true);
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabs.close(true);
            }
        });

        SerieTask task = new SerieTask(this.serie.getId(), loader, findViewById(R.id.serie_infos__toload), new Closure() {

            @Override
            public void go(Object data) {
                serie = (TvSeries) data;

                seasons.setText(String.valueOf(serie.getNumberOfSeasons()));
                episodes.setText(String.valueOf(serie.getNumberOfEpisodes()));

                if (serie.getNumberOfSeasons() == 0 && serie.getNumberOfEpisodes() == 0) {
                    random.setVisibility(View.GONE);
                    favorite.setVisibility(View.GONE);
                }
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

    private void giveRandomEpisode() {
        Random rand = new Random();
        final int season = rand.nextInt(serie.getNumberOfSeasons()) + 1;
        final int episode = rand.nextInt(serie.getEpisodeRuntime().size()) + 1;

        RandomTask task = new RandomTask(serie.getId(), season, episode, loader, random, new Closure() {
            @Override
            public void go(Object data) {
                createDialog((TvEpisode) data);
            }
        });
        task.execute();
    }

    private void createDialog(TvEpisode episode) {
        final Dialog dialog = new Dialog(SerieActivity.this);
        dialog.setContentView(R.layout.dialog_episode);
        dialog.setTitle("Watch " + episode.getName() + "?");

        TextView title = (TextView) dialog.findViewById(R.id.episode_title);
        title.setText(episode.getName());
        NetworkImageView image = (NetworkImageView) dialog.findViewById(R.id.episode_image);
        image.setDefaultImageResId(R.drawable.ic_no_image);
        image.setErrorImageResId(R.drawable.ic_no_image);
        image.setImageUrl("https://image.tmdb.org/t/p/w185/" + episode.getStillPath(), HttpSingleton.getInstance(SerieActivity.this).getImageLoader());
        if (episode.getStillPath().length() == 0) image.setVisibility(View.GONE);

        ImageButton no = (ImageButton) dialog.findViewById(R.id.episode_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ImageButton yes = (ImageButton) dialog.findViewById(R.id.episode_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //yes
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
