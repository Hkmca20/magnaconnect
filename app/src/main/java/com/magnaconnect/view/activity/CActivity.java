/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.magnaconnect.BApp;
import com.magnaconnect.R;
import com.magnaconnect.services.MyJobService;
import com.magnaconnect.services.UploadWorker;
import com.magnaconnect.view.fragment.FragmentCallback;
import com.magnaconnect.view.fragment.SPFragment;
import com.socks.library.KLog;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import butterknife.BindString;

import static com.magnaconnect.utils.Utility.validateSession;

public class CActivity extends BActivity implements MainMvpView, FragmentCallback {
    private static final String TAG = CActivity.class.getSimpleName();
    private static FirebaseAnalytics fAnal;
    @BindString(R.string.message_back)
    String message_back;
    private int count = 0;
    private MainPresenter mainPresenter;
    private AppCompatActivity activity;
    private long mBackPressed;
    private DatabaseReference myRef;

    public static FirebaseAnalytics getfAnal() {
        return fAnal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        Log.i(TAG, "--------------CONTAINER ACTIVITY---------");
        fAnal = FirebaseAnalytics.getInstance(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        LinearLayout parent = new LinearLayout(activity);
        parent.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        parent.setOrientation(LinearLayout.VERTICAL);
//        LayoutInflater inflater = LayoutInflater.from(activity);
//        viewToolbar = inflater.inflate(R.layout.tool_com, null);
////        View inflatedLayout= inflater.inflate(R.layout.yourLayout, (ViewGroup) view, false);
//        parent.addView(viewToolbar);
        FrameLayout frameLayout = new FrameLayout(activity);
        frameLayout.setId(FRAME_CONTAINER_ID);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        parent.addView(frameLayout);
        setContentView(parent);
        if (validateSession()) {
            if (savedInstanceState == null) {
                openFragment(new SPFragment(), true);
            }
        } else {
            ImageView imageView = new ImageView(activity);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(500, 500));
            imageView.setBackgroundResource(R.drawable.ic_block);
            frameLayout.addView(imageView);
            showToast("Invalid session!");
        }
        myRef = fdb.getReference(MESSAGE_ALERT);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                fcr.setCustomKey(MESSAGE_OK, value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                fcr.recordException(error.toException());
            }
        });

//---------------start code firebase messaging cloud-----------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]

        Log.d(TAG, "Subscribing to weather topic");
        // [START subscribe_topics]
        fcm.subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d(TAG, msg);
                        showToast(msg);
                    }
                });
        // [END subscribe_topics]
        fcm.getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                    return;
                }

                // Get new FCM registration token
                String token = task.getResult();

                // Log and toast
                String msg = getString(R.string.msg_token_fmt, token);
                Log.d(TAG, msg);
            }
        });
        //----------Job Service and Scheduler Task here----------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startingJobService();
        }
        startWorkManagerTask();

        MyTestClass c1 = new MyTestClass();
        MyTestClass c2 = new MyTestClass();
        c1.display();c2.display();
    }

    private void startWorkManagerTask() {
        WorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class).build();
        WorkManager.getInstance(activity).enqueue(uploadWorkRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startingJobService() {
        ComponentName componentName = new ComponentName(BApp.appContext, MyJobService.class);

//        PersistableBundle bundle = new PersistableBundle();
//        bundle.putInt(JobFlags.KEY_PERIODIC_SYNC_JOB, JobFlags.JOB_TYPE_INITIAL_FETCH);
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, componentName)
//                .setExtras(bundle)
//                .setPeriodic(3 * 60 * 1000)
//                .setMinimumLatency(3 * 60 * 1000)
                .setRequiresCharging(false)
                .setPersisted(true)  // relaunch on reboot
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setMinimumLatency(1)
                .setOverrideDeadline(3 * 60 * 1000)
                .build();
        JobScheduler jobScheduler = (JobScheduler) BApp.appContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = jobScheduler.schedule(jobInfo);

/**     -----Other method to control jobs-----------------
 jobScheduler.cancel(JOB_ID);

 abstract void cancel(int jobId)
 abstract void cancelAll()
 abstract int enqueue(JobInfo job, JobWorkItem work)
 abstract List<JobInfo> getAllPendingJobs()
 abstract JobInfo getPendingJob(int jobId)
 abstract int schedule(JobInfo job)
 */
    }

    public void setFragment(Fragment fragment) {
        fcr.setCustomKey(MESSAGE_ALERT, TAG);
        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putString("args", "0");
            fragment.setArguments(bundle);
            String fragmentTAG = fragment.getClass().getSimpleName();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.add(FRAME_CONTAINER_ID, fragment, fragmentTAG);
            transaction.addToBackStack(fragmentTAG).commit();
        } else {
            fcr.setCustomKey(MESSAGE_ERROR, TAG);
            Log.e(TAG, "Error in creating frame");
        }
    }

    private void backAction() {
        try {
            String tempValue = count++ + ":" + getUserId() + ":" + getContact() + ":" + getUserType();
            myRef.setValue(tempValue);
            int i = getSupportFragmentManager().getBackStackEntryCount();
            if (i < 1) {
                if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                    super.onBackPressed();
                    return;
                } else {
                    showToast(message_back);
                    mBackPressed = System.currentTimeMillis();
                }
            } else {
                getSupportFragmentManager().popBackStack();
            }
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, getContact());
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, TAG);
            bundle.putString(FirebaseAnalytics.Param.METHOD, getUserId());
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, getUserType());
            fAnal.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        } catch (Exception ex) {
            fcr.recordException(ex);
            Log.w(TAG, MESSAGE_ERROR, ex);
            showErrorMessageAlert(activity);
        }
    }

    @Override
    public void onBackPressed() {
        backAction();
    }

    @Override
    public void doing_nothing() {
    }

    @Override
    public void Update(String str, int navigationId) {
        switch (str) {
            case FR_HFragment:
                KLog.d(FR_HFragment);
                break;
            case FR_HEFragment:
                KLog.d(FR_HEFragment);
                break;
            case FR_TFragment:
                KLog.d(FR_TFragment);
                break;
        }
    }
}
