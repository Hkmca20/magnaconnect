/*
 * Created by Hariom.Gupta.Gurugram on 22/12/2019.
 * hk.mca08@gmail.com
 * 8510887828
 */
/*
 * Copyright Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.magnaconnect;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.StrictMode;
import android.util.Log;

import com.magnaconnect.di.component.ApplicationComponent;
import com.magnaconnect.di.component.DaggerApplicationComponent;
import com.magnaconnect.di.module.ApplicationModule;
import com.magnaconnect.utils.Cons;
import com.magnaconnect.utils.FontsOverride;
import com.socks.library.KLog;

public class BApp extends Application implements Cons {
    private static final BApp bApp = new BApp();
    public static Context appContext;

    //    public static MyEventBus eventBus;
    private ApplicationComponent mApplicationComponent;
    private String TAG = BApp.class.getSimpleName();

    /*
     * default c
     */
    public BApp() {
        super();
        Log.v(TAG, "Init---<(*_*)>---");
    }

    /*
     * methos to get instance of application
     */
    public static BApp getInstance() {
        return bApp;
    }

    public static BApp get(Context context) {
        return (BApp) context.getApplicationContext();
    }

    /*
     *  methos to get application context
     */
    public Context getContext() {
        return appContext;
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        }
        return mApplicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        fcr.setCrashlyticsCollectionEnabled(true);
//        FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/Quicksand-Regular.ttf");
//        FontsOverride.setDefaultFont(this, "DEFAULT_BOLD", "fonts/Quicksand-Bold.ttf");
//        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/Quicksand-Regular.ttf");
//        FontsOverride.setDefaultFont(this, "SERIF", "fonts/Quicksand-Regular.ttf");
//        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/Quicksand-Regular.ttf");
//        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/Quicksand-Regular.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/Calibri.ttf");
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //initialize event bus
//        eventBus = new MyEventBus(ThreadEnforcer.MAIN);
        boolean isDebuggable = (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));

        if (isDebuggable) {
            KLog.init(true);
        } else {
            KLog.init(false);
        }
//        if (BuildConfig.DEBUG) {
//            Timber.plant(new Timber.DebugTree());
//        } else {
//            //production
//        }
//        final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//// set in-app defaults
//        Map<String, Object> remoteConfigDefaults = new HashMap();
//        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_REQUIRED, false);
//        remoteConfigDefaults.put(ForceUpdateChecker.KEY_CURRENT_VERSION, "1.0.0");
//        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_URL,
//                "https://play.google.com/store/apps/details?id=com.sembozdemir.renstagram");
//        firebaseRemoteConfig.setDefaults(remoteConfigDefaults);
//        firebaseRemoteConfig.fetch(60) // fetch every minutes
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "remote config is fetched.");
//                            firebaseRemoteConfig.activateFetched();
//                        }
//                    }
//                });
//        throw new RuntimeException("Test Crash"); // Force a crash
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        KLog.e("########## onLowMemory ##########");
    }
}
