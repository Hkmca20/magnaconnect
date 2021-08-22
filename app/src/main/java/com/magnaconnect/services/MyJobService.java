package com.magnaconnect.services;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.magnaconnect.R;
import com.magnaconnect.utils.Cons;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService implements Cons {
    static final String TAG = MyJobService.class.getSimpleName();
    private static int COUNTER = 0;
    final Handler workHandler = new Handler();
    ScheduledExecutorService mDialogService;
    Runnable workRunnable = () -> {
        mDialogService = new ScheduledThreadPoolExecutor(1);
        //This process will execute immediately, then execute every 3 seconds
        mDialogService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // See https://stackoverflow.com/questions/45692181/android-job-scheduler-schedule-job-to-execute-immediately-and-exactly-once
                String message = "Time: " + new Date().toString() + " background: " + appIsInBackground();
                Intent intent = new Intent();

                PendingIntent pendingIntent = PendingIntent.getActivity(MyJobService.this, 1, intent, 0);

                Notification.Builder builder = new Notification.Builder(MyJobService.this);

                builder.setAutoCancel(false);
                builder.setTicker("Job Service ticker info");
                builder.setContentTitle("Job Service content title");
                builder.setContentText(message);
                builder.setSmallIcon(R.drawable.ic_launcher_foreground);
                builder.setContentIntent(pendingIntent);
//                        builder.setOngoing(true);   // optionally uncomment this to prevent the user from being able to swipe away the notification
                builder.setSubText("Job Service Subtext");   //API level 16
                builder.setNumber(100);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setupNotificationChannel(NOTIFICATION_CHANNEL_ID);
                    builder.setChannelId(NOTIFICATION_CHANNEL_ID);
                }
                builder.build();

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(93423, builder.build());

                ++COUNTER;
                Log.d(TAG, " Notification message: " + message + ", Count=" + COUNTER);

//            PersistableBundle bundle = jobParameters.getExtras();
//            int type = bundle.getInt(JobFlags.KEY_PERIODIC_SYNC_JOB);
//            jobBackend.startJobBack(type, this);
            }
        }, 0L, 3000L, TimeUnit.MILLISECONDS);
    };
    private JobParameters jobParameters;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        this.jobParameters = jobParameters;
        workHandler.postDelayed(workRunnable, 5 * 1000);

        boolean reschedule = false;
        jobFinished(jobParameters, reschedule);
        // return true so Android knows the workRunnable is continuing in the background;
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i(TAG, "==========>Job Stopped");
        return false;
    }

    /**
     * Source: <a href="https://stackoverflow.com/a/39589149/2848676">https://stackoverflow.com/a/39589149/2848676</a>
     *
     * @return true if the app is in the background, false otherwise
     */
    private boolean appIsInBackground() {
        ActivityManager.RunningAppProcessInfo myProcess = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(myProcess);
        boolean isInBackground = myProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
        return isInBackground;
    }

    /**
     * https://stackoverflow.com/a/45692202/2848676
     *
     * @param channelId pass the channel id.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupNotificationChannel(String channelId) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // The user-visible name of the channel.
        CharSequence name = getString(R.string.my_channel_name);

        // The user-visible description of the channel.
        String description = getString(R.string.my_channel_name);

        int importance = NotificationManager.IMPORTANCE_LOW;

        NotificationChannel mChannel = new NotificationChannel(channelId, name, importance);

        // Configure the notification channel.
        mChannel.setDescription(description);

        mChannel.enableLights(true);
        // Sets the notification light color for notifications posted to this
        // channel, if the device supports this feature.
        mChannel.setLightColor(Color.RED);

        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        mNotificationManager.createNotificationChannel(mChannel);
    }

}
