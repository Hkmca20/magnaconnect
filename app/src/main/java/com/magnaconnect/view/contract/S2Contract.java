/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.contract;
import com.magnaconnect.view.model.ProdResponse;
import com.magnaconnect.view.model.VerifyResponse;

import java.util.List;

public class S2Contract {

    public interface View {
        //view methods
        void successDealerRes(List<VerifyResponse> data);
        void successProduct(List<ProdResponse> data);
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
