package com.example.arsalansiddiq.beem.databases.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.arsalansiddiq.beem.databases.room.interfaces.DAOBeemDatabase;
import com.example.arsalansiddiq.beem.databases.room.tables.LoginInfoTable;
import com.example.arsalansiddiq.beem.databases.room.tables.SalesStatus;


@Database(entities = {SalesStatus.class}, version = 1)
public abstract class BeemAppDatabase extends RoomDatabase {

    public abstract DAOBeemDatabase daoBeemDatabase();

}
