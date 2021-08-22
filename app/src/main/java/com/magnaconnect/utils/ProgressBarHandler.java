/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class ProgressBarHandler {
    private ProgressBar mProgressBar;
    private Context mContext;

    public ProgressBarHandler(Context context) {
        mContext = context;

        ViewGroup layout = (ViewGroup) ((Activity) context).findViewById(android.R.id.content).getRootView();

        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        Drawable progressDrawable = mProgressBar.getProgressDrawable().mutate();
        progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        mProgressBar.setProgressDrawable(progressDrawable);

//        int color = 0xFF00FFFF;
//        mProgressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
//        mProgressBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        mProgressBar.setSecondaryProgress(0);
        mProgressBar.setIndeterminate(false);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        RelativeLayout rl = new RelativeLayout(context);

        rl.setGravity(Gravity.CENTER_VERTICAL);
        rl.addView(mProgressBar);

        layout.addView(rl, params);

        hide();
    }

    public void setProgress(int progress) {
        mProgressBar.setProgress(progress);
        mProgressBar.setSecondaryProgress(progress);
    }

    public void show() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hide() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}