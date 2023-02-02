package com.igor.mamba.User;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.igor.mamba.Driverenroute;
import com.igor.mamba.R;
import com.igor.mamba.RequestHandle;
import com.igor.mamba.SharedPrefManagerDriver;
import com.igor.mamba.URLs;
import com.igor.mamba.ordermodel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class driverorderadapter extends RecyclerView.Adapter<driverorderadapter.orderHolder> {


    Dialog dialog;

    ArrayList<ordermodel> DriverorderList;
    private Context context;
    View view;
    public driverorderadapter(Context mCtx, ArrayList<ordermodel> orderList){
        this.DriverorderList = orderList;
        this.context = mCtx;
    }
    public driverorderadapter.orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.row_ordereditemtwo, null);
        return new driverorderadapter.orderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull driverorderadapter.orderHolder holder, int position) {

             ordermodel om = DriverorderList.get(position);


        String phontTg = om.getPhonenumber();

             holder.rel.setOnClickListener(new OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     dialog = new Dialog(context);


                     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                     Window window = dialog.getWindow();
                     WindowManager.LayoutParams wlp = window.getAttributes();
                     wlp.gravity = Gravity.CENTER;

                     LayoutInflater layoutInflater = LayoutInflater.from(context);;
                     View root = layoutInflater.inflate(R.layout.dialog_droppoint4, null);
                     dialog.setContentView(root);

                     dialog.setCancelable(true);
                     dialog.show();
                     window.setAttributes(wlp);
                        TextView tv = root.findViewById(R.id.textView26);
                     String ordernumber = om.getOrdernumber();
                        tv.setText("Accept order number"+ordernumber+" for delivery");

                          root.findViewById(R.id
                                  .button11).setOnClickListener(new OnClickListener() {


                              @Override
                              public void onClick(View view) {



                                  StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.accept, new Response.Listener<String>() {
                                      @Override
                                      public void onResponse(String response) {

                                          try {
                                              JSONObject obj = new JSONObject(response);
                                              tv.setText(obj.getString("message"));

                                              context.startActivity(new Intent(context.getApplicationContext(), Driverenroute.class));



                                          } catch (JSONException e) {
                                              e.printStackTrace();
                                          }

                                      }
                                  }, new Response.ErrorListener() {
                                      @Override
                                      public void onErrorResponse(VolleyError error) {

                                          tv.setText(error.getMessage());

                                      }
                                  }){
                                      protected Map<String, String> getParams() throws AuthFailureError {
                                          Map<String, String> params = new HashMap<>();
                                          params.put("orderid", ordernumber);
                                          params.put("driverid", SharedPrefManagerDriver.getInstance(context).getDriver().getDriverId());
                                          return params;
                                      }
                                  };

                                  RequestHandle.getInstance(context).addToRequestQueue(stringRequest);
                                  /////run stringrequest here update order to 3 for picklup aproval and 4 for delivered
                              }
                          });

                     root.findViewById(R.id.button13).setOnClickListener(new OnClickListener() {


                         @Override
                         public void onClick(View view) {



                             StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.confirm, new Response.Listener<String>() {
                                 @Override
                                 public void onResponse(String response) {

                                     try {
                                         JSONObject obj = new JSONObject(response);
                                         tv.setText(obj.getString("message"));

                                         context.startActivity(new Intent(context.getApplicationContext(), DriverComplete.class));


                                     } catch (JSONException e) {
                                         e.printStackTrace();
                                     }

                                 }
                             }, new Response.ErrorListener() {
                                 @Override
                                 public void onErrorResponse(VolleyError error) {

                                     tv.setText(error.getMessage());

                                 }
                             }){
                                 protected Map<String, String> getParams() throws AuthFailureError {
                                     Map<String, String> params = new HashMap<>();
                                     params.put("orderid", ordernumber);
                                     params.put("driverid", SharedPrefManagerDriver.getInstance(context).getDriver().getDriverId());
                                     return params;
                                 }
                             };

                             RequestHandle.getInstance(context).addToRequestQueue(stringRequest);
                             /////run stringrequest here update order to 3 for picklup aproval and 4 for delivered
                         }
                     });


                 }
             });

             holder.ordernumber.setText(om.getOrdernumber());
             holder.orderlocation.setText(om.getLocation());
             holder.phonenumber.setText(om.getPhonenumber());
             holder.ordername.setText(om.getOrdername());


        if(om.getStatus() == 4)
        {

            holder.status.setText("Order has been Completed");
            holder.status.setTextColor(context.getResources().getColor(R.color.green));
        }


        else if(om.getStatus() == 2) {
            holder.status.setText("Driver Assigned");
            holder.status.setTextColor(context.getResources().getColor(R.color.blue));
        }


        else if(om.getStatus() == 3)
        {

            holder.status.setText("Driver En Route");
            holder.status.setTextColor(context.getResources().getColor(R.color.orange));
        }


             holder.callBtn.setOnClickListener(new OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     Intent intent = new Intent(Intent.ACTION_DIAL);
                     intent.setData(Uri.parse("tel:" + phontTg));
                     // Start the activity to dial the phone number
                     context.startActivity(intent);

                 }
             });
