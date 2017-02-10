package lpsmin.randsode.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.ArrayList;
import java.util.Random;

import info.movito.themoviedbapi.model.tv.TvEpisode;
import info.movito.themoviedbapi.model.tv.TvSeries;
import lpsmin.randsode.R;
import lpsmin.randsode.adapters.RecyclerViewAdapter;
import lpsmin.randsode.adapters.holders.SerieHolder;
import lpsmin.randsode.models.Episode;
import lpsmin.randsode.models.Serie;
import lpsmin.randsode.shared.HttpSingleton;
import lpsmin.randsode.tasks.models.Closure;
import lpsmin.randsode.tasks.RandomTask;
import lpsmin.randsode.tasks.SerieTask;

public class SerieActivity extends AppCompatActivity {

    private TvSeries serie;

    private FrameLayout loader;
    private FloatingActionButton random;
    private TextView seasons, episodes;
    private FloatingActionMenu fabs;
    private FloatingActionButton favorite, favoriteDelete;

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
        seasons = (TextView) findViewById(R.id.serie_number_seasons);
        episodes = (TextView) findViewById(R.id.serie_number_episodes);
        favorite = (FloatingActionButton) findViewById(R.id.serie_favorite);
        favoriteDelete = (FloatingActionButton) findViewById(R.id.serie_favorite_delete);
        fabs = (FloatingActionMenu) findViewById(R.id.serie_fabs);

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
                saveSerie();
                fabs.close(true);
                Toast.makeText(getApplicationContext(), "Added !", Toast.LENGTH_SHORT).show();
            }
        });

        favoriteDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSerie();
                fabs.close(true);
                Toast.makeText(getApplicationContext(), "Deleted !", Toast.LENGTH_SHORT).show();
            }
        });

        if (serie instanceof Serie) {
            loader.setVisibility(View.GONE);
            favorite.setVisibility(View.GONE);
            favoriteDelete.setVisibility(View.VISIBLE);

            addNumbersInfos();
        } else {
            SerieTask task = new SerieTask(this.serie.getId(), loader, findViewById(R.id.serie_infos__toload), new Closure() {

                @Override
                public void go(Object data) {
                serie = (TvSeries) data;

                addNumbersInfos();
                }
            });
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

    private void addNumbersInfos() {
        seasons.setText(String.valueOf(serie.getNumberOfSeasons()));
        episodes.setText(String.valueOf(serie.getNumberOfEpisodes()));

        if (serie.getNumberOfSeasons() == 0 && serie.getNumberOfEpisodes() == 0) {
            fabs.setVisibility(View.GONE);
        }
    }

    private void giveRandomEpisodeProcess() {
        Random rand = new Random();
        final int season = rand.nextInt(serie.getNumberOfSeasons()) + 1;
        final int episode = rand.nextInt(serie.getSeasons().get(season).getEpisodes().size()) + 1;

        RandomTask task = new RandomTask(serie.getId(), season, episode, loader, random, new Closure() {
            @Override
            public void go(Object data) {
                createDialog((TvEpisode) data);
            }
        });
        task.execute();
    }

    private void giveRandomEpisode() {
        if (serie instanceof Serie) {
            SerieTask task = new SerieTask(this.serie.getId(), loader, findViewById(R.id.serie_infos__toload), new Closure() {

                @Override
                public void go(Object data) {
                    serie = (TvSeries) data;

                    giveRandomEpisodeProcess();
                }
            });
            task.execute();
        } else giveRandomEpisodeProcess();
    }

    private void saveSerie() {
        if (!(this.serie instanceof Serie)) {
            Serie serieORM = new Serie(serie);
            FlowManager.getModelAdapter(Serie.class).save(serieORM);
            favorite.setVisibility(View.GONE);
            favoriteDelete.setVisibility(View.VISIBLE);
        }
    }

    private void deleteSerie() {
        if (this.serie instanceof Serie) {
            FlowManager.getModelAdapter(Serie.class).delete((Serie) serie);
            favorite.setVisibility(View.VISIBLE);
            favoriteDelete.setVisibility(View.GONE);
        }
    }

    private void createDialog(final TvEpisode episode) {
        final Dialog dialog = new Dialog(SerieActivity.this);
        dialog.setContentView(R.layout.dialog_episode);
        dialog.setTitle(getResources().getString(R.string.dialog_title_episode));

        TextView title = (TextView) dialog.findViewById(R.id.episode_title);
        title.setText(episode.getName() + " (" + episode.getSeasonNumber() + "x" + episode.getEpisodeNumber() + ")");
        NetworkImageView image = (NetworkImageView) dialog.findViewById(R.id.episode_image);
        image.setDefaultImageResId(R.drawable.ic_no_image);
        image.setErrorImageResId(R.drawable.ic_no_image);
        image.setImageUrl("https://image.tmdb.org/t/p/w185/" + episode.getStillPath(), HttpSingleton.getInstance(SerieActivity.this).getImageLoader());
        if (episode.getStillPath().length() == 0) image.setVisibility(View.GONE);

        ImageButton no = (ImageButton) dialog.findViewById(R.id.episode_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Episode episodeOrm = new Episode(episode, false);
//                FlowManager.getModelAdapter(Serie.class).delete((Serie) serie);
//                Toast.makeText(getApplicationContext(), "Deleted !", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                giveRandomEpisode();
            }
        });

        ImageButton yes = (ImageButton) dialog.findViewById(R.id.episode_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSerie();
                Episode episodeOrm = new Episode(episode, true);
                FlowManager.getModelAdapter(Episode.class).save(episodeOrm);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
