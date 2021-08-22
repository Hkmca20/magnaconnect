/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.presenter;

import android.content.Context;

import com.magnaconnect.BApp;
import com.magnaconnect.network.IServices;
import com.magnaconnect.di.Presenter;
import com.magnaconnect.view.contract.HISMvpView;

import javax.inject.Inject;
import javax.inject.Named;

public class HISPresenter implements Presenter<HISMvpView> {

  private HISMvpView HISMvpView;

  @Inject public HISPresenter(Context ctx) {
    ((BApp) ctx.getApplicationContext()).getComponent().inject(this);
  }

  @Inject @Named("cached")
  IServices IServices;

  @Override public void attachView(HISMvpView view) {
    this.HISMvpView = view;
  }

  @Override public void detachView() {
    this.HISMvpView = null;
  }

  public void show_headers() {

//    IServices.getHeaders().enqueue(new Callback<IPInfo>() {
//      @Override public void onResponse(@NonNull Call<IPInfo> call, @NonNull Response<IPInfo> response) {
//
//        HISMvpView.showHeaders(response.body().toString());
//      }
//
//      @Override public void onFailure(@NonNull Call<IPInfo> call, @NonNull Throwable t) {
//        KLog.e(t);
//        HISMvpView.showError("error");
//      }
//    });

  }
}