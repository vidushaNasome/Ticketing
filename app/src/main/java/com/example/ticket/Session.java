package com.example.ticket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public Session(Context cntx) {

        try {
            prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
        }catch(Exception e){}
    }


    public void setusename(String usename) {
        prefs.edit().putString("usename", usename).commit();
    }

    public String getusename() {
        String usename = prefs.getString("usename","");
        return usename;
    }
    public void logout(){
        editor.clear();
        editor.commit();

    }
}
