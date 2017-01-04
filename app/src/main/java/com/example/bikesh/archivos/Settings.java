package com.example.bikesh.archivos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

/**
 * Created by bikesh on 1/4/17.
 */

public class Settings extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    SharedPreferences storedPrefs;


    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //loading the Setting page from the XML
        addPreferencesFromResource(R.xml.preferences);

        //Sets Default Values for the first time
       PreferenceManager.setDefaultValues(getActivity().getApplicationContext(), R.xml.preferences, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         storedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        this.readStoredPreferences();
        this.setSummaryValues();


        Preference testPreference = findPreference(CommonData.KEY_PREF_TEST_CONNECTION);
        testPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                readStoredPreferences();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FTPConnection ftpConnection = new FTPConnection("10.2.1.70","micnepal","nepal123");
                            ftpConnection.listFiles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                return true;
            }
        });
    }

     public void readStoredPreferences() {
        CommonData.IPADDRESS = storedPrefs.getString(CommonData.KEY_PREF_IPADDRESS,"");
        CommonData.DIRECTORY = storedPrefs.getString(CommonData.KEY_PREF_DIRECTORY,"");
        CommonData.USERNAME = storedPrefs.getString(CommonData.KEY_PREF_USERNAME,"");
        CommonData.PASSWORD = storedPrefs.getString(CommonData.KEY_PREF_PASSWORD,"");
        CommonData.PORT = storedPrefs.getString(CommonData.KEY_PREF_PORT,"");
    }

    public void setSummaryValues() {
        Preference ipAddress = findPreference(CommonData.KEY_PREF_IPADDRESS);
        Preference directory = findPreference(CommonData.KEY_PREF_DIRECTORY);
        Preference username = findPreference(CommonData.KEY_PREF_USERNAME);
        Preference password = findPreference(CommonData.KEY_PREF_PASSWORD);
        Preference port = findPreference(CommonData.KEY_PREF_PORT);

        if (!CommonData.IPADDRESS.trim().isEmpty()) {
            ipAddress.setSummary(CommonData.IPADDRESS);
        }
        if (!CommonData.DIRECTORY.trim().isEmpty()) {
            directory.setSummary(CommonData.DIRECTORY);
        }
        if(!CommonData.USERNAME.trim().isEmpty()) {
            username.setSummary(CommonData.USERNAME);
        }
        if(!CommonData.PASSWORD.trim().isEmpty()) {
            password.setSummary(getResources().getString(R.string.hintEntered));
        }
        if(!CommonData.PORT.trim().isEmpty()) {
            port.setSummary(CommonData.PORT);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d("BIKI", "key: " + key);
        if(key.equals(CommonData.KEY_PREF_IPADDRESS)){
            Preference ipPreference = findPreference(key);
            ipPreference.setSummary(sharedPreferences.getString(key,""));
        } else if (key.equals(CommonData.KEY_PREF_USERNAME)){
            Preference userPreference = findPreference(key);
            userPreference.setSummary(sharedPreferences.getString(key,""));
        } else if (key.equals(CommonData.KEY_PREF_PASSWORD)) {
            Preference passwordPreference = findPreference(key);
            passwordPreference.setSummary(getResources().getString(R.string.hintEntered));
        } else if (key.equals(CommonData.KEY_PREF_DIRECTORY)) {
            Preference directoryPref = findPreference(key);
            directoryPref.setSummary(sharedPreferences.getString(key,""));
        } else if (key.equals(CommonData.KEY_PREF_PORT)) {
            Preference prefPort = findPreference(key);
            prefPort.setSummary(sharedPreferences.getString(key,""));
        }

    }
}
