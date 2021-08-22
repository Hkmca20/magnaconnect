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

public class DlrRequest {
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("distributorId")
    @Expose
    private String distributorId;
    @SerializedName("distsId")
    @Expose
    private String distsId;
    @SerializedName("stateCode")
    @Expose
    private String stateCode;
    @SerializedName("partnerType")
    @Expose
    private String partnerType;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("emailId")
    @Expose
    private String emailId;
    @SerializedName("userCode")
    @Expose
    private String userCode;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("log")
    @Expose
    private String log;
    @SerializedName("shopName")
    @Expose
    private String shopName;
    @SerializedName("gstn")
    @Expose
    private String gstn;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("userPassword")
    @Expose
    private String userPassword;

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public void setDistsId(String distsId) {
        this.distsId = distsId;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setGstn(String gstn) {
        this.gstn = gstn;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
