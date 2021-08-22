/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.magnaconnect.R;
import com.magnaconnect.utils.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import butterknife.BindView;
import butterknife.OnClick;

import com.magnaconnect.view.contract.PContract;
import com.magnaconnect.view.model.VerifyResponse;
import com.magnaconnect.view.presenter.PPresenter;
import com.socks.library.KLog;

public class PFragment extends BFragment implements PContract.View{
    PPresenter pPresenter;
    //    @Inject
//    SharedPrefUtils<String> util;
    @BindView(R.id.et_confirmPassword)
    EditText et_confirmPassword;
    @BindView(R.id.et_password)
    EditText et_Password;
    String mPassword, mConfirmPassword;
    @BindView(R.id.coordinator_password)
    CoordinatorLayout coordinator_password;
    AppCompatActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AppCompatActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pPresenter = new PPresenter(activity);
        pPresenter.attachView(this);
        return inflater.inflate(R.layout.fr_p, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragment(view, activity, SCREEN_Change, statusColor, themeColor, true);
    }

    @Override
    public void success(VerifyResponse data) {
        if (data != null) {
            KLog.d("=====" + data);
            showMessage("Password changed successfully!");
        }
    }

    @Override
    public void error(String message, String action) {
        showSnack(message, action);
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
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void showSnack(String msg, String action) {
        snackBar(coordinator_password, msg, action);
    }

    @OnClick({R.id.submitButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submitButton:
                Utils.dismissKeyboard(activity);
                mPassword = et_Password.getText().toString();
                mConfirmPassword = et_confirmPassword.getText().toString();
//                pPresenter.validateCredAndSubmit(profile.get_id(), mPassword, mConfirmPassword);
                break;
        }
    }

}
