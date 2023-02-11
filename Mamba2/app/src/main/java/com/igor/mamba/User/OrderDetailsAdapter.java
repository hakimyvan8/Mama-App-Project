package com.igor.mamba.User;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.igor.mamba.ItemsPurchased;
import com.igor.mamba.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.orderHolder> {
    ArrayList<Order> orderList;
    private Context context;

    View view;
    public OrderDetailsAdapter(Context mCtx, ArrayList<Order> orderList){
        this.orderList = orderList;
        this.context = mCtx;
    }
    @NonNull
    @Override
    public OrderDetailsAdapter.orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.row_ordereditem, null);
        return new OrderDetailsAdapter.orderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orderHolder holder, int position) {
        final Order order = orderList.get(position);
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
        holder.amountTv.setText("Amount :"+decimalFormat.format(order.getGrandtotal()));



        holder.nextIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemsPurchased.class);
                intent.putExtra("id",id);
                intent.putExtra("date", date);
                intent.putExtra("price", price);
                intent.putExtra("name", name);
                intent.putExtra("address",address);
                intent.putExtra("payment",payment);
                intent.putExtra("province",province);
                intent.putExtra("paymentStatus",paymentStatus);
                intent.putExtra("country",country);
                intent.putExtra("payment",order.getPaymentsecreat());
                context.startActivity(intent);

            }
        });

        if(order.getStatus() == 0) {
            holder.statusTv.setText("Pending verification");
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.grey));
        }

        if(order.getStatus() == 5) {
            holder.statusTv.setText("Order Declined");
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.red));
            holder.amountTv.setText(decimalFormat.format(order.getGrandtotal())+"(Refund)");
            holder.amountTv.setPaintFlags(holder.amountTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.orderIdTv.setPaintFlags(holder.orderIdTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
        else if(order.getStatus() == 1)
        {

            holder.statusTv.setText("Your Order has been Approved");

            holder.statusTv.setTextColor(context.getResources().getColor(R.color.green));
        }


        else if(order.getStatus() == 2)
        {

            holder.statusTv.setText("Your Order has been Assigned to Driver");

            holder.statusTv.setTextColor(context.getResources().getColor(R.color.blue));
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
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public class orderHolder extends RecyclerView.ViewHolder {
        TextView orderIdTv,dateTv,shopNameTv,amountTv,statusTv,PaymentsTATUS,phoneT,PaymentsTATS;
        ImageView nextIv;
        public orderHolder(@NonNull View itemView) {
            super(itemView);
            //init ui views
            orderIdTv = itemView.findViewById(R.id.orderIdTver);
            dateTv = itemView.findViewById(R.id.dateTv);
            phoneT = itemView.findViewById(R.id.phoneT);
            shopNameTv = itemView.findViewById(R.id.shopNameTv);
            amountTv = itemView.findViewById(R.id.amountTv);
            statusTv = itemView.findViewById(R.id.statusTv);
            nextIv   = itemView.findViewById(R.id.nextIv);


        }
    }
}
