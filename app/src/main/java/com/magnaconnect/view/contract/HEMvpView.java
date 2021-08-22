/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.contract;

import android.content.Context;

import com.magnaconnect.view.model.ProdResponse;
import com.magnaconnect.view.fragment.MvpView;
import com.magnaconnect.view.model.AttResponse;

import java.util.List;

public interface HEMvpView extends MvpView {

    public interface View {
        //view methods
        void successAtt(List<AttResponse> attendanceList);
        void successEmpHome(List<ProdResponse.Dashboard> data);
        void messageAlert(String title, String msg);
        void errorAlert();
        void openPunchIn(PUNCH status);
        void startProgress();
        void endProgress();
        void showMessage(String msg);
        Context getContext();
    }

    interface HomeActionsListener {
        // processing to be done here
//        void dbo(String user, String password);
        void showHeaders(String headers);

    }
}