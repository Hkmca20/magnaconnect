/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.contract;
import com.magnaconnect.view.model.DashResponse;
import com.magnaconnect.view.model.ScnResponse;

public class HContract {

    public interface View {
        //view methods
        void successDashboard(DashResponse data);
        void successScan(ScnResponse data);
        void successScanVerification(String serialNo, ScnResponse data);
        void messageAlert(String title, String msg);
        void errorAlert();
        void startProgress();
        void endProgress();
        void showMessage(String msg);
    }

    interface HomeActionsListener {
        // processing to be done here
        void dashboardApi(String user, String password);

    }
}
