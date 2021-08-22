/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 *
 * Copyright 2014 KC Ochibili
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.magnaconnect.network;

import android.content.Context;
import android.util.Log;

import com.magnaconnect.BuildConfig;
import com.magnaconnect.R;
import com.magnaconnect.utils.Cons;
import com.magnaconnect.BApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.socks.library.KLog;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient implements Cons {
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = null;

    public static Retrofit getInstance() {
        if (okHttpClient == null) {
            initOkHttp(BApp.getInstance().getContext(), true);
        }
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setDateFormat(DATE_PATTERN).setLenient().create();
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit;
    }

    private static void initOkHttp(Context context, boolean cached) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT_CONNECT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT_READ, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT_WRITE, TimeUnit.SECONDS);
        try {
            getSSLSocketFactory(context);
        } catch (Exception e) {
            KLog.e("ERRO", String.valueOf(e));
        }
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        if (cached) {
            Cache cache = new Cache(context.getCacheDir(), CACHE_SIZE);
            builder.cache(cache);
            builder.addInterceptor(chain -> {
                Request request = chain.request();
                if (NetworkUtil.isNetworkConnected(context)) {
                    request = request.newBuilder()
                            .header("Cache-Control", "public, max-age=" + MAX_AGE)
                            .build();
                } else {
                    request = request.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + MAX_STALE)
                            .build();
                }
                return chain.proceed(request);
            });
        }
        builder.addNetworkInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("x-access-token", pdb.getString(PF_TOKEN))
                    .addHeader("X-Parse-Application-Id", API_APP_ID)
                    .addHeader("Accept", "application/json")
//                    .addHeader("Authorization", Credentials.basic("aUsername", "aPassword"))
                    .addHeader("Content-Type", "application/x-www-form-urlencoded");
            // Requests will be denied without API key
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        okHttpClient = builder.build();
    }

    public static SSLSocketFactory getSSLSocketFactory(Context c)
            throws CertificateException, KeyStoreException, IOException,
            NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream caInput = c.getResources().openRawResource(R.raw.cert);
        Certificate ca = cf.generateCertificate(caInput);
        caInput.close();
        KeyStore keyStore = KeyStore.getInstance("BKS");
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);
        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, wrappedTrustManagers, null);
        return sslContext.getSocketFactory();
    }

    private static TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        try {
                            originalTrustManager.checkClientTrusted(certs, authType);
                        } catch (CertificateException ignored) {
                        }
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        try {
                            originalTrustManager.checkServerTrusted(certs, authType);
                        } catch (CertificateException ignored) {
                        }
                    }
                }
        };
    }

}
