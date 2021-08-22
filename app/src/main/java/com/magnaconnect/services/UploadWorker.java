package com.magnaconnect.services;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UploadWorker extends Worker {
    static final String TAG = UploadWorker.class.getSimpleName();
   public UploadWorker(
       @NonNull Context context,
       @NonNull WorkerParameters params) {
       super(context, params);
   }

   @Override
   public Result doWork() {

     // Do the work here--in this case, upload the images.
     uploadImages();

     // Indicate whether the work finished successfully with the Result
     return Result.success();
   }

    private void uploadImages() {
        for(int i =0; i<5;i++){
            Log.d(TAG, "------->Upload progress==> I="+i);
        }
        Log.i(TAG, "==========>Upload Done");
    }
}
