package lpsmin.randsode.activities;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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

import java.util.Date;
import java.util.Random;

import lpsmin.randsode.R;
import lpsmin.randsode.fragments.EpisodeListFragment;
import lpsmin.randsode.models.database.Episode;
import lpsmin.randsode.models.database.Serie;
import lpsmin.randsode.requests.EpisodeRequest;
import lpsmin.randsode.requests.SerieRequest;
import lpsmin.randsode.shared.HttpSingleton;
import lpsmin.randsode.shared.Synchro;

public class SerieActivity extends AppCompatActivity {

    private Serie serie;

    private FrameLayout loader;
    private FloatingActionMenu fabs;
    private FloatingActionButton random, favorite, favoriteDelete;
    private TextView summary;
    private FrameLayout episodeListFragmentContainer;
    private EpisodeListFragment episodeListFragment;

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
        this.summary = (TextView) findViewById(R.id.serie_summary);
        final NetworkImageView image = (NetworkImageView) findViewById(R.id.serie_image);
        favorite = (FloatingActionButton) findViewById(R.id.serie_favorite);
        favoriteDelete = (FloatingActionButton) findViewById(R.id.serie_favorite_delete);
        fabs = (FloatingActionMenu) findViewById(R.id.serie_fabs);
        this.episodeListFragmentContainer = (FrameLayout) findViewById(R.id.serie_list_episode_fragment);

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
            }
        });

        favoriteDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSerie();
                fabs.close(true);
            }
        });

        checkRandomPossible();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        this.episodeListFragment = new EpisodeListFragment();
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
                if (serie.getNumber_of_episodes() != 0) {
                    fabs.setVisibility(View.VISIBLE);

                    if (serie.exists() && serie.getEpisodes().size() < serie.getNumber_of_episodes()) {
                        favorite.setVisibility(View.GONE);
                        favoriteDelete.setVisibility(View.VISIBLE);
                        episodeListFragmentContainer.setVisibility(View.VISIBLE);

                        serie.save();
                    }
                } else {
                    Toast.makeText(SerieActivity.this, getString(R.string.no_informations_serie), Toast.LENGTH_LONG).show();
                }
            }
        }, loader);
    }

    private void giveRandomEpisode() {
        Random rand = new Random();

        int season = -1, episode = -1;
        boolean loop = true;

        while(loop){
            season = rand.nextInt(serie.getSeasons().size());
            episode = rand.nextInt(serie.getSeasons().get(season).getEpisodeCount()) + 1;
            if (serie.getNumber_of_seasons() == 1) season++;

            int i = 0;
            while (i < serie.getEpisodes().size() &&
                    serie.getEpisodes().get(i).getSeason_number() != season &&
                    serie.getEpisodes().get(i).getEpisode_number() != episode)
                i++;
            if (i == serie.getEpisodes().size()) loop = false;
        }

        if (season != -1 && episode != -1) {
            new EpisodeRequest(serie.getId(), season, episode, new Response.Listener<Episode>() {
                @Override
                public void onResponse(Episode response) {
                    response.setSerieId(serie.getId());
                    response.setDate_added(new Date().getTime());
                    createEpisodeDialog(response);
                }
            }, loader, random);
        } else Snackbar.make(loader, getResources().getString(R.string.error_random), Snackbar.LENGTH_SHORT).show();
    }

    private void saveSerie() {
        if (!this.serie.exists()) {
            serie.setDate_added(new Date().getTime());
            serie.save();
            favorite.setVisibility(View.GONE);
            favoriteDelete.setVisibility(View.VISIBLE);
            episodeListFragmentContainer.setVisibility(View.VISIBLE);
            Snackbar.make(loader, getResources().getString(R.string.serie_added), Snackbar.LENGTH_SHORT).show();

            if (Synchro.isAutoEnable(this)) Synchro.execute(this, Synchro.TYPE_EXPORT);
        }
    }

    private void deleteSerie() {
        if (this.serie.exists()) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setIcon(android.R.drawable.ic_delete);
            alertDialogBuilder.setTitle(R.string.delete_question_title);
            alertDialogBuilder.setMessage(R.string.delete_question);
            alertDialogBuilder.setPositiveButton(getResources().getString(R.string.dialog_ok_delete), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Snackbar.make(loader, getResources().getString(R.string.serie_deleted), Snackbar.LENGTH_SHORT).show();

                    if (Synchro.isAutoEnable(SerieActivity.this))
                        Synchro.execute(SerieActivity.this, Synchro.TYPE_EXPORT);

                    serie.delete();

                    favorite.setVisibility(View.VISIBLE);
                    favoriteDelete.setVisibility(View.GONE);
                    episodeListFragmentContainer.setVisibility(View.GONE);
                    episodeListFragment.refresh();
                }
            });
            alertDialogBuilder.setNegativeButton(getResources().getString(R.string.dialog_cancel_delete), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialogBuilder.show();
        }
    }

    private void createEpisodeDialog(final Episode episode) {
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
        TextView description = (TextView) dialog.findViewById(R.id.dialog_episode_description);
        description.setText(episode.getOverview());

        ImageButton no = (ImageButton) dialog.findViewById(R.id.dialog_episode_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                giveRandomEpisode();
            }
        });

        ImageButton yes = (ImageButton) dialog.findViewById(R.id.dialog_episode_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serie.setLast_watched(new Date().getTime());
                saveSerie();
                episode.save();

                if (Synchro.isAutoEnable(SerieActivity.this))
                    Synchro.execute(SerieActivity.this, Synchro.TYPE_EXPORT);

                dialog.dismiss();
                episodeListFragment.addEpisode(episode);
            }
        });

        dialog.show();
    }
}
