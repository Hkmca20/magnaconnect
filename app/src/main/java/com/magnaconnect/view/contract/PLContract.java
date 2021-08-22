/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.contract;

import com.magnaconnect.view.model.VerifyResponse;

public class PLContract {

    public interface View {
        //view methods
        void success(VerifyResponse data);
        void error(String msg);
        void startProgress();
        void endProgress();
        void showMessage(String msg);
        void showMessageT(String msg);
        void showSnackCustom(String msg, String action);
        void internetError();
        boolean getRememberStatus();
    }

    interface PreLoginActionsListener {
        // processing to be done here

    }
}
