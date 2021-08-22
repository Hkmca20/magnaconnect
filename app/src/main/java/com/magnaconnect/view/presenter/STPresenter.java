/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.presenter;

import android.content.Context;


import com.magnaconnect.BApp;
import com.magnaconnect.network.IServices;
import com.magnaconnect.di.Presenter;
import com.magnaconnect.view.contract.SMvpView;

import javax.inject.Inject;
import javax.inject.Named;

public class STPresenter implements Presenter<SMvpView> {

  private SMvpView sMvpView;

  @Inject public STPresenter(Context ctx) {
    ((BApp) ctx.getApplicationContext()).getComponent().inject(this);
  }

  @Inject @Named("cached")
  IServices IServices;

  @Override public void attachView(SMvpView view) {
    this.sMvpView = view;
  }

  @Override public void detachView() {
    this.sMvpView = null;
  }

  public void show_headers() {

//    IServices.getHeaders().enqueue(new Callback<IPInfo>() {
//      @Override public void onResponse(@NonNull Call<IPInfo> call, @NonNull Response<IPInfo> response) {
//        sMvpView.showHeaders(response.body().toString());
//      }
//
//      @Override public void onFailure(@NonNull Call<IPInfo> call, @NonNull Throwable t) {
//        sMvpView.showError(t.getMessage());
//      }
//    });

  }
}