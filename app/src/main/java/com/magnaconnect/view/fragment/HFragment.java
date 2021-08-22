/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.receiver.ObservableObject;
import com.magnaconnect.utils.CircleTransform;
import com.magnaconnect.utils.Cons;
import com.magnaconnect.view.contract.HContract;
import com.magnaconnect.view.model.DashResponse;
import com.magnaconnect.view.model.ScnResponse;
import com.magnaconnect.view.model.StatResponse;
import com.magnaconnect.view.presenter.HPresenter;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.magnaconnect.utils.Utility.closeSession;
import static com.magnaconnect.utils.Utility.notNullParams;
import static com.magnaconnect.utils.Utility.underlineText;

public class HFragment extends BFragment implements HContract.View, Observer, Cons, NavigationView.OnNavigationItemSelectedListener {
    private static HFragment IC;
    private static String TAG = HFragment.class.getSimpleName();
    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;
    @BindString(R.string.cant_change)
    String cant_change;
    @BindView(R.id.schemeButton)
    TextView scButton;
    @BindView(R.id.other1BTN)
    TextView other1BTN;
    @BindView(R.id.other2BTN)
    TextView other2BTN;
    @BindView(R.id.img_profile)
    ImageView img_profile;
    @BindView(R.id.titleTV)
    TextView titleTV;
    @BindView(R.id.subtitleTV)
    TextView subtitleTV;
    @BindView(R.id.csnTV)
    TextView csnTV;
    @BindView(R.id.lastScanDateTV)
    TextView lastScanDateTV;
    @BindView(R.id.inventoryTV)
    TextView invTV;
    @BindView(R.id.tertiaryTV)
    TextView terTV;
    HPresenter hPresenter;
    AppCompatActivity activity;
    View rootView;
    CATEGORY mCategory = CATEGORY.EMPLOYEE;
    AlertDialog alert;
    DashResponse.Dashboard dab;
    List<DashResponse.Inventory> iList = new ArrayList<>();
    List<DashResponse.Tertiary> tList = new ArrayList<>();
    String profileimage;
    private DrawerLayout drawerLayout;
    private ArrayList<String> iMenuList = new ArrayList<>();
    private ArrayList<String> tMenuList = new ArrayList<>();
    private List<StatResponse.StateItem> dList = new ArrayList<>();

    public static HFragment newInstance() {
        HFragment hFragment = new HFragment();
        return hFragment;
    }

    public static HFragment getInstance() {
        return IC;
    }

