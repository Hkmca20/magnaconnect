/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.events.MessagesEvent;
import com.magnaconnect.view.contract.S6Contract;
import com.magnaconnect.view.model.ProdResponse;
import com.magnaconnect.view.presenter.S6Presenter;
import com.ncorti.slidetoact.SlideToActView;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;

public class S6Fragment extends BFragment implements S6Contract.View {
    private static S6Fragment INSTANCE;
    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;
    @BindView(R.id.productCountTV)
    TextView productCountTV;
    @BindView(R.id.counterCountTV)
    TextView counterCountTV;
    @BindView(R.id.marketingCountTV)
    TextView marketingCountTV;
    @BindView(R.id.slideToNext)
    SlideToActView slideToNext;
    AppCompatActivity activity;
    CATEGORY mCategory = CATEGORY.EMPLOYEE;
    S6Presenter presenter;
    FragmentCallback fragmentCallback;
    String masterJson;
    private String TAG = S6Fragment.class.getSimpleName();

    public static S6Fragment newInstance(String masterJson) {
        Bundle b = new Bundle();
        b.putString(MASTER_JSON, masterJson);
        S6Fragment f = new S6Fragment();
        f.setArguments(b);
        return f;
    }

    public static S6Fragment getInstance() {
        return INSTANCE;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragment(view, activity, SCREEN_Summary, statusColor, themeColor, true);
        setLayout();
    }

    private void setLayout() {
        productCountTV.setText(String.valueOf(S3Fragment.getInstance().selectedProductCount()));
        counterCountTV.setText(String.valueOf(S4Fragment.getInstance().getCounterCount()));
        marketingCountTV.setText(String.valueOf(S5Fragment.getInstance().getMarketingCount()));
        enableVisible(slideToNext);
        slideToNext.setText(SWIPE_TO);
        slideToNext.setOnSlideCompleteListener(view1 -> {
                    slideToNext.resetSlider();
                    presenter.esubJson(S6Fragment.getInstance().masterJson);
                }
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategory = getCFrom(pdb.getString(Pf_cat));
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            fcr.setCustomKey(MESSAGE_ALERT, TAG);
            fragmentCallback = (FragmentCallback) activity;
            fragmentCallback.Update(TAG, 101);
        } catch (ClassCastException e) {
            fcr.recordException(e);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setCurrentFragment(TAG);
        //int example_argument = getArguments().getInt(ARG_ONE);
        activity = (AppCompatActivity) getActivity();
        INSTANCE = this;
        masterJson = getArguments().getString(MASTER_JSON);
        presenter = new S6Presenter(activity);
        presenter.attachView(this);
        return inflater.inflate(R.layout.fr_s6, container, false);
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void successSubmit(List<ProdResponse> data) {
        if (data.size() > 0) {
            ProdResponse item = data.get(0);
            if (item.getStatus().equalsIgnoreCase(MESSAGE_OK)) {
                showToast(item.getMessage());
                showErrorMessageDialog(activity, MESSAGE_ALERT, "Submitted successfully!", false, () -> {
                    FragmentManager fm = activity.getSupportFragmentManager();
                    for (int i = fm.getBackStackEntryCount(); i > 0; i--) {
                        fm.popBackStack();
                    }
                });
            } else {
                showMessage("->" + item.getMessage());
                FragmentManager fm = activity.getSupportFragmentManager();
                for (int i = fm.getBackStackEntryCount(); i > 0; i--) {
                    fm.popBackStack();
                }
            }
        }
    }

    @Override
    public void messageAlert(String title, String msg) {
        showMessageAlert(title, msg);
    }

    @Override
    public void errorAlert() {
        showErrorAlert();
    }

    @Override
    public void resetSubmitButton() {
        slideToNext.resetSlider();
    }

    @Override
    public void startProgress() {
        showProgressD("Please wait...");
    }

    @Override
    public void endProgress() {
        hideProgressD();
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }
}
