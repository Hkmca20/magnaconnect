/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProdResponse {
    //-----------------------emp start-----------
    @SerializedName("dashboard")
    @Expose
    public List<Dashboard> dashboard;
    @SerializedName("counters")
    @Expose
    private ArrayList<String> counters;
    @SerializedName("marketing")
    @Expose
    private ArrayList<String> marketing;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    //-----------------------emp end-----------
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("userEmail")
    @Expose
    private String userEmail;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("log")
    @Expose
    private String log;
    //-----------------------emp end-----------
    //-----------------------empstart-----------
    @SerializedName("moduleNo")
    @Expose
    private String moduleNo;
    @SerializedName("moduleName")
    @Expose
    private String moduleName;
    @SerializedName("capacity")
    @Expose
    private String capacity;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("warranty")
    @Expose
    private String warranty;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("unit_price")
    @Expose
    private String unit_price;
    private int quantity = 0;

    public List<Dashboard> getDashboardList() {
        return dashboard;
    }

    public ArrayList<String> getCounters() {
        return counters;
    }

    public ArrayList<String> getMarketing() {
        return marketing;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getUserName() {
        return userName;
    }

    //------------------

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getModuleNo() {
        return moduleNo;
    }

    //--------------
    public void setModuleNo(String moduleNo) {
        this.moduleNo = moduleNo;
    }

    public String getModuleName() {
        return moduleName == null ? "" : moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getCapacity() {
        return capacity == null ? "" : capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getProductName() {
        return productName == null ? "" : productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWarranty() {
        return warranty == null ? "" : warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit_price() {
        return unit_price == null ? "" : unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    //-----------------------emp product end-----------
    public static class Dashboard {
        @SerializedName("item")
        @Expose
        private String item;
        @SerializedName("count")
        @Expose
        private String count;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
}
