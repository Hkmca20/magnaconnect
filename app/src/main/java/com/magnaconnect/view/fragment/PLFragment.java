/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.magnaconnect.R;
import com.magnaconnect.view.contract.PLContract;
import com.magnaconnect.view.model.VerifyResponse;
import com.magnaconnect.view.presenter.PLPresenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class PLFragment extends BFragment implements PLContract.View {
    @BindView(R.id.gotoLoginBTN)
    Button gotoLoginBTN;
    @BindView(R.id.skipBTN)
    Button skipBTN;
    PLPresenter PLPresenter;
    AppCompatActivity activity;
    View rootView;
    private CATEGORY mCategory;

    public static PLFragment newInstance() {
        PLFragment PLFragment = new PLFragment();
        return PLFragment;
    }

    public static PLFragment newInstance(CATEGORY category_argument) {
        PLFragment f = new PLFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        f.mCategory = category_argument;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (AppCompatActivity) getActivity();
        PLPresenter = new PLPresenter(activity);
        PLPresenter.attachView(this);
        return inflater.inflate(R.layout.fr_pl, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
    }

    @Override
    public void success(VerifyResponse data) {

    }

    @Override
    public void error(String msg) {

    }

    @Override
    public void startProgress() {

    }

    @Override
    public void endProgress() {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showMessageT(String msg) {

    }

    @Override
    public void showSnackCustom(String msg, String action) {

    }

    @Override
    public void internetError() {

    }

    @Override
    public boolean getRememberStatus() {
        return false;
    }

    @OnClick({R.id.gotoLoginBTN, R.id.gotoSignupBTN, R.id.skipBTN})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gotoLoginBTN:
                setFrame(LFragment.newInstance(mCategory, ""), true);
                break;
            case R.id.gotoSignupBTN:
                setFrame(new SIFragment(), false);
                break;
            case R.id.skipBTN:
                setFrame(HISFragment.newInstance(), true);
                pdb.putString(Pf_first_time, "1");
                pdb.putString(Pf_cat, mCategory.toString());
                showMessage("Login Successful!");
                break;
        }
    }
}