    public void CallD() {
        fcr.log(getUser());
        hPresenter.dbo(getUser());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AppCompatActivity) getActivity();
        fcr.setCustomKey(MESSAGE_ALERT, TAG);
        ObservableObject.getInstance().addObserver(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        IC = this;
        setCurrentFragment(TAG);
        mCategory = getCFrom(pdb.getString(Pf_cat));
        hPresenter = new HPresenter(activity);
        hPresenter.attachView(this);
        return inflater.inflate(R.layout.fr_hr_scroll, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        initFragment(view, activity, mCategory.toString(), R.color.colorPrimaryDark, android.R.color.transparent, false);
//        viewLogEvent("HomeScreenFragment", Logger.LogType.Screen, TAG, "onCreateView");
//        AppEventsLogger.newLogger(activity).logEvent(TAG);
//        Fabric fabric = new Fabric.Builder(activity).debuggable(true).kits(new Crashlytics()).build();
//        Fabric.with(fabric);
        navigationDrawerSetup();
        setLayout();
        setTextData();
        CallD();
    }

    private void setTextData() {
        titleTV.setText(getName());
        subtitleTV.setText("Category : " + mCategory);
    }

    private void setLayout() {
        underlineText(scButton, scButton.getText().toString());
    }

    @Override
    public void successDashboard(DashResponse data) {
        try {
            dList.clear();
            dab = data.dab;
            if (data.iList != null) {
                iList.clear();
                iList.addAll(data.iList);
            }
            if (data.tList != null) {
                tList.clear();
                tList.addAll(data.tList);
            }
            if (data.dab.getInventoryMenuList() != null && data.dab.getInventoryMenuList().size() > 0) {
                iMenuList.clear();
                iMenuList.addAll(data.dab.getInventoryMenuList());
            }
            if (data.dab.getTertiaryMenuList() != null && data.dab.getTertiaryMenuList().size() > 0) {
                tMenuList.clear();
                tMenuList.addAll(data.dab.getTertiaryMenuList());
            }
            profileimage = dab.getProfileimage();
            StatResponse.StateItem s = new StatResponse.StateItem();
            s.setCityId(dab.getPdflink());
            s.setCityName(dab.getSchemeimglink());
            if (notNullParams(dab.getDisplayName())) {
                s.setDisplayName(dab.getDisplayName());
            } else {
                s.setDisplayName(ARG_DIS);
            }
            dList.add(s);
            if (notNullParams(dab.getPdflink1()) && notNullParams(dab.getSchemeimglink1())) {
                StatResponse.StateItem s1 = new StatResponse.StateItem();
                s1.setCityId(dab.getPdflink1());
                s1.setCityName(dab.getSchemeimglink1());
                if (notNullParams(dab.getDisplayName1())) {
                    s1.setDisplayName(dab.getDisplayName1());
                } else {
                    s1.setDisplayName(ARG_DIS1);
                }
                dList.add(s1);
            }
            if (notNullParams(dab.getPdflink2()) && notNullParams(dab.getSchemeimglink2())) {
                StatResponse.StateItem s2 = new StatResponse.StateItem();
                s2.setCityId(dab.getPdflink2());
                s2.setCityName(dab.getSchemeimglink2());
                if (notNullParams(dab.getDisplayName2())) {
                    s2.setDisplayName(dab.getDisplayName2());
                } else {
                    s2.setDisplayName(ARG_DIS2);
                }
                dList.add(s2);
            }
            if (notNullParams(profileimage)) {
                Picasso.get().load(profileimage)
                        .transform(new CircleTransform())
                        .error(R.drawable.people_icon)
                        .placeholder(R.drawable.progress_animation)
                        .into(img_profile);
            }
            invTV.setText(String.valueOf(dab.getInventorycount()));
            terTV.setText(dab.getTertiaryAward() + "/" + dab.getSecondaryaward());
            csnTV.setText("CSN : " + getUser());
            lastScanDateTV.setText("Date: " + dab.getLastScanningDate());
        } catch (Exception e) {
            showErrorAlert();
        }
    }

    @Override
    public void successScan(ScnResponse data) {
        if (alert != null) {
            alert.cancel();
        }
        invTV.setText("--");
        terTV.setText("--");
        iList.clear();
        tList.clear();
        showErrorMessageDialog(activity, MESSAGE_ALERT, "Thanks for registering the paperless warranty.", true, () -> {
            hPresenter.dbo(getUser());
        });
    }

    @Override
    public void successScanVerification(String serialNo, ScnResponse data) {
        barcodeDialog(serialNo, data);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            try {
                switch (requestCode) {
                    case CUSTOMIZED_REQUEST_CODE:
                        IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
                        if (result.getContents() == null) {
                        } else {
                            String contents = result.getContents();
                            String date = sdf.format(Calendar.getInstance().getTime());
                            pdb.putString(Pf_last_scan, date);
                            hPresenter.slverify(contents, getUser());
                        }
                        break;
                    case OPEN_DOCUMENT_CODE:
                        if (data != null) {
                            Uri imageUri = data.getData();
                            Picasso.get()
                                    .load(imageUri)
                                    .transform(new CircleTransform())
                                    .resize(200, 200)
                                    .error(R.drawable.people_icon)
                                    .into(img_profile);
                        }
                        break;
                }
                if (requestCode != CUSTOMIZED_REQUEST_CODE && requestCode != IntentIntegrator.REQUEST_CODE) {
                    // This is important, otherwise the result will not be passed to the fragment
                    super.onActivityResult(requestCode, resultCode, data);
                    return;
                }
            } catch (Exception e) {
                showErrorAlert();
            }
        } else if (resultCode == RESULT_CANCELED) {
        }
    }

