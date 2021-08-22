/*
 * Created by Hariom.Gupta on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the Activity to be memorised in the
 * correct component.
 */
package com.magnaconnect.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttResponse {
    @SerializedName("userId")
    @Expose
    public String userId;
    @SerializedName("attendanceIn")
    @Expose
    public String attendanceIn;
    @SerializedName("attendanceOut")
    @Expose
    public String attendanceOut;
    @SerializedName("inLat")
    @Expose
    public String inLat;
    @SerializedName("inLog")
    @Expose
    public String inLog;
    @SerializedName("outLat")
    @Expose
    public String outLat;
    @SerializedName("outLog")
    @Expose
    public String outLog;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("response_status")
    @Expose
    public String response_status;
    @SerializedName("err")
    @Expose
    public String err;

    public String getUserId() {
        return userId;
    }

    public String getAttendanceIn() {
        return attendanceIn;
    }

    public String getAttendanceOut() {
        return attendanceOut;
    }

    public String getInLat() {
        return inLat;
    }

    public String getInLog() {
        return inLog;
    }

    public String getOutLat() {
        return outLat;
    }

    public String getOutLog() {
        return outLog;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public String getResponse_status() {
        return response_status == null ? "" : response_status;
    }

    public String getErr() {
        return err == null ? "" : err;
    }
}
