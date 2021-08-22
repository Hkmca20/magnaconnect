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

import java.util.ArrayList;
import java.util.List;

public class DashResponse {
    @SerializedName("dashboard")
    @Expose
    public Dashboard dab;
    @SerializedName("inventory")
    @Expose
    public List<Inventory> iList;
    @SerializedName("tertiary")
    @Expose
    public List<Tertiary> tList;

    public static class Dashboard {
        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("userMobileNumber")
        @Expose
        private String userMobileNumber;
        @SerializedName("totalAward")
        @Expose
        private String totalAward;
        @SerializedName("lastScanningDate")
        @Expose
        private String lastScanningDate;
        @SerializedName("secondaryCount")
        @Expose
        private String secondaryCount;
        @SerializedName("tertiaryCount")
        @Expose
        private String tertiaryCount;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("tertiaryAward")
        @Expose
        private String tertiaryAward;
        @SerializedName("lastSecondaryDate")
        @Expose
        private String lastSecondaryDate;
        @SerializedName("inventorycount")
        @Expose
        private String inventorycount;
        @SerializedName("secondaryaward")
        @Expose
        private String secondaryaward;
        @SerializedName("disName")
        @Expose
        private String displayName;
        @SerializedName("disName1")
        @Expose
        private String displayName1;
        @SerializedName("disName2")
        @Expose
        private String displayName2;
        @SerializedName("pdflink")
        @Expose
        private String pdflink;
        @SerializedName("pdflink1")
        @Expose
        private String pdflink1;
        @SerializedName("pdflink2")
        @Expose
        private String pdflink2;
        @SerializedName("profileimage")
        @Expose
        private String profileimage;
        @SerializedName("schemeimglink")
        @Expose
        private String schemeimglink;
        @SerializedName("schemeimglink1")
        @Expose
        private String schemeimglink1;
        @SerializedName("schemeimglink2")
        @Expose
        private String schemeimglink2;
        @SerializedName("inventory")
        @Expose
        private ArrayList<String> inventoryMenuList;
        @SerializedName("tertiary")
        @Expose
        private ArrayList<String> tertiaryMenuList;

        public String getUserName() {
            return userName;
        }

        public String getUserMobileNumber() {
            return userMobileNumber;
        }

        public String getTotalAward() {
            return totalAward;
        }

        public String getLastScanningDate() {
            return lastScanningDate;
        }

        public String getSecondaryCount() {
            return secondaryCount;
        }

        public String getTertiaryCount() {
            return tertiaryCount;
        }

        public String getId() {
            return id;
        }

        public String getTertiaryAward() {
            return tertiaryAward;
        }

        public String getLastSecondaryDate() {
            return lastSecondaryDate;
        }

        public String getInventorycount() {
            return inventorycount;
        }

        public String getSecondaryaward() {
            return secondaryaward;
        }

        public String getProfileimage() {
            return profileimage;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getDisplayName1() {
            return displayName1;
        }

        public String getDisplayName2() {
            return displayName2;
        }

        public String getPdflink() {
            return pdflink;
        }

        public String getPdflink1() {
            return pdflink1;
        }

        public String getPdflink2() {
            return pdflink2;
        }

        public String getSchemeimglink() {
            return schemeimglink;
        }

        public String getSchemeimglink1() {
            return schemeimglink1;
        }

        public String getSchemeimglink2() {
            return schemeimglink2;
        }

        public ArrayList<String> getInventoryMenuList() {
            return inventoryMenuList;
        }

        public ArrayList<String> getTertiaryMenuList() {
            return tertiaryMenuList;
        }
    }