    public Boolean getCameraPermission() {
        if (HasPermission(Manifest.permission.CAMERA))
            return true;
        else
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
        return false;
    }

    public Boolean getWritePermission() {
        if (HasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            return true;
        else
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        return false;
    }

    @OnClick({R.id.img_profile, R.id.schemeButton, R.id.scanButton, R.id.lnr_inventory, R.id.lnr_rewards, R.id.other1BTN, R.id.other2BTN})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.schemeButton:
                if (dList.size() == 0) {
                    showMessage("Scheme not found currently!");
                } else {
                    setFrame(DFragment.newInstance("", ""), false);
                }
                break;
            case R.id.scanButton:
                if (getCameraPermission() && getWritePermission()) {
                    IntentIntegrator.forSupportFragment(this).setRequestCode(CUSTOMIZED_REQUEST_CODE).initiateScan();
                }
                break;
            case R.id.logoutBTN:
                closeSession(activity);
                break;
            case R.id.lnr_inventory:
                if (checkListSize(iList, "Inventory")) {
                    setFrame(IFragment.newInstance(1, iList, iMenuList), false);
                }
                break;
            case R.id.lnr_rewards:
                if (checkListSize(tList, "Tertiary")) {
                    setFrame(TFragment.newInstance(2, tList, tMenuList), false);
                }
                break;
            case R.id.other1BTN:
                messageAlert(MESSAGE_ALERT, "Coming soon!");
                break;
            case R.id.other2BTN:
                messageAlert(MESSAGE_ALERT, "Under development!");
                break;
        }
    }

    public List<DashResponse.Inventory> getiList() {
        return iList;
    }

    public ArrayList<String> getiMenuList() {
        return iMenuList;
    }

    public List<DashResponse.Tertiary> gettList() {
        return tList;
    }

    public ArrayList<String> gettMenuList() {
        return tMenuList;
    }

    public List<StatResponse.StateItem> getdList() {
        return dList;
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
                nav_menu.findItem(R.id.nav_inventory).setVisible(true);
                nav_menu.findItem(R.id.nav_logout).setVisible(true);
                nav_menu.findItem(R.id.nav_revard).setVisible(true);
                userNameTV.setText(getName() + "\n(" + getUser() + ")"
                );
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_inventory:
                if (checkListSize(iList, ARG_INVENTORY)) {
                    setFrame(IFragment.newInstance(1, iList, iMenuList), false);
                }
                return true;
            case R.id.nav_revard:
                if (checkListSize(tList, ARG_TERTIARY)) {
                    setFrame(TFragment.newInstance(2, tList, tMenuList), false);
                }
                return true;
            case R.id.nav_login:
                setFrame(STFragment.newInstance(), false);
                return true;
            case R.id.nav_logout:
                closeSession(activity);
                return true;
            default:
//                closeSearchView();
                break;
        }
        return true;
    }

    boolean checkListSize(List<?> list, String s) {
        if (list != null && list.size() > 0) {
            return true;
        } else {
            showMessage(s + " not found!");
        }
        return false;
    }

    public void barcodeDialog(String barcode, ScnResponse barcodeResponse) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomDialog);
            LayoutInflater inflater = activity.getLayoutInflater();
            final View targetDialogView = inflater.inflate(R.layout.di_bar, null);
