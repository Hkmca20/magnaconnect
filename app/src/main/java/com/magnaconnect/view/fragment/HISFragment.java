/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.events.MessagesEvent;
import com.magnaconnect.utils.DialogFactory;
import com.magnaconnect.utils.Utility;
import com.magnaconnect.view.adapter.HAdapter;
import com.magnaconnect.view.contract.HISMvpView;
import com.magnaconnect.view.model.SpinItem;
import com.magnaconnect.view.presenter.HISPresenter;
import com.google.zxing.integration.android.IntentIntegrator;
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

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.magnaconnect.utils.Utility.closeSession;
public class HISFragment extends BFragment implements HISMvpView {
    private static final String ARG_EXAMPLE = "ARG_EXAMPLE";
    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;
    @BindView(R.id.scanButton)
    Button scanButton;
    @BindView(R.id.barcodeTV)
    TextView barcodeTV;
    AppCompatActivity activity;
    CATEGORY mCategory = CATEGORY.EMPLOYEE;
    private String TAG = HISFragment.class.getSimpleName();
    private ArrayList<SpinItem> gridListFiltered = new ArrayList<>();
    private RecyclerView.Adapter gridAdapter;
    private RecyclerView mRecyclerView;

    public static HISFragment newInstance() {
        HISFragment HISFragment = new HISFragment();
        return HISFragment;
    }

    public static HISFragment newInstance(int example_argument) {
        HISFragment f = new HISFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_EXAMPLE, example_argument);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        String titleString = "";
        switch (getArguments().getInt(ARG_EXAMPLE)) {
            case 1:
                titleString = "Inventory";
                break;
            case 2:
                titleString = "Rewards";
                break;
        }
        initFragment(view, activity, titleString, statusColor, themeColor, true);
        Utility.applyAnim(barcodeTV, 4000, false);
        HISPresenter presenter = new HISPresenter(getActivity());
        presenter.attachView(this);
        presenter.show_headers();
        setRecyclerView(view);
    }

    private void setRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setHasFixedSize(true);
        gridAdapter = new HAdapter(activity, gridListFiltered);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(gridAdapter);
        List<SpinItem> tempList = (ArrayList) pdb.getListObject(PF_GRID_L, SpinItem.class);
        if (tempList != null && tempList.size() > 0) {
            gridListFiltered.addAll(tempList);
        }
        gridAdapter.notifyDataSetChanged();
        ((HAdapter) gridAdapter).setOnItemClickListener(new HAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                try {
                    if (v.getId() == R.id.mSquareView) {
                        showToast("Barcode: " + gridListFiltered.get(position).getTitle());
                    }
                } catch (Exception ex) {
                }
            }
        });

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fcr.setCustomKey(MESSAGE_ALERT, TAG);
        activity = (AppCompatActivity) getActivity();
        setCurrentFragment(TAG);
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
        fcr.setCustomKey(TAG, event.getMessage());
        KLog.d(">>> %s", event.toString());
    }

    @Override
    public void showHeaders(String headers) {
        fcr.setCustomKey(MESSAGE_ALERT, TAG);
        //textView_fragment_headers.setText(headers);
    }

    @Override
    public void showError(String error) {
        DialogFactory.createGenericErrorDialog(getActivity(), error).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != CUSTOMIZED_REQUEST_CODE && requestCode != IntentIntegrator.REQUEST_CODE) {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CUSTOMIZED_REQUEST_CODE: {
//                    IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
//                    if (result.getContents() == null) {
//                        Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show();
//                    } else {
//                        String contents = result.getContents();
//                        String format = result.getFormatName();
//                        barcodeTV.setText("contents: " + contents + "\nformat: " + format);
//                        String date = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a").format(Calendar.getInstance().getTime());
//                        pdb.putString(Pf_last_scan, date);
//                        gridListFiltered.add(new SpinItem(null, null, contents, date, R.drawable.ic_lockscreen_glowdot, R.drawable.ic_lockscreen_glowdot, R.id.action_info));
//                        gridAdapter.notifyDataSetChanged();
//                    }
                    break;
                }
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            showToast("Cancelled");
            barcodeTV.setText("Cancelled");
        }
    }

    @Override
    public void onDestroyView() {
        pdb.putListObject(PF_GRID_L, gridListFiltered);
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