//
//        holder.textBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
//                View dialogView = LayoutInflater.from(context).inflate(R.layout.enquirytext, null);
//                bottomSheetDialog.setContentView(dialogView);
//                bottomSheetDialog.show();
//
//
//                EditText feedbackTxt = dialogView.findViewById(R.id.feedback_edit_text);
//
//                TextView driverTt = dialogView.findViewById(R.id.vadimII);
//                Button submit = dialogView.findViewById(R.id.submit_feedback);
//                submit.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//
//                        StringRequest vr = new StringRequest(Request.Method.POST, URLs.FEEDBACK, new Response.Listener<String>() {
//
//
//                            @Override
//                            public void onResponse(String response) {
//                                try {
//                                    JSONObject json = new JSONObject(response);
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                                //Toast.makeText(ItemsPurchased.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                            }
//                        }) {
//
//                            protected Map<String, String> getParams() {
//                               // Map<String, String> params = new HashMap<String, String>();
//                               // params.put("driverId", drvrID);
//                               // params.put("FeedBack", feedbackTxt.getText().toString());
//                               // params.put("receiverName", duke);
//                               // params.put("receiverPhone", phoneGj);
//                              //  params.put("id", bundleII.getString("id"));
//                                //params.put("userid", SharedPrefManagerDriver.getInstance(ItemsPurchased.this).getUser().getUUID());
//                               // params.put("senderName", SharedPrefManagerDriver.getInstance(ItemsPurchased.this).getUser().getFirstName());
//                               // params.put("senderNumber", SharedPrefManagerDriver.getInstance(ItemsPurchased.this).getUser().getPhone());
//
//                               // return params;
//                            }
//
//                            @Override
//                            public Map<String, String> getHeaders() throws AuthFailureError {
//                                Map<String, String> params = new HashMap<String, String>();
//                                params.put("Content-Type", "application/x-www-form-urlencoded");
//
//                                return params;
//                            }
//                        };
//                        RequestQueue requestQueue = Volley.newRequestQueue(ItemsPurchased.this);
//                        requestQueue.add(vr);
//
//                        new SweetAlertDialog(ItemsPurchased.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Message Sent!").setContentText("Message sent to "+duke+"Succesfully !!").setConfirmText("close").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {@Override
//                        public void onClick(SweetAlertDialog sDialog) {
//                            // Showing simple toast message to user
//
//                            startActivity(new Intent(getApplicationContext(), enquirydriverlistActivity.class));
//                        }
//                        }).show();
//
//
//                    }
//                });
//            }
//        });
    };



    @Override
    public int getItemCount() {
        return DriverorderList.size();
    }

    public class orderHolder extends RecyclerView.ViewHolder {

        RelativeLayout rel;
        ImageView callBtn,textBtn;
        TextView ordernumber,orderlocation,ordername,status,phonenumber;//orderIdTver,statusTv,shopNameTv,amountTv,dateTv;

        public orderHolder(@NonNull View itemView) {
            super(itemView);

            rel       =  itemView.findViewById(R.id.rel);
            ordernumber = itemView.findViewById(R.id.orderIdTver);
            orderlocation = itemView.findViewById(R.id.dateTv);
            phonenumber = itemView.findViewById(R.id.amountTv);
            ordername = itemView.findViewById(R.id.shopNameTv);
             status = itemView.findViewById(R.id.statusTv);
             callBtn = itemView.findViewById(R.id.callBtn);
             textBtn = itemView.findViewById(R.id.textBtn);
        }
    }
}
