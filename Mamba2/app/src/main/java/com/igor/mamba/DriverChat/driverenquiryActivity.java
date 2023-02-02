package com.igor.mamba.DriverChat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.igor.mamba.CustomerChat.enquiryModel;
import com.igor.mamba.R;
import com.igor.mamba.URLs;
import com.igor.mamba.User.enquirylistDriverAdapter;
import com.igor.mamba.feedbackAdapterReplyUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class driverenquiryActivity extends AppCompatActivity {


    ImageView back;
    TextView username;


    feedbackAdapterReplyUser enquiryAdapterReplyUser;

    private String URLsenquiry = "http://" + URLs.IP + "/admin_area/enquirylistDriver.php";

    enquirylistDriverAdapter enquiryAdapter;
    JSONArray jsonArray = null;
    RecyclerView recycler,recyclerII;
    ArrayList<enquiryModel> enquirymodel;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverenquiry);


        back = findViewById(R.id.back);
        recycler = findViewById(R.id.enquiryRv);
        username = findViewById(R.id.username);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    }
