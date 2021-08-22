/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.presenter;

import android.content.Context;

import com.magnaconnect.BApp;
import com.magnaconnect.R;
import com.magnaconnect.network.IServices;
import com.magnaconnect.di.Presenter;
import com.magnaconnect.utils.Cons;
import com.magnaconnect.utils.Utils;
import com.magnaconnect.view.contract.SIContract;
import com.magnaconnect.view.model.ScanRequest;
import com.magnaconnect.view.model.RegRequest;
import com.magnaconnect.view.model.StatResponse;
import com.magnaconnect.view.model.VerifyResponse;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.magnaconnect.utils.Utility.notNullParams;

public class SIPresenter implements Presenter<SIContract.View>, Cons {
    String TAG = SIPresenter.class.getSimpleName();
    SIContract.View view;
    Context context;

    @Inject
    @Named("cached")
    IServices IServices;

    @Inject
    public SIPresenter(Context ctx) {
        ((BApp) ctx.getApplicationContext()).getComponent().inject(this);
        this.context = ctx;
    }

    @Override
    public void attachView(SIContract.View mvpView) {
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
                cat = "e";
                break;
            case BUSINESS_ASSOCIATE:
                cat = "r";
                break;
            case BUSINESS_PARTNER:
                cat = "d";
                break;
        }
        return cat;
    }

    public void getstList(String userId) {
        ScanRequest request = new ScanRequest();
        request.setUserId(userId);
        try {
            view.startProgress();
            Call iCall = IServices.getstList(request);
            iCall.enqueue(new Callback<StatResponse>() {
                @Override
                public void onResponse(@NonNull Call<StatResponse> call, @NonNull Response<StatResponse> response) {
                    view.endProgress();
                    try {
                        if (response.code() != 200) {
                            view.errorAlert();
                            return;
                        }
                        if (response.body() != null && response.body().getStateList().size() > 0) {
                            view.successCity(response.body().getStateList());
                        } else if (response.body().getStateList().size() == 0) {
                            view.messageAlert(MESSAGE_ALERT, "No State Data found, please try later!");
                        } else {
                            view.errorAlert();
                        }
                    } catch (Exception e) {
                        fcr.recordException(e);
                        view.errorAlert();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<StatResponse> call, @NonNull Throwable t) {
                    view.endProgress();
                    view.errorAlert();
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
        }
    }

    public void signupApi(CATEGORY mCategory, String userId, String mFullName, String mShopName, String mGSTNo,
                          String mPinCode, String mAddress, String mState, String mEmail, String mMobileNumber, String mAltMobileNumber, String password, String otp) {
        RegRequest request = new RegRequest();
        request.setCategory(getCat(mCategory));
        request.setUserId(userId);
        request.setUserName(mFullName);
        request.setFullName(mFullName);
        request.setShopName(mShopName);
        request.setGSTNo(mGSTNo);
        request.setPincode(mPinCode);
        request.setAddress(mAddress);
        request.setState(mState);
        request.setUserEmail(mEmail);
        request.setMobileNo(mMobileNumber);
        request.setAltMobileNo(mAltMobileNumber);
        request.setUserPassword(password);
        request.setOtp(otp);
        try {
            view.startProgress();
            Call iCall = IServices.signup(request);
            iCall.enqueue(new Callback<VerifyResponse>() {
                @Override
                public void onResponse(@NonNull Call<VerifyResponse> call, @NonNull Response<VerifyResponse> response) {
                    view.endProgress();
                    if (response.body() != null && response.body().getStatus().equalsIgnoreCase(MESSAGE_OK)) {
                        view.successRegistration(response.body());
                    } else if (response.body() != null && response.body().getStatus().equalsIgnoreCase(MESSAGE_ERROR)) {
                        view.showMessage(response.body().getMessage());
                    } else {
                        view.errorAlert();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<VerifyResponse> call, @NonNull Throwable t) {
                    view.endProgress();
                    view.showMessage("No record found!");
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
        }
    }

    public void validateSubmit(String mFullName, String mShopName, String mGSTNo, String mPinCode, String mAddress, String mState, String mEmail, String mMobileNumber, String mAltMobileNumber, String mPassword, String mConfirmPassword) {
        if (mFullName.isEmpty()) {
            view.showSnackCustom(context.getResources().getString(R.string.name_empty), context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else if (!notNullParams(mShopName)) {
            view.showSnackCustom("Please provide shop name!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));

        } else if (!notNullParams(mPinCode)) {
            view.showSnackCustom("PIN Code required!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else if (!notNullParams(mAddress)) {
            view.showSnackCustom("Address is required to continue!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else if (!notNullParams(mState) || mState.equals("0")) {
            view.showSnackCustom("Please select your State to continue!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));

        } else if (!notNullParams(mEmail)) {
            view.showSnackCustom("Please enter your email id!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else if (!EMAIL_ADDRESS.matcher(mEmail).matches()) {
            view.showSnackCustom("Invalid email id!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else if (!notNullParams(mMobileNumber)) {
            view.showSnackCustom("Please enter your mobile number!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else if (mMobileNumber.length() < 10) {
            view.showSnackCustom("Invalid mobile number!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else if (mPassword.isEmpty()) {
            view.showSnackCustom(context.getResources().getString(R.string.password_empty), context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
//        } else if (!mPassword.equals(mConfirmPassword)) {
//            view.showSnackCustom(context.getResources().getString(R.string.confirm_password), context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else {// TODO: 11/6/2019  condition for confirm password here
            if (Utils.isNetworkAvailable(context)) {
//                CallProgressWheel.showLoadingDialog(activity, "");
                view.showOtpDialog();
            } else {
                view.errorAlert();
            }
        }
    }
}
