package lpsmin.randsode.activities;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.NetworkImageView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.Random;

import lpsmin.randsode.R;
import lpsmin.randsode.fragments.EpisodeListFragment;
import lpsmin.randsode.models.Episode;
import lpsmin.randsode.models.Serie;
import lpsmin.randsode.requests.EpisodeRequest;
import lpsmin.randsode.requests.SerieRequest;
import lpsmin.randsode.shared.HttpSingleton;

public class SerieActivity extends AppCompatActivity {

    private Serie serie;

    private FrameLayout loader;
    private FloatingActionButton random;
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

        this.serie = (Serie) getIntent().getSerializableExtra("serie");

        this.loader = (FrameLayout) findViewById(R.id.serie_load);
        this.random = (FloatingActionButton) findViewById(R.id.serie_random);
        final TextView summary = (TextView) findViewById(R.id.serie_summary);
        final NetworkImageView image = (NetworkImageView) findViewById(R.id.serie_image);
        favorite = (FloatingActionButton) findViewById(R.id.serie_favorite);
        favoriteDelete = (FloatingActionButton) findViewById(R.id.serie_favorite_delete);
        fabs = (FloatingActionMenu) findViewById(R.id.serie_fabs);

        setTitle(serie.getName());
        summary.setText(serie.getOverview());

        if (serie.getBackdrop_path() != null)
            image.setImageUrl("https://image.tmdb.org/t/p/w342/" + serie.getBackdrop_path(), HttpSingleton.getInstance().getImageLoader());
        else if (serie.getPoster_path() != null)
            image.setImageUrl("https://image.tmdb.org/t/p/w342/" + serie.getPoster_path(), HttpSingleton.getInstance().getImageLoader());

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

        checkRandomPossible();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        EpisodeListFragment episodeListFragment = new EpisodeListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("serie", serie);
        episodeListFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.serie_list_episode_fragment, episodeListFragment);
        fragmentTransaction.commit();
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

    private void checkRandomPossible() {
        new SerieRequest(this.serie.getId(), new Response.Listener<Serie>() {
            @Override
            public void onResponse(Serie response) {
                serie = response;
                if (serie.getNumber_of_seasons() == 0 && serie.getNumber_of_episodes() == 0) {
                    fabs.setVisibility(View.GONE);
                }
            }
        }, loader);
    }

    private void giveRandomEpisode() {
        Random rand = new Random();
        final int season = rand.nextInt(serie.getNumber_of_seasons()) + 1;
        final int episode = rand.nextInt(serie.getSeasons().get(season).getEpisodeCount()) + 1;

        new EpisodeRequest(serie.getId(), season, episode, new Response.Listener<Episode>() {
            @Override
            public void onResponse(Episode response) {
                response.setSerieId(serie.getId());
                createDialog(response);
            }
        }, loader, random);
    }

    private void saveSerie() {
        if (!this.serie.exists()) {
            serie.save();
            favorite.setVisibility(View.GONE);
            favoriteDelete.setVisibility(View.VISIBLE);
        }
    }

    private void deleteSerie() {
        if (this.serie.exists()) {
            serie.delete();
            favorite.setVisibility(View.VISIBLE);
            favoriteDelete.setVisibility(View.GONE);
        }
    }

    private void createDialog(final Episode episode) {
        final Dialog dialog = new Dialog(SerieActivity.this);
        dialog.setContentView(R.layout.dialog_episode);
        dialog.setTitle(getResources().getString(R.string.dialog_title_episode));

        TextView title = (TextView) dialog.findViewById(R.id.dialog_episode_title);
        title.setText(episode.getName() + " (" + episode.getSeason_number() + "x" + episode.getEpisode_number() + ")");
        NetworkImageView image = (NetworkImageView) dialog.findViewById(R.id.dialog_episode_image);
        image.setDefaultImageResId(R.drawable.ic_no_image);
        image.setErrorImageResId(R.drawable.ic_no_image);
        if (episode.getStill_path() == null) image.setVisibility(View.GONE);
        else image.setImageUrl("https://image.tmdb.org/t/p/w185/" + episode.getStill_path(), HttpSingleton.getInstance().getImageLoader());

        ImageButton no = (ImageButton) dialog.findViewById(R.id.dialog_episode_no);
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

        ImageButton yes = (ImageButton) dialog.findViewById(R.id.dialog_episode_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSerie();
                episode.save();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
