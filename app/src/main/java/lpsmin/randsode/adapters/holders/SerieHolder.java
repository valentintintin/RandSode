package lpsmin.randsode.adapters.holders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import lpsmin.randsode.R;
import lpsmin.randsode.activities.SerieActivity;
import lpsmin.randsode.models.database.Serie;
import lpsmin.randsode.shared.HttpSingleton;

public class SerieHolder extends Holder<Serie> {

    private final TextView name;
    private final TextView year;
    private final TextView rank;
    private final TextView summary;
    private final NetworkImageView image;

    private Serie serie;

    public SerieHolder(View itemView, Context context) {
        super(itemView, context);

        this.name = (TextView) itemView.findViewById(R.id.holder_serie_name);
        this.year = (TextView) itemView.findViewById(R.id.holder_serie_year);
        this.image = (NetworkImageView) itemView.findViewById(R.id.holder_serie_image);
        this.image.setDefaultImageResId(R.drawable.ic_no_image);
        this.image.setErrorImageResId(R.drawable.ic_no_image);
        this.rank = (TextView) itemView.findViewById(R.id.holder_serie_rank);
        TextView genres = (TextView) itemView.findViewById(R.id.holder_serie_genres);
        this.summary = (TextView) itemView.findViewById(R.id.holder_serie_summary);

        itemView.setOnClickListener(this);
    }

    public void bind(Serie serie) {
        this.serie = serie;

        this.name.setText(serie.getName());

        this.rank.setText(String.valueOf(serie.getVote_average()));
        if (serie.getVote_count() == 0) this.rank.setVisibility(View.GONE);

        this.summary.setText(serie.getOverview());
        if (serie.getOverview().length() == 0) this.summary.setVisibility(View.GONE);

//        String genres = "";
////        try {
//            for (Genre g : serie.getGenres()) genres += g.getId() + ",";
//            if (genres.length() > 0) genres = genres.substring(-1);
//            this.genres.setText(genres);
////        } catch (NullPointerException e) {}

        if (serie.getFirst_air_date().length() >= 4) this.year.setText(serie.getFirst_air_date().substring(0, 4));
        else this.year.setVisibility(View.INVISIBLE);

        if (serie.getPoster_path() != null) image.setImageUrl("https://image.tmdb.org/t/p/w185/" + serie.getPoster_path(), HttpSingleton.getInstance().getImageLoader());
        else if (serie.getBackdrop_path() != null) image.setImageUrl("https://image.tmdb.org/t/p/w185/" + serie.getBackdrop_path(), HttpSingleton.getInstance().getImageLoader());
    }

    @Override
    public void onClick(View view) {
        Intent serieActivity = new Intent(this.context, SerieActivity.class);
        serieActivity.putExtra("serie", serie);
        this.context.startActivity(serieActivity);
    }
}