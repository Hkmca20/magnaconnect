/*
 * Created by Hariom.Gupta.Gurugram on 02/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.dialog;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class BDialog extends AlertDialog {
    protected BDialog(@NonNull Context context) {
        super(context);
    }

    protected BDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
