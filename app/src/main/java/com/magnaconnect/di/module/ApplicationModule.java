/*
 * Created by Hariom.Gupta on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the Activity to be memorised in the
 * correct component.
 */
package com.magnaconnect.di.module;

import android.app.Application;
import android.content.Context;


import com.magnaconnect.di.ApplicationContext;
import com.magnaconnect.network.IServices;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module public class ApplicationModule {

  protected final Application mApplication;

  public ApplicationModule(Application application) {
    mApplication = application;
  }

  @Provides public Application provideApplication() {

    return mApplication;
  }

  @Provides @ApplicationContext
  public Context provideContext() {
    return mApplication;
  }

  @Provides @Named("cached") @Singleton public IServices provideApiService() {
    return IServices.Factory.create(mApplication, true);
  }

  @Provides @Named("non_cached") @Singleton public IServices provideApiServiceNonCached() {
    return IServices.Factory.create(mApplication, false);
  }

  @Provides @Singleton public EventBus eventBus() {
    return new EventBus();
  }
}