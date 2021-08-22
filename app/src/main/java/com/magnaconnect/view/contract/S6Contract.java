/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.contract;

import com.magnaconnect.view.model.ProdResponse;

import java.util.List;

public class S6Contract {

    public interface View {
        //view methods
        void successSubmit(List<ProdResponse> data);
        void messageAlert(String title, String msg);
        void errorAlert();
        void resetSubmitButton();
        void startProgress();
        void endProgress();
        void showMessage(String msg);
    }

    interface HomeActionsListener {
        // processing to be done here
        void dashboardApi(String user, String password);

    }
}
