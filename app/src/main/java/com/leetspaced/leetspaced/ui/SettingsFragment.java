package com.leetspaced.leetspaced.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;

import com.leetspaced.leetspaced.R;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String TAG = SettingsFragment.class.getSimpleName();
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_pref);
        updateListPreferenceSummary();
        addValidationDurationEditTextPref();
    }

    private void addValidationDurationEditTextPref() {
        EditTextPreference bucketOnePref = (EditTextPreference) getPreferenceScreen().findPreference(getString(R.string.bucket_one_duration_key));
        EditTextPreference bucketTwoPref = (EditTextPreference) getPreferenceScreen().findPreference(getString(R.string.bucket_two_duration_key));

        Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                try {
                    int duration = Integer.parseInt((String)newValue);
                    if (duration > 0 && duration < 91) return true;
                }
                catch (NumberFormatException nfe){
                    Log.d(TAG, nfe.toString());
                }
                Toast.makeText(getContext(), "Enter a number between 1 and 90", Toast.LENGTH_LONG).show();
                return false;
            }
        };
        bucketOnePref.setOnPreferenceChangeListener(listener);
        bucketTwoPref.setOnPreferenceChangeListener(listener);
    }

    private void updateListPreferenceSummary() {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences preferences = preferenceScreen.getSharedPreferences();
        int count = preferenceScreen.getPreferenceCount();

        for (int i = 0; i < count; i++){
            Preference preference = preferenceScreen.getPreference(i);
            if (preference instanceof ListPreference) {
                String activeValue = preferences.getString(preference.getKey(), null);
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(activeValue);
                if (index >= 0){
                    listPreference.setSummary(activeValue);
                }
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updateListPreferenceSummary();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
