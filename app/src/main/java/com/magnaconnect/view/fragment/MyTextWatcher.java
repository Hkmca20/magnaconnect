/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.fragment;

import android.text.Editable;
import android.text.TextWatcher;

import com.magnaconnect.view.adapter.DISAdapter;
import com.magnaconnect.view.adapter.PTAdapter;
import com.magnaconnect.view.adapter.STAdapter;

public class MyTextWatcher implements TextWatcher {
    STAdapter STAdapter;
    PTAdapter partnerAdapter;
    DISAdapter DISAdapter;

    public MyTextWatcher(STAdapter STAdapter, PTAdapter partnerAdapter, DISAdapter DISAdapter) {
        this.STAdapter = STAdapter;
        this.partnerAdapter = partnerAdapter;
        this.DISAdapter = DISAdapter;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (STAdapter != null) {
            STAdapter.getFilter().filter(charSequence);
        } else if (partnerAdapter != null) {
            partnerAdapter.getFilter().filter(charSequence);
        } else {
            DISAdapter.getFilter().filter(charSequence);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}
