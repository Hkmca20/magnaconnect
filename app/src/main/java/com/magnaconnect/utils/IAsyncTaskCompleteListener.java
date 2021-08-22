/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.utils;

public interface IAsyncTaskCompleteListener extends Cons {

    void onAsyncTaskComplete(Tuple<String, Object> result);
}
