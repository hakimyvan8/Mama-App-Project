package com.igor.mamba.User;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.igor.mamba.AdminMod;
import com.igor.mamba.Driverenroute;
import com.igor.mamba.R;
import com.igor.mamba.RequestHandle;
import com.igor.mamba.URLs;
import com.igor.mamba.driverdetailDispatch;
import com.igor.mamba.itemspurchasedadapter;
import com.igor.mamba.purchaseditemsmodel;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.igor.mamba.URLs.ADMINS;
import static com.igor.mamba.URLs.DRIVERONLINE;

public class orderSheetDialogue extends BottomSheetDialogFragment {
    List<purchaseditemsmodel> purchaseditemsmodel;
    ArrayList<driverdetailDispatch> driverarraylist;
    itemspurchasedadapter adapter;
    TextView text6;
    RecyclerView recyclerView;
    ImageView callcentr;
    Spinner spinner;
    ArrayList<String> adminname = new ArrayList<String>();
    String locationid, phone, texterese;

    ArrayAdapter<String> dispatchAdapter;
    BottomSheetDialog dialog;
    BottomSheetBehavior<View> bottomSheetBehaviour;
    OrderDispatchAdapter orderDispatchAdapter;
    Context context;
    RequestQueue requestQueue;
    Bundle bundleIII;
    String adminid,adminName;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.manage_order, container, false);
        requestQueue = Volley.newRequestQueue(context);
        spinner = v.findViewById(R.id.algo_button);
        text6 = v.findViewById(R.id.textView6);
        callcentr = v.findViewById(R.id.callcentr);

        recyclerView = v.findViewById(R.id.songsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);

        purchaseditemsmodel = new ArrayList<>();

        Button adduser = v.findViewById(R.id.adduser);
        retrieveJSON();
        loadData();


       bundleIII = getArguments();
        if (bundleIII != null) {

            //textViews
            TextView choosetag = v.findViewById(R.id.textView6);
            TextView orderIdTv = v.findViewById(R.id.orderIdTv);
            TextView dateTv = v.findViewById(R.id.dateTv);

            TextView nameTv = v.findViewById(R.id.nameTv);
            TextView amountTv = v.findViewById(R.id.amountTv);


            TextView addressTv = v.findViewById(R.id.addressTv);

            TextView KgaddressTv = v.findViewById(R.id.KgaddressTv);

            TextView paymentTv = v.findViewById(R.id.paymentTv);

            TextView paymentStatusTv = v.findViewById(R.id.paymentStatusTv);


            //Strings
            String id = bundleIII.getString("id");
            String date = bundleIII.getString("date");

            String name = bundleIII.getString("name");
            String amount = bundleIII.getString("price");


            String address = bundleIII.getString("address");
            String payment = bundleIII.getString("payment");

            String province = bundleIII.getString("province");
            String paymentStatus = bundleIII.getString("paymentStatus");


            //Assign TextViews to strings
            orderIdTv.setText(id);
            dateTv.setText(date);
            nameTv.setText(name);
            amountTv.setText(amount);
            addressTv.setText(address);
            KgaddressTv.setText(province);
            paymentTv.setText(payment);
            paymentStatusTv.setText(paymentStatus);


        }

        callcentr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                assignDriver();
//                dialog.dismiss();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                adminid = driverarraylist.get(i).getDriverID();
                adminName = driverarraylist.get(i).getFullName();
                text6.setText(adminName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return v;
    }

    public orderSheetDialogue(Context context) {
        this.context = context;
    }


    //load ordered items

    private void loadData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PURCHASEDITEMS, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject orderi = jsonArray.getJSONObject(i);


                        purchaseditemsmodel purchaseditemsmodelpm = new purchaseditemsmodel();
                        purchaseditemsmodelpm.setStatus(orderi.getString("content"));
                        purchaseditemsmodelpm.setTitle(orderi.getString("title"));
                        purchaseditemsmodelpm.setPrice((float) orderi.getDouble("price"));
                        purchaseditemsmodelpm.setQuantity(orderi.getInt("quantity"));
                        purchaseditemsmodelpm.setProductimage(orderi.getString("product_img1"));

                        purchaseditemsmodel.add(purchaseditemsmodelpm);


                    }

                    adapter = new itemspurchasedadapter(context, purchaseditemsmodel);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("orderid",bundleIII.getString("id"));

                return params;
            }
        };
        RequestHandle.getInstance(context).addToRequestQueue(stringRequest);
    }





    //retrieve drivers from server and populate them to spiner
    private void retrieveJSON() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, DRIVERONLINE , null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            driverarraylist = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("driver");
                            for(int i=0; i<jsonArray.length(); i++) {

                                driverdetailDispatch dets = new driverdetailDispatch();
                                JSONObject driveri = jsonArray.getJSONObject(i);

                                dets.setPhone(driveri.getString("phone"));
                                dets.setFullName(driveri.getString("FullName"));
                                dets.setDriverID(driveri.getString("driverID"));
                                dets.setNumberPlate(driveri.getString("NumberPlate"));
                                dets.setDriverLicense(driveri.getString("DriverLicense"));
                                dets.setStatus(driveri.getString("status"));
                                dets.setAdmin_email(driveri.getString("admin_email"));


                                driverarraylist.add(dets);

                            }

                            for (int i = 0; i <driverarraylist.size(); i++){
                                adminname.add(driverarraylist.get(i).getFullName());

                            }

                            dispatchAdapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_item,adminname);
                            dispatchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(dispatchAdapter);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);

    }



    private void assignDriver() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.assign, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                                adminarraylist.clear();

                try {
                    JSONObject obj = new JSONObject(response);

                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("orderid", bundleIII.getString("id"));
                params.put("driverid", adminid);
                return params;
            }
        };

        RequestHandle.getInstance(context).addToRequestQueue(stringRequest);
        /////run stringrequest here update order to 3 for picklup aproval and 4 for delivered


    }


    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomSheetBehaviour = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
        //

        RelativeLayout layout = dialog.findViewById(R.id.relativityHah);
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
    }



}
