package lpsmin.randsode.adapters.holders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

import lpsmin.randsode.R;
import lpsmin.randsode.models.Episode;
import lpsmin.randsode.shared.HttpSingleton;

public class EpisodeHolder extends Holder<Episode> {

    private final TextView name;
    private final TextView year;
    private final TextView rank;
    private final TextView watched;
    private final NetworkImageView image;

    public EpisodeHolder(View itemView, Context context) {
        super(itemView, context);

        this.name = (TextView) itemView.findViewById(R.id.holder_episode_name);
        this.year = (TextView) itemView.findViewById(R.id.holder_episode_year);
        this.image = (NetworkImageView) itemView.findViewById(R.id.holder_episode_image);
        this.image.setDefaultImageResId(R.drawable.ic_no_image);
        this.image.setErrorImageResId(R.drawable.ic_no_image);
        this.rank = (TextView) itemView.findViewById(R.id.holder_episode_rank);
        this.watched = (TextView) itemView.findViewById(R.id.holder_episode_watched);

        itemView.setOnClickListener(this);
    }

    public void bind(Episode episode) {
        this.name.setText(episode.getName() + " (" + episode.getSeason_number() + "x" + episode.getEpisode_number() + ")");

        this.watched.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date(episode.getDate_added())));

        this.rank.setText(String.valueOf(episode.getVote_average()));
        if (episode.getVote_count() == 0) this.rank.setVisibility(View.GONE);

        if (episode.getAir_date().length() >= 4)
            this.year.setText(episode.getAir_date().substring(0, 4));
        else this.year.setVisibility(View.INVISIBLE);

        if (episode.getStill_path() != null)
            image.setImageUrl("https://image.tmdb.org/t/p/w185/" + episode.getStill_path(), HttpSingleton.getInstance().getImageLoader());
    }
}