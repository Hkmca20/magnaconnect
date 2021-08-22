/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.presenter;
import android.content.Context;

import com.magnaconnect.network.IServices;
import com.magnaconnect.BApp;
import com.magnaconnect.R;
import com.magnaconnect.di.Presenter;
import com.magnaconnect.utils.Cons;
import com.magnaconnect.utils.Utils;
import com.magnaconnect.view.contract.PContract;
import com.magnaconnect.view.model.LoginRequest;
import com.magnaconnect.view.model.VerifyResponse;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PPresenter implements Presenter<PContract.View>, Cons {
    String TAG = PPresenter.class.getSimpleName();
    PContract.View view;
    Context context;

    @Inject
    @Named("cached")
    IServices IServices;

    @Inject
    public PPresenter(Context ctx) {
        ((BApp) ctx.getApplicationContext()).getComponent().inject(this);
        this.context = ctx;
    }

    @Override
    public void attachView(PContract.View mvpView) {
        this.view = mvpView;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void password(String userId, String password) {
        LoginRequest request = new LoginRequest();
        request.setPassword(password);
        try {
            view.startProgress();
            Call iCall = IServices.password(userId, request);
            iCall.enqueue(new Callback<VerifyResponse>() {
                @Override
                public void onResponse(@NonNull Call<VerifyResponse> call, @NonNull Response<VerifyResponse> response) {
                    view.endProgress();
                    try {
                        if (response.code() != 200) {
                            view.error("Failed to change password!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
                            return;
                        }
                        if (true) {
                            view.success(response.body());
                        } else {
//                            view.error(response.body().getError(), context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
                        }
                    } catch (Exception e) {
                        view.error(e.getMessage(), context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
                    }
                }
                @Override
                public void onFailure(@NonNull Call<VerifyResponse> call, @NonNull Throwable t) {
                    view.endProgress();
                    view.error(t.getMessage(), context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
        }
    }

    public void validCredSubmit(String userId, String password, String confirmPassword) {
        if (password.isEmpty()) {
            view.showSnack("Please enter your password", "DISMISS");
        } else if (confirmPassword.isEmpty()) {
            view.showSnack("Please confirm your password", "DISMISS");
        } else if (!password.equals(confirmPassword)) {
            view.showSnack("Password and confirm password not matched!", "DISMISS");
        } else {
            if (Utils.isNetworkAvailable(context)) {
//                CallProgressWheel.showLoadingDialog(activity, "");
                password(userId, password);
            } else {
//                Utils.showErrorMessageDialog(context, "Error", "Please check your Internet connection",true, () -> {
//                });
            }
        }
    }
}
