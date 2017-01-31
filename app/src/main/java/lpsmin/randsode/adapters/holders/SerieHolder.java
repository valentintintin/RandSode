package lpsmin.randsode.adapters.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import info.movito.themoviedbapi.model.tv.TvSeries;
import lpsmin.randsode.R;
import lpsmin.randsode.activities.SerieActivity;
import lpsmin.randsode.shared.HttpSingleton;

public class SerieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView name;
    private final TextView yearLabel, year;
    private final NetworkImageView image;

    private final Context context;

    private TvSeries serie;

    public SerieHolder(View itemView, Context context) {
        super(itemView);

        this.name = (TextView) itemView.findViewById(R.id.holder_serie_name);
        this.yearLabel = (TextView) itemView.findViewById(R.id.holder_serie_year_label);
        this.year = (TextView) itemView.findViewById(R.id.holder_serie_year);
        this.image = (NetworkImageView) itemView.findViewById(R.id.holder_serie_image);
        this.image.setDefaultImageResId(R.drawable.ic_no_image);
        this.image.setErrorImageResId(R.drawable.ic_no_image);

        this.context = context;

        itemView.setOnClickListener(this);
    }

    public void bind(TvSeries serie) {
        this.serie = serie;

        this.name.setText(serie.getName());

        if (serie.getFirstAirDate().length() >= 4) {
            this.year.setText(serie.getFirstAirDate().substring(0, 4));
        } else {
            this.yearLabel.setVisibility(View.INVISIBLE);
            this.year.setVisibility(View.INVISIBLE);
        }

        if (serie.getBackdropPath() != null) image.setImageUrl("https://image.tmdb.org/t/p/w185/" + serie.getBackdropPath(), HttpSingleton.getInstance(this.context).getImageLoader());
        else if (serie.getPosterPath() != null) image.setImageUrl("https://image.tmdb.org/t/p/w185/" + serie.getPosterPath(), HttpSingleton.getInstance(this.context).getImageLoader());
    }

    @Override
    public void onClick(View view) {
        Intent serieActivity = new Intent(this.context, SerieActivity.class);
        serieActivity.putExtra("serie", serie);
        this.context.startActivity(serieActivity);
    }
}