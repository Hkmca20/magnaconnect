/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.fragment;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.magnaconnect.R;
import com.magnaconnect.utils.Cons;
import com.magnaconnect.utils.Utility;
import com.magnaconnect.utils.Utils;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.magnaconnect.utils.Utility.IsInternetConnected;
import static com.magnaconnect.utils.Utility.notNullParams;
import static com.magnaconnect.utils.Utility.showSnackMessage;

public class BFragment extends UpdateFrame implements Cons {
    Unbinder unbinder;
    private ProgressDialog mProgressD;

    public void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    String getCat() {
        return pdb.getString(Pf_cat);
    }

    String getMob() {
        return pdb.getString(Pf_m);
    }

    String getPU() {
        return pdb.getString(Pf_pu);
    }

    String getUser() {
        return pdb.getString(Pf_uId);
    }

    String getName() {
        return pdb.getString(PF_uName);
    }

    protected boolean setFrame(Fragment fragment, boolean isReplace) {
        if (fragment != null) {
            String fragmentTAG = fragment.getClass().getSimpleName();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            if (isReplace) {
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ft.replace(FRAME_CONTAINER_ID, fragment, fragmentTAG);
            } else {
                ft.add(FRAME_CONTAINER_ID, fragment, fragmentTAG);
                ft.addToBackStack(fragmentTAG);
            }
            ft.commit();
            return true;
        } else {
            return false;
        }
    }

    public Boolean HasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), permission);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        } else
            return true;
        return false;
    }

    protected void hideKeyBoard() {
        try {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        } catch (Exception ex) {
        }
    }

    protected void showKeyBoard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        } catch (Exception ex) {
        }
    }

    protected void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
        }
    }

    protected void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

    protected void enableVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    protected void disableVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }

    protected void initFragment(View rootView, final AppCompatActivity activity, String titleName, int statusColor, int bgColor, boolean showBack) {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(statusColor));
        }
        toolbar.setBackgroundColor(activity.getResources().getColor(bgColor));
        toolbar.setTitle(titleName);

        activity.setSupportActionBar(toolbar);
        final ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayShowHomeEnabled(false);

        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.lay_tools, null);
        TextView tv_ToolbarTitle = view.findViewById(R.id.tv_ToolbarTitle);
        tv_ToolbarTitle.setText(titleName);

        ab.setCustomView(view);
        ab.setDisplayShowCustomEnabled(true);

        if (showBack) {
            toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
            toolbar.setNavigationOnClickListener(
                    v -> {
                        if (activity.getSupportFragmentManager().getBackStackEntryCount() < 1) {
                            activity.finish();
                        } else {
                            activity.getSupportFragmentManager().popBackStack();
                        }
                    }
            );
        } else {
            ab.setDisplayShowHomeEnabled(false);
            ab.setDisplayHomeAsUpEnabled(false);
        }
    }

    protected void Nav(String latitude, String longitude) {
        if (!notNullParams(latitude) || !notNullParams(longitude) || latitude.equals("0.0") || longitude.equals("0.0")) {
            showToast("Location not found");
            return;
        }
        LatLng destinationLatLng = null;
        try {
            destinationLatLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        } catch (NumberFormatException e) {
        } catch (Exception e) {
        }
        if (destinationLatLng == null) {
            showToast("Location not found");
            return;
        }
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + destinationLatLng.latitude + "," + destinationLatLng.longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            getActivity().startActivity(mapIntent);
        }
    }

    protected void Call(String phoneNo) {
        int permissionCheck;
        if (Utility.validateMobileNo(phoneNo)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED)
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 11);
                else {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(TEL_PLUS_NINE_ONE + phoneNo)));
                }
            } else {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(TEL_PLUS_NINE_ONE + phoneNo)));
            }
        } else {
            showToast("Invalid number!");
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    protected void showSnackTop(String message) {
        showSnackMessage(getActivity(), message, true);
    }

    protected CATEGORY getCFrom(String s) {
        CATEGORY c = CATEGORY.BUSINESS_ASSOCIATE;
        switch (s) {
            case CAT_EMPLOYEE:
                c = CATEGORY.EMPLOYEE;
                break;
            case CAT_BUSINESS_ASSOCIATE:
                c = CATEGORY.BUSINESS_ASSOCIATE;
                break;
            case CAT_BUSINESS_PARTNER:
                c = CATEGORY.BUSINESS_PARTNER;
                break;
        }
        return c;
    }

    public PUNCH getPFrom(String s) {
        PUNCH a = PUNCH.OTHER;
        switch (s) {
            case STATUS_IN:
                a = PUNCH.IN;
                break;
            case STATUS_OUT:
                a = PUNCH.OUT;
                break;
            case STATUS_MEETING:
                a = PUNCH.MEETING;
                break;
            default:
                a = PUNCH.OTHER;
                break;
        }
        return a;
    }

    protected void showProgressD(String message) {
        if (mProgressD == null) {
            mProgressD = new ProgressDialog(getActivity(), R.style.CustomDialog);
            mProgressD.getWindow().getDecorView().getBackground().setColorFilter(new LightingColorFilter(0xFFffffff,
                    getActivity().getResources().getColor(R.color.White)));
//            mProgressD.getWindow().setBackgroundDrawableResource(R.color.White);
            mProgressD.setCancelable(false);
            mProgressD.setMessage(message);
        }

        mProgressD.show();
    }

    public void snackBar(View view, String message, String buttonText) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).
                setAction(buttonText, v -> {
                });
        snackbar.setActionTextColor(Color.WHITE);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.notification_template_icon_bg, 0, 0, 0);
        textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.size_margin20));
        snackbar.show();
    }

    protected void hideProgressD() {
        if (mProgressD != null && mProgressD.isShowing()) {
            mProgressD.dismiss();
        }
    }

    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    protected void showSnack(String message) {
        try {
            showSnackMessage(getActivity(), message, false);
        } catch (Exception e) {
        }
    }

    protected Bitmap GetBitmapFromEncodedImage(String encodedString) {
        if (!encodedString.equals("")) {
            byte[] decodeString = Base64.decode(encodedString.getBytes(), Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
            return decodedBitmap;
        } else return null;
    }

    public void showErrorMessageDialog(final Context mActivity, final String heading, final String title, final boolean onBackClose, final Utils.OnDialogOkClickListener onDialogOkClickListener) {
        Dialog mDialog = new Dialog(mActivity, R.style.CustomDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.di_err);
        mDialog.setCancelable(false);
        TextView title_error = mDialog.findViewById(R.id.title_error);
        title_error.setText(heading);
        TextView title_error_msg = mDialog.findViewById(R.id.title_error_msg);
        title_error_msg.setText(title);
        Button okButton = mDialog.findViewById(R.id.okButton);
        okButton.setOnClickListener(v -> {
            if (onDialogOkClickListener != null) {
                onDialogOkClickListener.onOkButtonClicked();
            }
            mDialog.dismiss();
        });
        mDialog.setOnKeyListener((arg0, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (onBackClose) {
                    mDialog.dismiss();
                }
            }
            return true;
        });
        mDialog.show();
    }

    protected void showMessageAlert(String title, String msg) {
        showErrorMessageDialog(getActivity(), title, msg, true, () -> {
        });
    }

    protected void showErrorAlert() {
        if (!IsInternetConnected(getActivity())) {
            showErrorMessageDialog(getActivity(), "Error", "Please check your Internet connection\nand try again!", true, () -> {
            });
        } else {
            showErrorMessageDialog(getActivity(), "Error", "Something went wrong,\nplease contact tech support!", true, () -> {
            });
        }
    }
}
