/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.contract;

import com.magnaconnect.view.fragment.MvpView;

public interface SMvpView extends MvpView {

  void showHeaders(String headers);

  void showError(String error);

}