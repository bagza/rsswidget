package com.example.gryazin.rsswidget;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.gryazin.rsswidget.data.Repository;
import com.example.gryazin.rsswidget.data.update.UpdateScheduler;
import com.example.gryazin.rsswidget.domain.RssSettings;

import javax.inject.Inject;

public class SettingsActivity extends PreferenceActivity {

    @Inject
    UpdateScheduler updateScheduler;
    @Inject
    Repository repository;
    WhitelistProvider whitelistProvider = new WhitelistProvider();
    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            }
            else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        setupActionBar();
        setDefaultResultIntent();
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new GeneralPreferenceFragment()).commit();
    }

    private void inject(){
        RssApplication.getAppComponent().inject(this);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        android.app.ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        whitelistProvider.maybeAskForWhitelist();
    }

    private class WhitelistProvider{
        public boolean isWhitelisted(){
            PowerManager powerManager = getSystemService(PowerManager.class);
            return powerManager.isIgnoringBatteryOptimizations(getPackageName());
        }

        public void maybeAskForWhitelist(){
            if (!isWhitelisted()){
                showRationaleDialog();
            }
        }

        private void showRationaleDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
            builder.setTitle(R.string.whitelist_dialog_title)
                    .setMessage(R.string.whitelist_rationale)
                    .setPositiveButton(android.R.string.ok, ((dialog, which) -> {startWhitelistActivity();}))
                    .show();
        }

        private void startWhitelistActivity(){
            Intent i = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                            .setData(Uri.parse("package:" + getPackageName()));
            startActivity(i);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    public int getAppWidgetId(){
        int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        return mAppWidgetId;
    }

    private void setDefaultResultIntent(){
        Intent intent = new Intent();
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, getAppWidgetId());
        setResult(RESULT_CANCELED, intent);
    }

    public void confirmAndRunRss(String url){
        Toast.makeText(this, "CONFIRM: " + getAppWidgetId() + ", " + url, Toast.LENGTH_SHORT).show();
        saveSettingsAsync(url, getAppWidgetId());
    }

    //Need async, because it might block if the db is locked.
    private void saveSettingsAsync(String url, int appWidgetId){
        RssSettings settings = new RssSettings(appWidgetId, url);
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                repository.saveSettings(settings);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                runService();
                finishOk();
            }
        }.execute();
    }

    private void runService(){
        updateScheduler.refreshWidget(getAppWidgetId());
        updateScheduler.refreshFetchAndReschedule();
    }

    private void finishOk(){
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, getAppWidgetId());
        setResult(RESULT_OK, resultValue);
        finish();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        private static final String RSS_URL_PREF_KEY = "rss_url";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);
            bindURLPreferenceWithValidator((EditTextPreference) findPreference(RSS_URL_PREF_KEY));
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.settings_menu, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            switch (id){
                case android.R.id.home:
                    getActivity().finish();
                    return true;
                case R.id.action_confirm:
                    confirm();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        private void confirm(){
            SettingsActivity host = (SettingsActivity)getActivity();
            String url = ((EditTextPreference)findPreference(RSS_URL_PREF_KEY)).getText();
            host.confirmAndRunRss(url);
        }

        private void bindURLPreferenceWithValidator(EditTextPreference preference) {

            AutoCompleteTextView.Validator urlValidator = new AutoCompleteTextView.Validator() {
                @Override
                public boolean isValid(CharSequence text) {
                    return Patterns.WEB_URL.matcher(text).matches();
                }

                @Override
                public CharSequence fixText(CharSequence invalidText) {
                    return null;
                }
            };

            // Set the listener to watch for value changes.
            preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String value = newValue.toString();
                    if (urlValidator.isValid(value)){
                        return sBindPreferenceSummaryToValueListener.onPreferenceChange(preference, newValue);
                    }
                    else{
                        Toast.makeText(getActivity(), "Malformed RSS feed url", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            });

            // Trigger the listener immediately with the preference's
            // current value.
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        }
    }
}
