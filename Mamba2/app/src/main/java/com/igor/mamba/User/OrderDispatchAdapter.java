package com.igor.mamba.User;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.igor.mamba.AdminMod;
import com.igor.mamba.BottomSheetDialogue;
import com.igor.mamba.Dispatch.Dispatc;
import com.igor.mamba.Dispatch.DriversAdapter;
import com.igor.mamba.Dispatch.dispatchHome;
import com.igor.mamba.Driverenroute;
import com.igor.mamba.ItemsPurchased;
import com.igor.mamba.R;
import com.igor.mamba.RequestHandle;
import com.igor.mamba.SharedPrefManager;
import com.igor.mamba.SharedPrefManagerDriver;
import com.igor.mamba.URLs;
import com.igor.mamba.driverdetail;
import com.igor.mamba.driverdetailDispatch;
import com.igor.mamba.itemspurchasedadapter;
import com.igor.mamba.purchaseditemsmodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OrderDispatchAdapter extends RecyclerView.Adapter<OrderDispatchAdapter.orderHolder> {
    ArrayList<Dispatc> orderList;
    private Context context;

    View view;
    public OrderDispatchAdapter(Context mCtx, ArrayList<Dispatc> orderList){
        this.orderList = orderList;
        this.context = mCtx;
    }
    @NonNull
    @Override
    public OrderDispatchAdapter.orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.row_order_dispatch, null);
        return new OrderDispatchAdapter.orderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orderHolder holder, int position) {
        final Dispatc order = orderList.get(position);
        int orderstatus = order.getStatus();
        String orderstat = Integer.toString(orderstatus);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\'');
        symbols.setDecimalSeparator(',');
       symbols.setCurrency(Currency.getInstance(new Locale("es", "ES")));
        DecimalFormat decimalFormat = new DecimalFormat("R₣ #,###.00", symbols);

        String id = String.valueOf(order.getOrderid());
        String date = order.getCreateAt();
        String name = order.getFirstname();
        String price = String.valueOf(order.getGrandtotal());
        String address = order.getCity();
        String province = order.getProvince();
        String payment = order.getPaymentsecreat();
        String paymentStatus = order.getPaymentStatus();
        String country = order.getCountry();
        int quantity = order.getQuantity();

        holder.orderIdTv.setText("Order ID :"+id);

        holder.shopNameTv.setText("First Name :"+ order.getFirstname());
        holder.dateTv.setText(order.getCreateAt());
        holder.phoneT.setText(order.getMobile());



        holder.nextIv.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(context, orderSheetDialogue.class);



                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                bundle.putString("date", date);
                bundle.putString("price", price);
                bundle.putString("name", name);
                bundle.putString("address",address);
                bundle.putString("payment",payment);
                bundle.putString("province",province);
                bundle.putString("paymentStatus",paymentStatus);
                bundle.putString("country",country);
                bundle.putString("payment",order.getPaymentsecreat());

                orderSheetDialogue bottomSheet = new orderSheetDialogue(context);
                bottomSheet .setArguments(bundle);
                bottomSheet.show(((FragmentActivity) context).getSupportFragmentManager(),bottomSheet.getTag());
            }

        });

        //order status change function
        if(order.getStatus() == 0) {
            holder.statusTv.setText("Pending verification");
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.grey));
        }

        if(order.getStatus() == 2) {
            holder.statusTv.setText("Order Declined");
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.red));
            holder.orderIdTv.setPaintFlags(holder.orderIdTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
        else if(order.getStatus() == 1)
        {

            holder.statusTv.setText(" Order has been Approved");

            holder.statusTv.setTextColor(context.getResources().getColor(R.color.green));
        }


        else if(order.getStatus() == 4)
        {

            holder.statusTv.setText("Order has been Completed");
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.green));
        }


        else if(order.getStatus() == 3)
        {

            holder.statusTv.setText("Driver En Route");
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.orange));
        }




        //payment status change function
        if (paymentStatus.equals("pending approval")) {
            holder.PaymentsTATUS.setText("Pending Approval");

            holder.PaymentsTATUS.setTextColor(context.getResources().getColor(R.color.grey));

        }

        else if (paymentStatus.equals("Payment Complete")) {
            holder.PaymentsTATUS.setText("Payment Complete");

            holder.PaymentsTATUS.setTextColor(context.getResources().getColor(R.color.green));

        }

        else if (paymentStatus.equals("Payment Declined")) {
            holder.PaymentsTATUS.setText("Payment Declined");

            holder.PaymentsTATUS.setTextColor(context.getResources().getColor(R.color.red));



        }

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public class orderHolder extends RecyclerView.ViewHolder {
        TextView orderIdTv,dateTv,shopNameTv,statusTv,PaymentsTATUS,phoneT,nextIv;
        public orderHolder(@NonNull View itemView) {
            super(itemView);
            //init ui views
            orderIdTv = itemView.findViewById(R.id.ordersTv);
            dateTv = itemView.findViewById(R.id.dateTv);
            shopNameTv = itemView.findViewById(R.id.shopNameTv);
            phoneT = itemView.findViewById(R.id.phoneT);
            statusTv = itemView.findViewById(R.id.AssignstatusTv);
            PaymentsTATUS = itemView.findViewById(R.id.PaymentstatusTv);
            nextIv   = itemView.findViewById(R.id.nextIv);


        }
    }
}
