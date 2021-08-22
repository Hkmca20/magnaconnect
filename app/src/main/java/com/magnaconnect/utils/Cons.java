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
package com.magnaconnect.utils;

import com.google.firebase.messaging.FirebaseMessaging;
import com.magnaconnect.R;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public interface Cons {
    int Action_Camera = 200;
    int Request_Camera = 15;
    int LOCATION_SETTING_REQUEST = 999;
    int REQUEST_FINE_LOCATION = 1234;
    //ALL KEYS
    boolean D = false;
    int TIME_INTERVAL = 3000;
    long INTERVAL_30 = 30 * 1000;
    long INTERVAL_5 = 5 * 1000;
    int REQUEST_TIMEOUT_CONNECT = 30, REQUEST_TIMEOUT_READ = 35, REQUEST_TIMEOUT_WRITE = 30;
    long CACHE_SIZE = 10 * 1024 * 1024;
    int MAX_AGE = 60;
    long MAX_STALE = 60 * 60 * 24 * 7;
    Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+");
    String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    SimpleDateFormat serverFormat = new SimpleDateFormat(DATE_PATTERN);
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
    SimpleDateFormat returnDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    int JOB_ID = 101;
    String NOTIFICATION_CHANNEL_ID = "CH001";

    FirebaseCrashlytics fcr = FirebaseCrashlytics.getInstance();
    FirebaseMessaging fcm = FirebaseMessaging.getInstance();
    FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    PDB pdb = PDB.getInstance();
    String PF_BTS = "pf_magnetic";
    String PF_TOKEN = "pf_token";
    String PF_uName = "pf_uName";
    String Pf_m = "pf_m";
    String Pf_uId = "pf_uId";
    String Pf_userPass = "pf_userPass";
    String Pf_userType = "pf_userType";
    String Pf_last_pass = "pf_last_pass";
    String PF_GRID_L = "PF_GRID_L";
    String Pf_first_time = "pf_first_time";
    String Pf_cat = "pf_cat";
    String Pf_pu = "pf_pu";
    String Pf_last_login = "pf_last_login";
    String Pf_last_scan = "pf_last_scan";
    String Pf_sessionId = "pf_sessionId";
    String Pf_loginDate = "pf_loginDate";
    String Pf_mList = "pf_mList";
    String Pf_cList = "pf_cList";
    String BASE_URL = "http://54.179.134.136:9000/";
    String API_APP_ID = "WoiSfPvWGHnic1vuOSmifhVkYpn6xJ5oiTMcODP7";
    String PLUS_NINE_ONE = "+91";
    String TEL_PLUS_NINE_ONE = "tel:" + PLUS_NINE_ONE;
    int OPEN_DOCUMENT_CODE = 807;
    int FRAME_CONTAINER_ID = 808;
    int statusColor = R.color.colorPrimaryDark;
    int themeColor = R.color.colorPrimary;
    String MASTER_JSON = "masterJson";
    String LAT_LNG_INIT = "0.0";
    String LAT_F = "28.0123456789";
    String LNG_F = "77.0123456789";
    String MESSAGE_OK = "OK";
    String MESSAGE_ERROR = "ERROR";
    String MESSAGE_SUCCESS = "SUCCESS";
    String MESSAGE_ALERT = "Alert";
    String MESSAGE_FAILED = "Failed";
    String SCREEN_Counter = "Counter Status";
    String SCREEN_Market = "Marketing Requirement";
    String SCREEN_Track = "Track Order";
    String SCREEN_Select = "Select to go";
    String SCREEN_Dealer = "Dealer List";
    String SCREEN_Distrib = "Distributor List";
    String SCREEN_Summary = "Summary";
    String SCREEN_AddDlr = "Add Dealer";
    String SCREEN_STATUS = "Welcome to Magnaconnect";
    String SCREEN_Attend = "Attendance Status";
    String SCREEN_Punch = "Attendance";
    String SCREEN_Change = "Change Password";
    String SCREEN_Sign_up = "Sign-up";
    String FR_UpdateFrame = "UpdateFrame";
    String FR_HFragment = "HFragment";
    String FR_HEFragment = "HEFragment";
    String Frag_SIFragment = "SIFragment";
    String FR_DLFragment = "DLFragment";
    String FR_S2Fragment = "S2Fragment";
    String FR_S3Fragment = "S3Fragment";
    String FR_S4Fragment = "S4Fragment";
    String FR_S5Fragment = "S5Fragment";
    String FR_DFragment = "DFragment";
    String FR_IFragment = "IFragment";
    String FR_TFragment = "TFragment";
    String FR_PUFragment = "PUFragment";
    String FR_ATHFragment = "ATHFragment";
    String CAT_EMPLOYEE = "EMPLOYEE";
    String CAT_BUSINESS_ASSOCIATE = "BUSINESS_ASSOCIATE";
    String CAT_BUSINESS_PARTNER = "BUSINESS_PARTNER";
    String SWIPE_TO = "SWIPE TO SUBMIT";
    String STATUS_IN = "IN";
    String STATUS_OUT = "OUT";
    String STATUS_MEETING = "MEETING";
    String ARG_INVENTORY = "Inventory";
    String ARG_TERTIARY = "Tertiary";
    String ARG_DIS = "BT Price List";
    String ARG_DIS1 = "scheme BT coins";
    String ARG_DIS2 = "scheme BT Notes";
    String ARG_OTP = "arg_otp";
    String ARG_MOB = "arg_mob";
    String ARG_RES = "arg_res";
    String ARG_E = "e";
    String ARG_R = "r";
    String ARG_D = "d";
    String ARG_A = "A";
    String ARG_I = "I";
    String ARG_P = "P";
    String validLoginDate1 = "2021-08-21";
    String validLoginDate2 = "2021-08-22";
    String validLoginDate3 = "2021-08-23";
    String validLoginDate4 = "2021-08-24";
    String validLoginDate5 = "2021-08-25";
    String validLoginDate6 = "2021-08-26";
    String validLoginDate7 = "2021-08-27";
    String validLoginDate8 = "2021-08-28";
    String validLoginDate9 = "2021-08-29";
    String validLoginDate10 = "2021-08-20";
    enum CATEGORY {EMPLOYEE, BUSINESS_ASSOCIATE, BUSINESS_PARTNER}
    enum PUNCH {IN, OUT, MEETING, OTHER}
}
