package lpsmin.randsode.adapters.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import info.movito.themoviedbapi.model.tv.TvSeries;
import lpsmin.randsode.R;
import lpsmin.randsode.activities.SerieActivity;
import lpsmin.randsode.shared.HttpSingleton;
import lpsmin.randsode.tasks.PopularTask;

public class HeaderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView title, help;


    public HeaderHolder(View itemView) {
        super(itemView);

        this.title = (TextView) itemView.findViewById(R.id.holder_header_title);
        this.help = (TextView) itemView.findViewById(R.id.holder_header_help);
    }

    public void bind(boolean empty) {
        if (!empty) { //TODO bug
            title.setText(R.string.main_title_popular);
            help.setVisibility(View.VISIBLE);
        } else {
            help.setVisibility(View.GONE);
            title.setText(R.string.main_title_favorite);
        }
    }

    @Override
    public void onClick(View view) {
    }
}