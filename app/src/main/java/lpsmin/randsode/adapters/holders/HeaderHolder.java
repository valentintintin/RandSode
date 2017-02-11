package lpsmin.randsode.adapters.holders;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import lpsmin.randsode.R;

public class HeaderHolder extends Holder<Boolean> {

    private final TextView title, help;

    public HeaderHolder(View itemView, Context context) {
        super(itemView, context);

        this.title = (TextView) itemView.findViewById(R.id.holder_header_title);
        this.help = (TextView) itemView.findViewById(R.id.holder_header_help);
    }

    public void bind(Boolean empty) {
    }

    @Override
    public void onClick(View view) {
    }
}