/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.events.MessagesEvent;
import com.magnaconnect.utils.DialogFactory;
import com.magnaconnect.view.contract.SMvpView;
import com.magnaconnect.view.presenter.STPresenter;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class STFragment extends BFragment implements SMvpView {
    private String TAG = STFragment.class.getSimpleName();
    @BindView(R.id.categoryTV)
    TextView categoryTV;
    @BindView(R.id.logoIV)
    ImageView logoIV;
    private AppCompatActivity activity;

    public static STFragment newInstance() {
        STFragment STFragment = new STFragment();
        return STFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragment(view, activity, SCREEN_STATUS, statusColor, themeColor, false);
        STPresenter presenter = new STPresenter(getActivity());
        presenter.attachView(this);
        presenter.show_headers();
        enableVisible(logoIV);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fcr.setCustomKey(MESSAGE_ALERT, TAG);
        activity = (AppCompatActivity) getActivity();
        setCurrentFragment(TAG);
        return inflater.inflate(R.layout.fr_st, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onEvent(MessagesEvent event) {
        fcr.setCustomKey(MESSAGE_ALERT, event.getMessage());
        KLog.d(">>> %s", event.toString());
    }

    @Override
    public void showHeaders(String headers) {
        fcr.setCustomKey(MESSAGE_ALERT, headers);
        categoryTV.setText(headers);
    }

    @Override
    public void showError(String error) {
        try {
            DialogFactory.createGenericErrorDialog(getActivity(), error).show();
        } catch (Exception ex) {
            KLog.e(ex);
        }
    }

    @OnClick({R.id.categoryButton1, R.id.categoryButton2, R.id.categoryButton3,R.id.analogClock})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.categoryButton1:
                setFrame(LFragment.newInstance(CATEGORY.EMPLOYEE, ""), false);
                break;
            case R.id.categoryButton2:
                setFrame(LFragment.newInstance(CATEGORY.BUSINESS_ASSOCIATE, ""), false);
                break;
            case R.id.categoryButton3:
                setFrame(LFragment.newInstance(CATEGORY.BUSINESS_PARTNER, ""), false);
                break;
            case R.id.analogClock:
                String date = sdf.format(Calendar.getInstance().getTime());
                showToast(date);
                break;
            default:
                break;
        }
    }
}
