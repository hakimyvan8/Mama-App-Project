package com.igor.mamba;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.igor.mamba.CustomerChat.CustomerChatList;
import com.igor.mamba.Dispatch.dispatchModel;
import com.igor.mamba.User.Driver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ItemsPurchased extends AppCompatActivity {
    RecyclerView recyclerView, recyclerViewII;
    ImageButton callBtn;
    BottomSheetDialog dialog;
    driverdetail dest;
    List<purchaseditemsmodel> purchaseditemsmodel;


    //spinner values
    ArrayList<Driver> driverarraylist;
    ArrayList<adminsModel> adminsarraylist;
    String driverarrayid, drivername, driverphone;

    List<driverdetail> dyga;

    List<adminModel> dul;

    List<financeModel> fin;

    List<dispatchModel> disp;

    private static String JSON_URL = URLs.PURCHASEDITEMS;
    itemspurchasedadapter adapter;

    driverdetailadapter adapterII;
    Bundle bundle, bundleII, bundleIII;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_purchased);
        recyclerView = findViewById(R.id.songsList);
        dialog = new BottomSheetDialog(this);
        purchaseditemsmodel = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);
        bundle = getIntent().getExtras();


        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OrderDetailsActivity.class));
                ItemsPurchased.this.finish();
            }
        });


        if (bundle != null) {

            TextView textView = findViewById(R.id.textView20);


            TextView status = findViewById(R.id.paymentStatusTv);

            TextView orderIdTv = findViewById(R.id.orderIdTv);

            TextView dateTv = findViewById(R.id.dateTv);

            TextView nameTv = findViewById(R.id.nameTv);

            TextView paymentTv = findViewById(R.id.paymentTv);

            TextView amountTv = findViewById(R.id.amountTv);

            TextView addressTv = findViewById(R.id.addressTv);


            orderIdTv.setText(bundle.getString("id"));
            dateTv.setText(bundle.getString("date"));
            nameTv.setText(bundle.getString("name"));
            amountTv.setText(bundle.getString("price") + "RWF");
            addressTv.setText(bundle.getString("address"));
            status.setText(bundle.getString("paymentStatus"));


            paymentTv.setText(bundle.getString("payment"));
        }


        loadData();
        loaddriver();
        SendMessage();
    }

    private void loadData() {

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

                    adapter = new itemspurchasedadapter(getApplicationContext(), purchaseditemsmodel);
                    recyclerView.setAdapter(adapter);

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
                params.put("orderid", bundle.getString("id"));

                return params;
            }
        };
        RequestHandle.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    private void loaddriver() {

        TextView phoneTv = findViewById(R.id.phoneTv);
        TextView driverTv = findViewById(R.id.driverTv);
        TextView platenoTv = findViewById(R.id.platenoTv);

        dyga = new ArrayList<>();
        bundleII = getIntent().getExtras();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.DRIVERDETAILS, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                //  Toast.makeText(getApplicationContext(), "Show something", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("dmitri");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject driveri = jsonArray.getJSONObject(i);


                        String phoneGj = driveri.getString("phone");
                        String duke = driveri.getString("FullName");
                        String drvrID = driveri.getString("driverID");
                        String numbrPLate = driveri.getString("NumberPlate");

                        driverdetail dets = new driverdetail();
                        dets.setFullName(duke);
                        dets.setDriverID(drvrID);
                        dets.setPhone(phoneGj);
                        dets.setNumberPlate(numbrPLate);
                        dyga.add(dets);


                        driverTv.setText(duke);
                        phoneTv.setText(phoneGj);
                        platenoTv.setText(numbrPLate);


                        findViewById(R.id.callBtn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + phoneGj));
                                // Start the activity to dial the phone number
                                startActivity(intent);
                                finish();
                            }
                        });



                        findViewById(R.id.textdriverBtn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ItemsPurchased.this);
                                View dialogView = getLayoutInflater().inflate(R.layout.enquirytext, null);
                                bottomSheetDialog.setContentView(dialogView);
                                bottomSheetDialog.show();


                                EditText feedbackTxt = dialogView.findViewById(R.id.feedback_edit_text);

                                TextView driverTt = dialogView.findViewById(R.id.vadimII);
                                Button submit = dialogView.findViewById(R.id.submit_feedback);
                                driverTt.setText(duke);

                                submit.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        StringRequest vr = new StringRequest(Request.Method.POST, URLs.FEEDBACK, new Response.Listener<String>() {


                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject json = new JSONObject(response);


                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                Toast.makeText(ItemsPurchased.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }) {

                                            protected Map<String, String> getParams() {
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("driverId", drvrID);
                                                params.put("FeedBack", feedbackTxt.getText().toString());
                                                params.put("receiverName", duke);
                                                params.put("receiverPhone", phoneGj);
                                                params.put("id", bundleII.getString("id"));
                                                params.put("userid", SharedPrefManager.getInstance(ItemsPurchased.this).getUser().getUUID());
                                                params.put("senderName", SharedPrefManager.getInstance(ItemsPurchased.this).getUser().getFirstName());
                                                params.put("senderNumber", SharedPrefManager.getInstance(ItemsPurchased.this).getUser().getPhone());

                                                return params;
                                            }

                                            @Override
                                            public Map<String, String> getHeaders() throws AuthFailureError {
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("Content-Type", "application/x-www-form-urlencoded");

                                                return params;
                                            }
                                        };
                                        RequestQueue requestQueue = Volley.newRequestQueue(ItemsPurchased.this);
                                        requestQueue.add(vr);

                                        new SweetAlertDialog(ItemsPurchased.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Message Sent!").setContentText("Message sent to "+duke+"Succesfully !!").setConfirmText("close").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {@Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            // Showing simple toast message to user

                                            startActivity(new Intent(getApplicationContext(), CustomerChatList.class));
                                        }
                                        }).show();


                                    }
                                });
                            }
                        });

                    }
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
                params.put("orderid", bundleII.getString("id"));

                return params;
            }
        };
        RequestHandle.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    private void SendMessage() {

        findViewById(R.id.enquiryII).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dul = new ArrayList<>();
                fin = new ArrayList<>();
                disp = new ArrayList<>();

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ItemsPurchased.this);
                View dialogView = getLayoutInflater().inflate(R.layout.feedbackdialog, null);
                bottomSheetDialog.setContentView(dialogView);
                bottomSheetDialog.show();


                //dispatchManager
                TextView dispatchnumber = dialogView.findViewById(R.id.dispatchnumber);
                TextView replydispatchBtn = dialogView.findViewById(R.id.replydispatchBtn);
                TextView calldispatchBtn = dialogView.findViewById(R.id.calldispatchBtn);
                TextView whtsdispatchBtn = dialogView.findViewById(R.id.whtsdispatchBtn);


                //FinanceManager
                TextView financenumber = dialogView.findViewById(R.id.financenumber);
                TextView replyfinanceBtn = dialogView.findViewById(R.id.replyfinanceBtn);
                TextView callfinanceBtn = dialogView.findViewById(R.id.callfinanceBtn);
                TextView whtsfinanceBtn = dialogView.findViewById(R.id.whtsfinanceBtn);









                //dispatchManager
                StringRequest dispatchManager = new StringRequest(Request.Method.POST, URLs.DISPATCHDETAILS, new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getApplicationContext(), "Show something", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject driveri = jsonArray.getJSONObject(i);

                                String disId = driveri.getString("admin_id");
                                String disPContact = driveri.getString("admin_contact");
                                String disJob = driveri.getString("admin_job");

                                dispatchModel dis = new  dispatchModel();
                                dis.setAdmin_id(disId);
                                dis.setAdmin_contact(disPContact);
                                dis.setAdmin_job(disJob);
                                disp.add(dis);

                                dispatchnumber.setText(disPContact);



                                calldispatchBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                        intent.setData(Uri.parse("tel:" + disPContact));
                                        // Start the activity to dial the phone number
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                whtsdispatchBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse("https://wa.me/" + disPContact));
                                        // Start the activity to dial the phone number
                                        startActivity(intent);
                                        finish();

                                    }
                                });


                                replydispatchBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ItemsPurchased.this);
                                        View dialogView = getLayoutInflater().inflate(R.layout.enquirytext, null);
                                        bottomSheetDialog.setContentView(dialogView);
                                        bottomSheetDialog.show();


                                        EditText feedbackTxt = dialogView.findViewById(R.id.feedback_edit_text);
                                        TextView driverTt = dialogView.findViewById(R.id.vadimII);
                                        Button submit = dialogView.findViewById(R.id.submit_feedback);
                                        driverTt.setText(disJob);


                                        submit.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {


                                                StringRequest vr = new StringRequest(Request.Method.POST, URLs.FEEDBACK, new Response.Listener<String>() {


                                                    @Override
                                                    public void onResponse(String response) {
                                                        try {
                                                            JSONObject json = new JSONObject(response);

                                                            if (!(json.getBoolean("error"))) {


                                                            }

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {

                                                        Toast.makeText(ItemsPurchased.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                                    }
                                                }) {

                                                    protected Map<String, String> getParams() {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("driverId", disId);
                                                        params.put("FeedBack", feedbackTxt.getText().toString());
                                                        params.put("receiverName", disJob);
                                                        params.put("receiverPhone", disPContact);
                                                        params.put("id", bundleII.getString("id"));
                                                        params.put("userid", SharedPrefManager.getInstance(ItemsPurchased.this).getUser().getUUID());
                                                        params.put("senderName", SharedPrefManager.getInstance(ItemsPurchased.this).getUser().getFirstName());
                                                        params.put("senderNumber", SharedPrefManager.getInstance(ItemsPurchased.this).getUser().getPhone());

                                                        return params;
                                                    }

                                                    @Override
                                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("Content-Type", "application/x-www-form-urlencoded");

                                                        return params;
                                                    }
                                                };
                                                RequestQueue requestQueue = Volley.newRequestQueue(ItemsPurchased.this);
                                                requestQueue.add(vr);

                                            }
                                        });
                                    }
                                });

                            }
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
                RequestHandle.getInstance(getApplicationContext()).addToRequestQueue(dispatchManager);





                //FinanceManager
                StringRequest financeRequest = new StringRequest(Request.Method.POST, URLs.FINANCEDETAILS, new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getApplicationContext(), "Show something", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject driveri = jsonArray.getJSONObject(i);

                                String disId = driveri.getString("admin_id");
                                String disFContact = driveri.getString("admin_contact");
                                String disJob = driveri.getString("admin_job");

                                financeModel dis = new financeModel();
                                dis.setAdmin_id(disId);
                                dis.setAdmin_contact(disFContact);
                                dis.setAdmin_job(disJob);
                                fin.add(dis);

                                financenumber.setText(disFContact);


                                callfinanceBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                        intent.setData(Uri.parse("tel:" + disFContact));
                                        // Start the activity to dial the phone number
                                        startActivity(intent);
                                        finish();
                                    }
                                });


                                whtsdispatchBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse("https://wa.me/" + financenumber));
                                        // Start the activity to dial the phone number
                                        startActivity(intent);
                                        finish();

                                    }
                                });



                                replyfinanceBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse("https://wa.me/" + financenumber));
                                        // Start the activity to dial the phone number
                                        startActivity(intent);
                                        finish();

                                    }
                                });


                            }
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
                RequestHandle.getInstance(getApplicationContext()).addToRequestQueue(financeRequest);

            }

        });

    }

}






