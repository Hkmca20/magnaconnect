/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.contract;

import com.magnaconnect.view.model.VerifyResponse;

public class PContract {
    public interface View {
        //view methods

        void success(VerifyResponse data);

        void error(String msg, String action);

        void startProgress();

        void endProgress();

        void showMessage(String msg);

        void showSnack(String msg, String action);
    }

    interface LoginActionsListener {
        // processing to be done here

        void password(String userId, String password);

        void validateCredAndSubmit(String userId, String password, String confirmPassword);

    }
}
