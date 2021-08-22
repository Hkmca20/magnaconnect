/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScnResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("err")
    @Expose
    private String message;

    @SerializedName("moduleNo")
    @Expose
    private String moduleNo;

    @SerializedName("moduleName")
    @Expose
    private String moduleName;

    @SerializedName("warranty")
    @Expose
    private String warranty;

    @SerializedName("tertiaryaward")
    @Expose
    private String tertiaryaward;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getModuleNo() {
        return moduleNo;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getWarranty() {
        return warranty;
    }

    public String getTertiaryaward() {
        return tertiaryaward;
    }
}
