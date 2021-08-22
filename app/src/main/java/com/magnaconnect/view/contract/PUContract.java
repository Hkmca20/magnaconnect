/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.contract;

import android.content.Context;

import com.magnaconnect.view.model.AttResponse;
import com.magnaconnect.view.model.ScanRequest;
import com.magnaconnect.view.model.VerifyResponse;

public class PUContract {

    public interface View {
        //view methods
        void successAtt(AttResponse item);

        void successAttSave(VerifyResponse data);

        void messageAlert(String title, String msg);

        Context getCtx();

        void errorAlert();

        void startProgress();

        void endProgress();

        void showMessage(String msg);
    }

    interface HomeActionsListener {
        // processing to be done here
        void dashboardApi(String user, String password);

        void empSubmitAttendance(ScanRequest request);

    }
}
