/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magnaconnect.R;
import com.magnaconnect.utils.Cons;
import com.magnaconnect.view.activity.CActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SPFragment extends BFragment implements Cons {
    private String TAG = SPFragment.class.getSimpleName();
    private AppCompatActivity activity;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (AppCompatActivity) getActivity();
        fcr.setCustomKey(MESSAGE_ALERT, TAG);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, getMob());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, TAG);
        CActivity.getfAnal().logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        return inflater.inflate(R.layout.fr_sp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(() -> {
            if (pdb.getString(Pf_first_time).equals("1")) {
                switch (getCFrom(pdb.getString(Cons.Pf_cat))){
                    case EMPLOYEE:
                        setFrame(HEFragment.newInstance(), true);
                        break;
                    case BUSINESS_ASSOCIATE:
                        setFrame(HFragment.newInstance(), true);
                        break;
                    case BUSINESS_PARTNER:
                        setFrame(HDFragment.newInstance(), true);
                        break;
                }
            } else {
                setFrame(STFragment.newInstance(), true);
            }
        }, 1500);
    }

//    void initApiServices() {
//            Call iCall = api.splash();
//            iCall.enqueue(new Callback<List<RolesResponse>>() {
//                @Override
//                public void onResponse(@NonNull Call<List<RolesResponse>> call, @NonNull Response<List<RolesResponse>> response) {
//                    setFrame(new MainFragment(), true);
//                    try {
//                        if (response.code() != 200) {
//                            return;
//                        }
//                    } catch (Exception e) {
//                        Timber.d(e.getMessage());
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<List<RolesResponse>> call, @NonNull Throwable t) {
//                    setFrame(new MainFragment(), true);
//                    Log.e(TAG, "onFailure-------: ", t);
//                    Timber.d(t.getMessage());
//                }
//            });
//    }

}
