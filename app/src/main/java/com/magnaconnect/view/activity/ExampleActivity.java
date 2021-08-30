package com.magnaconnect.view.activity;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toolbar;

import com.magnaconnect.BApp;
import com.magnaconnect.services.MyJobService;
import com.magnaconnect.services.UploadWorker;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ExampleActivity extends BActivity implements View.OnClickListener {
    ScrollView scrollView;
    AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AppCompatActivity) this;
        scrollView = new ScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        Toolbar toolbar = new Toolbar(this);
        toolbar.setTitle("My Examples");
        linearLayout.addView(toolbar);
        scrollView.addView(linearLayout);

        for (int i = 0; i < 5; i++) {
            Button b = new Button(this);
            b.setId(i + 1);
            b.setText("btn" + i);
            b.setTextSize(30);
            b.setPadding(20, 20, 20, 0);
            b.setOnClickListener(this);
            linearLayout.addView(b);
        }
        setContentView(scrollView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case 1:
                //----------Job Service and Scheduler Task here----------
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startingJobService();
                }
                break;
            case 2:
                startWorkManagerTask();
                break;
            case 3:
                MyTestClass c1 = new MyTestClass();
                MyTestClass c2 = new MyTestClass();
                c1.display();
                c2.display();
                break;
            case 4:
                implementRXJavaExample();
                break;
            case 5:
                break;
        }
    }

    private void implementRXJavaExample() {
        final String[] alphabets = {"a","b","c","d","e","f"};
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter em) {
                try {
                    for (String s : alphabets) {
                        em.onNext(s);
                    }
                    em.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    em.onError(e);
                }
            }
        });
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("----------------->onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                System.out.println("----------------->onNext: " + o);
            }

            @Override
            public void onComplete() {
                System.out.println("----------------->onComplete");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("----------------->onError: " + e.getMessage());
            }
        };
        observable.subscribe(observer);
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

}
