package com.example.arsalansiddiq.beem.models.responsemodels.salesresponsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jellani on 8/19/2018.
 */

public class SalesObjectResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("sku")
    @Expose
    private List<SalesSKUArrayResponse> sku = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SalesSKUArrayResponse> getSku() {
        return sku;
    }

    public void setSku(List<SalesSKUArrayResponse> sku) {
        this.sku = sku;
    }

}
