/*
 * Created by Hariom.Gupta on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the Activity to be memorised in the
 * correct component.
 */
package com.magnaconnect.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.utils.Utils;
import com.magnaconnect.view.activity.CActivity;
import com.magnaconnect.view.contract.LContract;
import com.magnaconnect.view.model.VerifyResponse;
import com.magnaconnect.view.presenter.LPresenter;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.magnaconnect.utils.Utility.notNullParams;
import static com.magnaconnect.utils.Utility.showSnackMessage;
import static com.magnaconnect.utils.Utility.underlineText;

public class LFragment extends BFragment implements LContract.View {
    final static String TAG = LFragment.class.getSimpleName();
    LPresenter lPresenter;
    @BindView(R.id.et_mobile)
    EditText et_mobile;
    @BindView(R.id.et_password)
    EditText et_Password;
    @BindView(R.id.rl_login)
    RelativeLayout rl_login;
    @BindView(R.id.rememberCB)
    CheckBox rememberCB;
    @BindView(R.id.forgotTV)
    TextView forgotTV;
    @BindView(R.id.signupTV)
    TextView signupTV;
    @BindString(R.string.check_connection)
    String check_connection;
    @BindString(R.string.password_error)
    String password_error;
    @BindString(R.string.login_success)
    String login_success;
    String mMob = "", mPassword;
    AlertDialog alert;
    private AppCompatActivity activity;
    private View rootView;
    //    CallbackManager callbackManager;
//    LoginButton login_facebook;
    private CATEGORY mCategory;

    public static LFragment newInstance() {
        LFragment f = new LFragment();
        return f;
    }

    public static LFragment newInstance(CATEGORY category_argument, String mobileNo) {
        LFragment f = new LFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        f.mCategory = category_argument;
        if (notNullParams(mobileNo)) {
            f.mMob = mobileNo;
        } else {
            f.mMob = pdb.getString(Pf_last_login);
        }
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fcr.setCustomKey(MESSAGE_ALERT, TAG);
        activity = (AppCompatActivity) getActivity();
        lPresenter = new LPresenter(activity);
        lPresenter.attachView(this);
        return inflater.inflate(R.layout.fr_l, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        initFragment(rootView, activity, mCategory + " LOGIN", statusColor, themeColor, true);
//        facebookSetup();
        et_mobile.setText(mMob);
        et_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPassword = null;
                disableVisible(et_Password);
            }
        });
        et_Password.setText(pdb.getString(Pf_last_pass));
        underlineText(forgotTV, forgotTV.getText().toString());
        lPresenter.setJWTProfile(pdb.getString(PF_TOKEN));
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
//
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//            }
//
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

    public void setOtpDialog() {
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
                if (targetET.getText().length() == 0) {
                    showToast("Please provide otp.");
                    return;
                } else if (targetET.getText().length() < 6) {
                    String message = "Invalid otp";
                    showToast(message);
                    return;
                } else if (targetET.getText().length() > 6) {
                    String message = "Invalid otp";
                    showToast(message);
                    return;
                } else {
                    hideKeyBoard();
                    if (alert != null) {
                        alert.cancel();
                    }
                    try {
                        showMessage("Password reset failed!");
                    } catch (Exception e) {
                        showToast("Failed!");
                    }
                }
            });
            alert = builder.create();
            alert.setCancelable(false);
            alert.show();
        } catch (Exception ex) {
            fcr.recordException(ex);
            throw ex;
        }
    }

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
//                        } catch (JSONException e) {
//                        }
//
//                    }
//                });
//
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "first_name,last_name,email,id");
//        request.setParameters(parameters);
//        request.executeAsync();
//    }
    @Override
    public void successLogin(String mPassword) {
        fcr.setCustomKey(MESSAGE_SUCCESS, getMob());
        this.mPassword = mPassword;
        enableVisible(et_Password);
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

    public void showSnackTop(String message) {
        showSnackMessage(getActivity(), message, true);
    }

    public void snackBar(View view, String message, String buttonText) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).
                setAction(buttonText, v -> {
                });
        snackbar.setActionTextColor(Color.WHITE);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.notification_template_icon_bg, 0, 0, 0);
        textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.size_margin20));
        snackbar.show();
    }

    @Override
    public boolean getRememberStatus() {
        return rememberCB.isChecked();
    }

    @Override
    public void userNotFound(VerifyResponse response) {
        activity.getSupportFragmentManager().popBackStack();
        response.setMobileNo(mMob);
        Fragment f = SIFragment.newInstance(mCategory, mMob, response.getUserOtp());
        Bundle b = new Bundle();
        b.putSerializable(ARG_RES, response);
        f.setArguments(b);
        setFrame(f, false);
    }

    @OnClick({R.id.btn_login, R.id.forgotTV, R.id.signupTV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Utils.dismissKeyboard(activity);
                mMob = et_mobile.getText().toString();
                if (Utils.isNetworkAvailable(activity)) {
                    if (notNullParams(mPassword)) {
                        if (mPassword.equals(et_Password.getText().toString())) {
                            showMessage(login_success);
                            fcr.setUserId(mMob);
                            pdb.putString(Pf_m, mMob);
                            pdb.putString(Pf_first_time, "1");
                            pdb.putString(Pf_cat, mCategory.toString());
                            activity.finish();
                            startActivity(new Intent(activity, CActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        } else {
                            showMessage(password_error);
                        }
                    } else {
                        lPresenter.validateSubmit(mCategory, mMob, mPassword);
                    }
                } else {
                    showErrorMessageDialog(activity, MESSAGE_ERROR, check_connection, true, () -> {
                    });
                }
                break;
            case R.id.forgotTV:
                setOtpDialog();
                break;
        }
    }
}
