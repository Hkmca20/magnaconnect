/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmpRequest {
    @SerializedName("orderSec")
    @Expose
    private List<OrderSec> orderSec;
    @SerializedName("counter")
    @Expose
    private List<Counter> counter;
    @SerializedName("marketing")
    @Expose
    private List<Marketing> marketing;
    @SerializedName("finalComment")
    @Expose
    private String finalComment;
    @SerializedName("userId")
    @Expose
    private String userId;

    public void setOrderSec(List<OrderSec> orderSec) {
        this.orderSec = orderSec;
    }

    public void setCounter(List<Counter> counter) {
        this.counter = counter;
    }

    public void setMarketing(List<Marketing> marketing) {
        this.marketing = marketing;
    }

    public void setFinalComment(String finalComment) {
        this.finalComment = finalComment;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static class OrderSec {
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
        @SerializedName("qunatity")
        @Expose
        private String qunatity;

        public void setModuleNo(String moduleNo) {
            this.moduleNo = moduleNo;
        }

        public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
        }

        public void setCapacity(String capacity) {
            this.capacity = capacity;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public void setWarranty(String warranty) {
            this.warranty = warranty;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setUnit_price(String unit_price) {
            this.unit_price = unit_price;
        }

        public void setQunatity(String qunatity) {
            this.qunatity = qunatity;
        }
    }

    public static class Counter {
        @SerializedName("Amaron")
        @Expose
        private String Amaron;
        @SerializedName("Okaya")
        @Expose
        private String Okaya;
        @SerializedName("Livguard")
        @Expose
        private String Livguard;
        @SerializedName("Livfast")
        @Expose
        private String Livfast;
        @SerializedName("Others")
        @Expose
        private String Others;
        @SerializedName("comment")
        @Expose
        private String comment;

        public void setAmaron(String amaron) {
            Amaron = amaron;
        }

        public void setOkaya(String okaya) {
            Okaya = okaya;
        }

        public void setLivguard(String livguard) {
            Livguard = livguard;
        }

        public void setLivfast(String livfast) {
            Livfast = livfast;
        }

        public void setOthers(String others) {
            Others = others;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

    public static class Marketing {
        @SerializedName("LeafPlates")
        @Expose
        private String LeafPlates;
        @SerializedName("Hodings")
        @Expose
        private String Hodings;
        @SerializedName("Banners")
        @Expose
        private String Banners;
        @SerializedName("BookLates")
        @Expose
        private String BookLates;
        @SerializedName("Pan")
        @Expose
        private String Pan;
        @SerializedName("Certificate")
        @Expose
        private String Certificate;
        @SerializedName("unit_price")
        @Expose
        private String unit_price;
        @SerializedName("Others")
        @Expose
        private String Others;
        @SerializedName("comment")
        @Expose
        private String comment;

        public String getLeafPlates() {
            return LeafPlates;
        }

        public String getHodings() {
            return Hodings;
        }

        public String getBanners() {
            return Banners;
        }

        public String getBookLates() {
            return BookLates;
        }

        public String getPan() {
            return Pan;
        }

        public String getCertificate() {
            return Certificate;
        }

        public String getUnit_price() {
            return unit_price;
        }

        public String getOthers() {
            return Others;
        }

        public String getComment() {
            return comment;
        }
    }
}
