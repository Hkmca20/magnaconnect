/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VerifyResponse implements Serializable {
    @SerializedName("response_status")
    @Expose
    private String response_status;
    @SerializedName("err")
    @Expose
    private String err;
    @SerializedName("otp")
    @Expose
    private String userOtp;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("userStatus")
    @Expose
    private String userStatus;
    @SerializedName("userPassword")
    @Expose
    private String userPassword;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("shopName")
    @Expose
    private String shopName;
    @SerializedName("GSTNo")
    @Expose
    private String GSTNo;
    @SerializedName("userEmail")
    @Expose
    private String userEmail;
    @SerializedName("userDor")
    @Expose
    private String userDor;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("emp_code")
    @Expose
    private String emp_code;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("log")
    @Expose
    private String log;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("partnerType")
    @Expose
    private String partnerType;
    @SerializedName("userType")
    @Expose
    private String userType;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("distributorId")
    @Expose
    private String distributorId;

    public String getResponse_status() {
        return response_status;
    }

    public String getErr() {
        return err;
    }

    public void setUserOtp(String userOtp) {
        this.userOtp = userOtp;
    }

    public String getUserOtp() {
        return userOtp;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getShopName() {
        return shopName;
    }

    public String getGSTNo() {
        return GSTNo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserDor() {
        return userDor;
    }

    public String getUid() {
        return uid;
    }

    public String getEmp_code() {
        return emp_code;
    }

    public String getLat() {
        return lat;
    }

    public String getLog() {
        return log;
    }

    public String getPincode() {
        return pincode;
    }

    public String getAddress() {
        return address;
    }

    public String getState() {
        return state;
    }

    public String getPartnerType() {
        return partnerType;
    }

    public String getUserType() {
        return userType;
    }

    public String getId() {
        return id;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }
}
