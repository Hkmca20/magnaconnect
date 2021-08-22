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
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.events.MessagesEvent;
import com.magnaconnect.utils.DialogFactory;
import com.magnaconnect.view.adapter.HDAdapter;
import com.magnaconnect.view.contract.HISMvpView;
import com.magnaconnect.view.presenter.HISPresenter;
import com.magnaconnect.view.model.DashResponse;
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
import butterknife.OnClick;

import static com.magnaconnect.utils.Utility.closeSession;

public class HDFragment extends BFragment implements HISMvpView {
    private static final String ARG_ONE = "ARG_ONE";
    private static HDFragment INSTANCE;
    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;
    @BindView(R.id.scanButton)
    Button scanButton;
    @BindView(R.id.barcodeTV)
    TextView barcodeTV;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    AppCompatActivity activity;
    CATEGORY mCategory = CATEGORY.EMPLOYEE;
    HISPresenter presenter;
    FragmentCallback fragmentCallback;
    private String TAG = HDFragment.class.getSimpleName();
    private RecyclerView.Adapter gridAdapter;
    private List<DashResponse.Inventory> inventoryList = new ArrayList<>();

    public static HDFragment newInstance() {
        HDFragment historyFragment = new HDFragment();
        return historyFragment;
    }

    public static HDFragment getInstance() {
        return INSTANCE;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String titleString = "";
        switch (getArguments().getInt(ARG_ONE)) {
            case 1:
                titleString = "Inventory";
                break;
            case 2:
                titleString = "Rewards";
                break;
        }
        initFragment(view, activity, titleString, statusColor, themeColor, true);
        presenter.show_headers();
        setRecyclerView();
    }

    private void setRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setHasFixedSize(true);
        gridAdapter = new HDAdapter(activity, inventoryList);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
        ((HDAdapter) gridAdapter).setOnItemClickListener((position, v) -> {
            try {
                if (v.getId() == R.id.mSquareView) {
                    showToast("Barcode: " + inventoryList.get(position).getSerialNo());
                }
            } catch (Exception ex) {
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
        presenter = new HISPresenter(activity);
        presenter.attachView(this);
        mCategory = getCFrom(pdb.getString(Pf_cat));
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
    public void showHeaders(String headers) {
        //textView_fragment_headers.setText(headers);
    }

    @Override
    public void showError(String error) {
        DialogFactory.createGenericErrorDialog(getActivity(), error).show();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode != CUSTOMIZED_REQUEST_CODE && requestCode != IntentIntegrator.REQUEST_CODE) {
//            // This is important, otherwise the result will not be passed to the fragment
//            super.onActivityResult(requestCode, resultCode, data);
//            return;
//        }
//
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case CUSTOMIZED_REQUEST_CODE: {
//                    IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
//                    if (result.getContents() == null) {
//                        Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show();
//                    } else {
//                        String contents = result.getContents();
//                        String format = result.getFormatName();
//                        barcodeTV.setText("contents: " + contents + "\nformat: " + format);
//                        Log.i("xZing", "contents: " + contents + " format: " + format);
//
//                        String date = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a").format(Calendar.getInstance().getTime());
//                        pdb.putString(Pf_last_scan, date);
//                    }
//                    break;
//                }
//                default:
//                    break;
//            }
//        } else if (resultCode == RESULT_CANCELED) {
//            Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show();
//            barcodeTV.setText("Cancelled");
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.scanButton, R.id.logoutBTN})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.scanButton:
                break;
            case R.id.logoutBTN:
                closeSession(activity);
                break;
        }
    }
}
