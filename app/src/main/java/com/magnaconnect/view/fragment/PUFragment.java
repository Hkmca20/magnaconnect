/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.events.MessagesEvent;
import com.magnaconnect.view.contract.PUContract;
import com.magnaconnect.view.model.AttResponse;
import com.magnaconnect.view.model.ScanRequest;
import com.magnaconnect.view.model.VerifyResponse;
import com.magnaconnect.view.presenter.PUPresenter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

import static com.magnaconnect.utils.Utility.IsInternetConnected;

public class PUFragment extends BFragment implements PUContract.View,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String STATUS = "status";
    private static PUFragment INSTANCE;
    @BindView(R.id.categoryTV)
    TextView categoryTV;
    @BindView(R.id.categoryButton1)
    Button categoryButton1;
    @BindView(R.id.categoryButton2)
    Button categoryButton2;
    @BindView(R.id.categoryButton3)
    Button categoryButton3;
    @BindView(R.id.analogClock)
    AnalogClock analogClock;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindString(R.string.meeting_on)
    String meeting_on;
    @BindString(R.string.logout_msg)
    String logout_msg;
    @BindString(R.string.location_permission)
    String location_permission;
    @BindString(R.string.internet_not_connected)
    String internet_not_connected;
    @BindString(R.string.please_wait)
    String please_wait;
    @BindString(R.string.location_service_enable)
    String location_service_enable;
    AppCompatActivity activity;
    PUPresenter presenter;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    PUNCH mStatus = PUNCH.OTHER;
    int selectedButton;
    private String TAG = PUFragment.class.getSimpleName();

    public static PUFragment newInstance(PUNCH status) {
        PUFragment f = new PUFragment();
        Bundle args = new Bundle();
        args.putString(STATUS, status.toString());
        f.setArguments(args);
        f.mStatus = status;
        return f;
    }

    public static PUFragment getInstance() {
        return INSTANCE;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean showBack = false;
        if (mStatus == PUNCH.IN) {
            showBack = true;
        }
        initFragment(view, activity, SCREEN_Punch, statusColor, themeColor, showBack);
        PUPresenter presenter = new PUPresenter(getActivity());
        presenter.attachView(this);
        presenter.show_headers();
        setLayout();
        presenter.empgetatt(getUser());
    }

    private void setLayout() {
        enableVisible(analogClock);
        categoryButton1.setText(PUNCH.IN.toString());
        categoryButton2.setText(PUNCH.OUT.toString());
        categoryButton3.setText(PUNCH.MEETING.toString());
        disableVisible(categoryButton1, categoryButton2, categoryButton3);
    }

    private void setAtt(PUNCH mStatus) {
        switch (mStatus) {
            case IN:
                enableVisible(categoryButton2);
                enableVisible(categoryButton3);
                break;
            case MEETING:
                enableVisible(categoryTV);
                categoryTV.setText(meeting_on);
                break;
            case OUT:
                enableVisible(categoryTV);
                categoryTV.setText(logout_msg);
                break;
            case OTHER:
                enableVisible(categoryButton1);
                enableVisible(categoryButton3);
                break;
        }
    }

    @Override
    public void successAtt(AttResponse item) {
        disableVisible(categoryButton1, categoryButton2, categoryButton3);
        String status = item.getStatus();
        setAtt(getPFrom(status));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setCurrentFragment(TAG);
        fcr.setCustomKey(MESSAGE_ALERT, TAG);
        activity = (AppCompatActivity) getActivity();
        INSTANCE = this;
        presenter = new PUPresenter(activity);
        presenter.attachView(this);
        mStatus = getPFrom(getArguments().getString(STATUS));
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        return inflater.inflate(R.layout.fr_st, container, false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    mGoogleApiClient.disconnect();
                    showMessageAlert(MESSAGE_ALERT, location_permission);
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onEvent(MessagesEvent event) {
        fcr.setCustomKey(TAG, event.getMessage());
        KLog.d(">>> %s", event.toString());
    }

    @OnLongClick(R.id.analogClock)
    public void onViewLongClicked(View view) {
        switch (view.getId()) {
            case R.id.analogClock:
                String date = sdf.format(Calendar.getInstance().getTime());
                showToast(date);
                break;
        }
    }

    @OnClick({R.id.categoryButton1, R.id.categoryButton2, R.id.categoryButton3})
    public void onViewClicked(View view) {
        if (!IsInternetConnected(view.getContext())) {
            messageAlert(MESSAGE_ALERT, internet_not_connected);
            return;
        }
        switch (view.getId()) {
            case R.id.categoryButton1:
            case R.id.categoryButton2:
            case R.id.categoryButton3:
                if (mGoogleApiClient != null) {
                    enableVisible(progressBar);
                    selectedButton = view.getId();
                    mGoogleApiClient.connect();
                } else {
                    showMessage(internet_not_connected);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void successAttSave(VerifyResponse data) {
        setFrame(HEFragment.newInstance(), true);
    }

    @Override
    public void messageAlert(String title, String msg) {
        showMessageAlert(title, msg);
    }

    @Override
    public Context getCtx() {
        return activity;
    }

    @Override
    public void errorAlert() {
        showErrorAlert();
    }

    @Override
    public void startProgress() {
        showProgressD(please_wait);
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
    public void onConnected(@Nullable Bundle bundle) {
        settingRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {
//        showMessage("Connection Suspended!");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showMessage(MESSAGE_FAILED);
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(activity, 90000);
            } catch (IntentSender.SendIntentException e) {
                fcr.recordException(e);
            }
        } else {
            fcr.setCustomKey(MESSAGE_ALERT, MESSAGE_FAILED);
        }
    }

    public void settingRequest() {
        mLocationRequest = new LocationRequest().create();
        mLocationRequest.setInterval(INTERVAL_30);
        mLocationRequest.setFastestInterval(INTERVAL_5);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(result1 -> {
            final Status status = result1.getStatus();
//            final LocationSettingsStates state = result1.getLocationSettingsStates();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    getLocation();
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    try {
                        startIntentSenderForResult(status.getResolution().getIntentSender(), LOCATION_SETTING_REQUEST, null, 0, 0, 0, null);
//                        status.startResolutionForResult(activity,LOCATION_SETTING_REQUEST);
                    } catch (IntentSender.SendIntentException e) {
                        // Ignore the error.
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    break;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case LOCATION_SETTING_REQUEST:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        showMessage(location_service_enable);
                        mGoogleApiClient.disconnect();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_FINE_LOCATION);
            return;
        } else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            disableVisible(progressBar);
            ScanRequest request = new ScanRequest();
            if (mLastLocation != null) {
                switch (selectedButton) {
                    case R.id.categoryButton1:
                        request.setStatus(PUNCH.IN.toString());
                        break;
                    case R.id.categoryButton2:
                        request.setStatus(PUNCH.OUT.toString());
                        break;
                    case R.id.categoryButton3:
                        request.setStatus(PUNCH.MEETING.toString());
                        break;
                }
                request.setUserId(getUser());
                request.setLatIn(String.valueOf(mLastLocation.getLatitude()));
                request.setLogIn(String.valueOf(mLastLocation.getLongitude()));
                request.setLatIn(LAT_LNG_INIT);
                request.setLogIn(LAT_LNG_INIT);
                request.setLatOut(LAT_LNG_INIT);
                request.setLogOut(LAT_LNG_INIT);
                presenter.esubatt(request);
                mGoogleApiClient.disconnect();
            } else {
                if (!mGoogleApiClient.isConnected())
                    mGoogleApiClient.connect();
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }
    }

    @Override
    public void onLocationChanged(Location currentLocation) {
        mLastLocation = currentLocation;
        disableVisible(progressBar);
        ScanRequest request = new ScanRequest();
        if (mLastLocation != null) {
            switch (selectedButton) {
                case R.id.categoryButton1:
                    request.setStatus(PUNCH.IN.toString());
                    break;
                case R.id.categoryButton2:
                    request.setStatus(PUNCH.OUT.toString());
                    break;
                case R.id.categoryButton3:
                    request.setStatus(PUNCH.MEETING.toString());
                    break;
            }
            request.setUserId(getUser());
            request.setLatIn(String.valueOf(mLastLocation.getLatitude()));
            request.setLogIn(String.valueOf(mLastLocation.getLongitude()));
            request.setLatIn(LAT_LNG_INIT);
            request.setLogIn(LAT_LNG_INIT);
            request.setLatOut(LAT_LNG_INIT);
            request.setLogOut(LAT_LNG_INIT);
            presenter.esubatt(request);
            mGoogleApiClient.disconnect();
        }
    }
}
