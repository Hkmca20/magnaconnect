package com.magnaconnect.view.activity;

import android.util.Log;

class MyTestClass {
    static final String TAG = MyTestClass.class.getSimpleName();
    int id;
    String name;

    void display() {
        Log.d(TAG, "---id=" + id + ", name=" + name);
    }
}
