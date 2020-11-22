package com.leetspaced.leetspaced;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.leetspaced.leetspaced.ui.SettingsActivity;
import com.leetspaced.leetspaced.ui.StatsFragment;
import com.leetspaced.leetspaced.ui.TodayFragment;
import com.leetspaced.leetspaced.ui.HomeFragment;
import com.leetspaced.leetspaced.utils.utils;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private int mPrevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = sharedPreferences.getString(getString(R.string.pref_theme_key), getString(R.string.pref_theme_key_system_default));
        utils.setTheme(theme);

        if (savedInstanceState == null) {
            // Default Fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, TodayFragment.newInstance("1", "2"))
                    .commit();
        }

        // Bottom bar
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Avoid replacing the same fragment
                if (mPrevMenuItem == item.getItemId()) return true;
                mPrevMenuItem = item.getItemId();

                Fragment fragment;

                switch (item.getItemId()){
                    case R.id.page_today:
                        fragment = TodayFragment.newInstance("1", "2");
                        break;
                    case R.id.page_home:
                        Log.d("TAB", "HOME");
                        fragment = HomeFragment.newInstance("1", "2");
                        break;
                    case R.id.page_stats:
                        Log.d("TAB", "STATS");
                        fragment = StatsFragment.newInstance("1", "2");
                        break;
                    default:
                        fragment = TodayFragment.newInstance("1", "2");
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, fragment)
                        .commit();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_menu_option:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}