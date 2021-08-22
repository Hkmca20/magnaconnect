/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.utils;

import android.os.AsyncTask;

public class ServerTask extends AsyncTask<String,Integer,Tuple<String,Object>> {

//    public ServerRequest serverRequest = null;
    private IAsyncTaskCompleteListener callBackListener;

    public ServerTask(IAsyncTaskCompleteListener listener) {
        callBackListener = listener;
//        serverRequest = new ServerRequest();
    }

    @Override
    protected Tuple<String,Object> doInBackground(String... urls) {
        try {
            Object response = null;//serverRequest.Call(urls[0]);
            return new Tuple<String, Object>(urls[0], response);
        }
        catch(Exception ex){
            throw ex;
        }
    }

    protected void onProgressUpdate(Integer... progress) {
        //Yet to code
    }

    protected void onPostExecute(Tuple<String,Object> result){
        callBackListener.onAsyncTaskComplete(result);
    }

}
