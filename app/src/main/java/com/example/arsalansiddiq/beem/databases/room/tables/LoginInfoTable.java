package com.example.arsalansiddiq.beem.databases.room.tables;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by jellani on 8/30/2018.
 */

@Entity
public class LoginInfoTable {

    @PrimaryKey
    private int user_id;

    @ColumnInfo(name = "name")
    private String user_name;

    @ColumnInfo(name = "brand")
    private String brand;

    @ColumnInfo(name = "ut")
    private String ut;

    @ColumnInfo(name = "store_id")
    private String storeId;

    @ColumnInfo(name = "status")
    private int status;


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUt() {
        return ut;
    }

    public void setUt(String ut) {
        this.ut = ut;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
