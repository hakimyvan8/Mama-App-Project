package com.igor.mamba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.igor.mamba.User.Supplier;
import com.igor.mamba.User.User;
import com.igor.mamba.User.login;


public class SharedPrefManagerSUPPLIER {


    private static final String SHARED_PREF_NAME = "MambaDispatch";
    private static final String SUPPLIERUUID = "SUPPLIERUUID";
    private static final String SUPPLIEREMAIL = "SUPPLIEREMAIL";
    private static final String SUPPLIERSTATE = "SUPPLIERSTATE";
    private static final String SUPPLIERDISTRICT = "SUPPLIERDISTRICT";
    private static final String SUPPLIERBUSINESS = "SUPPLIERBUSINESS";
    private static final String SUPPLIERPHONE= "SUPPLIERPHONE";


    private static SharedPrefManagerSUPPLIER mInstance;
    private static Context mCtx;

    private SharedPrefManagerSUPPLIER(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManagerSUPPLIER getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerSUPPLIER(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void supplierLogin(Supplier supplr) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SUPPLIERUUID, supplr.getUuid());
        editor.putString(SUPPLIEREMAIL, supplr.getSupplier_email());
        editor.putString(SUPPLIERSTATE, supplr.getSupplier_state());
        editor.putString(SUPPLIERDISTRICT, supplr.getSupplier_district());
        editor.putString(SUPPLIERBUSINESS, supplr.getSupplier_Business());
        editor.putString(SUPPLIERPHONE, supplr.getSupplier_phone());
        editor.apply();
    }
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SUPPLIERPHONE, null) != null;
    }


    public Supplier getSupplier() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Supplier(

                sharedPreferences.getString(SUPPLIERUUID,null),
                sharedPreferences.getString(SUPPLIEREMAIL,null),
                sharedPreferences.getString(SUPPLIERSTATE, null),
                sharedPreferences.getString(SUPPLIERDISTRICT, null),
                sharedPreferences.getString(SUPPLIERBUSINESS, null),
                sharedPreferences.getString(SUPPLIERPHONE, null)
        );
    }


    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(mCtx, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);

    }
}