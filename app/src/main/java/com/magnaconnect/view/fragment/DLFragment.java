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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.events.MessagesEvent;
import com.magnaconnect.view.adapter.DLAdapter;
import com.magnaconnect.view.contract.S2Contract;
import com.magnaconnect.view.model.ProdResponse;
import com.magnaconnect.view.presenter.S2Presenter;
import com.magnaconnect.view.model.VerifyResponse;
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

public class DLFragment extends BFragment implements S2Contract.View {
    private static DLFragment INSTANCE;
    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;
    @BindView(R.id.scanButton)
    Button scanButton;
    @BindView(R.id.barcodeTV)
    TextView barcodeTV;
    @BindView(R.id.logoIV)
    ImageView logoIV;
    AppCompatActivity activity;
    CATEGORY mCategory = CATEGORY.EMPLOYEE;
    S2Presenter presenter;
    FragmentCallback fragmentCallback;
    private String TAG = DLFragment.class.getSimpleName();
    private RecyclerView.Adapter adapter;
    private RecyclerView mRecyclerView;
    private List<VerifyResponse> dealerList = new ArrayList<>();

    public static DLFragment newInstance() {
        Bundle b = new Bundle();
        DLFragment f = new DLFragment();
        f.setArguments(b);
        return f;
    }

    public static DLFragment getInstance() {
        return INSTANCE;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragment(view, activity, SCREEN_Dealer, statusColor, themeColor, true);
        setRecyclerView(view);
        CallDL();
    }

    public void CallDL() {
        presenter.dlList(getUser());
    }

    private void setRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        setMargins(mRecyclerView, 4, 4, 4, 4);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setHasFixedSize(true);
        adapter = new DLAdapter(activity, dealerList);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ((DLAdapter) adapter).setOnItemClickListener((position, v) -> {
            switch (v.getId()) {
                case R.id.card_view:
                    break;
                case R.id.locationLayout:
                    Nav(dealerList.get(position).getLat(), dealerList.get(position).getLog());
                    break;
                case R.id.callLayout:
                    Call(dealerList.get(position).getMobileNumber());
                    break;
            }
        });
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            fcr.setCustomKey(MESSAGE_ALERT, TAG);
            fragmentCallback = (FragmentCallback) activity;
            fragmentCallback.Update(TAG, 101);
        } catch (ClassCastException e) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (AppCompatActivity) getActivity();
        INSTANCE = this;
        setCurrentFragment(TAG);
        mCategory = getCFrom(pdb.getString(Pf_cat));
        presenter = new S2Presenter(activity);
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
    public void successProduct(List<ProdResponse> data) {
    }
    @Override
    public void successDealerRes(List<VerifyResponse> data) {
        dealerList.clear();
        dealerList.addAll(data);
        adapter.notifyDataSetChanged();
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

    public void OpADLFragment() {
        setFrame(ADLFragment.newInstance(), false);
    }
}
