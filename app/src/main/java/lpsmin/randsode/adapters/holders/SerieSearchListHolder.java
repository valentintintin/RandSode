package lpsmin.randsode.adapters.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import info.movito.themoviedbapi.model.tv.TvSeries;
import lpsmin.randsode.R;
import lpsmin.randsode.activities.SerieActivity;
import lpsmin.randsode.shared.HttpSingleton;

public class SerieSearchListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final int MAX_SUMMARY_LENGTH = 300;

    private final ImageLoader imageLoader;

    private final TextView name;
    private final TextView summary;
    private final NetworkImageView image;

    private final Context context;

    private TvSeries serie;

    public SerieSearchListHolder(View itemView, Context context) {
        super(itemView);

        this.name = (TextView) itemView.findViewById(R.id.card_layout_name);
        this.summary = (TextView) itemView.findViewById(R.id.card_layout_summary);
        this.image = (NetworkImageView) itemView.findViewById(R.id.card_layout_image);

        this.context = context;
        this.imageLoader = HttpSingleton.getInstance(context).getImageLoader();

        itemView.setOnClickListener(this);
    }

    public void bind(TvSeries serie) {
        this.serie = serie;

        this.name.setText(serie.getName());
        this.summary.setText(serie.getOverview().substring(0, (serie.getOverview().length() < MAX_SUMMARY_LENGTH ? serie.getOverview().length() : MAX_SUMMARY_LENGTH)));

        if (serie.getPosterPath() != null) {
            this.image.setImageUrl("https://image.tmdb.org/t/p/w185/" + serie.getPosterPath(), this.imageLoader);
        } else this.image.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        Intent serieActivity = new Intent(this.context, SerieActivity.class);
        serieActivity.putExtra("serie", serie);
        this.context.startActivity(serieActivity);
    }
}