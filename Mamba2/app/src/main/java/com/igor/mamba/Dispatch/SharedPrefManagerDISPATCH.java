package com.igor.mamba.Dispatch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.igor.mamba.User.Dispatch;
import com.igor.mamba.User.login;


public class SharedPrefManagerDISPATCH {


    private static final String SHARED_PREF_NAME = "MambaSupplier";
    private static final String DISPATCHID = "DISPATCHID";
    private static final String DISPATCHEMAIL = "DISPATCHEMAIL";
    private static final String DISPATCHNAME = "DISPATCHNAME";
    private static final String DISPATHCIMAGE = "DISPATHCIMAGE";
    private static final String DISPATCHCONTACT = "DISPATCHCONTACT";
    private static final String DISPATCHCOUNTRY = "DISPATCHCOUNTRY";
    private static final String ADMINJOB= "ADMINJOB";
    private static final String ADMINABOUT= "ADMINABOUT";


    private static SharedPrefManagerDISPATCH mInstance;
    private static Context mCtx;

    private SharedPrefManagerDISPATCH(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManagerDISPATCH getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerDISPATCH(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void dispatchLogin(Dispatch dispatch) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(DISPATCHID, dispatch.getAdminId());
        editor.putString(DISPATCHEMAIL, dispatch.getAdminEmail());
        editor.putString(DISPATHCIMAGE, dispatch.getAdminEmail());
        editor.putString(DISPATCHNAME, dispatch.getAdminName());
        editor.putString(DISPATCHCONTACT, dispatch.getAdminContact());
        editor.putString(DISPATCHCOUNTRY, dispatch.getAdminCountry());
        editor.putString(ADMINJOB, dispatch.getAdminJob());
        editor.putString(ADMINABOUT, dispatch.getAdminAbout());
        editor.apply();
    }
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DISPATCHCONTACT, null) != null;
    }


    public Dispatch getDispatch() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Dispatch(

                sharedPreferences.getString(DISPATCHID, null),
                sharedPreferences.getString(DISPATCHEMAIL,null),
                sharedPreferences.getString(DISPATHCIMAGE, null),
                sharedPreferences.getString(DISPATCHNAME, null),
                sharedPreferences.getString(DISPATCHCONTACT, null),
                sharedPreferences.getString(DISPATCHCOUNTRY, null),
                sharedPreferences.getString(ADMINJOB, null),
                sharedPreferences.getString(ADMINABOUT, null)
        );
    }


    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(mCtx, dispatchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);

    }
}