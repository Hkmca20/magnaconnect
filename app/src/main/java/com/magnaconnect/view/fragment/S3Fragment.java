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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.events.MessagesEvent;
import com.magnaconnect.utils.CustomAutoCompleteTextView;
import com.magnaconnect.view.adapter.S3Adapter;
import com.magnaconnect.view.adapter.S3PAdapter;
import com.magnaconnect.view.contract.S3Contract;
import com.magnaconnect.view.model.ProdResponse;
import com.magnaconnect.view.presenter.S3Presenter;
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

public class S3Fragment extends BFragment implements S3Contract.View {
    private static S3Fragment INSTANCE;
    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;
    @BindView(R.id.scanButton)
    Button scanButton;
    @BindView(R.id.barcodeTV)
    TextView barcodeTV;
    @BindView(R.id.logoIV)
    ImageView logoIV;
    @BindView(R.id.searchContainer)
    LinearLayout searchContainer;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.emptyContainer)
    LinearLayout emptyContainer;
    @BindView(R.id.act_search)
    CustomAutoCompleteTextView act_search;
    @BindView(R.id.slideToNext)
    SlideToActView slideToNext;
    AppCompatActivity activity;
    CATEGORY mCategory = CATEGORY.EMPLOYEE;
    S3Presenter presenter;
    FragmentCallback fragmentCallback;
    S3PAdapter allProductAdapter;
    private String TAG = S3Fragment.class.getSimpleName();
    private RecyclerView.Adapter mAdapter;
    private List<ProdResponse> productList = new ArrayList<>();
    private List<ProdResponse> selectedList = new ArrayList<>();
    private String masterJson;

    public static S3Fragment newInstance(String masterJson) {
        Bundle b = new Bundle();
        b.putString(MASTER_JSON, masterJson);
        S3Fragment f = new S3Fragment();
        f.setArguments(b);
        return f;
    }

    public static S3Fragment getInstance() {
        return INSTANCE;
    }

    public int selectedProductCount() {
        return selectedList.size();
    }

    public void skipP() {
        gotoNext(true);
    }

    private void gotoNext(boolean isSkip) {
        JSONArray a = new JSONArray();
        try {
            for (ProdResponse item : S3Fragment.getInstance().selectedList) {
                JSONObject o = new JSONObject();
                o.put("moduleNo", item.getModuleNo());
                o.put("moduleName", item.getModuleName());
                o.put("capacity", item.getCapacity());
                o.put("productName", item.getProductName());
                o.put("warranty", item.getWarranty());
                o.put("id", item.getId());
                o.put("unit_price", item.getUnit_price());
                o.put("qunatity", item.getQuantity());
                a.put(o);
            }
            JSONObject parentJson = new JSONObject(S3Fragment.getInstance().masterJson);
            parentJson.put("userId", getUser());
            parentJson.put("orderSec", a);
            S3Fragment.getInstance().masterJson = parentJson.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setFrame(S4Fragment.newInstance(S3Fragment.getInstance().masterJson), false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragment(view, activity, SCREEN_Track, statusColor, themeColor, true);
        setLayout();
        CallEmpProductService();
    }

    public void CallEmpProductService() {
        if (productList.size() > 0) {
            showMessage("Product already loaded!");
            return;
        }
        act_search.setText("");
        presenter.pList(getUser());
    }

    private void setLayout() {
        enableVisible(searchContainer, slideToNext, emptyContainer);
//        act_search.addTextChangedListener(new TextChangeListener());
        //------------------------------------
        setMargins(mRecyclerView, 4, 4, 4, 130);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new S3Adapter(activity, selectedList);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        ((S3Adapter) mAdapter).setOnItemClickListener((position, v) -> {
            if (v.getId() == R.id.removeButton) {
                selectedList.remove(position);
                mAdapter.notifyDataSetChanged();
            } else if (v.getId() == R.id.plusButton) {
                onAdapterClick(position, 1);
            } else if (v.getId() == R.id.minusButton) {
                onAdapterClick(position, 2);
            }
        });
        slideToNext.setOnSlideCompleteListener(view1 -> {
                    hideKeyBoard();
                    slideToNext.resetSlider();
                    if (S3Fragment.getInstance().selectedList.size() == 0) {
                        showMessageAlert(MESSAGE_ALERT, "Please add at-lease one product and continue");
                        return;
                    }
                    gotoNext(false);
                }
        );
    }

    @Override
    public void onAdapterClick(int position, int action) {
        try {
            disableVisible(emptyContainer);
            switch (action) {
                case 0:
                    if (isExist(productList.get(position).getModuleNo())) {
                        showMessage("This product already added.");
                    } else {
                        hideKeyBoard();
                        ProdResponse item = productList.get(position);
                        item.setQuantity(1);
                        selectedList.add(item);
                        mAdapter.notifyDataSetChanged();
                        act_search.setText("");
                    }
                    break;
                case 1://plus
                    ProdResponse addItem = selectedList.get(position);
                    addItem.setQuantity(addItem.getQuantity() + 1);
                    mAdapter.notifyDataSetChanged();
                    break;
                case 2://minus
                    ProdResponse removeItem = selectedList.get(position);
                    int count = removeItem.getQuantity();
                    if (count > 1) {
                        selectedList.get(position).setQuantity(count - 1);
                    } else {
                        selectedList.remove(position);
                        if (selectedList.size() == 0) {
                            enableVisible(emptyContainer);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        } catch (Exception e) {
            fcr.recordException(e);
        }
    }

    private boolean isExist(String moduleNo) {
        for (ProdResponse m : selectedList) {
            if (moduleNo.equalsIgnoreCase(m.getModuleNo())) {
                return true;
            }
        }
        return false;
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
        mCategory = getCFrom(pdb.getString(Pf_cat));
        presenter = new S3Presenter(activity);
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
        productList.clear();
        productList.addAll(data);
        allProductAdapter = new S3PAdapter(this, activity, R.layout.ad_e_p, productList);
        act_search.setThreshold(1);
        act_search.setAdapter(allProductAdapter);
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
//    private class TextChangeListener implements TextWatcher {
//        public TextChangeListener() {
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            searchedText = s.toString();
//            searchByText(searchedText);
//        }
//
//        protected void searchByText(String s) {
//            try {
//                if (pList == null || pList.size() == 0) {
//                    return;
//                }
//                if (Utility.notNullParams(s.trim())) {
//                    List<ProdResponse> tempList = new ArrayList<>(pList);
//                    int deleteCount = 0;
//                    for (int i = 0; i < pList.size(); i++) {
//                        int position = i - deleteCount;
//                        ProdResponse listItem = tempList.get(position);
//                        if (!listItem.getProductName().trim().toLowerCase().contains(s.toLowerCase())
//                                && !listItem.getModuleName().trim().toLowerCase().contains(s.toLowerCase())
//                                && !listItem.getModuleNo().trim().toLowerCase().contains(s.toLowerCase())
//                                && !listItem.getCapacity().trim().toLowerCase().contains(s.toLowerCase())
//                        ) {
//                            tempList.remove(position);
//                            deleteCount++;
//                        }
//                    }
//                    mAdapter = new S3Adapter(activity, tempList);
//                    mRecyclerView.setAdapter(mAdapter);
//                } else {
//                    mAdapter = new S3Adapter(activity, pList);
//                    mRecyclerView.setAdapter(mAdapter);
//
//                }
//            } catch (Exception ex) {
//            }
//        }
//
//    }
}
