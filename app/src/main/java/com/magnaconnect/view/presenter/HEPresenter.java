/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.presenter;

import android.content.Context;
import android.util.Log;

import com.magnaconnect.R;
import com.magnaconnect.di.Presenter;
import com.magnaconnect.network.IServices;
import com.magnaconnect.utils.Cons;
import com.magnaconnect.BApp;
import com.magnaconnect.view.contract.HEMvpView;
import com.magnaconnect.view.model.AttResponse;
import com.magnaconnect.view.model.ProdResponse;
import com.magnaconnect.view.model.ScanRequest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HEPresenter implements Presenter<HEMvpView.View>, Cons {
    String TAG = HEPresenter.class.getSimpleName();

    @Inject
    @Named("cached")
    IServices IServices;
    private HEMvpView.View view;

    @Inject
    public HEPresenter(Context ctx) {
        ((BApp) ctx.getApplicationContext()).getComponent().inject(this);
    }

    @Override
    public void attachView(HEMvpView.View mvpView) {
        this.view = mvpView;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void empgetatt(String userId) {
        ScanRequest request = new ScanRequest();
        request.setUserId(userId);
        try {
            view.startProgress();
            Call iCall = IServices.empgetatt(request);
            iCall.enqueue(new Callback<List<AttResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<AttResponse>> call, @NonNull Response<List<AttResponse>> response) {
                    view.endProgress();
                    try {
                        if (response.code() != 200) {
                            view.errorAlert();
                            return;
                        }
                        if (response.body() != null && response.body().size() > 0) {
                            AttResponse item = response.body().get(response.body().size() - 1);
                            if (item.getResponse_status().equalsIgnoreCase(MESSAGE_ERROR)) {
                                view.showMessage(item.getErr());
                                view.openPunchIn(PUNCH.OTHER);
                                view.messageAlert(MESSAGE_ALERT, view.getContext().getResources().getString(R.string.attend_udpate));
                            } else if (item.getResponse_status().equalsIgnoreCase(MESSAGE_OK)) {
                                view.successAtt(response.body());
                            } else {
                                view.errorAlert();
                                view.showMessage(item.getErr());
                            }
                        } else if (response.body().size() == 0) {
                            view.openPunchIn(PUNCH.OTHER);
                            view.messageAlert(MESSAGE_ALERT, view.getContext().getResources().getString(R.string.attend_udpate));
                        } else {
                            view.errorAlert();
                        }
                    } catch (Exception e) {
                        fcr.recordException(e);
                        view.errorAlert();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<AttResponse>> call, @NonNull Throwable t) {
                    view.endProgress();
                    Log.e(TAG, "onFailure: ", t);
                    view.errorAlert();
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
        }
    }

    public void edbo(String userId) {
        try {
            view.startProgress();
            Call iCall = IServices.edbo();
            iCall.enqueue(new Callback<ProdResponse>() {
                @Override
                public void onResponse(@NonNull Call<ProdResponse> call, @NonNull Response<ProdResponse> response) {
                    view.endProgress();
                    try {
                        if (response.code() != 200) {
                            view.errorAlert();
                            return;
                        }
                        if (response.body().getCounters() != null) {
                            if (response.body().getCounters().size() > 0) {
                                pdb.putListString(Pf_cList, response.body().getCounters());
                            }
                        }
                        if (response.body().getMarketing() != null) {
                            if (response.body().getMarketing().size() > 0) {
                                pdb.putListString(Pf_mList, response.body().getMarketing());
                            }
                        }
                        if (response.body().getDashboardList() != null && response.body().getDashboardList().size() > 0) {
                            view.successEmpHome(response.body().getDashboardList());
                        } else if (response.body().getDashboardList().size() == 0) {
                            view.messageAlert(MESSAGE_ALERT, "No data found, please try later!");
                        } else {
                            view.errorAlert();
                        }
                    } catch (Exception e) {
                        fcr.recordException(e);
                        view.errorAlert();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProdResponse> call, @NonNull Throwable t) {
                    view.endProgress();
                    Log.e(TAG, "onFailure: ", t);
                    view.errorAlert();
                }
            });
        } catch (Exception e) {
            fcr.recordException(e);
        }
    }

    public void show_headers() {

//    IServices.getHeaders().enqueue(new Callback<IPInfo>() {
//      @Override public void onResponse(@NonNull Call<IPInfo> call, @NonNull Response<IPInfo> response) {
//
//        HEMvpView.showHeaders(response.body().toString());
//      }
//
//      @Override public void onFailure(@NonNull Call<IPInfo> call, @NonNull Throwable t) {
//        KLog.e(t);
//        HEMvpView.showError("error");
//      }
//    });

    }
}