    public static class Inventory {
        @SerializedName("serialNo")
        @Expose
        private String serialNo;
        @SerializedName("moduleNo")
        @Expose
        private String moduleNo;
        @SerializedName("moduleName")
        @Expose
        private String moduleName;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("toName")
        @Expose
        private String toName;
        @SerializedName("toUserId")
        @Expose
        private String toUserId;
        @SerializedName("billedById")
        @Expose
        private String billedById;
        @SerializedName("billedByName")
        @Expose
        private String billedByName;
        @SerializedName("billingDate")
        @Expose
        private String billingDate;
        @SerializedName("schemeId")
        @Expose
        private String schemeId;
        @SerializedName("primaryAward")
        @Expose
        private String primaryAward;
        @SerializedName("secondaryAward")
        @Expose
        private String secondaryAward;
        @SerializedName("primaryTo")
        @Expose
        private String primaryTo;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("cattype")
        @Expose
        private String cattype;

        public Inventory(String serialNo, String moduleNo, String moduleName, String state, String toName, String toUserId, String billedById, String billedByName, String billingDate, String schemeId, String primaryAward, String secondaryAward, String primaryTo, String id) {
            this.serialNo = serialNo;
            this.moduleNo = moduleNo;
            this.moduleName = moduleName;
            this.state = state;
            this.toName = toName;
            this.toUserId = toUserId;
            this.billedById = billedById;
            this.billedByName = billedByName;
            this.billingDate = billingDate;
            this.schemeId = schemeId;
            this.primaryAward = primaryAward;
            this.secondaryAward = secondaryAward;
            this.primaryTo = primaryTo;
            this.id = id;
        }

        public String getSerialNo() {
            return serialNo;
        }

        public String getModuleNo() {
            return moduleNo;
        }

        public String getModuleName() {
            return moduleName;
        }

        public String getState() {
            return state;
        }

        public String getToName() {
            return toName;
        }

        public String getToUserId() {
            return toUserId;
        }

        public String getBilledById() {
            return billedById;
        }

        public String getBilledByName() {
            return billedByName;
        }

        public String getBillingDate() {
            return billingDate;
        }

        public String getSchemeId() {
            return schemeId;
        }

        public String getPrimaryAward() {
            return primaryAward;
        }

        public String getSecondaryAward() {
            return secondaryAward;
        }

        public String getPrimaryTo() {
            return primaryTo;
        }

        public String getId() {
            return id;
        }

        public String getCattype() {
            return cattype;
        }
    }

    public static class Tertiary {
        @SerializedName("serialNo")
        @Expose
        private String serialNo;
        @SerializedName("moduleNo")
        @Expose
        private String moduleNo;
        @SerializedName("moduleName")
        @Expose
        private String moduleName;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("toName")
        @Expose
        private String toName;
        @SerializedName("toUserId")
        @Expose
        private String toUserId;
        @SerializedName("billedById")
        @Expose
        private String billedById;
        @SerializedName("billedByName")
        @Expose
        private String billedByName;
        @SerializedName("billingDate")
        @Expose
        private String billingDate;
        @SerializedName("schemeId")
        @Expose
        private String schemeId;
        @SerializedName("primaryAward")
        @Expose
        private String primaryAward;
        @SerializedName("secondaryAward")
        @Expose
        private String secondaryAward;
        @SerializedName("primaryTo")
        @Expose
        private String primaryTo;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("cattype")
        @Expose
        private String cattype;

        public String getSerialNo() {
            return serialNo;
        }

        public String getModuleNo() {
            return moduleNo;
        }

        public String getModuleName() {
            return moduleName;
        }

        public String getState() {
            return state;
        }

        public String getToName() {
            return toName;
        }

        public String getToUserId() {
            return toUserId;
        }

        public String getBilledById() {
            return billedById;
        }

        public String getBilledByName() {
            return billedByName;
        }

        public String getBillingDate() {
            return billingDate;
        }

        public String getSchemeId() {
            return schemeId;
        }

        public String getPrimaryAward() {
            return primaryAward;
        }

        public String getSecondaryAward() {
            return secondaryAward;
        }

        public String getPrimaryTo() {
            return primaryTo;
        }

        public String getId() {
            return id;
        }

        public String getCattype() {
            return cattype;
        }
    }
}
