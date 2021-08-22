/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.presenter;

import android.content.Context;

import com.magnaconnect.BApp;
import com.magnaconnect.network.IServices;
import com.magnaconnect.view.contract.S6Contract;
import com.magnaconnect.view.model.ProdResponse;
import com.magnaconnect.di.Presenter;
import com.magnaconnect.utils.Cons;
import com.magnaconnect.view.model.EmpRequest;
import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class S6Presenter implements Presenter<S6Contract.View>, Cons {
    String TAG = HPresenter.class.getSimpleName();

    @Inject
    @Named("cached")
    IServices IServices;
    private S6Contract.View view;

    @Inject
    public S6Presenter(Context ctx) {
        ((BApp) ctx.getApplicationContext()).getComponent().inject(this);
    }

    @Override
    public void attachView(S6Contract.View mvpView) {
        this.view = mvpView;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void esubJson(String masterJson) {
        Gson gson = new Gson();
        EmpRequest request = gson.fromJson(masterJson, EmpRequest.class);
        try {
            view.startProgress();
            Call iCall = IServices.esubJson(request);
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
                            view.successSubmit(response.body());
                        } else if (response.body().size() == 0) {
                            view.messageAlert(MESSAGE_ALERT, "No Data found, please try later!");
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
                    view.errorAlert();
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
            KLog.d(e.getMessage());
        }
    }
}