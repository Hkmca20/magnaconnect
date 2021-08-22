/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.presenter;

import android.content.Context;

import com.magnaconnect.BApp;
import com.magnaconnect.di.Presenter;
import com.magnaconnect.network.IServices;
import com.magnaconnect.utils.Cons;
import com.magnaconnect.utils.Utility;
import com.magnaconnect.view.contract.HContract;
import com.magnaconnect.view.model.DashResponse;
import com.magnaconnect.view.model.ScanRequest;
import com.magnaconnect.view.model.ScnResponse;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HPresenter implements Presenter<HContract.View>, Cons {
    @Inject
    @Named("cached")
    IServices IServices;
    String TAG = HPresenter.class.getSimpleName();
    HContract.View view;
    Context context;

    @Inject
    public HPresenter(Context ctx) {
        ((BApp) ctx.getApplicationContext()).getComponent().inject(this);
        this.context = ctx;
    }

    @Override
    public void attachView(HContract.View mvpView) {
        this.view = mvpView;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void dbo(String userId) {
        ScanRequest request = new ScanRequest();
        request.setUserId(userId);
        try {
            view.startProgress();
            Call iCall = IServices.dbo(request);
            iCall.enqueue(new Callback<DashResponse>() {
                @Override
                public void onResponse(@NonNull Call<DashResponse> call, @NonNull Response<DashResponse> response) {
                    view.endProgress();
                    try {
                        if (response.code() != 200) {
                            view.errorAlert();
                            return;
                        }
                        if (response.body().dab != null) {
                            view.successDashboard(response.body());
                        } else {
                            view.errorAlert();
                        }
                    } catch (Exception e) {
                        fcr.recordException(e);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DashResponse> call, @NonNull Throwable t) {
                    view.endProgress();
                    view.errorAlert();
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
        }
    }

    public void slverify(String serialNo, String userId) {
        ScanRequest request = new ScanRequest();
        request.setSerialNo(serialNo);
        request.setUserId(userId);
        try {
            view.startProgress();
            Call iCall = IServices.slverify(request);
            iCall.enqueue(new Callback<List<ScnResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<ScnResponse>> call, @NonNull Response<List<ScnResponse>> response) {
                    view.endProgress();
                    try {
                        if (response.code() != 200) {
                            view.errorAlert();
                            return;
                        }
                        if (response.body() != null && response.body().size() > 0) {
                            if (response.body().get(0).getStatus().equalsIgnoreCase("ok")) {
                                if (Utility.notNullParams(response.body().get(0).getModuleName())) {
                                    view.successScanVerification(serialNo, response.body().get(0));
                                    view.showMessage("Verification success!");
                                } else {
                                    view.messageAlert(MESSAGE_ALERT, response.body().get(0).getMessage());
                                }
                            } else {
                                view.messageAlert(MESSAGE_ALERT, response.body().get(0).getMessage());
                            }
                        } else {
                            view.messageAlert(MESSAGE_ALERT, "Verification failed.");
                        }
                    } catch (Exception e) {
                        fcr.recordException(e);
                        view.errorAlert();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<ScnResponse>> call, @NonNull Throwable t) {
                    view.endProgress();
                    view.showMessage("Verification failed");
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
        }
    }

    public void scan(String serialNo, String userId, String toMobileNumber, String toName, String toPincode, String toAddress) {
        ScanRequest request = new ScanRequest();
        request.setSerialNo(serialNo);
        request.setUserId(userId);
        request.setToName(toName);
        request.setToMobileNumber(toMobileNumber);
        request.setToPincode(toPincode);
        request.setToAddress(toAddress);
        try {
            view.startProgress();
            Call iCall = IServices.scan(request);
            iCall.enqueue(new Callback<ScnResponse>() {
                @Override
                public void onResponse(@NonNull Call<ScnResponse> call, @NonNull Response<ScnResponse> response) {
                    view.endProgress();
                    try {
                        if (response.code() != 200) {
                            view.errorAlert();
                            return;
                        }
                        if (response.body().getStatus().equalsIgnoreCase("OK")) {
                            view.successScan(response.body());
//                            view.showMessage(String.valueOf(response.body().getMessage()));
                        } else {
                            view.messageAlert(MESSAGE_ALERT, response.body().getMessage());
                        }
                    } catch (Exception e) {
                        fcr.recordException(e);
                        view.errorAlert();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ScnResponse> call, @NonNull Throwable t) {
                    view.endProgress();
                    view.errorAlert();
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
            KLog.d(e.getMessage());
        }
    }

}
