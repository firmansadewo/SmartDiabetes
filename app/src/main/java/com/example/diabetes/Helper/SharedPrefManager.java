package com.example.diabetes.Helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefManager {
    public static final String SP_DIABETES_APP = "spdiabetes";
    public static final String SP_USER_REGISTER= "spRegUser";
    public static final String SP_NAME= "spName";
    public static final String SP_EMAIL = "spEmail";

    public static final String SP_LOGIN = "spLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;




    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_DIABETES_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }



    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }


    public String getSPName(){
        return sp.getString(SP_NAME, "");
    }

    public String getSPEmail(){
        return sp.getString(SP_EMAIL, "");
    }

    public Boolean getSPLogin(){
        return sp.getBoolean(SP_LOGIN, false);
    }
}

