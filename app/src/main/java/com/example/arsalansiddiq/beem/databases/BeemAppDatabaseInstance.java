package com.example.arsalansiddiq.beem.databases;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.arsalansiddiq.beem.databases.room.BeemAppDatabase;

public class BeemAppDatabaseInstance {

    private static Context context;
    private static BeemAppDatabase beemAppDatabase = null;

    public BeemAppDatabaseInstance(Context context) {
        this.context = context;
    }


//    Room.databaseBuilder(context.getApplicationContext(),
//            BeemAppDatabase.class, "beem-database").build();

    public static BeemAppDatabase getBeemDatabaseInstance() {
        if (beemAppDatabase == null) {
            beemAppDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    BeemAppDatabase.class, "beem-database").build();
        }

        return beemAppDatabase;
    }
}
