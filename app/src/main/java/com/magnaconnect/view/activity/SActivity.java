/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.magnaconnect.R;

import androidx.core.app.NavUtils;

public class SActivity extends BActivity {


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivityComponent().inject(this);
    getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

    getSupportActionBar().setElevation(0);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public static class MyPreferenceFragment extends PreferenceFragment {
    @Override public void onCreate(final Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.settings);

      Preference version = findPreference("version");
      try {
        String versionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        version.setSummary(versionName);
      } catch (PackageManager.NameNotFoundException e) {
      }

      Preference buttonfeedback = findPreference("send_feedback");
      buttonfeedback.setOnPreferenceClickListener(preference -> {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "your_email@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, MyPreferenceFragment.this.getString(R.string.app_name) + " Feedback");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Write your feedback here...");
        MyPreferenceFragment.this.startActivity(Intent.createChooser(emailIntent, "Send email..."));

        return true;
      });
    }
  }

  @Override protected void onPause() {
    super.onPause();

    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    boolean notificationsPrefs = prefs.getBoolean("prefs_notifications", false);
//    preferencesHelper.setNotificationsPrefs(notificationsPrefs);
  }
}