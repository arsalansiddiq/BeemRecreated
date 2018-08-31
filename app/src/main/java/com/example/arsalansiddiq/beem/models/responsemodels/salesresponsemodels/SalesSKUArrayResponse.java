package com.example.arsalansiddiq.beem.models.responsemodels.salesresponsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jellani on 8/19/2018.
 */

public class SalesSKUArrayResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Brand")
    @Expose
    private String brand;
    @SerializedName("brandId")
    @Expose
    private Integer brandId;
    @SerializedName("SKUCaategory")
    @Expose
    private String sKUCaategory;
    @SerializedName("CateId")
    @Expose
    private Integer cateId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("Price")
    @Expose
    private Integer price;
    @SerializedName("ItemPerCarton")
    @Expose
    private Integer itemPerCarton;
    @SerializedName("SKUImage")
    @Expose
    private String sKUImage;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getSKUCaategory() {
        return sKUCaategory;
    }

    public void setSKUCaategory(String sKUCaategory) {
        this.sKUCaategory = sKUCaategory;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getItemPerCarton() {
        return itemPerCarton;
    }

    public void setItemPerCarton(Integer itemPerCarton) {
        this.itemPerCarton = itemPerCarton;
    }

    public String getSKUImage() {
        return sKUImage;
    }

    public void setSKUImage(String sKUImage) {
        this.sKUImage = sKUImage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

}


