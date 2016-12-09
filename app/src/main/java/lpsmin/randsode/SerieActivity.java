package lpsmin.randsode;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import info.movito.themoviedbapi.model.tv.TvSeries;

public class SerieActivity extends AppCompatActivity {

    TvSeries serie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.serie = (TvSeries) getIntent().getSerializableExtra("serie");

        TextView summary = (TextView) findViewById(R.id.serie_summary);
        setTitle(this.serie.getName());
        ImageView image = (ImageView) findViewById(R.id.serie_image);
        Picasso.with(this).load("https://image.tmdb.org/t/p/w185/" + serie.getPosterPath()).into(image);
        summary.setText(this.serie.getOverview());

        FloatingActionButton favorite = (FloatingActionButton) findViewById(R.id.fab_favorite);
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
}
