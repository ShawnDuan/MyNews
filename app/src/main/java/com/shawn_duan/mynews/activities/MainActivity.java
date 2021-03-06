package com.shawn_duan.mynews.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.shawn_duan.mynews.fragments.DigestFragment;
import com.shawn_duan.mynews.R;
import com.shawn_duan.mynews.fragments.FilterDialogFragment;
import com.shawn_duan.mynews.fragments.ResultsFragment;
import com.shawn_duan.mynews.models.FilterSettings;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private boolean isShowingResults;

    private FragmentManager mFragmentManager = getSupportFragmentManager();
    public ResultsFragment mResultsFragment;

    private MenuItem mSearchItem, mSettingItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Most Viewed");

        pushFragment(new DigestFragment(), false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            showSettingsDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {     // todo refactor this method
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mSettingItem = menu.findItem(R.id.action_settings);
        mSearchItem = menu.findItem(R.id.action_search);

        mSettingItem.setVisible(false);
        setSearchMenuItem();
        return true;
    }

    private void setSearchMenuItem() {
        MenuItemCompat.setOnActionExpandListener(mSearchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                // mSettingItem.setVisible(true);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                // back button pressed, back arrow clicked, should go back to main browsing mode.
                mSettingItem.setVisible(false);
                if (isShowingResults) {
                    Log.d(TAG, "fragment # : " + mFragmentManager.getFragments().size());
                    onBackPressed();
                    isShowingResults = false;
                }
                return true;
            }
        });

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(mSearchItem);
        // Customize searchview text and hint colors
        int searchEditId = android.support.v7.appcompat.R.id.search_src_text;
        EditText et = (EditText) searchView.findViewById(searchEditId);
        et.setTextColor(Color.WHITE);
        et.setHintTextColor(Color.WHITE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // do query, show result in resultFragment
                if (!isShowingResults) {
                    isShowingResults = true;
                    mResultsFragment = ResultsFragment.newInstance(query);
                    pushFragment(mResultsFragment, true);
                    mSettingItem.setVisible(true);
                } else {
                    // refresh resultFragment
                    mResultsFragment.updateQuery(query);
                }
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void pushFragment(Fragment frag, boolean addToBackStack) {
        String name = frag.getClass().getSimpleName();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, frag, name);
        if (addToBackStack) {
            transaction.addToBackStack(name);
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
        if (mFragmentManager.getFragments() != null) {
            Log.d(TAG, "fragment # : " + mFragmentManager.getFragments().size());
        }
    }

    private void showSettingsDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FilterDialogFragment settingsDialogFragment = FilterDialogFragment.newInstance(mResultsFragment.getmFilterSettings());
        settingsDialogFragment.show(fm, "fragment_settings_name");
    }
}
