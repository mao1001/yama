package edu.uw.mao1001.yama;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

/**
 * Created by Nick on 5/2/2016.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String KEY_PREF_AUTO_REPLY = "pref_key_auto_reply";
    public static final String KEY_PREF_PRESET_MESSAGE = "pref_key_preset_message";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key == KEY_PREF_PRESET_MESSAGE) {
            EditTextPreference presetMessage = (EditTextPreference)findPreference(key);
            presetMessage.setSummary(presetMessage.getText());
        }
    }
}
