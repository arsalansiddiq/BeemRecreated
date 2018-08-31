package com.example.arsalansiddiq.beem.models.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("brand")
    @Expose
    private String brand;

    @SerializedName("sales_id")
    @Expose
    private Integer sales_id;

    @SerializedName("order_id")
    @Expose
    private String order_id;

    @SerializedName("UT")
    @Expose
    private String uT;
    @SerializedName("StoreId")
    @Expose
    private String storeId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getSales_id() {
        return sales_id;
    }

    public void setSales_id(Integer sales_id) {
        this.sales_id = sales_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getuT() { return uT; }

    public void setuT(String uT) { this.uT = uT; }

    public String getStoreId() { return storeId; }

    public void setStoreId(String storeId) { this.storeId = storeId; }
}
