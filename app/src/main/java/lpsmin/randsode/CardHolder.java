package lpsmin.randsode;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import info.movito.themoviedbapi.model.tv.TvSeries;

public class CardHolder extends RecyclerView.ViewHolder {

    private static final int MAX_SUMMARY_LENGTH = 300;

   private TextView name;
   private TextView summary;
   private ImageView image;

    private Context context;

   public CardHolder(View itemView, Context context) {
       super(itemView);

       this.name = (TextView) itemView.findViewById(R.id.card_layout_name);
       this.summary = (TextView) itemView.findViewById(R.id.card_layout_summary);
       this.image = (ImageView) itemView.findViewById(R.id.card_layout_image);

       this.context = context;
   }

    public void bind(TvSeries serie) {
        this.name.setText(serie.getName());
        this.summary.setText(serie.getOverview().substring(0, (serie.getOverview().length() < MAX_SUMMARY_LENGTH ? serie.getOverview().length() : MAX_SUMMARY_LENGTH)));

        if (serie.getPosterPath() != null) {
            Picasso.with(this.context).load("https://image.tmdb.org/t/p/w185/" + serie.getPosterPath()).into(this.image);
        } else this.image.setVisibility(View.INVISIBLE);
    }
}