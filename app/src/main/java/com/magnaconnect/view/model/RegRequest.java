/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegRequest {
    @SerializedName("userEmail")
    @Expose
    private String userEmail;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("shopName")
    @Expose
    private String shopName;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("GSTNo")
    @Expose
    private String GSTNo;
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
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("altMobileNo")
    @Expose
    private String altMobileNo;
    @SerializedName("userPassword")
    @Expose
    private String userPassword;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("category")
    @Expose
    private String category;

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setGSTNo(String GSTNo) {
        this.GSTNo = GSTNo;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setAltMobileNo(String altMobileNo) {
        this.altMobileNo = altMobileNo;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
