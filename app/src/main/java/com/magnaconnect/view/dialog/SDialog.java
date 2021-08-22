/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.utils.MyDividerItemDecoration;
import com.magnaconnect.utils.Utils;
import com.magnaconnect.view.adapter.STAdapter;
import com.magnaconnect.view.fragment.MyTextWatcher;
import com.magnaconnect.view.model.StatResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SDialog extends Dialog {
    String TAG = SDialog.class.getCanonicalName();
    Utils.OnDialogIntOkClickListener onDialogIntOkClickListener;
    OnCancelListener onCancelListener;
    Activity activity;
    List<StatResponse.StateItem> spinnerList;
    String titleString;
    boolean can;

    public SDialog(@NonNull Activity context, List<StatResponse.StateItem> spinnerList, String titleString, boolean cancelable, @Nullable OnCancelListener cancelListener, Utils.OnDialogIntOkClickListener onDialogIntOkClickListener) {
        super(context, R.style.CustomDialog);
        this.onDialogIntOkClickListener = onDialogIntOkClickListener;
        this.onCancelListener = cancelListener;
        this.activity = context;
        this.spinnerList = spinnerList;
        this.titleString = titleString;
        this.can = cancelable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.di_sea);
        TextView dialogTitleTv = findViewById(R.id.dialogTitleTv);
        dialogTitleTv.setText(titleString);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerDialog);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(activity, DividerItemDecoration.VERTICAL, 36));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        STAdapter STAdapter = new STAdapter(activity, spinnerList);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(STAdapter);
        STAdapter.notifyDataSetChanged();
        STAdapter.setOnItemClickListener((id, v) -> {
            if (onDialogIntOkClickListener != null) {
                onDialogIntOkClickListener.onClicked(id);
            }
            dismiss();
        });
        Button closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(view -> dismiss());
        EditText mSearchView = findViewById(R.id.ListSearch);
        mSearchView.addTextChangedListener(new MyTextWatcher(STAdapter, null, null));
        setOnCancelListener(dialogInterface -> {
            if (onCancelListener != null) {
                onCancelListener.onCancel(dialogInterface);
            }
        });
        setOnKeyListener((arg0, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dismiss();
            }
            return true;
        });
        setCancelable(can);
    }
}
