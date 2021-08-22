/*
 * Created by Hariom.Gupta on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the Activity to be memorised in the
 * correct component.
 */
package com.magnaconnect.di.component;

import com.magnaconnect.di.PerActivity;
import com.magnaconnect.di.module.ActivityModule;
import com.magnaconnect.view.activity.CActivity;
import com.magnaconnect.view.activity.SActivity;
import com.magnaconnect.view.fragment.ADLFragment;
import com.magnaconnect.view.fragment.ATHFragment;
import com.magnaconnect.view.fragment.DFragment;
import com.magnaconnect.view.fragment.DISFragment;
import com.magnaconnect.view.fragment.DLFragment;
import com.magnaconnect.view.fragment.HEFragment;
import com.magnaconnect.view.fragment.HFragment;
import com.magnaconnect.view.fragment.HISFragment;
import com.magnaconnect.view.fragment.LFragment;
import com.magnaconnect.view.fragment.PFragment;
import com.magnaconnect.view.fragment.PLFragment;
import com.magnaconnect.view.fragment.PUFragment;
import com.magnaconnect.view.fragment.S2Fragment;
import com.magnaconnect.view.fragment.S3Fragment;
import com.magnaconnect.view.fragment.S4Fragment;
import com.magnaconnect.view.fragment.S5Fragment;
import com.magnaconnect.view.fragment.S6Fragment;
import com.magnaconnect.view.fragment.SIFragment;
import com.magnaconnect.view.fragment.STFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(CActivity cActivity);

    void inject(SActivity sActivity);

    void inject(STFragment stFragment);

    void inject(HFragment hFragment);

    void inject(ATHFragment athFragment);

    void inject(PUFragment puFragment);

    void inject(HEFragment heFragment);

    void inject(DLFragment dlFragment);

    void inject(DISFragment disFragment);

    void inject(S2Fragment s2Fragment);

    void inject(S3Fragment s3Fragment);

    void inject(S4Fragment s4Fragment);

    void inject(S5Fragment s5Fragment);

    void inject(S6Fragment s6Fragment);

    void inject(HISFragment hisFragment);

    void inject(PLFragment plFragment);

    void inject(LFragment lFragment);

    void inject(PFragment pFragment);

    void inject(SIFragment siFragment);

    void inject(ADLFragment adlFragment);

    void inject(DFragment dFragment);
}

