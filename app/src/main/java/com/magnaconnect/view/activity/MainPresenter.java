/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.activity;

import com.magnaconnect.di.Presenter;

public class MainPresenter implements Presenter<MainMvpView> {
    private MainMvpView mainMvpView;

    @Override
    public void attachView(MainMvpView view) {
        this.mainMvpView = view;
    }

    @Override
    public void detachView() {
        this.mainMvpView = null;
    }

    public void do_stuff() {
        //doing stuff here...
        mainMvpView.doing_nothing();
    }
}