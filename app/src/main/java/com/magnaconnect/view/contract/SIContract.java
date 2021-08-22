/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.contract;

import com.magnaconnect.view.model.StatResponse;
import com.magnaconnect.view.model.VerifyResponse;

import java.util.List;

public class SIContract {

    public interface View {
        //view methods
        void successCity(List<StatResponse.StateItem> spinnerData);
        void successRegistration(VerifyResponse data);
        void errorAlert();
        void messageAlert(String title, String msg);
        void startProgress();
        void endProgress();
        void showMessage(String msg);
        void showSnackCustom(String msg, String action);
        void showOtpDialog();
    }

    interface SignupActionsListener {
        // processing to be done here
        void signup(String fullName, String email, String mobileNumber, String password);

        void validateSubmit(String mFullName, String mEmail, String mMobileNumber, String mPassword);

    }
}
