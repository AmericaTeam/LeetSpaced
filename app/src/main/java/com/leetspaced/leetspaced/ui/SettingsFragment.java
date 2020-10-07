package com.leetspaced.leetspaced.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;

import com.leetspaced.leetspaced.AlarmReceiver;
import com.leetspaced.leetspaced.CreateNotificationActivity;
import com.leetspaced.leetspaced.R;

import java.util.Calendar;

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

        // Detect Notification Switch Preference
        if (key.equals(getString(R.string.pref_notification_key)) && sharedPreferences.getBoolean(key, false)) {
            Log.d("SettingsFragment", "Creating alarm yay");
            AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getContext(), AlarmReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            // Set the alarm to start at approximately 2:00 p.m.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 8);
//            calendar.set(Calendar.MINUTE, 55);

        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants--in this case, AlarmManager.INTERVAL_DAY.
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);
        }
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
