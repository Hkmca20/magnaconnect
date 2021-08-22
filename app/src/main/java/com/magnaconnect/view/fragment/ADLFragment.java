/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.magnaconnect.R;
import com.magnaconnect.utils.Utils;
import com.magnaconnect.view.contract.ADLContract;
import com.magnaconnect.view.dialog.DISDialog;
import com.magnaconnect.view.dialog.PTDialog;
import com.magnaconnect.view.dialog.SDialog;
import com.magnaconnect.view.model.StatResponse;
import com.magnaconnect.view.model.VerifyResponse;
import com.magnaconnect.view.presenter.ADLPresenter;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.magnaconnect.utils.Utility.notNullParams;

public class ADLFragment extends BFragment implements ADLContract.View {
    private static ADLFragment INSTANCE;
    ADLPresenter presenter;
    //    @Inject
//    SharedPrefUtils<String> util;
    @BindString(R.string.select_state)
    String select_state;
    @BindString(R.string.select_type)
    String select_type;
    @BindString(R.string.select_dist)
    String select_dist;
    @BindView(R.id.shopNameLayout)
    TextInputLayout shopNameLayout;
    @BindView(R.id.pincodeLayout)
    TextInputLayout pincodeLayout;
    @BindView(R.id.input_layout_confirm_password)
    TextInputLayout input_layout_confirm_password;
    @BindView(R.id.et_full_name)
    EditText et_full_name;
    @BindView(R.id.et_shop_name)
    EditText et_shop_name;
    @BindView(R.id.et_GST_no)
    EditText et_GST_no;
    @BindView(R.id.et_pincode)
    EditText et_pincode;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_mobileNumber)
    EditText et_mobileNumber;
    @BindView(R.id.et_altMobileNumber)
    EditText et_altMobileNumber;
    @BindView(R.id.et_password)
    EditText et_Password;
    @BindView(R.id.et_confirmPassword)
    EditText et_confirmPassword;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.spinner_state_name)
    EditText spinner_state_name;
    @BindView(R.id.spinner_partnerType)
    EditText spinner_partnerType;
    @BindView(R.id.spinner_distributor)
    EditText spinner_distributor;
    @BindView(R.id.partnerTypeLayout)
    LinearLayout partnerTypeLayout;
    @BindView(R.id.distributorContainer)
    LinearLayout distributorContainer;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private List<StatResponse.StateItem> stateList = new ArrayList<>();
    private List<StatResponse.PartnerTypeItem> partnerList = new ArrayList<>();
    private List<VerifyResponse> distributorList = new ArrayList<>();
    private String selectedStateId, selectedPartnerId;
    private String userStatus;
    private String latitude = LAT_F, longitude = LNG_F;
    private String selectedDistributorId;
    private String TAG = ADLFragment.class.getSimpleName();
    private String mFullName, mShopName, mGSTNo, mPinCode, mAddress, mEmail,
            mMobileNumber, mAltMobileNumber, mPassword;
    private AppCompatActivity activity;
    private View rootView;
    private CATEGORY mCategory;

    public static ADLFragment newInstance() {
        ADLFragment f = new ADLFragment();

        return f;
    }

    public static ADLFragment getInstance() {
        return INSTANCE;
    }

    public static ADLFragment newInstance(CATEGORY category_argument, String mMobileNumber) {
        ADLFragment f = new ADLFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        f.mCategory = category_argument;
        f.mMobileNumber = mMobileNumber;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (AppCompatActivity) getActivity();
        fcr.setCustomKey(MESSAGE_ALERT, TAG);
        INSTANCE = this;
        setCurrentFragment(TAG);
        presenter = new ADLPresenter(activity);
        presenter.attachView(this);
        return inflater.inflate(R.layout.fr_adl, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        initFragment(rootView, activity, SCREEN_AddDlr, statusColor, themeColor, true);
        setLayout();
    }

    private void setLayout() {
        enableVisible(partnerTypeLayout, distributorContainer);
        disableVisible(input_layout_confirm_password);
        disableViews(et_Password);
        spinner_state_name.setFocusable(false);
        spinner_partnerType.setFocusable(false);
        spinner_distributor.setFocusable(false);
        et_mobileNumber.setText(mMobileNumber);
        et_mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                userStatus = null;
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                resetField();
            }
//            private void resetField() {
//                et_full_name.setText("");
//                et_shop_name.setText("");
//                et_GST_no.setText("");
//                et_pincode.setText("");
//                et_address.setText("");
//                et_email.setText("");
//                et_Password.setText("");
//            }
        });
        if (notNullParams(mMobileNumber)) {
            et_mobileNumber.setFocusable(false);
        }
        CallStateListService();
    }

    public void CallStateListService() {
        presenter.distList(getUser());
    }

    @Override
    public void successCity(List<StatResponse.StateItem> spinnerData) {
        stateList.clear();
        stateList.addAll(spinnerData);
    }

    @Override
    public void successPartnerT(List<StatResponse.PartnerTypeItem> partnerData) {
        partnerList.clear();
        partnerList.addAll(partnerData);
    }

    @Override
    public void successDistributor(List<VerifyResponse> distributorData) {
        distributorList.clear();
        distributorList.addAll(distributorData);
        presenter.getstList(getUser());
    }

    @Override
    public void numberVerifiedRes(VerifyResponse body) {
        try {
            if (notNullParams(body.getResponse_status()) && body.getResponse_status().equalsIgnoreCase(MESSAGE_ERROR)) {
                if (notNullParams(body.getErr())) {
                    showMessage(body.getErr());
                }
                return;
            }
            if (notNullParams(body.getUserName())) {
                et_full_name.setText(body.getUserName());
            }
            if (notNullParams(body.getShopName())) {
                et_shop_name.setText(body.getShopName());
            }
            if (notNullParams(body.getGSTNo())) {
                et_GST_no.setText(body.getGSTNo());
            }
            if (notNullParams(body.getUserEmail())) {
                et_email.setText(body.getUserEmail());
            }
            if (notNullParams(body.getAddress())) {
                et_address.setText(body.getAddress());
            }
            if (notNullParams(body.getPincode())) {
                et_pincode.setText(body.getPincode());
            }
            if (notNullParams(body.getUserPassword())) {
                et_Password.setText(body.getUserPassword());
            }
            if (notNullParams(body.getState())) {
                for (StatResponse.StateItem m : stateList) {
                    if (notNullParams(m.getCityName()) && m.getCityName().toLowerCase().equals(body.getState().toLowerCase())) {
                        spinner_state_name.setText(m.getCityName());
                        selectedStateId = m.getCityId();
                        break;
                    }
                }
            }
            if (notNullParams(body.getPartnerType())) {
                for (StatResponse.PartnerTypeItem m : partnerList) {
                    if (notNullParams(m.getCode()) && m.getCode().toLowerCase().equals(body.getPartnerType().toLowerCase())) {
                        spinner_partnerType.setText(m.getType());
                        selectedPartnerId = m.getCode();
                        break;
                    }
                }
            }
            if (notNullParams(body.getDistributorId())) {
                for (VerifyResponse m : distributorList) {
                    if (notNullParams(m.getDistributorId()) && m.getDistributorId().toLowerCase().equals(body.getDistributorId().toLowerCase())) {
                        spinner_distributor.setText(m.getShopName());
                        selectedDistributorId = m.getDistributorId();
                        break;
                    }
                }
            }
            if (notNullParams(body.getUserStatus())) {
                userStatus = body.getUserStatus();
            }
            disableVisible(btn_submit);
            disableViews(et_mobileNumber);
        } catch (Exception e) {
        }
    }

    @Override
    public void successDealerRes(VerifyResponse response) {
        if (notNullParams(response.getStatus()) && response.getStatus().equalsIgnoreCase("ok"))
            showErrorMessageDialog(activity, MESSAGE_ALERT, String.valueOf(response.getMessage()), false, () -> {
                activity.getSupportFragmentManager().popBackStack();
                DLFragment.getInstance().CallDL();
            });
        else
            showErrorMessageDialog(activity, MESSAGE_ALERT, MESSAGE_FAILED, false, () -> {
            });
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
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public void showSnackCustom(String message, String action) {
        snackBar(btn_submit, message, action);
    }

    @Override
    public String getUUID() {
        return getUser();
    }

    @OnClick({R.id.btn_submit, R.id.et_mobileNumber, R.id.spinner_state_name, R.id.spinner_partnerType, R.id.spinner_distributor, R.id.verifyButton})
    public void onViewClicked(View view) {
        mMobileNumber = et_mobileNumber.getText().toString();
        switch (view.getId()) {
            case R.id.btn_submit:
                Utils.dismissKeyboard(activity);
//                if (!Utility.notNullParams(userStatus)) {
//                    showMessageAlert(MESSAGE_ALERT, "Please verify your mobile number first!");
//                    return;
//                }
//                if (!userStatus.equalsIgnoreCase("a")) {
//                    showMessageAlert(MESSAGE_ALERT, "This Mobile number is not active, \nplease contact support team!");
//                    return;
//                }
                mFullName = et_full_name.getText().toString();
                mShopName = et_shop_name.getText().toString();
                mGSTNo = et_GST_no.getText().toString();
                mPinCode = et_pincode.getText().toString();
                mAddress = et_address.getText().toString();
                mEmail = et_email.getText().toString();
                mPassword = et_Password.getText().toString();
                if (Utils.isNetworkAvailable(activity)) {
                    presenter.validateSubmit(mFullName, mShopName, mGSTNo, mPinCode, mAddress, selectedDistributorId, selectedStateId, selectedPartnerId, mEmail, mMobileNumber, mAltMobileNumber, mPassword, latitude, longitude);
                } else {
                    showErrorAlert();
                }
                break;
            case R.id.et_mobileNumber:
//                if (Utility.notNullParams(mMobileNumber))
//                    showSnackCustom("This field can't be change!", "OK");
                break;
            case R.id.spinner_state_name:
                if (stateList.size() == 0) {
                    presenter.getstList(getUser());
                    return;
                }
                SDialog c = new SDialog(activity, stateList, select_state, false,
                        dialogInterface -> {
                        },
                        id -> {
                            for (StatResponse.StateItem item : stateList) {
                                if (notNullParams(item.getCityId()) && item.getCityId().equals(id)) {
                                    spinner_state_name.setText(item.getCityName());
                                    selectedStateId = item.getCityId();
                                    break;
                                }
                            }
                        });
                c.show();
                break;
            case R.id.spinner_partnerType:
                if (partnerList.size() == 0) {
                    presenter.getstList(getUser());
                    return;
                }
                PTDialog d = new PTDialog(activity, partnerList, select_type, false,
                        dialogInterface -> {
                        },
                        id -> {
                            for (StatResponse.PartnerTypeItem item : partnerList) {
                                if (notNullParams(item.getCode()) && item.getCode().equals(id)) {
                                    spinner_partnerType.setText(item.getType());
                                    selectedPartnerId = item.getCode();
                                    break;
                                }
                            }
                        });
                d.show();
                break;
            case R.id.spinner_distributor:
                if (distributorList.size() == 0) {
                    presenter.distList(getUser());
                    return;
                }
                DISDialog cd = new DISDialog(activity, distributorList, select_dist, false,
                        dialogInterface -> {
                        },
                        id -> {
                            for (VerifyResponse item : ADLFragment.getInstance().distributorList) {
                                if (notNullParams(item.getDistributorId()) && item.getDistributorId().equals(id)) {
                                    spinner_distributor.setText(item.getShopName());
                                    selectedDistributorId = item.getDistributorId();
                                    break;
                                }
                            }
                        });
                cd.show();
                break;
            case R.id.verifyButton:
                hideKeyBoard();
                presenter.verifydl(mMobileNumber);
                break;
        }
    }

}