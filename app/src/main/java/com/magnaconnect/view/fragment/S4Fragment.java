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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.events.MessagesEvent;
import com.magnaconnect.utils.DialogFactory;
import com.magnaconnect.view.adapter.S4Adapter;
import com.magnaconnect.view.contract.HISMvpView;
import com.magnaconnect.view.model.ProdResponse;
import com.magnaconnect.view.presenter.HISPresenter;
import com.ncorti.slidetoact.SlideToActView;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
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

public class S4Fragment extends BFragment implements HISMvpView {
    private static S4Fragment INSTANCE;
    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;
    @BindView(R.id.scanButton)
    Button scanButton;
    @BindView(R.id.barcodeTV)
    TextView barcodeTV;
    @BindView(R.id.slideToNext)
    SlideToActView slideToNext;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.commentLayout)
    LinearLayout commentLayout;
    @BindView(R.id.commentET)
    EditText commentET;
    AppCompatActivity activity;
    CATEGORY mCategory = CATEGORY.EMPLOYEE;
    HISPresenter presenter;
    FragmentCallback fragmentCallback;
    int counterCount = 0;
    private String TAG = S4Fragment.class.getSimpleName();
    private RecyclerView.Adapter gridAdapter;
    private List<ProdResponse> inventoryList = new ArrayList<>();
    private String masterJson;

    public static S4Fragment newInstance(String masterJson) {
        Bundle b = new Bundle();
        b.putString(MASTER_JSON, masterJson);
        S4Fragment f = new S4Fragment();
        f.setArguments(b);
        return f;
    }

    public static S4Fragment getInstance() {
        return INSTANCE;
    }

    public int getCounterCount() {
        return counterCount;
    }

    public void skipC() {
        gotoNext(true);
    }

    private void gotoNext(boolean isSkip) {
        JSONArray a = new JSONArray();
        JSONObject o = new JSONObject();
        try {
            for (int i = 0; i < S4Fragment.getInstance().inventoryList.size(); i++) {
//                            S4Adapter.DataObjectHolder viewHolder = (S4Adapter.DataObjectHolder) S4Fragment.getInstance().mRecyclerView.findViewHolderForAdapterPosition(i);
                ProdResponse item = S4Fragment.getInstance().inventoryList.get(i);
//                            if (viewHolder.inputET != null && viewHolder.inputET.getText().toString().length() > 0) {
//                                item.setQuantity(Integer.parseInt(viewHolder.inputET.getText().toString()));
//                            } else {
//                                item.setQuantity(0);
//                            }

                if (item.getQuantity() > 0) {
                    counterCount++;
                }

                if (item.getProductName().equalsIgnoreCase("Amaron")) {
                    o.put("Amaron", item.getQuantity());
                }
                if (item.getProductName().equalsIgnoreCase("Okaya")) {
                    o.put("Okaya", item.getQuantity());
                }
                if (item.getProductName().equalsIgnoreCase("Livguard")) {
                    o.put("Livguard", item.getQuantity());
                }
                if (item.getProductName().equalsIgnoreCase("Livfast")) {
                    o.put("Livfast", item.getQuantity());
                }
                if (item.getProductName().equalsIgnoreCase("Others")) {
                    o.put("Others", item.getQuantity());
                }
            }
            o.put("comment", S4Fragment.getInstance().commentET.getText().toString());
            a.put(o);
            JSONObject parentObject = new JSONObject(S4Fragment.getInstance().masterJson);
            parentObject.put("counter", a);
            S4Fragment.getInstance().masterJson = parentObject.toString();
        } catch (Exception e) {
            fcr.recordException(e);
        }
        setFrame(S5Fragment.newInstance(S4Fragment.getInstance().masterJson), false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragment(view, activity, SCREEN_Counter, statusColor, themeColor, true);
        setRecyclerView(view);
    }

    private void setRecyclerView(View view) {
        enableVisible(slideToNext, commentLayout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setHasFixedSize(true);
        gridAdapter = new S4Adapter(activity, inventoryList);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(gridAdapter);
        for (String c : pdb.getListString(Pf_cList)) {
            ProdResponse m = new ProdResponse();
            m.setProductName(c);
            m.setQuantity(0);
            inventoryList.add(m);
        }
        gridAdapter.notifyDataSetChanged();
        ((S4Adapter) gridAdapter).setOnItemClickListener((position, v) -> {
        });
        slideToNext.setOnSlideCompleteListener(view1 -> {
                    hideKeyBoard();
                    slideToNext.resetSlider();
//                    if (S4Fragment.getInstance().commentET.getText().toString().trim().length() == 0) {
//                        showMessageAlert(MESSAGE_ALERT, "Please provide comment and proceed.");
//                        return;
//                    }
                    gotoNext(false);
                }
        );
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
        setCurrentFragment(TAG);
        //int example_argument = getArguments().getInt(ARG_ONE);
        activity = (AppCompatActivity) getActivity();
        INSTANCE = this;
        masterJson = getArguments().getString(MASTER_JSON);
        mCategory = getCFrom(pdb.getString(Pf_cat));
        presenter = new HISPresenter(activity);
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
        fcr.setCustomKey(MESSAGE_ALERT, event.getMessage());
        KLog.d(">>> %s", event.toString());
    }

    @Override
    public void showHeaders(String headers) {
        fcr.setCustomKey(MESSAGE_ALERT, headers);
        //textView_fragment_headers.setText(headers);
    }

    @Override
    public void showError(String error) {
        DialogFactory.createGenericErrorDialog(getActivity(), error).show();
    }

}
