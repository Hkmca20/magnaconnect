
/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatResponse {
    @SerializedName("stateList")
    @Expose
    private List<StateItem> stateList;
    @SerializedName("partnerType")
    @Expose
    private List<PartnerTypeItem> partnerTypeList;

    public List<StateItem> getStateList() {
        return stateList;
    }

    public List<PartnerTypeItem> getPartnerTypeList() {
        return partnerTypeList;
    }

    public static class StateItem {
        @SerializedName("stateCode")
        @Expose
        String cityId;
        @SerializedName("stateName")
        @Expose
        String cityName;
        @SerializedName("displayName")
        @Expose
        String displayName;

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }

    public static class PartnerTypeItem {
        @SerializedName("code")
        @Expose
        String code;
        @SerializedName("Type")
        @Expose
        String Type;

        public String getCode() {
            return code;
        }

        public String getType() {
            return Type;
        }
    }
}
