/*
 * Created by Hariom.Gupta on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the Activity to be memorised in the
 * correct component.
 */
package com.magnaconnect.view.fragment;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magnaconnect.R;
import com.magnaconnect.utils.Cons;
import com.magnaconnect.view.adapter.DAdapter;
import com.magnaconnect.view.contract.HISMvpView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static com.magnaconnect.utils.Utility.getCurrentTimeStamp;
import static com.magnaconnect.utils.Utility.notNullParams;

public class DFragment extends BFragment implements HISMvpView {
    private static String TAG = DFragment.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    AppCompatActivity activity;
    Cons.CATEGORY mCategory = Cons.CATEGORY.EMPLOYEE;
    long DOWNLOAD_TASK_ID;
    BroadcastReceiver onComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, activity.RESULT_OK);
            if (DOWNLOAD_TASK_ID == id) {
                showToast("Download Completed");
            }
        }
    };

    public static DFragment newInstance(String dLink, String iLink) {
        DFragment f = new DFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_D_LINK, dLink);
//        args.putString(ARG_I_LINK, iLink);
        f.setArguments(args);
        return f;
    }

    /*
     * @param context used to check the device version and DownloadManager information
     * @return true if the download manager is available
     */
    public static boolean isDownloadManagerAvailable(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return true;
        }
        return false;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //int example_argument = getArguments().getInt(ARG_EXAMPLE);
        activity = (AppCompatActivity) getActivity();
        setCurrentFragment(TAG);
        mCategory = getCFrom(pdb.getString(Pf_cat));
        return inflater.inflate(R.layout.fr_h, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragment(view, activity, mCategory.toString() + " SCHEME", statusColor, themeColor, true);
        activity.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        setLayout();
    }

    private void setLayout() {
        setMargins(mRecyclerView, 4, 4, 4, 4);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new DAdapter(activity, HFragment.getInstance().getdList());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        ((DAdapter) mAdapter).setOnItemClickListener((i, v) -> {
            switch (v.getId()) {
                case R.id.pdf_download_lnr:
                    if (getWritePermission()) {
                        startDownload(HFragment.getInstance().getdList().get(i).getCityId());
                    }
                    break;
            }
        });
        activity.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onDestroyView() {
        activity.unregisterReceiver(onComplete);
        super.onDestroyView();
    }

    private void startDownload(String urlString) {
        if (!notNullParams(urlString)) {
            showToast("URL not found,\nplease try later!");
            return;
        }
        if (!isDownloadManagerAvailable(activity)) {
            showToast("failed to download");
            return;
        }
        showToast("Download started...");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(urlString));
        request.setDescription("PDF_" + getUser());
        request.setTitle("pdf_" + getUser() + getCurrentTimeStamp());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "pdf_" + getUser() + getCurrentTimeStamp() + ".pdf");
        request.setAllowedOverMetered(true);// Set if download is allowed on Mobile network
        request.setAllowedOverRoaming(true);// Set if download is allowed on roaming network
        DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        DOWNLOAD_TASK_ID = manager.enqueue(request);
//      startDownload2(download_link);
    }
//    void okhttpdownload(String pdfUrlString) {
//        if (!isDownloadManagerAvailable(activity)) {
//            showToast("failed to download");
//            return;
//        }
//        File file = new File(activity.getExternalFilesDir(null), "pdf_" + getUser() + getCurrentTimeStamp());
//
//        /* Create a DownloadManager.Request with all the information necessary to start the download */
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdfUrlString))
//                .setTitle("Pdf_" + getUser())// Title of the Download Notification
//                .setDescription("Downloading pdf")// Description of the Download Notification
//                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
//                .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
////                        .setRequiresCharging(false)// Set if charging is required to begin the download
//                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
//                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
//        DownloadManager downloadManager = (DownloadManager) activity.getSystemService(DOWNLOAD_SERVICE);
//        DOWNLOAD_TASK_ID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
//    }

    public Boolean getWritePermission() {
        if (HasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            return true;
        else
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        return false;
    }

    @Override
    public void showHeaders(String headers) {
        fcr.setCustomKey(MESSAGE_ALERT, TAG);
    }

    @Override
    public void showError(String error) {
        fcr.log(error);
    }
}
