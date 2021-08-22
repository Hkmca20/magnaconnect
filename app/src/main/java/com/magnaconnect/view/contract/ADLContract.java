/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.contract;

import com.magnaconnect.view.model.StatResponse;
import com.magnaconnect.view.model.VerifyResponse;

import java.util.List;

public class ADLContract {

    public interface View {
        //view methods
        void successDistributor(List<VerifyResponse> stateData);

        void successCity(List<StatResponse.StateItem> stateData);

        void successPartnerT(List<StatResponse.PartnerTypeItem> partnerData);

        void numberVerifiedRes(VerifyResponse body);

        void successDealerRes(VerifyResponse data);

        void errorAlert();

        void messageAlert(String title, String msg);

        void startProgress();

        void endProgress();

        void showMessage(String msg);

        void showSnackCustom(String msg, String action);

        String getUUID();
    }

    interface AddDealerActionsListener {
        // processing to be done here
        void signup(String fullName, String email, String mobileNumber, String password);

        void validateSubmit(String mFullName, String mEmail, String mMobileNumber, String mPassword);

    }
}
