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
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.events.MessagesEvent;
import com.magnaconnect.utils.DialogFactory;
import com.magnaconnect.view.adapter.TAdapter;
import com.magnaconnect.view.contract.HISMvpView;
import com.magnaconnect.view.model.DashResponse;
import com.magnaconnect.view.presenter.HISPresenter;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.magnaconnect.utils.Utility.closeSession;
import static com.magnaconnect.utils.Utility.notNullParams;

public class TFragment extends BFragment implements HISMvpView {
    private static final String ARG_TITLE = "ARG_TITLE";
    private static final String TITLE = "title";
    private static final String ICON = "icon";
    static TFragment INSTANCE;
    private static String TAG = TFragment.class.getSimpleName();
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
    int position = 0;
    ListPopupWindow pw;
    List<HashMap<String, Object>> data = new ArrayList<>();
    HashMap<String, Object> map = new HashMap<>();
    String titleString = "";
    private RecyclerView.Adapter mAdapter;
    private ArrayList<DashResponse.Tertiary> tList = new ArrayList<>();
    private ArrayList<DashResponse.Tertiary> sList = new ArrayList<>();
    private ArrayList<String> tMenuList = new ArrayList<>();
    private View rootView;
    private TextView titleTextView;

    public static TFragment newInstance(List<DashResponse.Tertiary> t, int position) {
        TFragment historyFragment = new TFragment();
        historyFragment.position = position;
        return historyFragment;
    }

    public static TFragment newInstance(int arg, List<DashResponse.Tertiary> tList, ArrayList<String> tMenuList) {
        TFragment f = new TFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TITLE, arg);
        f.setArguments(args);
        f.tList.addAll(tList);
        f.sList.addAll(tList);
        f.tMenuList.addAll(tMenuList);
        return f;
    }

    public static TFragment getInstance() {
        return INSTANCE;
    }

    public void showListMenu() {
        View anchors = rootView.findViewById(R.id.ac_type_t);
        pw = new ListPopupWindow(activity);
        ListAdapter adapter = new SimpleAdapter(activity, data, R.layout.pop_win,
                new String[]{TITLE, ICON},
                // The view ids to map the data to
                new int[]{R.id.shoe_select_text, R.id.shoe_select_icon});
        pw.setAnchorView(anchors);
        pw.setAdapter(adapter);
        pw.setWidth(400);
        pw.setOnItemClickListener((parent, view, position, id) -> {
            try {
                sList.clear();
                tList.clear();
                tMenuList.clear();
                tList.addAll(HFragment.getInstance().gettList());
                tMenuList.addAll(HFragment.getInstance().gettMenuList());
                if (position == 0) {
                    titleTextView.setText(titleString);
                    sList.addAll(tList);
                    return;
                }
                for (int i = 0; i < tList.size(); i++) {
                    if (notNullParams(tList.get(i).getCattype()) && tList.get(i).getCattype().equalsIgnoreCase(tMenuList.get(position - 1))) {
                        sList.add(tList.get(i));
                    }
                }
                titleTextView.setText(titleString + " / [" + tMenuList.get(position - 1)
                        + ":" + sList.size() + "]");
            } catch (Exception e) {
                sList.addAll(tList);
                titleTextView.setText(titleString);
            } finally {
                activity.runOnUiThread(() -> mAdapter.notifyDataSetChanged());
                pw.dismiss();
            }
        });
        pw.show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;

        switch (getArguments().getInt(ARG_TITLE)) {
            case 1:
                titleString = "Inventory";
                break;
            case 2:
                titleString = "Rewards - [" + tList.size() + "]";
                break;
        }
        initFragment(view, activity, titleString, statusColor, themeColor, true);
        titleTextView = rootView.findViewById(R.id.tv_ToolbarTitle);
        presenter.show_headers();
        setLayout();
    }

    private void setLayout() {
        setMargins(mRecyclerView, 4, 4, 4, 10);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new TAdapter(activity, sList);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        ((TAdapter) mAdapter).setOnItemClickListener((position, v) -> {
            try {
                if (v.getId() == R.id.mSquareView) {
                    showToast("Barcode: " + tList.get(position).getSerialNo());
                }
            } catch (Exception ex) {
                fcr.recordException(ex);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //int example_argument = getArguments().getInt(ARG_TITLE);
        activity = (AppCompatActivity) getActivity();
        fcr.setCustomKey(MESSAGE_ALERT, TAG);
        INSTANCE = this;
        setCurrentFragment(TAG);
        mCategory = getCFrom(getCat());
//        if (getArguments() != null) {
//            tList = getArguments().getParcelableArrayList(ARG_TERTIARY_LIST);
//            this.position = getArguments().getInt("KEY_POSITION");
//        }
        presenter = new HISPresenter(activity);
        presenter.attachView(this);
        map.put(TITLE, "All type");
        map.put(ICON, R.drawable.ic_camera_24dp);
        data.add(map);
        for (int i = 0; i < tMenuList.size(); i++) {
            map = new HashMap<>();
            map.put(TITLE, tMenuList.get(i));
            map.put(ICON, R.drawable.ic_camera_24dp);
            data.add(map);
        }
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
        fcr.setCustomKey(MESSAGE_ALERT, event.toString());
        KLog.d(">>> %s", event.toString());
    }

    @Override
    public void showHeaders(String headers) {
        fcr.setCustomKey(MESSAGE_ALERT, headers);
        barcodeTV.setText(headers);
    }

    @Override
    public void showError(String error) {
        DialogFactory.createGenericErrorDialog(getActivity(), error).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CUSTOMIZED_REQUEST_CODE: {
                    break;
                }
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            barcodeTV.setText("Cancelled");
            showToast(barcodeTV.getText().toString());
        }
    }

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
