package com.igor.mamba.Supplier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.igor.mamba.R;
import com.igor.mamba.SupFormActivity;
import com.igor.mamba.User.Supplier;

public class HomeSupplier extends AppCompatActivity  implements Runnable {

    ImageView logout,homeView,forumView,settingView;
    TextView usernameView,sup_portal;
    Button newsupply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_supplier);

        usernameView = findViewById(R.id.usernameView);
        homeView = findViewById(R.id.homeView);




    }

    @Override
    public void run() {

    }
}