package lpsmin.randsode;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;

import info.movito.themoviedbapi.model.tv.TvSeries;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AutoCompleteTextView search_field = (AutoCompleteTextView) findViewById(R.id.main_search_field2);

        ArrayList<TvSeries> series = new ArrayList<>();

        final RecyclerView list = (RecyclerView) findViewById(R.id.main_list);
        final RecyclerViewAdapter listAdapter = new RecyclerViewAdapter(this, series);
        list.setAdapter(listAdapter);
        list.setLayoutManager(new LinearLayoutManager(this));

        search_field.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (textView.getText().length() > 0) {
                        SearchTask task = new SearchTask(textView.getText().toString(), listAdapter);
                        task.execute();
                        InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    }
                }
                return false;
            }
        });
    }
}
