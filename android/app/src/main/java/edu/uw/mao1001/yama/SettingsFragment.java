package edu.uw.mao1001.yama;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Nick on 5/2/2016.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
