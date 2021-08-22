/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.presenter;

import android.content.Context;

import com.magnaconnect.di.Presenter;
import com.magnaconnect.network.IServices;
import com.magnaconnect.utils.Cons;
import com.magnaconnect.BApp;
import com.magnaconnect.view.contract.S2Contract;
import com.magnaconnect.view.model.ProdResponse;
import com.magnaconnect.view.model.ScanRequest;
import com.magnaconnect.view.model.VerifyResponse;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class S2Presenter implements Presenter<S2Contract.View>, Cons {
    String TAG = HPresenter.class.getSimpleName();

    @Inject
    @Named("cached")
    IServices IServices;
    private S2Contract.View view;

    @Inject
    public S2Presenter(Context ctx) {
        ((BApp) ctx.getApplicationContext()).getComponent().inject(this);
    }

    @Override
    public void attachView(S2Contract.View mvpView) {
        this.view = mvpView;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void edl(String userId) {
        ScanRequest request = new ScanRequest();
        request.setUserId(userId);
        try {
            view.startProgress();
            Call iCall = IServices.edl(request);
            iCall.enqueue(new Callback<List<ProdResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<ProdResponse>> call, @NonNull Response<List<ProdResponse>> response) {
                    view.endProgress();
                    try {
                        if (response.code() != 200) {
                            view.errorAlert();
                            return;
                        }
                        if (response.body() != null && response.body().size() > 0) {
                            view.successProduct(response.body());
                        } else if (response.body().size() == 0) {
                            view.messageAlert(MESSAGE_ALERT, "Dealer data not found!");
                        } else {
                            view.errorAlert();
                        }
                    } catch (Exception e) {
                        fcr.recordException(e);
                        view.errorAlert();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<ProdResponse>> call, @NonNull Throwable t) {
                    view.endProgress();
                    view.messageAlert(MESSAGE_ALERT, "No record found!");
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
            KLog.d(e.getMessage());
        }
    }

    public void dlList(String userId) {
        ScanRequest request = new ScanRequest();
        request.setUserId(userId);
        try {
            view.startProgress();
            Call iCall = IServices.dlList(request);
            iCall.enqueue(new Callback<List<VerifyResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<VerifyResponse>> call, @NonNull Response<List<VerifyResponse>> response) {
                    view.endProgress();
                    try {
                        if (response.code() != 200) {
                            view.errorAlert();
                            return;
                        }
                        if (response.body() != null && response.body().size() > 0) {
                            view.successDealerRes(response.body());
                        } else if (response.body().size() == 0) {
                            view.showMessage("Dealer not found!");
                        } else {
                            view.errorAlert();
                        }
                    } catch (Exception e) {
                        fcr.recordException(e);
                        view.errorAlert();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<VerifyResponse>> call, @NonNull Throwable t) {
                    view.endProgress();
                    view.errorAlert();
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
            KLog.d(e.getMessage());
        }
    }

    public void distdList(String userId) {
        ScanRequest request = new ScanRequest();
        request.setDistsId(userId);
        try {
            view.startProgress();
            Call iCall = IServices.distdList(request);
            iCall.enqueue(new Callback<List<VerifyResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<VerifyResponse>> call, @NonNull Response<List<VerifyResponse>> response) {
                    view.endProgress();
                    try {
                        if (response.code() != 200) {
                            view.errorAlert();
                            return;
                        }
                        if (response.body() != null && response.body().size() > 0) {
                            view.successDealerRes(response.body());
                        } else if (response.body().size() == 0) {
                            view.messageAlert(MESSAGE_ALERT, "No record found, please try later!");
                        } else {
                            view.errorAlert();
                        }
                    } catch (Exception e) {
                        fcr.recordException(e);
                        view.errorAlert();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<VerifyResponse>> call, @NonNull Throwable t) {
                    view.endProgress();
                    view.errorAlert();
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
        }
    }
}