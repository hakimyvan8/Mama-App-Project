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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

public class driverorderrouteadapter extends RecyclerView.Adapter<driverorderrouteadapter.orderHolder> {


    Dialog dialog;

    ArrayList<ordermodel> DriverorderList;
    private Context context;
    View view;
    public driverorderrouteadapter(Context mCtx, ArrayList<ordermodel> orderList){
        this.DriverorderList = orderList;
        this.context = mCtx;
    }
    public driverorderrouteadapter.orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.row_ordereditemtwo, null);
        return new driverorderrouteadapter.orderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull driverorderrouteadapter.orderHolder holder, int position) {

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
                        tv.setText("Accept order number"+ordernumber+" As Delivered ");

                          root.findViewById(R.id
                                  .button11).setVisibility(View.INVISIBLE);
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


             holder.phonenumber.setOnClickListener(new OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     Intent intent = new Intent(Intent.ACTION_DIAL);
                     intent.setData(Uri.parse("tel:" + phontTg));
                     // Start the activity to dial the phone number
                     context.startActivity(intent);

                 }
             });
    };



    @Override
    public int getItemCount() {
        return DriverorderList.size();
    }

    public class orderHolder extends RecyclerView.ViewHolder {

        RelativeLayout rel;
        TextView ordernumber,orderlocation,ordername,status,phonenumber;//orderIdTver,statusTv,shopNameTv,amountTv,dateTv;

        public orderHolder(@NonNull View itemView) {
            super(itemView);

            rel       =  itemView.findViewById(R.id.rel);
            ordernumber = itemView.findViewById(R.id.orderIdTver);
            orderlocation = itemView.findViewById(R.id.dateTv);
            phonenumber = itemView.findViewById(R.id.amountTv);
            ordername = itemView.findViewById(R.id.shopNameTv);
             status = itemView.findViewById(R.id.statusTv);
        }
    }
}
