/*
 * Created by Hariom.Gupta on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the Activity to be memorised in the
 * correct component.
 */
package com.magnaconnect.di.component;

import android.app.Application;
import android.content.Context;

import com.magnaconnect.di.ApplicationContext;
import com.magnaconnect.di.module.ApplicationModule;
import com.magnaconnect.network.IServices;
import com.magnaconnect.view.presenter.ADLPresenter;
import com.magnaconnect.view.presenter.ATHPresenter;
import com.magnaconnect.view.presenter.HEPresenter;
import com.magnaconnect.view.presenter.HISPresenter;
import com.magnaconnect.view.presenter.HPresenter;
import com.magnaconnect.view.presenter.LPresenter;
import com.magnaconnect.view.presenter.PLPresenter;
import com.magnaconnect.view.presenter.PPresenter;
import com.magnaconnect.view.presenter.PUPresenter;
import com.magnaconnect.view.presenter.S2Presenter;
import com.magnaconnect.view.presenter.S3Presenter;
import com.magnaconnect.view.presenter.S6Presenter;
import com.magnaconnect.view.presenter.SIPresenter;
import com.magnaconnect.view.presenter.STPresenter;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(HPresenter hPresenter);

    void inject(ATHPresenter athPresenter);

    void inject(PUPresenter puPresenter);

    void inject(HEPresenter hePresenter);

    void inject(S2Presenter s2Presenter);

    void inject(S3Presenter s3Presenter);

    void inject(S6Presenter s6Presenter);

    void inject(HISPresenter hisPresenter);

    void inject(STPresenter siPresenter);

    void inject(ADLPresenter adlPresenter);

    void inject(PLPresenter plPresenter);

    void inject(LPresenter lPresenter);

    void inject(PPresenter pPresenter);

    void inject(SIPresenter siPresenter);

    @ApplicationContext
    Context context();

    Application application();

    @Named("cached")
    IServices apiService();

    @Named("non_cached")
    IServices apiServiceNonCached();

    EventBus eventBus();

}