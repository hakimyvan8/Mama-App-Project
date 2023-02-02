package com.igor.mamba;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class aboutActivity extends AppCompatActivity {


    private ArrayList<adminModel> locationmodelarraylist;

    ExpandableRelativeLayout mycontent,mycontentII;
    ImageView btnopen,btnopenII,back;


    private String URLstrig = "http://"+URLs.IP+"/admin_area/Customer/populateadmin.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        back = findViewById(R.id.back);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        retrieveJSON();
    }



    private void retrieveJSON() {


        TextView denII = findViewById(R.id.denII);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstrig,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ///          Log.d("strrrrr", ">>" + response);

                        try {

                            JSONObject obj = new JSONObject(response);
                            if (obj.optString("status").equals("true")) {

                                locationmodelarraylist = new ArrayList<>();
                                JSONArray dataArray = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    adminModel playerModel = new adminModel();
                                    JSONObject dataobj = dataArray.getJSONObject(i);


                                    String drvrFD = dataobj.getString("admin_id");
                                    String phoneGj = dataobj.getString("admin_contact");


                                    adminModel admins = new adminModel();
                                    admins.setAdmin_id(drvrFD);
                                    admins.setAdmin_contact(phoneGj);

                                    locationmodelarraylist.add(playerModel);

                                    denII.setText(phoneGj);

                                   denII.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(Intent.ACTION_DIAL);
                                           intent.setData(Uri.parse("tel:" + phoneGj));
                                           // Start the activity to dial the phone number
                                           startActivity(intent);
                                           finish();

                                       }
                                   });

                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        //  Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue mQueue = Volley.newRequestQueue(this);
        mQueue.add(stringRequest);
    }


}