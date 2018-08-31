package com.example.arsalansiddiq.beem.models;

public class HolderListModel {

    private int storeId;
    private int salesId;
    private String oDate;
    private String brand;
    private int skuCategory;
    private int SKU;
    private int saleType;
    private int noItem;
    private int price;
    private int sAmount;


    public HolderListModel(int storeId, int salesId, String oDate, String brand, int skuCategory, int SKU, int saleType, int noItem, int price, int sAmount) {
        this.storeId = storeId;
        this.salesId = salesId;
        this.oDate = oDate;
        this.brand = brand;
        this.skuCategory = skuCategory;
        this.SKU = SKU;
        this.saleType = saleType;
        this.noItem = noItem;
        this.price = price;
        this.sAmount = sAmount;
    }


    public int getStoreId() {
        return storeId;
    }

    public int getSalesId() {
        return salesId;
    }

    public String getoDate() {
        return oDate;
    }

    public String getBrand() {
        return brand;
    }

    public int getSkuCategory() {
        return skuCategory;
    }

    public int getSKU() {
        return SKU;
    }

    public int getSaleType() {
        return saleType;
    }

    public int getNoItem() {
        return noItem;
    }

    public int getPrice() {
        return price;
    }

    public int getsAmount() {
        return sAmount;
    }
}
