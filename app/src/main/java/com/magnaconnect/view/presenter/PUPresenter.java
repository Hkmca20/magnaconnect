/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.presenter;
import android.content.Context;
import android.util.Log;

import com.magnaconnect.R;
import com.magnaconnect.network.IServices;
import com.magnaconnect.BApp;
import com.magnaconnect.di.Presenter;
import com.magnaconnect.utils.Cons;
import com.magnaconnect.view.contract.PUContract;
import com.magnaconnect.view.model.AttResponse;
import com.magnaconnect.view.model.ScanRequest;
import com.magnaconnect.view.model.VerifyResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PUPresenter implements Presenter<PUContract.View>, Cons {
    String TAG = PUPresenter.class.getSimpleName();

    @Inject
    @Named("cached")
    IServices IServices;
    private PUContract.View view;

    @Inject
    public PUPresenter(Context ctx) {
        ((BApp) ctx.getApplicationContext()).getComponent().inject(this);
    }

    @Override
    public void attachView(PUContract.View mvpView) {
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
                            AttResponse item = response.body().get(response.body().size() - 1);
                            view.successAtt(item);
                        } else if (response.body().size() == 0) {
                            view.messageAlert(MESSAGE_ALERT, view.getCtx().getResources().getString(R.string.attend_udpate));
                        } else {
                            view.errorAlert();
                        }
                    } catch (Exception e) {
                        fcr.recordException(e);
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
            fcr.recordException(e);
        }
    }

    public void esubatt(ScanRequest request) {
        try {
            view.startProgress();
            Call iCall = IServices.esubatt(request);
            iCall.enqueue(new Callback<VerifyResponse>() {
                @Override
                public void onResponse(@NonNull Call<VerifyResponse> call, @NonNull Response<VerifyResponse> response) {
                    view.endProgress();
                    try {
                        if (response.code() != 200) {
                            view.errorAlert();
                            return;
                        }
                        if (response.body() != null ) {
                            view.successAttSave(response.body());
                        } else {
                            view.errorAlert();
                        }
                    } catch (Exception e) {
                        view.errorAlert();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<VerifyResponse> call, @NonNull Throwable t) {
                    view.endProgress();
                    view.errorAlert();
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
        }
    }

    public void show_headers() {

//    IServices.getHeaders().enqueue(new Callback<IPInfo>() {
//      @Override public void onResponse(@NonNull Call<IPInfo> call, @NonNull Response<IPInfo> response) {
//
//        PUContract.showHeaders(response.body().toString());
//      }
//
//      @Override public void onFailure(@NonNull Call<IPInfo> call, @NonNull Throwable t) {
//        KLog.e(t);
//        PUContract.showError("error");
//      }
//    });

    }
}