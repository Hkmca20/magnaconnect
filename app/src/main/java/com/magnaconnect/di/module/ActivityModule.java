/*
 * Created by Hariom.Gupta on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the Activity to be memorised in the
 * correct component.
 */
package com.magnaconnect.di.module;
import android.app.Activity;
import android.content.Context;


import com.magnaconnect.di.ActivityContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
  private final Activity mActivity;

  public ActivityModule(Activity activity) {
    mActivity = activity;
  }

  @Provides
  Activity provideActivity() {
    return mActivity;
  }

  @Provides
  @ActivityContext
  Context providesContext() {
    return mActivity;
  }
}