package com.example.bikesh.archivos.Class;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by bikesh on 1/5/17.
 */

public class ReadStoredPreference {

    SharedPreferences storedPrefs;

    public void readStoredPreferences(Context context) {
        storedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        CommonData.IPADDRESS = storedPrefs.getString(CommonData.KEY_PREF_IPADDRESS,"");
        CommonData.DIRECTORY = storedPrefs.getString(CommonData.KEY_PREF_DIRECTORY,"");
        CommonData.USERNAME = storedPrefs.getString(CommonData.KEY_PREF_USERNAME,"");
        CommonData.PASSWORD = storedPrefs.getString(CommonData.KEY_PREF_PASSWORD,"");
        CommonData.PORT = storedPrefs.getString(CommonData.KEY_PREF_PORT,"");
    }
}
