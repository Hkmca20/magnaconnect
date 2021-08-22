/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.presenter;

import android.content.Context;

import com.magnaconnect.network.IServices;
import com.magnaconnect.BApp;
import com.magnaconnect.R;
import com.magnaconnect.di.Presenter;
import com.magnaconnect.utils.Cons;
import com.magnaconnect.utils.Utils;
import com.magnaconnect.view.contract.PLContract;

import javax.inject.Inject;
import javax.inject.Named;

public class PLPresenter implements Presenter<PLContract.View>, Cons {
    String TAG = PLPresenter.class.getSimpleName();
    PLContract.View view;
    Context context;

    @Inject
    @Named("cached")
    IServices IServices;

    @Inject
    public PLPresenter(Context ctx) {
        ((BApp) ctx.getApplicationContext()).getComponent().inject(this);
        this.context = ctx;
    }

    @Override
    public void attachView(PLContract.View mvpView) {
        this.view = mvpView;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void validateSubmit(String mEmail, String mPassword) {
        if (mEmail.isEmpty()) {
            view.showSnackCustom(context.getResources().getString(R.string.email_empty), context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else if (mEmail.isEmpty()) {
            view.showSnackCustom(context.getResources().getString(R.string.email_empty), context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else if (mPassword.isEmpty()) {
            view.showSnackCustom(context.getResources().getString(R.string.password_empty), context.getResources().getString(R.string.SNACKBAR_BTN_TEXT_DISMISS));
        } else {
            if (Utils.isNetworkAvailable(context)) {
//                CallProgressWheel.showLoadingDialog(activity, "");
                if (view.getRememberStatus()) {
                    pdb.putString(Pf_last_login, mEmail);
                    pdb.putString(Pf_last_pass, mPassword);
                } else {
                    pdb.putString(Pf_last_login, "");
                    pdb.putString(Pf_last_pass, "");
                }
            } else {
                view.showMessage(context.getResources().getString(R.string.internet_message));
//                showErrorMessageDialog(context, context.getResources().getString(R.string.error), context.getResources().getString(R.string.internet_message),true, () -> {
//                });
            }
        }
    }
}
