/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */

package com.magnaconnect.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.events.MessagesEvent;
import com.magnaconnect.view.adapter.HEAdapter;
import com.magnaconnect.view.contract.HEMvpView;
import com.magnaconnect.view.model.AttResponse;
import com.magnaconnect.view.model.ProdResponse;
import com.magnaconnect.view.presenter.HEPresenter;
import com.google.android.material.navigation.NavigationView;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static com.magnaconnect.utils.Utility.closeSession;

public class HEFragment extends BFragment implements HEMvpView.View, NavigationView.OnNavigationItemSelectedListener {
    private static HEFragment INSTANCE;
    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;
    @BindView(R.id.scanButton)
    Button scanButton;
    @BindView(R.id.barcodeTV)
    TextView barcodeTV;
    @BindView(R.id.logoIV)
    ImageView logoIV;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private AppCompatActivity activity;
    private CATEGORY mCategory = CATEGORY.EMPLOYEE;
    private HEPresenter presenter;
    private FragmentCallback fragmentCallback;
    private List<AttResponse> attedHistoryList;
    private MenuItem menu_att_punch, nav_att_history;
    private String TAG = HEFragment.class.getSimpleName();
    private RecyclerView.Adapter homeEmpAdapter;
    private List<ProdResponse.Dashboard> homeList = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private View rootView;

    public static HEFragment newInstance() {
        HEFragment historyFragment = new HEFragment();

        return historyFragment;
    }

    public static HEFragment getInstance() {
        return INSTANCE;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        initFragment(view, activity, mCategory.toString(), statusColor, themeColor, false);
        navigationDrawerSetup();
        setLayout();
        CallED();
    }

    public void CallED() {
        fcr.log(getUser());
        presenter.show_headers();
//        if (getPFrom(getPU()) == PUNCH.IN) {
            presenter.edbo(getUser());
//        } else {
//            presenter.empgetatt(getUser());
//        }
    }

    private void setLayout() {
        enableVisible(logoIV);
        setMargins(mRecyclerView, 4, 4, 4, 4);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setHasFixedSize(true);
        homeEmpAdapter = new HEAdapter(activity, homeList);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(homeEmpAdapter);
        ((HEAdapter) homeEmpAdapter).setOnItemClickListener((position, v) -> {
            if (v.getId() == R.id.mSquareView) {
                switch (position) {
                    case 0:
                        setFrame(S2Fragment.newInstance(homeList.get(position).getItem()), false);
                        break;
                    case 2:
                        setFrame(DLFragment.newInstance(), false);
                        break;
                    case 1:
                    case 3:
                        messageAlert(MESSAGE_ALERT, "Coming soon!");
                        break;
                    case 4:
                        messageAlert(MESSAGE_ALERT, "Under development!");
                        break;
                    case 5:
                        break;
                }
            }
        });
    }

    private void navigationDrawerSetup() {
//        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        try {
            Toolbar toolbar = rootView.findViewById(R.id.toolbar);
            drawerLayout = rootView.findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    activity, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.setDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = rootView.findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setItemIconTintList(null);
            View headerView = navigationView.getHeaderView(0);
            TextView userNameTV = headerView.findViewById(R.id.userNameTV);
            drawerLayout.setScrimColor(Color.TRANSPARENT);
            Menu nav_menu = navigationView.getMenu();
            if (nav_menu != null) {
                nav_menu.findItem(R.id.action_Admin).setVisible(true);
                nav_att_history = nav_menu.findItem(R.id.nav_att_history);
                menu_att_punch = nav_menu.findItem(R.id.nav_att_punch);
                nav_menu.findItem(R.id.nav_logout).setVisible(true);
                userNameTV.setText(getName() + "\n\n(" + getUser() + ")");
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_att_punch:
                setFrame(PUFragment.newInstance(PUNCH.IN), false);
                return true;
            case R.id.nav_att_history:
                if (attedHistoryList != null && attedHistoryList.size() > 0) {
                    setFrame(ATHFragment.newInstance(attedHistoryList), false);
                } else {
                    showMessageAlert(MESSAGE_ALERT, "Attendance history not found!");
                }
                return true;
            case R.id.nav_login:
                setFrame(STFragment.newInstance(), false);
                return true;
            case R.id.nav_logout:
                closeSession(activity);
                return true;
            case R.id.nav_6:
                return true;
        }
        return true;
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
    public Context getContext() {
        return activity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setCurrentFragment(TAG);
        //int example_argument = getArguments().getInt(ARG_ONE);
        activity = (AppCompatActivity) getActivity();
        INSTANCE = this;
        mCategory = getCFrom(pdb.getString(Pf_cat));
        presenter = new HEPresenter(activity);
        presenter.attachView(this);
        return inflater.inflate(R.layout.fr_he, container, false);
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
        KLog.d("-->>> %s", event.toString());
    }

    public void showHeaders(String headers) {
        fcr.setCustomKey(MESSAGE_ALERT, headers);
        //textView_fragment_headers.setText(headers);
    }

    @Override
    public void successAtt(List<AttResponse> attendanceList) {
        attedHistoryList = new ArrayList<>();
        attedHistoryList.addAll(attendanceList);
        String status = attendanceList.get(attendanceList.size() - 1).getStatus();
        if (status.equalsIgnoreCase(PUNCH.IN.toString())) {
            presenter.edbo(getUser());
        } else {
            switch (HEFragment.getInstance().getPFrom(status)) {
                case OUT:
                    openPunchIn(PUNCH.OUT);
                    break;
                case MEETING:
                    openPunchIn(PUNCH.MEETING);
                    break;
                default:
                    openPunchIn(PUNCH.OTHER);
                    break;
            }
        }
    }

    @Override
    public void successEmpHome(List<ProdResponse.Dashboard> data) {
        KLog.d(">>> %s", "response product size = " + data.size());
        homeList.clear();
        homeList.addAll(data);
        homeEmpAdapter.notifyDataSetChanged();
        nav_att_history.setVisible(true);
        menu_att_punch.setVisible(true);
    }

    @Override
    public void messageAlert(String title, String msg) {
        showMessageAlert(title, msg);
    }

    @Override
    public void errorAlert() {
        showErrorAlert();

//        DialogFactory.createGenericErrorDialog(getActivity(), error).show();
    }

    @Override
    public void openPunchIn(PUNCH status) {
        setFrame(PUFragment.newInstance(status), true);
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
