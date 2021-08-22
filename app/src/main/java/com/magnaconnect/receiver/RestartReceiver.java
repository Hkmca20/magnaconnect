/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 *
 * Copyright 2014 KC Ochibili
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.magnaconnect.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestartReceiver extends BroadcastReceiver {
    String TAG = RestartReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Broadcast Listened, Service tried to stop");

        if (intent.getAction().equals("timerService")) {
            Log.i(TAG, "******Service TIMER TASK******");
            // TODO: 2/12/2019 automatic refresh current service.
//            ObservableObject.getInstance().updateValue(intent);
//            int notificationId = intent.getIntExtra("notificationId", 0);
//            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            manager.cancel(notificationId);
        }
    }
}