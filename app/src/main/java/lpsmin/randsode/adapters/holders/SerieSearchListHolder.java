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

public class SerieSearchListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ImageLoader imageLoader;

    private final TextView name;
    private final TextView yearLabel, year;
    private final NetworkImageView image;

    private final Context context;

    private TvSeries serie;

    public SerieSearchListHolder(View itemView, Context context) {
        super(itemView);

        this.name = (TextView) itemView.findViewById(R.id.holder_search_name);
        this.yearLabel = (TextView) itemView.findViewById(R.id.holder_search_year_label);
        this.year = (TextView) itemView.findViewById(R.id.holder_search_year);
        this.image = (NetworkImageView) itemView.findViewById(R.id.holder_search_image);

        this.context = context;
        this.imageLoader = HttpSingleton.getInstance(context).getImageLoader();

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

        if (serie.getPosterPath() != null) {
            this.image.setImageUrl("https://image.tmdb.org/t/p/w185/" + serie.getPosterPath(), this.imageLoader);
        }
    }

    @Override
    public void onClick(View view) {
        Intent serieActivity = new Intent(this.context, SerieActivity.class);
        serieActivity.putExtra("serie", serie);
        this.context.startActivity(serieActivity);
    }
}