//            final TextView titleTV = targetDialogView.findViewById(R.id.titleTV);
//            final TextView messageTV = targetDialogView.findViewById(R.id.messageTV);
            final EditText barcodeET = targetDialogView.findViewById(R.id.barcodeET);
            final EditText modelNoET = targetDialogView.findViewById(R.id.modelNoET);
            final EditText companyET = targetDialogView.findViewById(R.id.companyET);
            final EditText warrantyET = targetDialogView.findViewById(R.id.warrantyET);
            final EditText otherET = targetDialogView.findViewById(R.id.otherET);

            final EditText customerMobileNoET = targetDialogView.findViewById(R.id.customerMobileNoET);
            final EditText customerNameET = targetDialogView.findViewById(R.id.customerNameET);
            final EditText customerPinET = targetDialogView.findViewById(R.id.customerPinET);
            final EditText customerAddressET = targetDialogView.findViewById(R.id.customerAddressET);
            barcodeET.setFocusable(false);
            barcodeET.setOnClickListener(view -> showMessage(cant_change));
            modelNoET.setFocusable(false);
            modelNoET.setOnClickListener(view -> showMessage(cant_change));
            companyET.setFocusable(false);
            companyET.setOnClickListener(view -> showMessage(cant_change));
            warrantyET.setFocusable(false);
            warrantyET.setOnClickListener(view -> showMessage(cant_change));
            otherET.setFocusable(false);
            otherET.setOnClickListener(view -> showMessage(cant_change));
            barcodeET.setText(barcode);
            if (barcodeResponse != null) {
                modelNoET.setText(String.valueOf(barcodeResponse.getModuleNo()));
                companyET.setText(String.valueOf(barcodeResponse.getModuleName()));
                warrantyET.setText(String.valueOf(barcodeResponse.getWarranty()));
                otherET.setText(String.valueOf(barcodeResponse.getTertiaryaward()));
            }
            final Button cancelBTN = targetDialogView.findViewById(R.id.cancelBTN);
            final Button submitBTN = targetDialogView.findViewById(R.id.submitBTN);

            builder.setView(targetDialogView);
            cancelBTN.setOnClickListener(v -> {
                if (alert != null) {
                    alert.cancel();
                }
            });
            submitBTN.setOnClickListener(v -> {
                if (barcodeET.getText().length() == 0) {
                    showToast("Please provide barcode.");
                    return;
                } else if (barcodeET.getText().length() < 6) {
                    String message = "Invalid barcode";
                    showToast(message);
                    return;
                } else if (!notNullParams(modelNoET.getText().toString())) {
                    String message = "Please provide model No.";
                    showToast(message);
                    return;
                } else if (!notNullParams(companyET.getText().toString())) {
                    String message = "Please provide Company.";
                    showToast(message);
                    return;
                } else if (!notNullParams(warrantyET.getText().toString())) {
                    String message = "Please provide warranty.";
                    showToast(message);
                } else if (!notNullParams(customerMobileNoET.getText().toString())) {
                    String message = "Please provide customer mobile number.";
                    showToast(message);
                    return;
                } else if (customerMobileNoET.getText().length() < 10) {
                    String message = "Invalid mobile number";
                    showToast(message);
                    return;
                } else if (!notNullParams(customerNameET.getText().toString())) {
                    String message = "Please provide customer name.";
                    showToast(message);
                    return;
                } else if (!notNullParams(customerPinET.getText().toString())) {
                    String message = "Please provide customer pin code.";
                    showToast(message);
                    return;
                } else if (customerPinET.getText().length() < 6) {
                    String message = "Invalid mobile number";
                    showToast(message);
                    return;
                } else {
                    if (alert != null) {
                        alert.cancel();
                    }
                    hideKeyBoard();
                    try {
                        hPresenter.scan(barcodeET.getText().toString(), getUser(),
                                customerMobileNoET.getText().toString(), customerNameET.getText().toString(),
                                customerPinET.getText().toString(), customerAddressET.getText().toString());
                    } catch (Exception e) {
                        showToast("Failed to submit form, please try later!");
                    }
                }
            });
            alert = builder.create();
            alert.setCancelable(false);
            alert.show();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(Observable observable, Object o) {
    }
}
