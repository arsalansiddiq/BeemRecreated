package com.example.arsalansiddiq.beem.databases.room.tables;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class SalesStatus {

    @PrimaryKey(autoGenerate = true)
    private int ids;

    @ColumnInfo(name = "total_sales")
    private int total_sales;

    @ColumnInfo(name = "total_nosales")
    private int total_nosales;


    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public int getTotal_sales() {
        return total_sales;
    }

    public void setTotal_sales(int total_sales) {
        this.total_sales = total_sales;
    }

    public int getTotal_nosales() {
        return total_nosales;
    }

    public void setTotal_nosales(int total_nosales) {
        this.total_nosales = total_nosales;
    }
}
