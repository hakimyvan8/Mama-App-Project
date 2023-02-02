package com.igor.mamba.DriverChat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.igor.mamba.CustomerChat.enquiryModel;
import com.igor.mamba.DriverselectActivity;
import com.igor.mamba.R;
import com.igor.mamba.RequestHandle;
import com.igor.mamba.SharedPrefManagerDriver;
import com.igor.mamba.URLs;
import com.igor.mamba.User.Driver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class driverenquiryActivity extends AppCompatActivity {


    ImageView back;
    TextView username;

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

        Driver user = SharedPrefManagerDriver.getInstance(this).getDriver();

        username.setText(user.getDriverFullname());

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DriverselectActivity.class));
                driverenquiryActivity.this.finish();
            }
        });

        enquirymodel = new ArrayList<>();
        loadData();
        View view = getLayoutInflater().inflate(R.layout.row_ordereditem, null, true);
    }


    private void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLsenquiry, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                enquirymodel.clear();
                //  Toast.makeText(getApplicationContext(), "Show something", Toast.LENGTH_SHORT).show();
                try {
                    jsonObject = new JSONObject(response);
                    jsonArray = jsonObject.getJSONArray("enqlist");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject enqi = jsonArray.getJSONObject(i);
                        String Id = enqi.getString("id");
                        String Ordernumber = enqi.getString("ordernumber");
                        String ToId = enqi.getString("to_id");//to driver
                        String FromId = enqi.getString("from_id");//from user
                        String date = enqi.getString("date");
                        String rcvrName = enqi.getString("receiverName");
                        String rcvrPhone = enqi.getString("receiverPhone");

                        enquiryModel enquiryModl = new enquiryModel();


                        enquiryModl. setId(Id);

                        enquiryModl.setOrdernumber(Ordernumber);

                        enquiryModl.setTo_id(ToId);

                        enquiryModl.setFrom_id(FromId);

                        enquiryModl.setDate(date);

                        enquiryModl.setReceiverName(rcvrName);

                        enquiryModl.setReceiverPhone(rcvrPhone);

                        enquirymodel.add(enquiryModl);
                    }
                    enquiryAdapter = new enquirylistDriverAdapter( driverenquiryActivity.this,enquirymodel);
                    recycler.setAdapter(enquiryAdapter);
                    enquiryAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid", SharedPrefManagerDriver.getInstance(getApplicationContext()).getDriver().getDriverId());

                return params;
            }
        };
        RequestHandle.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    }
