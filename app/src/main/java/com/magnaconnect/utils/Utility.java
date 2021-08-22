/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.utils;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.magnaconnect.R;
import com.magnaconnect.view.activity.CActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import static android.content.ContentValues.TAG;

public class Utility implements Cons {
//    public final static String ErrorCode = "@@__!!Error";

//    public static void showSnackMessage(Activity activity, String message, boolean isTopView) {
//        snackMessage(activity, message, true, isTopView);
//    }

    static String SHARE_CON = "Share app! \nDownload BTSampark app \nhttp://tiny.cc/yxb6jz";

    public static void closeSession(Activity activity) {
        pdb.putString(Pf_first_time, "");
        pdb.putString(Pf_cat, "");
        pdb.putString(Pf_m, "");
        pdb.putString(Pf_uId, "");
        pdb.putString(PF_uName, "");
        pdb.putString(Pf_userPass, "");
        activity.finish();
        activity.startActivity(new Intent(activity, CActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        Toast.makeText(activity, "Logout success!", Toast.LENGTH_SHORT).show();
    }

    public static void openShareIntent(Activity activity, String app) {
        String shareContent = SHARE_CON;
        Intent intent;
        if (app == null) {
            intent = null;
        } else {
            intent = activity.getPackageManager().getLaunchIntentForPackage(app);
        }

        if (intent != null) {
            try {
                // The application exists
                Intent shareIntent = new Intent();
//                Uri imageUri = Uri.parse(pictureFile.getAbsolutePath());
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                //Target whatsapp:
//                shareIntent.setPackage("com.whatsapp");
//                //Add text and then Image URI
//                shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
//                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
//                shareIntent.setType("image/jpeg");
//                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                activity.startActivity(shareIntent);
            } catch (Exception e) {
                intent = null;
            }
        }
        if (intent == null) {
//            Common.snackMessage(mCoordinator, "Choose application to share..");
            // The application does not exist
            // Open GooglePlay or use the default system picker
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            activity.startActivity(Intent.createChooser(sharingIntent, "Share using"));
        }
    }

    public static void applyAnim(final TextView textView, int duration, boolean shouldCancelled) {
        final float[] hsv = new float[3];
        hsv[1] = 1;
        hsv[2] = 1;
        int hue = 0;
        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(Color.BLUE, Color.RED);
        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                hsv[0] = 360 * animation.getAnimatedFraction();
                textView.setTextColor(Color.HSVToColor(hsv));
            }
        });
        anim.setDuration(duration);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.start();
        if (shouldCancelled) anim.cancel();
    }

    public static void closeDatabase(SQLiteDatabase dbObject) {
        try {
            if (dbObject != null && dbObject.isOpen()) {
                dbObject.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean IsInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting())) {
            return false;
        } else
            return true;
    }

    public static boolean validateSession() {

        String currentDate = getCurrentDate();
//        String loginDate = db.getString(Pref_loginDate);
        if (
//                loginDate.equals("") || currentDate.equals(loginDate) ||
                currentDate.equals(validLoginDate10)
                        || currentDate.equals(validLoginDate1)
                        || currentDate.equals(validLoginDate2)
                        || currentDate.equals(validLoginDate3)
                        || currentDate.equals(validLoginDate4)
                        || currentDate.equals(validLoginDate5)
                        || currentDate.equals(validLoginDate6)
                        || currentDate.equals(validLoginDate7)
                        || currentDate.equals(validLoginDate8)
                        || currentDate.equals(validLoginDate9)
        ) {
            Log.i(TAG, "Session Valid!");
            return true;
        }
        Log.i(TAG, "Invalid Session!");
        return false;
    }

    public static String getLoggedInDate() {
        return pdb.getString(Pf_loginDate);
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    public static String saveCurrentDate() {
        String currentDate = returnDateFormat.format(Calendar.getInstance().getTime());
        pdb.putString(Pf_loginDate, currentDate);
        return currentDate;
    }

    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(Calendar.getInstance().getTime());
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
    }

    public static void underlineText(TextView textview, String str) {
        SpannableString underlineText = new SpannableString(str);
        underlineText.setSpan(new UnderlineSpan(), 0, underlineText.length(), 0);
        textview.setText(underlineText);
    }

    public static boolean notNullParams(String string) {
        if (string == null || string.equals("") || string.equals("-") || !(string.length() > 0)) {
            return false;
        }
        return true;
    }

    public static void showSnackMessage(Activity activity, String message, boolean isTopView) {
        snackMessage(activity, message, true, isTopView);
    }

    public static void snackMessage(Activity activity, String message, boolean showAction, boolean isTopView) {
        CoordinatorLayout mCoordinator = activity.findViewById(R.id.coordinatorLayout);
        final Snackbar snackbar = Snackbar.make(mCoordinator, message, Snackbar.LENGTH_LONG);
//        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
//        // Inflate our custom view
//        View snackView = mInflater.inflate(R.layout.my_snackbar, null);
//        ImageView imageView = (ImageView) snackView.findViewById(R.id.image);
// Changing action button text color
        View sbView = snackbar.getView();
        sbView.setOnClickListener(v -> snackbar.dismiss());
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        if (isTopView) {
            snackbar.setDuration(BaseTransientBottomBar.LENGTH_LONG);
            sbView.setBackgroundColor(activity.getResources().getColor(R.color.Jungle_Green));
            textView.setTextColor(Color.WHITE);
//            final ViewGroup.LayoutParams params = snackbar.getView().getLayoutParams();
//            if (params instanceof CoordinatorLayout.LayoutParams) {
//                ((CoordinatorLayout.LayoutParams) params).gravity = Gravity.TOP;
//                ((CoordinatorLayout.LayoutParams) params).setMargins(0, getActionBarHeight(mCoordinator.getContext()), 0, 0);
//                sbView.setLayoutParams(params);
//            }// else do with FrameLayout
        } else {
            textView.setTextColor(Color.YELLOW);
        }
        if (showAction) {
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            snackbar.setActionTextColor(Color.BLUE);
        }

        snackbar.show();
    }

    public static void printStackTrace(String TAG, Exception exception) {
        if (D) Log.w(TAG, "Warning(*_*): ", exception);
    }

    public static void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
        }
    }

    public static void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

    public static void enableVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    public static void disableVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }

    public static boolean validateMobileNo(String mobileNo) {
        if (!notNullParams(mobileNo) || mobileNo.length() < 10 || mobileNo.startsWith("0") || mobileNo.startsWith("1") ||
                mobileNo.startsWith("2") || mobileNo.startsWith("3") || mobileNo.startsWith("00")) {
            return false;
        }
        return true;
    }
}
