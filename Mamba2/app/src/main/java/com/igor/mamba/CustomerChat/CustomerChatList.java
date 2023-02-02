package com.igor.mamba.CustomerChat;

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
import com.igor.mamba.Home;
import com.igor.mamba.R;
import com.igor.mamba.RequestHandle;
import com.igor.mamba.SharedPrefManager;
import com.igor.mamba.URLs;
import com.igor.mamba.User.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerChatList extends AppCompatActivity {

    ImageView back;


    TextView UserName;

    private String URLsenquiry = "http://" + URLs.IP + "/admin_area/enquirylist.php";

    enquirylistCustomerAdapter enquiryAdapter;
    JSONArray jsonArray = null;
    RecyclerView recycler,recyclerII;
    ArrayList<enquiryModel> enquirymodel;
    JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquirydriverlist);
        back = findViewById(R.id.back);
        recycler = findViewById(R.id.enquiryRv);
        UserName = findViewById(R.id.username);
        recycler.setLayoutManager(new LinearLayoutManager(this));


        User user = SharedPrefManager.getInstance(this).getUser();

        UserName.setText(user.getFirstName());


        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
                CustomerChatList.this.finish();
            }
        });


        enquirymodel = new ArrayList<>();
        loadData();

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
                        String ToId = enqi.getString("to_id");
                        String FromId = enqi.getString("from_id");
                        String date = enqi.getString("date");
                        String rcvrName = enqi.getString("receiverName");
                        String rcvrPhone = enqi.getString("receiverPhone");
                        String orgnName = enqi.getString("originName");
                        String orgnPhone = enqi.getString("originPhone");

                        enquiryModel enquiryModl = new enquiryModel();


                        enquiryModl. setId(Id);

                        enquiryModl.setOrdernumber(Ordernumber);

                        enquiryModl.setTo_id(ToId);

                        enquiryModl.setFrom_id(FromId);

                        enquiryModl.setDate(date);

                        enquiryModl.setReceiverName(rcvrName);

                        enquiryModl.setReceiverPhone(rcvrPhone);

                        enquiryModl.setOriginName(orgnName);

                        enquiryModl.setOriginPhone(orgnPhone);

                        enquirymodel.add(enquiryModl);
                    }
                    enquiryAdapter = new enquirylistCustomerAdapter( CustomerChatList.this,enquirymodel);
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
                params.put("userid", SharedPrefManager.getInstance(getApplicationContext()).getUser().getUUID());

                return params;
            }
        };
        RequestHandle.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }




}