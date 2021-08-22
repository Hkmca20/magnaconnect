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

import com.magnaconnect.BuildConfig;
import com.magnaconnect.utils.Cons;
import com.magnaconnect.view.model.AttResponse;
import com.magnaconnect.view.model.DashResponse;
import com.magnaconnect.view.model.DlrRequest;
import com.magnaconnect.view.model.EmpRequest;
import com.magnaconnect.view.model.LoginRequest;
import com.magnaconnect.view.model.ProdResponse;
import com.magnaconnect.view.model.RegRequest;
import com.magnaconnect.view.model.ScanRequest;
import com.magnaconnect.view.model.ScnResponse;
import com.magnaconnect.view.model.StatResponse;
import com.magnaconnect.view.model.VerifyResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IServices {
    String USER_LOGIN = "login/userLogin";
    String REGISTER_USER = "login/registerUser";
    String CHANGE_PASSWORD = "change-password/{id}";
    String SERIAL_NO_SCANNING = "scanTransaction/serialNoScanning";
    String SERIAL_NO_VERIFICATION = "scanTransaction/serialNoVerification";
    String DASHBOARD = "scanTransaction/dashboard";
    String EMP_DASHBOARD = "empvisit/empDashboard";
    String EMP_DEALER_LIST = "empvisit/empDealerList";
    String DEALER_LIST = "empvisit/DealerList";
    String DISTRIBUTOR_LIST = "empvisit/distributorList";
    String PRODUCT_LIST = "empvisit/productList";
    String EMP_SUBMIT_JSON = "empvisit/empSubJson";
    String EMP_GET_ATT = "empvisit/getAtt";
    String EMP_GET_ATTENDANCE = "empvisit/getAttendance";
    String EMP_STATE_LIST = "empvisit/getStateDataList";
    String EMP_VERIFY_DEALER = "empvisit/verifyDealer";
    String EMP_CREATE_DEALER = "empvisit/createDlr";
    String EMP_DISTRIBUTOR_DEALER_LIST = "empvisit/distributorDealerList";
    ApiClient.CATEGORY c = null;

    @GET(DISTRIBUTOR_LIST + "?")
    Call<List<LoginRequest>> li(
            @Query("q") String query,
            @Query("settings") String token
    );

    @Headers("Content-Type: application/json")
    @POST(USER_LOGIN)
    Call<LoginRequest> no(@Body String requestString);

    @FormUrlEncoded
    @POST(USER_LOGIN + "pdb?")
    Call<VerifyResponse> fields(@Field("name") String name, @Field("job") String job);
//    @GET("params")
    @POST(USER_LOGIN)
    Call<List<VerifyResponse>> userLogin(@Body LoginRequest req);

    @POST(REGISTER_USER)
    Call<VerifyResponse> signup(@Body RegRequest req);

    @POST(CHANGE_PASSWORD)
    Call<VerifyResponse> password(@Path("id") String userId, @Body LoginRequest req);

    @POST(SERIAL_NO_SCANNING)
    Call<ScnResponse> scan(@Body ScanRequest req);

    @POST(SERIAL_NO_VERIFICATION)
    Call<List<ScnResponse>> slverify(@Body ScanRequest req);

    @POST(DASHBOARD)
    Call<DashResponse> dbo(@Body ScanRequest req);

    @POST(EMP_DASHBOARD)
    Call<ProdResponse> edbo();

    @POST(EMP_DEALER_LIST)
    Call<List<ProdResponse>> edl(@Body ScanRequest req);

    @POST(DEALER_LIST)
    Call<List<VerifyResponse>> dlList(@Body ScanRequest req);

    @POST(DISTRIBUTOR_LIST)
    Call<List<VerifyResponse>> distList(@Body ScanRequest req);

    @POST(EMP_DISTRIBUTOR_DEALER_LIST)
    Call<List<VerifyResponse>> distdList(@Body ScanRequest req);

    @POST(PRODUCT_LIST)
    Call<List<ProdResponse>> pList();

    @POST(EMP_SUBMIT_JSON)
    Call<List<ProdResponse>> esubJson(@Body EmpRequest req);

    @POST(EMP_GET_ATTENDANCE)
    Call<VerifyResponse> esubatt(@Body ScanRequest req);

    @POST(EMP_GET_ATT)
    Call<List<AttResponse>> empgetatt(@Body ScanRequest req);

    @POST(EMP_STATE_LIST)
    Call<StatResponse> getstList(@Body ScanRequest req);

    @POST(EMP_VERIFY_DEALER)
    Call<List<VerifyResponse>> verifydl(@Body LoginRequest req);

    @POST(EMP_CREATE_DEALER)
    Call<List<VerifyResponse>> createdlr(@Body DlrRequest req);

    class Factory implements Cons {
        public static IServices create(Context context, boolean cached) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.connectTimeout(REQUEST_TIMEOUT_CONNECT, TimeUnit.SECONDS);
            builder.readTimeout(REQUEST_TIMEOUT_READ, TimeUnit.SECONDS);
            builder.writeTimeout(REQUEST_TIMEOUT_WRITE, TimeUnit.SECONDS);
            //builder.certificatePinner(new CertificatePinner.Builder().add("*.androidadvance.com", "sha256/RqzElicVPA6LkKm9HblOvNOUqWmD+4zNXcRb+WjcaAE=")
            //    .add("*.xxxxxx.com", "sha256/8Rw90Ej3Ttt8RRkrg+WYDS9n7IS03bk5bjP/UXPtaY8=")
            //    .add("*.xxxxxxx.com", "sha256/Ko8tivDrEjiY90yGasP6ZpBU4jwXvHqVvQI0GS3GNdA=")
            //    .add("*.xxxxxxx.com", "sha256/VjLZe/p3W/PJnd6lL8JVNBCGQBZynFLdZSTIqcO0SJ8=")
            //    .build());
            //builder.addNetworkInterceptor().add(chain -> {
            //  Request request = chain.request().newBuilder().addHeader("Authorization", authToken).build();
            //  return chain.proceed(request);
            //});
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
            //builder.addInterceptor(new UnauthorisedInterceptor(context));
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
            OkHttpClient client = builder.build();
            Gson gson = new GsonBuilder().setDateFormat(DATE_PATTERN).setLenient().create();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client)
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
            return retrofit.create(IServices.class);
        }
    }
}