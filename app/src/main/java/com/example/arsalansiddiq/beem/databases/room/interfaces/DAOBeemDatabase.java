package com.example.arsalansiddiq.beem.databases.room.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.arsalansiddiq.beem.databases.room.tables.LoginInfoTable;
import com.example.arsalansiddiq.beem.databases.room.tables.SalesStatus;

import java.util.List;

/**
 * Created by jellani on 8/30/2018.
 */
@Dao
public interface DAOBeemDatabase {

//    @Insert
//    void insertUserInformation(LoginInfoTable loginInfoTable);


    @Insert
    void insertSaleStatus(SalesStatus salesStatus);

    @Query("SELECT * FROM salesstatus")
    List<SalesStatus> getAll();

}
