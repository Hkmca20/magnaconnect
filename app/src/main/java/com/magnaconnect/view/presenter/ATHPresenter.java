/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.presenter;
import android.content.Context;
import android.util.Log;

import com.magnaconnect.network.IServices;
import com.magnaconnect.BApp;
import com.magnaconnect.di.Presenter;
import com.magnaconnect.utils.Cons;
import com.magnaconnect.view.contract.ATHContract;
import com.magnaconnect.view.model.AttResponse;
import com.magnaconnect.view.model.ScanRequest;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ATHPresenter implements Presenter<ATHContract.View>, Cons {
    String TAG = HPresenter.class.getSimpleName();

    @Inject
    @Named("cached")
    IServices IServices;
    private ATHContract.View view;

    @Inject
    public ATHPresenter(Context ctx) {
        ((BApp) ctx.getApplicationContext()).getComponent().inject(this);
    }

    @Override
    public void attachView(ATHContract.View mvpView) {
        this.view = mvpView;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void empgetatt(String userId) {
        ScanRequest request = new ScanRequest();
        request.setUserId(userId);
        try {
            view.startProgress();
            Call iCall = IServices.empgetatt(request);
            iCall.enqueue(new Callback<List<AttResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<AttResponse>> call, @NonNull Response<List<AttResponse>> response) {
                    view.endProgress();
                    try {
                        if (response.code() != 200) {
                            view.errorAlert();
                            return;
                        }
                        if (response.body() != null && response.body().size() > 0) {
                            view.successAttendance(response.body());
                        } else if (response.body().size() == 0) {
                            view.messageAlert(MESSAGE_ALERT, "No data found.");
                        } else {
                            view.errorAlert();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        view.errorAlert();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<AttResponse>> call, @NonNull Throwable t) {
                    view.endProgress();
                    Log.e(TAG, "onFailure: ", t);
                    view.errorAlert();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            KLog.d(e.getMessage());
        }
    }

    public void show_headers() {

//    IServices.getHeaders().enqueue(new Callback<IPInfo>() {
//      @Override public void onResponse(@NonNull Call<IPInfo> call, @NonNull Response<IPInfo> response) {
//
//        ATHContract.showHeaders(response.body().toString());
//      }
//
//      @Override public void onFailure(@NonNull Call<IPInfo> call, @NonNull Throwable t) {
//        KLog.e(t);
//        ATHContract.showError("error");
//      }
//    });

    }
}