/*
 * Created by Hariom.Gupta on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the Activity to be memorised in the
 * correct component.
 */
package com.magnaconnect.view.contract;

import com.magnaconnect.utils.Cons;
import com.magnaconnect.view.model.VerifyResponse;

public class LContract {

    public interface View extends Cons {
        //view methods
        void successLogin(String password);

        void errorAlert();

        void messageAlert(String title, String msg);

        void startProgress();

        void endProgress();

        void showMessage(String msg);

        void showSnackCustom(String msg, String action);

        boolean getRememberStatus();

        void userNotFound(VerifyResponse response);
//        void setProfile(Profile profile);
    }

    interface LoginActionsListener {
        // processing to be done here
        void login(String user, String password);

        void setJWTProfile(String token);

        void validateSubmit(String mEmail, String mPassword);

    }
}
