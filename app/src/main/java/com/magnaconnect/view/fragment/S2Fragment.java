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
import com.magnaconnect.view.adapter.S2Adapter;
import com.magnaconnect.view.contract.S2Contract;
import com.magnaconnect.view.model.ProdResponse;
import com.magnaconnect.view.model.VerifyResponse;
import com.magnaconnect.view.presenter.S2Presenter;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class S2Fragment extends BFragment implements S2Contract.View {
    private static S2Fragment INSTANCE;
    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;
    @BindView(R.id.scanButton)
    Button scanButton;
    @BindView(R.id.barcodeTV)
    TextView barcodeTV;
    @BindView(R.id.logoIV)
    ImageView logoIV;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    AppCompatActivity activity;
    CATEGORY mCategory = CATEGORY.EMPLOYEE;
    S2Presenter presenter;
    FragmentCallback fragmentCallback;
    private String TAG = S2Fragment.class.getSimpleName();
    private RecyclerView.Adapter gridAdapter;
    private List<ProdResponse> inventoryList = new ArrayList<>();
    private String masterJson;

    public static S2Fragment newInstance(String masterJson) {
        Bundle b = new Bundle();
        b.putString(MASTER_JSON, masterJson);
        S2Fragment f = new S2Fragment();
        f.setArguments(b);
        return f;
    }

    public static S2Fragment getInstance() {
        return INSTANCE;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragment(view, activity, SCREEN_Select, statusColor, themeColor, true);
        setLayout();
        CallEDL();
    }

    public void CallEDL() {
        presenter.edl(getUser());
    }

    private void setLayout() {
        setMargins(mRecyclerView, 4, 4, 4, 4);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setHasFixedSize(true);
        gridAdapter = new S2Adapter(activity, inventoryList);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
        ((S2Adapter) gridAdapter).setOnItemClickListener((i, v) -> {
            switch (v.getId()) {
                case R.id.card_view:
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("dlrId", inventoryList.get(i).getId());
                    } catch (JSONException e) {
                        fcr.recordException(e);
                    }
                    masterJson = jsonObject.toString();
                    setFrame(S3Fragment.newInstance(masterJson), false);
                    break;
                case R.id.locationLayout:
                    Nav(inventoryList.get(i).getLat(), inventoryList.get(i).getLog());
                    break;
                case R.id.callLayout:
                    Call(inventoryList.get(i).getMobileNumber());
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
            fcr.recordException(e);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setCurrentFragment(TAG);
        activity = (AppCompatActivity) getActivity();
        INSTANCE = this;
        masterJson = getArguments().getString(MASTER_JSON);
        mCategory = getCFrom(getCat());
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
        fcr.setCustomKey(MESSAGE_ALERT, TAG);
        KLog.d(">>> %s", event.toString());
    }

    @Override
    public void successDealerRes(List<VerifyResponse> data) {
        fcr.setCustomKey(TAG, "sendData");
    }

    @Override
    public void successProduct(List<ProdResponse> data) {
        inventoryList.clear();
        inventoryList.addAll(data);
        gridAdapter.notifyDataSetChanged();
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
