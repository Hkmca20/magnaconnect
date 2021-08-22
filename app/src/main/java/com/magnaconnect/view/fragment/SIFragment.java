/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.utils.Utils;
import com.magnaconnect.view.contract.SIContract;
import com.magnaconnect.view.dialog.SDialog;
import com.magnaconnect.view.model.StatResponse;
import com.magnaconnect.view.model.VerifyResponse;
import com.magnaconnect.view.presenter.SIPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.OnClick;

import static com.magnaconnect.utils.Utility.notNullParams;

public class SIFragment extends BFragment implements SIContract.View {
    private static SIFragment INSTANCE;
    SIPresenter SIPresenter;
    //    @Inject
//    SharedPrefUtils<String> util;
    @BindView(R.id.et_full_name)
    EditText et_full_name;
    @BindView(R.id.et_shop_name)
    EditText et_shop_name;
    @BindView(R.id.et_GST_no)
    EditText et_GST_no;
    @BindView(R.id.et_pincode)
    EditText et_pincode;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_mobileNumber)
    EditText et_mobileNumber;
    @BindView(R.id.et_altMobileNumber)
    EditText et_altMobileNumber;
    @BindView(R.id.et_password)
    EditText et_Password;
    @BindView(R.id.et_confirmPassword)
    EditText et_confirmPassword;
    @BindView(R.id.rl_login)
    RelativeLayout rl_login;
    @BindView(R.id.spinner_state_name)
    EditText spinner_state_name;
    Dialog alert;
    List<StatResponse.StateItem> spinnerList = new ArrayList<>();
    String selectedStateId;
    private VerifyResponse res;
    private String TAG = SIFragment.class.getSimpleName();
    private String mFullName, mShopName, mGSTNo, mPinCode, mAddress, mEmail,
            mMob, mAltMobileNumber, mPassword, mConfirmPassword, mOtp;
    private AppCompatActivity activity;
    //    CallbackManager callbackManager;
//    LoginButton login_facebook;
    private View rootView;
    private CATEGORY mCategory;

    public static SIFragment newInstance() {
        SIFragment f = new SIFragment();

        return f;
    }

    public static SIFragment getInstance() {
        return INSTANCE;
    }

