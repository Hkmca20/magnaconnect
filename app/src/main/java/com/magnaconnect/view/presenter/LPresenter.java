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
import com.magnaconnect.utils.JWTUtils;
import com.magnaconnect.utils.Utils;
import com.magnaconnect.view.contract.LContract;
import com.magnaconnect.view.model.LoginRequest;
import com.magnaconnect.view.model.VerifyResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.magnaconnect.utils.Utility.notNullParams;
import static com.magnaconnect.utils.Utility.validateMobileNo;

public class LPresenter implements Presenter<LContract.View>, Cons {
    String TAG = LPresenter.class.getSimpleName();
    LContract.View view;
    Context context;
    @Inject
    @Named("cached")
    IServices IServices;

    @Inject
    public LPresenter(Context ctx) {
        ((BApp) ctx.getApplicationContext()).getComponent().inject(this);
        this.context = ctx;
    }

    @Override
    public void attachView(LContract.View mvpView) {
        this.view = mvpView;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    String getCat(CATEGORY mCategory) {
        String cat = "";
        switch (mCategory) {
            case EMPLOYEE:
                cat = ARG_E;
                break;
            case BUSINESS_ASSOCIATE:
                cat = ARG_R;
                break;
            case BUSINESS_PARTNER:
                cat = ARG_D;
                break;
        }
        return cat;
    }

    public void userLogin(CATEGORY mCategory, String mobileNo, String password) {
        LoginRequest request = new LoginRequest();
        request.setCategory(getCat(mCategory));
        request.setMobileNo(mobileNo);
        request.setPassword(password);
        try {
            view.startProgress();
            Call iCall = IServices.userLogin(request);
            iCall.enqueue(new Callback<List<VerifyResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<VerifyResponse>> call, @NonNull Response<List<VerifyResponse>> data) {
                    view.endProgress();
                    try {
                        if (data.code() != 200) {
                            view.errorAlert();
                            return;
                        }
                        VerifyResponse response;
                        if (data.body().size() > 0) {
                            response = data.body().get(0);
                        } else {
                            view.errorAlert();
                            return;
                        }
                        String status = response.getUserStatus();
                        if (!notNullParams(status)) {
                            view.errorAlert();
                            return;
                        }
                        switch (status) {
                            case ARG_A:
                                view.showMessage("Please enter your password.");
                                pdb.putString(Pf_uId, response.getUserId());
                                pdb.putString(PF_uName, response.getUserName());
                                view.successLogin(response.getUserPassword());
                                break;
                            case ARG_I:
                                view.showMessage("User not found, please register yourself.");
                                pdb.putString(Pf_uId, response.getUserId());
                                pdb.putString(PF_uName, response.getUserName());
                                response.setMobileNo(mobileNo);
                                view.userNotFound(response);
                                break;
                            case ARG_P:
                                view.messageAlert(MESSAGE_ALERT, "Pending status, please contact support team!");
                                break;
                            default:
                                view.errorAlert();
                                break;
                        }
                    } catch (Exception e) {
                        fcr.recordException(e);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<VerifyResponse>> call, @NonNull Throwable t) {
                    view.endProgress();
                    view.showMessage("No record found!");
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
        }
    }

    public void setJWTProfile(String token) {
        try {
            if (!notNullParams(token)) {
                return;
            }
            String jsonString = JWTUtils.decoded(token);
            JSONObject o = new JSONObject(jsonString);
            JSONObject jsonObject = o.getJSONObject("user");
//            Profile m = new Profile();
//            m.set_id(jsonObject.getString("_id"));
//            m.setIs_verified(jsonObject.getString("is_verified"));
//            m.setActivate(jsonObject.getString("activate"));
//            m.setEmail(jsonObject.getString("email"));
//            m.setFullname(jsonObject.getString("fullname"));
//            m.setMobile(jsonObject.getString("mobile"));
//            m.setRole(jsonObject.getString("role"));
//            m.setGroup(jsonObject.getString("group"));
//            m.set__v(jsonObject.getString("__v"));
//            view.setProfile(m);
//            Claim claim = new JWT(token).getClaims().get("user");
        } catch (JSONException e) {
            fcr.recordException(e);
        } catch (Exception e) {
            fcr.recordException(e);
        }
    }

    public void validateSubmit(CATEGORY mCategory, String mMobileNo, String mPassword) {
        if (mMobileNo.isEmpty()) {
            view.showSnackCustom(context.getResources().getString(R.string.mobile_empty), context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else if (!validateMobileNo(mMobileNo)) {
            view.showSnackCustom("Invalid mobile number.", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
//        } else if (mPassword.isEmpty()) {
//            view.showSnackCustom(context.getResources().getString(R.string.password_empty), context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else {
            if (Utils.isNetworkAvailable(context)) {
//                CallProgressWheel.showLoadingDialog(activity, "");
                if (view.getRememberStatus()) {
                    pdb.putString(Pf_last_login, mMobileNo);
                } else {
                    pdb.putString(Pf_last_login, "");
                }
                userLogin(mCategory, mMobileNo, mPassword);
            } else {
                view.errorAlert();
            }
        }
    }
}
