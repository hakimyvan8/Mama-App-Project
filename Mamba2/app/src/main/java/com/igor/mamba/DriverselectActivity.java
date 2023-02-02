package com.igor.mamba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.igor.mamba.User.Driver;
import com.igor.mamba.User.DriverComplete;
import com.igor.mamba.DriverChat.driverenquiryActivity;

public class DriverselectActivity extends AppCompatActivity {

    TextView usernameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverselect);

        usernameView = findViewById(R.id.usernameView);


        Driver driver = SharedPrefManagerDriver.getInstance(this).getDriver();

        usernameView.setText(driver.getDriverFullname());


        findViewById(R.id.carto1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    startActivity(new Intent(getApplicationContext(), Driverhome.class));
                DriverselectActivity.this.finish();

            }
        });

        findViewById(R.id.cart2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    startActivity(new Intent(getApplicationContext(), DriverComplete.class));
                DriverselectActivity.this.finish();


}
        });

        findViewById(R.id.card3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    startActivity(new Intent(getApplicationContext(), Driverenroute.class));

            }
        });

        findViewById(R.id.card5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(), driverenquiryActivity.class));
            }
        });

        findViewById(R.id.imageView6).setOnClickListener(view ->{

            SharedPrefManagerDriver.getInstance(getApplicationContext()).logout();
        });
    }
}