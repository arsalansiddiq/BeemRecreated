package com.example.arsalansiddiq.beem.databases;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.arsalansiddiq.beem.utils.Constants;

import static android.content.Context.MODE_PRIVATE;

public class BeemPreferences {

    private Context context;
    SharedPreferences.Editor sharedPreEditor;
    public static final String BA_ATTENDANCE_ID = "id";

    public BeemPreferences(Context context) {
        this.context = context;
    }

    public void initialize_and_createPreferences (int id) {
        sharedPreEditor = context.getSharedPreferences(BA_ATTENDANCE_ID, MODE_PRIVATE).edit();
        sharedPreEditor.putInt("baAttendanceID", id);
        sharedPreEditor.apply();
    }

    public void initialize_and_createPreferences_forStatus (int status) {
        sharedPreEditor = context.getSharedPreferences(Constants.SALE_STATUS, MODE_PRIVATE).edit();
        status += status;
        sharedPreEditor.putInt(Constants.KEY_SALE_STATUS, status);
        sharedPreEditor.apply();
    }

    public void initialize_and_createPreferences_forAttendanceStatus (int attendanceStatus) {
        sharedPreEditor = context.getSharedPreferences(Constants.ATTENDANCE_STATUS, MODE_PRIVATE).edit();
        sharedPreEditor.putInt(Constants.KEY_ATTENDANCE_STATUS, attendanceStatus);
        sharedPreEditor.apply();
    }

    public void initialize_and_createPreferences_forLoginSession (int loginStatus) {
        sharedPreEditor = context.getSharedPreferences(Constants.LOGIN_STATUS, MODE_PRIVATE).edit();
        sharedPreEditor.putInt(Constants.KEY_LOGIN_STATUS, loginStatus);
        sharedPreEditor.apply();
    }

}
