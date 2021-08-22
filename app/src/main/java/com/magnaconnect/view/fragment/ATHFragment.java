/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magnaconnect.R;
import com.magnaconnect.events.MessagesEvent;
import com.magnaconnect.view.adapter.ATHAdapter;
import com.magnaconnect.view.contract.ATHContract;
import com.magnaconnect.view.model.AttResponse;
import com.magnaconnect.view.presenter.ATHPresenter;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class ATHFragment extends BFragment implements ATHContract.View {
    private static ATHFragment INSTANCE;
    AppCompatActivity activity;
    ATHPresenter presenter;
    FragmentCallback fragmentCallback;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private String TAG = ATHFragment.class.getSimpleName();
    private RecyclerView.Adapter mAdapter;
    private List<AttResponse> attendList = new ArrayList<>();

    public static ATHFragment newInstance(List<AttResponse> attendList) {
        Bundle b = new Bundle();
        ATHFragment f = new ATHFragment();
        f.setArguments(b);
        f.attendList.addAll(attendList);
        return f;
    }

    public static ATHFragment getInstance() {
        return INSTANCE;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragment(view, activity, SCREEN_Attend, statusColor, themeColor, true);
        setLayout();
        CallEmpDealerService();
    }

    public void CallEmpDealerService() {
        presenter.empgetatt(getUser());
    }

    private void setLayout() {
        setMargins(mRecyclerView, 4, 4, 4, 4);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ATHAdapter(activity, attendList);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
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
        activity = (AppCompatActivity) getActivity();
        INSTANCE = this;
        presenter = new ATHPresenter(activity);
        presenter.attachView(this);
        return inflater.inflate(R.layout.fr_h, container, false);
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
        KLog.d(">>> %s", event.toString());
    }

    @Override
    public void successAttendance(List<AttResponse> data) {
        attendList.clear();
        attendList.addAll(data);
        mAdapter.notifyDataSetChanged();
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
