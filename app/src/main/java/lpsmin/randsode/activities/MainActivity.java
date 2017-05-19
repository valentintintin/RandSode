package lpsmin.randsode.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lpsmin.randsode.R;
import lpsmin.randsode.fragments.MySeriesListFragment;
import lpsmin.randsode.fragments.PopularListFragment;
import lpsmin.randsode.shared.HttpSingleton;
import lpsmin.randsode.shared.Synchro;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        HttpSingleton.createInstance(this); //Creation of the HTTP singleton

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.main_tablelayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_viewpager);

        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);

        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.main_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuItem.expandActionView();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO hide keyboard
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        menuItem = menu.findItem(R.id.main_search_menu);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setFocusable(true);

        final MenuItem connexionItem = menu.findItem(R.id.main_connexion_menu);
        final MenuItem synchroniseItem = menu.findItem(R.id.main_synchro_menu);
        final MenuItem autoSynchroItem = menu.findItem(R.id.main_auto_synchro_menu);

        autoSynchroItem.setChecked(Synchro.isAutoEnable(this));

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    connexionItem.setVisible(false);
                    synchroniseItem.setVisible(false);
                    autoSynchroItem.setVisible(false);
                    tabLayout.setVisibility(View.GONE);
                } else {
                    connexionItem.setVisible(true);
                    synchroniseItem.setVisible(true);
                    autoSynchroItem.setVisible(true);
                    tabLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        connexionItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

                return true;
            }
        });

        synchroniseItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dialogSynchroExecute();

                return true;
            }
        });

        autoSynchroItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                item.setChecked(!item.isChecked());
                Synchro.setAutoEnable(MainActivity.this, item.isChecked());
                if (item.isChecked()) dialogSynchroExecute();

                return true;
            }
        });

        return true;
    }

    private void dialogSynchroExecute() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.synchro_dialog_title)
                .setItems(R.array.synchro_type, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Synchro.execute(MainActivity.this, which);
                    }
                });
        builder.create().show();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MySeriesListFragment(), getResources().getString(R.string.main_title_my_series));
        adapter.addFragment(new PopularListFragment(), getResources().getString(R.string.main_title_popular));
        viewPager.setAdapter(adapter);

        tabLayout.getTabAt(0).setIcon(android.R.drawable.star_on);
        tabLayout.getTabAt(1).setIcon(android.R.drawable.ic_media_play);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
