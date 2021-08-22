/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import javax.inject.Singleton;

import androidx.annotation.NonNull;

@Singleton
public class Utils {

    private final boolean DEBUG = true;

    /*
 * This method is Show toast message
     *
     * @param msg
     */
    public static void showToast(@NonNull Context context, @NonNull String msg) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /*
 * Check Network Connectivity
     *
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        // Add a null check before you proceed
        if (context == null) return false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected() && info.isAvailable();
    }

    public static void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), 0);
    }

    /*
 * Print log
     *
     * @param message
     */
    public void showLog(@NonNull String message) {
        if (DEBUG) {
            System.err.println(message);
        }
    }

    /*
 * Show Popup message
     *
     * @param context
     * @param title
     * @param message
     */
    public void showBuilder(@NonNull Context context, @NonNull String title, @NonNull String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    public interface OnDialogOkClickListener {
        void onOkButtonClicked();
    }
    public interface OnDialogIntOkClickListener {
        void onClicked(String id);
    }

}
