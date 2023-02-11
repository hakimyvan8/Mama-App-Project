package com.igor.mamba.Dispatch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.igor.mamba.AdminMod;
import com.igor.mamba.BottomSheetDiag;
import com.igor.mamba.BottomSheetDialogue;
import com.igor.mamba.Home;
import com.igor.mamba.R;
import com.igor.mamba.RequestHandle;
import com.igor.mamba.SharedPrefManagerDriver;
import com.igor.mamba.URLs;
import com.igor.mamba.User.Dispatch;
import com.igor.mamba.User.DriverLogin;
import com.igor.mamba.User.Order;
import com.igor.mamba.User.OrderDAssignedAdapter;
import com.igor.mamba.User.OrderDetailsAdapter;
import com.igor.mamba.User.OrderDispatchAdapter;
import com.igor.mamba.driverdetail;
import com.igor.mamba.driverdetailDispatch;
import com.igor.mamba.locationModel;
import com.igor.mamba.productAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.igor.mamba.URLs.ADMINS;
import static com.igor.mamba.URLs.DRIVERONLINE;

public class dispatchHome extends AppCompatActivity {

    FloatingActionButton fab;
    TextView usernameView, textView5;
    RecyclerView recyclerDriver,recyclerNewOrder,recyclerOrder;
    TextView tabDriversTv, tabOrdersTv, tabNewOrdersTv, tabAssignedOrdersTv;
    RelativeLayout DriversRL, ordersRL, AssignedOrdersRL;
    RecyclerView DriversRv, ordersRv;
    EditText searchProductEt;
    OrderDispatchAdapter cartAdapter;
    OrderDAssignedAdapter orderassigned;
    DriversAdapter driveradapter;
    List<driverdetail> dyga;
    ArrayList<Dispatc> orderList;
    ArrayList<DispatchAssigned> assignedList;
    Button buttonrefresh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_home);

        usernameView = findViewById(R.id.usernameView);
        textView5 = findViewById(R.id.textView5);
        tabDriversTv = findViewById(R.id.tabDriversTv);
        tabNewOrdersTv = findViewById(R.id.tabNewOrdersTv);
        tabAssignedOrdersTv = findViewById(R.id.tabAssignedOrdersTv);
        DriversRL = findViewById(R.id.DriversRL);
        ordersRL = findViewById(R.id.ordersRL);
        AssignedOrdersRL = findViewById(R.id.AssignedOrdersRL);
        fab = findViewById(R.id.fab);
        DriversRv = findViewById(R.id.DriversRv);
        ordersRv = findViewById(R.id.ordersRv);
        searchProductEt = findViewById(R.id.searchProductEt);
        buttonrefresh = findViewById(R.id.buttonrefresh);


        Dispatch dispatch = SharedPrefManagerDISPATCH.getInstance(this).getDispatch();

        usernameView.setText(dispatch.getAdminJob());
        textView5.setText(dispatch.getAdminContact());


        searchProductEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    driveradapter.getFilter().filter(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        findViewById(R.id.imageView6).setOnClickListener(view -> {

            SharedPrefManagerDISPATCH.getInstance(getApplicationContext()).logout();
        });



//        findViewById(R.id.contact).setOnClickListener(view -> {
//            startActivity(new Intent(getApplicationContext(), spinnerActivity.class));
//
//        });

        buttonrefresh.invalidate();

        tabDriversTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show shops
                showDriverUI();
            }
        });


        tabNewOrdersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show orders
                showNewOrdersUI();
            }
        });

        tabAssignedOrdersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show orders
                showAssignedOrdersUI();
            }
        });

        loadDrivers();
        loadOrders();
        loadContacts();
        loadAssignedOrders();
    }


    private void showDriverUI() {
        //show shops ui, hide orders ui
        DriversRL.setVisibility(View.VISIBLE);
        ordersRL.setVisibility(View.GONE);
        AssignedOrdersRL.setVisibility(View.GONE);


        searchProductEt.setVisibility(View.VISIBLE);
        ordersRL.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);

        tabDriversTv.setTextColor(getResources().getColor(R.color.white));
        tabDriversTv.setBackgroundResource(R.drawable.shape_rect05);

        tabNewOrdersTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        tabNewOrdersTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        tabAssignedOrdersTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        tabAssignedOrdersTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));

    }

    //showOrdersRecycelerView and UI
    private void showNewOrdersUI() {
        //show orders ui, hide shops ui
        DriversRL.setVisibility(View.GONE);
        ordersRL.setVisibility(View.VISIBLE);
        AssignedOrdersRL.setVisibility(View.GONE);


        searchProductEt.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);

        tabDriversTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        tabDriversTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        tabNewOrdersTv.setTextColor(getResources().getColor(R.color.white));
        tabNewOrdersTv.setBackgroundResource(R.drawable.shape_rect05);

        tabAssignedOrdersTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        tabAssignedOrdersTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    //showAssignedOrdersRecyclerView and UI
    private void showAssignedOrdersUI() {


        //show orders ui, hide shops ui
        DriversRL.setVisibility(View.GONE);
        ordersRL.setVisibility(View.GONE);
        AssignedOrdersRL.setVisibility(View.VISIBLE);

        searchProductEt.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);

        tabDriversTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        tabDriversTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        tabAssignedOrdersTv.setTextColor(getResources().getColor(R.color.white));
        tabAssignedOrdersTv.setBackgroundResource(R.drawable.shape_rect05);

        tabNewOrdersTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        tabNewOrdersTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));


    }


    private void loadDrivers() {

        recyclerDriver = findViewById(R.id.DriversRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerDriver.setLayoutManager(linearLayoutManager);
        recyclerDriver.setHasFixedSize(false);

        dyga = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.DRIVER, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                //  Toast.makeText(getApplicationContext(), "Show something", Toast.LENGTH_SHORT).show();
                dyga.clear();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("driver");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject driveri = jsonArray.getJSONObject(i);


                        String phoneGj = driveri.getString("phone");
                        String duke = driveri.getString("FullName");
                        String drvrID = driveri.getString("driverID");
                        String numbrPLate = driveri.getString("NumberPlate");
                        String license = driveri.getString("DriverLicense");
                        String status = driveri.getString("status");
                        String adminEmail = driveri.getString("admin_email");

                        driverdetail dets = new driverdetail();
                        dets.setFullName(duke);
                        dets.setDriverID(drvrID);
                        dets.setPhone(phoneGj);
                        dets.setNumberPlate(numbrPLate);
                        dets.setDriverLicense(license);
                        dets.setStatus(status);
                        dets.setAdmin_email(adminEmail);
                        dyga.add(dets);
                    }
                    driveradapter = new DriversAdapter(dispatchHome.this, dyga);
                    recyclerDriver.setAdapter(driveradapter);
                    driveradapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        RequestHandle.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }


    private void loadContacts() {


        findViewById(R.id.contact).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                BottomSheetDiag bottomSheet = new BottomSheetDiag(getApplicationContext());
                bottomSheet.show(getSupportFragmentManager(),
                        "ModalBottomSheet");

            }
        });
    }



    private void loadOrders() {

        recyclerNewOrder = findViewById(R.id.ordersRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerNewOrder.setLayoutManager(linearLayoutManager);
        recyclerNewOrder.setHasFixedSize(false);

        orderList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.INCOMINGORDER, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                //  Toast.makeText(getApplicationContext(), "Show something", Toast.LENGTH_SHORT).show();
                orderList.clear();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("products");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject driveri = jsonArray.getJSONObject(i);
                        int id = Integer.parseInt(driveri.getString("orderid"));
                        int grandtotal = Integer.parseInt(driveri.getString("grandTotal"));
                        int Status = Integer.parseInt(driveri.getString("status"));

                        String createdAt = driveri.getString("date");
                        String province = driveri.getString("province");

                        String mobile = driveri.getString("mobile");
                        String country = driveri.getString("country");
                        String city = driveri.getString("city");

                       Dispatc order = new Dispatc();

                        order.setOrderid(id);

                        order.setGrandtotal(grandtotal);

                        order.setStatus(Status);

                        order.setMobile(mobile);


                        order.setCreateAt(createdAt);

                        order.setProvince(province);

                        order.setCountry(country);

                        order.setCity(city);


                        order.setPaymentsecreat(driveri.getString("transcode"));


                        order.setPaymentStatus(driveri.getString("paymentStatus"));

                        order.setFirstname(driveri.getString("firstname"));

                        orderList.add(order);

                    }
                    cartAdapter = new OrderDispatchAdapter(dispatchHome.this, orderList);
                    recyclerNewOrder.setAdapter(cartAdapter);
                    cartAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        RequestHandle.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    private void loadAssignedOrders() {
        recyclerOrder = findViewById(R.id.AssignedOrdersRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerOrder.setLayoutManager(linearLayoutManager);
        recyclerOrder.setHasFixedSize(false);

        assignedList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.INCOMINGORDERASSIGNED, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                //  Toast.makeText(getApplicationContext(), "Show something", Toast.LENGTH_SHORT).show();
//                assignedList.clear();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("products");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject driveri = jsonArray.getJSONObject(i);
                        int id = Integer.parseInt(driveri.getString("orderid"));
                        int grandtotal = Integer.parseInt(driveri.getString("grandTotal"));
                        int Status = Integer.parseInt(driveri.getString("status"));

                        String createdAt = driveri.getString("date");
                        String province = driveri.getString("province");

                        String mobile = driveri.getString("mobile");
                        String country = driveri.getString("country");
                        String city = driveri.getString("city");

                        DispatchAssigned order = new DispatchAssigned();

                        order.setOrderid(id);

                        order.setGrandtotal(grandtotal);

                        order.setStatus(Status);

                        order.setMobile(mobile);


                        order.setCreateAt(createdAt);

                        order.setProvince(province);

                        order.setCountry(country);

                        order.setCity(city);


                        order.setPaymentsecreat(driveri.getString("transcode"));


                        order.setPaymentStatus(driveri.getString("paymentStatus"));

                        order.setFirstname(driveri.getString("firstname"));

                        assignedList.add(order);

                    }
                    orderassigned = new OrderDAssignedAdapter(dispatchHome.this, assignedList);
                    recyclerOrder.setAdapter(orderassigned);
                    orderassigned.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        RequestHandle.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }

}