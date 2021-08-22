/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.presenter;

import android.content.Context;
import android.util.Log;

import com.magnaconnect.BApp;
import com.magnaconnect.R;
import com.magnaconnect.di.Presenter;
import com.magnaconnect.network.IServices;
import com.magnaconnect.utils.Cons;
import com.magnaconnect.utils.Utility;
import com.magnaconnect.utils.Utils;
import com.magnaconnect.view.contract.ADLContract;
import com.magnaconnect.view.model.DlrRequest;
import com.magnaconnect.view.model.LoginRequest;
import com.magnaconnect.view.model.ScanRequest;
import com.magnaconnect.view.model.StatResponse;
import com.magnaconnect.view.model.VerifyResponse;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.magnaconnect.utils.Utility.notNullParams;

public class ADLPresenter implements Presenter<ADLContract.View>, Cons {
    String TAG = ADLPresenter.class.getSimpleName();
    ADLContract.View view;
    Context context;

    @Inject
    @Named("cached")
    IServices IServices;

    @Inject
    public ADLPresenter(Context ctx) {
        ((BApp) ctx.getApplicationContext()).getComponent().inject(this);
        this.context = ctx;
    }

    @Override
    public void attachView(ADLContract.View mvpView) {
        this.view = mvpView;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void distList(String userId) {
        ScanRequest request = new ScanRequest();
        request.setUserId(userId);
        try {
            view.startProgress();
            Call iCall = IServices.distList(request);
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
                            view.successDistributor(response.body());
                        } else if (response.body().size() == 0) {
                            view.messageAlert(MESSAGE_ALERT, "Distributor record not found, please try later!");
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
                    Log.e(TAG, "onFailure: ", t);
                    view.errorAlert();
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
            KLog.d(e.getMessage());
        }
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
                            view.successPartnerT(response.body().getPartnerTypeList());
                        } else if (response.body().getStateList().size() == 0) {
                            view.messageAlert(MESSAGE_ALERT, "State data not found, please try later!");
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
                    Log.e(TAG, "onFailure: ", t);
                    view.errorAlert();
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
            KLog.d(e.getMessage());
        }
    }

    public void verifydl(String mobileNumber) {
        if (!Utility.validateMobileNo(mobileNumber)) {
            view.messageAlert("Alert!", "Invalid mobile number, \nPlease enter correct mobile number and try again.");
            return;
        }
        LoginRequest request = new LoginRequest();
        request.setMobileNo(mobileNumber);
        try {
            view.startProgress();
            Call iCall = IServices.verifydl(request);
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
                            view.numberVerifiedRes(response.body().get(0));
                        } else if (response.body().size() == 0) {
                            view.messageAlert(MESSAGE_ALERT, "This number is not verified!");
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
                    Log.e(TAG, "onFailure: ", t);
                    view.errorAlert();
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
            KLog.d(e.getMessage());
        }
    }

    public void createdlr(DlrRequest request) {
        try {
            view.startProgress();
            Call iCall = IServices.createdlr(request);
            iCall.enqueue(new Callback<List<VerifyResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<VerifyResponse>> call, @NonNull Response<List<VerifyResponse>> response) {
                    view.endProgress();
                    if (response.body() == null && response.body().size() == 0) {
                        view.errorAlert();
                    }
                    VerifyResponse item = response.body().get(0);
                    if (notNullParams(item.getStatus()) && item.getStatus().equalsIgnoreCase(MESSAGE_OK)) {
                        view.successDealerRes(item);
                    } else if (notNullParams(item.getResponse_status())
                            && item.getResponse_status().equalsIgnoreCase(MESSAGE_ERROR)) {
                        view.messageAlert(item.getResponse_status(), item.getErr());
                    } else if (item.getStatus().equalsIgnoreCase(MESSAGE_ERROR)) {
                        view.showMessage(item.getMessage());
                    } else {
                        view.errorAlert();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<VerifyResponse>> call, @NonNull Throwable t) {
                    view.endProgress();
                    Log.e(TAG, "onFailure: ", t);
                    view.showMessage("Failed to save data, please try later.");
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
            KLog.d(e.getMessage());
        }
    }

    public void validateSubmit(String mFullName, String mShopName, String mGSTNo, String mPinCode, String mAddress, String mDistributor, String mState, String mPartnerType, String mEmail, String mMobileNumber, String mAltMobileNumber, String mPassword, String mLatitude, String mLongitude) {
        if (!notNullParams(mMobileNumber)) {
            view.showSnackCustom("Please enter your mobile number!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else if (mMobileNumber.length() < 10) {
            view.showSnackCustom("Invalid mobile number!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));

        } else if (!notNullParams(mDistributor) || mDistributor.equals("0")) {
            view.showSnackCustom("Please select a distributor to continue!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else if (mFullName.isEmpty()) {
            view.showSnackCustom(context.getResources().getString(R.string.name_empty), context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else if (!notNullParams(mShopName)) {
            view.showSnackCustom("Please provide shop name!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));

        } else if (!notNullParams(mPinCode)) {
            view.showSnackCustom("PIN Code required!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else if (!notNullParams(mAddress)) {
            view.showSnackCustom("Address is required to continue!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else if (!notNullParams(mState) || mState.equals("0")) {
            view.showSnackCustom("Please select your State to continue!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else if (!notNullParams(mPartnerType) || mPartnerType.equals("0")) {
            view.showSnackCustom("Please select partner type to continue!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));

        } else if (mEmail.trim().length() > 0 && !EMAIL_ADDRESS.matcher(mEmail).matches()) {
            view.showSnackCustom("Invalid email id!", context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else if (mPassword.isEmpty()) {
            view.showSnackCustom(context.getResources().getString(R.string.password_empty), context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
//        } else if (!mPassword.equals(mConfirmPassword)) {
//            view.showSnackCustom(context.getResources().getString(R.string.confirm_password), context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else {// TODO: 11/6/2019  condition for confirm password here
            if (Utils.isNetworkAvailable(context)) {
//                CallProgressWheel.showLoadingDialog(activity, "");
                DlrRequest request = new DlrRequest();
//        request.setCategory(getCat(mCategory));
                request.setUserCode(view.getUUID());
                request.setUserName(mFullName);
                request.setShopName(mShopName);
                request.setGstn(mGSTNo);
                request.setPincode(mPinCode);
                request.setAddress(mAddress);
                request.setDistributorId(mDistributor);
                request.setDistsId(mDistributor);
                request.setStateCode(mState);
                request.setPartnerType(mPartnerType);
                request.setEmailId(mEmail);
                request.setMobileNo(mMobileNumber);
//        request.setAltMobileNo(mAltMobileNumber);
                request.setUserPassword(mPassword);
                request.setLat(mLatitude);
                request.setLog(mLongitude);
                createdlr(request);
            } else {
                view.errorAlert();
            }
        }
    }
}