//    private void facebookSetup() {
//        login_facebook = rootView.findViewById(R.id.login_facebook);
//        boolean loggedOut = AccessToken.getCurrentAccessToken() == null;
//
//        if (!loggedOut) {
////            Picasso.with(this).load(Profile.getCurrentProfile().getProfilePictureUri(200, 200)).into(imageView);
//            Log.d("TAG", "Username is: " + Profile.getCurrentProfile().getName());
//
//            //Using Graph API
//            getUserProfile(AccessToken.getCurrentAccessToken());
//        }
//        login_facebook.setReadPermissions(Arrays.asList("email", "public_profile"));
//        callbackManager = CallbackManager.Factory.create();
//
//        login_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//                //loginResult.getAccessToken();
//                //loginResult.getRecentlyDeniedPermissions()
//                //loginResult.getRecentlyGrantedPermissions()
//                boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
//                Log.d("API123", loggedIn + " ??");
//            }
//            @Override
//            public void onCancel() {
//                // App code
//            }
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//            }
//        });
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    //    private void getUserProfile(AccessToken currentAccessToken) {
//        GraphRequest request = GraphRequest.newMeRequest(
//                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(JSONObject object, GraphResponse response) {
//                        Log.d("TAG", object.toString());
//                        try {
//                            String first_name = object.getString("first_name");
//                            String last_name = object.getString("last_name");
//                            String email = object.getString("email");
//                            String id = object.getString("id");
//                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
//
//                            Log.d(TAG, "First Name: " + first_name + "\nLast Name: " + last_name);
//                            Log.d(TAG, "Email==========" + email);
////                            Picasso.with(activity).load(image_url).into(imageView);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "first_name,last_name,email,id");
//        request.setParameters(parameters);
//        request.executeAsync();
//    }

    public static SIFragment newInstance(CATEGORY cat, String m, String o) {
        SIFragment f = new SIFragment();
        Bundle args = new Bundle();
        args.putString(ARG_OTP, o);
        args.putString(ARG_MOB, m);
        f.setArguments(args);
        f.mCategory = cat;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
//        mMob = args.getString(ARG_MOB);
//        mOtp = args.getString(ARG_OTP);
        activity = (AppCompatActivity) getActivity();
        fcr.setCustomKey(MESSAGE_ALERT, TAG);
        INSTANCE = this;
        setCurrentFragment(TAG);
        res = (VerifyResponse) getArguments().getSerializable(ARG_RES);
        SIPresenter = new SIPresenter(activity);
        SIPresenter.attachView(this);
        return inflater.inflate(R.layout.fr_si, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        initFragment(rootView, activity, SCREEN_Sign_up, statusColor, themeColor, true);
        setLayout();
    }

    private void setLayout() {
        try {
            spinner_state_name.setFocusable(false);
            if (notNullParams(res.getUserName())) {
                et_full_name.setText(res.getUserName());
            }
            if (notNullParams(res.getShopName())) {
                et_shop_name.setText(res.getShopName());
            }
            if (notNullParams(res.getGSTNo())) {
                et_GST_no.setText(res.getGSTNo());
            }
            if (notNullParams(res.getPincode())) {
                et_pincode.setText(res.getPincode());
            }
            if (notNullParams(res.getAddress())) {
                et_address.setText(res.getAddress());
            }
            if (notNullParams(res.getState())) {
                selectedStateId = res.getState();
                for (StatResponse.StateItem s : spinnerList) {
                    if (s.getCityId().equals(selectedStateId)) {
                        spinner_state_name.setText(s.getCityName());
                    }
                }
            }
            if (notNullParams(res.getUserEmail())) {
                et_email.setText(res.getUserEmail());
            }
            mOtp = res.getUserOtp();
            mMob = res.getMobileNo();
            et_mobileNumber.setText(mMob);
            if (notNullParams(mMob)) {
                et_mobileNumber.setFocusable(false);
            }
            CallST();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CallST() {
        if (spinnerList.size() > 0) {
            showMessage("State list already added!");
            return;
        }
        SIPresenter.getstList(getUser());
    }

    @Override
    public void successCity(List<StatResponse.StateItem> spinnerData) {
        spinnerList.clear();
        spinnerList.addAll(spinnerData);
    }

    @Override
    public void successRegistration(VerifyResponse response) {
        showErrorMessageDialog(activity, MESSAGE_ALERT, "" + response.getMessage(), false, () -> {
            activity.getSupportFragmentManager().popBackStack();
            setFrame(LFragment.newInstance(mCategory, et_mobileNumber.getText().toString()), false);
            showMessage("Login required!");
        });
    }

    @Override
    public void messageAlert(String title, String msg) {
        showMessageAlert(title, msg);
    }

    @Override
    public void errorAlert() {
        showErrorAlert();
    }

    @Override
    public void startProgress() {
        showProgressD("Please wait...");
    }

    @Override
    public void endProgress() {
        hideProgressD();
    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public void showSnackCustom(String message, String action) {
        snackBar(rl_login, message, action);
    }

    @Override
    public void showOtpDialog() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomDialog);
            LayoutInflater inflater = activity.getLayoutInflater();
            final View targetDialogView = inflater.inflate(R.layout.di_ott, null);
            final EditText targetET = targetDialogView.findViewById(R.id.EnteredOTP);
            targetET.setHint("Enter your otp");
            targetET.setTextSize(16);
            final TextView messageTV = targetDialogView.findViewById(R.id.TextUserMessage);
            final Button cancelBTN = targetDialogView.findViewById(R.id.ResendOTP);
            final Button submitBTN = targetDialogView.findViewById(R.id.SubmitOTP);
            enableVisible(targetDialogView.findViewById(R.id.buttonLayout));
            builder.setView(targetDialogView);
            cancelBTN.setOnClickListener(v -> {
                if (alert != null) {
                    alert.cancel();
                }
            });
            submitBTN.setOnClickListener(v -> {
                String message;
                String mOTPm = targetET.getText().toString();
                if (mOTPm.length() == 0) {
                    message = "Please provide otp then submit.";
                    showMessage(message);
                    return;
                } else if (mOTPm.length() < 6) {
                    message = "Invalid otp";
                    showMessage(message);
                    return;
                } else {
                    hideKeyBoard();
                    if (mOtp.equals(mOTPm)) {
                        if (alert != null) {
                            alert.cancel();
                        }
                        try {
                            SIPresenter.signupApi(mCategory, getUser(), mFullName, mShopName, mGSTNo, mPinCode, mAddress, selectedStateId, mEmail, mMob, mAltMobileNumber, mPassword, mOTPm);
                        } catch (Exception e) {
                            e.printStackTrace();
                            showMessage("Failed!");
                        }
                    } else {
                        message = "Please provide correct otp and try again!";
                        showMessage(message);
                    }
                }
            });
            alert = builder.create();
            alert.setCancelable(false);
            alert.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @OnClick({R.id.btn_login, R.id.et_mobileNumber, R.id.spinner_state_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Utils.dismissKeyboard(activity);
                mFullName = et_full_name.getText().toString();
                mShopName = et_shop_name.getText().toString();
                mGSTNo = et_GST_no.getText().toString();
                mPinCode = et_pincode.getText().toString();
                mAddress = et_address.getText().toString();
                mEmail = et_email.getText().toString();
                mMob = et_mobileNumber.getText().toString();
                mPassword = et_Password.getText().toString();
                mConfirmPassword = et_confirmPassword.getText().toString();

                if (Utils.isNetworkAvailable(activity)) {
                    SIPresenter.validateSubmit(mFullName, mShopName, mGSTNo, mPinCode, mAddress, selectedStateId, mEmail, mMob, mAltMobileNumber, mPassword, mConfirmPassword);
                } else {
                    showErrorAlert();
                }
                break;
            case R.id.et_mobileNumber:
                if (notNullParams(mMob)) {
                    showSnackCustom("This field can't be change!", "OK");
                }
                break;
            case R.id.spinner_state_name:
                if (spinnerList.size() == 0) {
                    showMessage("State not found, please try again!");
                    SIPresenter.getstList(getUser());
                    return;
                }
                final SDialog c = new SDialog(activity, spinnerList, "Search here", false,
                        dialogInterface -> {
                        },
                        id -> {
                            for (StatResponse.StateItem item : spinnerList) {
                                if (item.getCityId().equals(id)) {
                                    spinner_state_name.setText(item.getCityName());
                                    selectedStateId = item.getCityId();
                                }
                            }
                        });
                c.show();
                break;
        }
    }
}
