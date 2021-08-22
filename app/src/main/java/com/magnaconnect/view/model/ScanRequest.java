/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScanRequest {

    @SerializedName("serialNo")
    @Expose
    private String serialNo;

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("distsId")
    @Expose
    private String distsId;

    @SerializedName("toName")
    @Expose
    private String toName;

    @SerializedName("toMobileNumber")
    @Expose
    private String toMobileNumber;

    @SerializedName("toPincode")
    @Expose
    private String toPincode;

    @SerializedName("toAddress")
    @Expose
    private String toAddress;

    // TODO: 1/18/2020 --------for attandance params-----------start
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("latIn")
    @Expose
    private String latIn;

    @SerializedName("logIn")
    @Expose
    private String logIn;

    @SerializedName("latOut")
    @Expose
    private String latOut;

    @SerializedName("logOut")
    @Expose
    private String logOut;
    // TODO: 1/18/2020 --------for attandance params-----------end

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDistsId(String distsId) {
        this.distsId = distsId;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public void setToMobileNumber(String toMobileNumber) {
        this.toMobileNumber = toMobileNumber;
    }

    public void setToPincode(String toPincode) {
        this.toPincode = toPincode;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLatIn(String latIn) {
        this.latIn = latIn;
    }

    public void setLogIn(String logIn) {
        this.logIn = logIn;
    }

    public void setLatOut(String latOut) {
        this.latOut = latOut;
    }

    public void setLogOut(String logOut) {
        this.logOut = logOut;
    }
}
