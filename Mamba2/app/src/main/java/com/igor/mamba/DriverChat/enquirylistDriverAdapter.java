package com.igor.mamba.DriverChat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.igor.mamba.CustomerChat.enquiryModel;
import com.igor.mamba.R;

import java.util.List;

public class enquirylistDriverAdapter extends RecyclerView.Adapter<enquirylistDriverAdapter.enquiryholder> {

    LayoutInflater infl;
    List<enquiryModel> enquiryModels;

    View view;
    Context context;

    public enquirylistDriverAdapter(Context context, List<enquiryModel> enquiryModels){
        this.context = context;
        this.infl = LayoutInflater.from(context);
        this.enquiryModels = enquiryModels;
    }
    @NonNull
    @Override
    public enquirylistDriverAdapter.enquiryholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater infl = LayoutInflater.from(context);
        view = infl.inflate(R.layout.enquiryreply, null);
        return new enquiryholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull enquirylistDriverAdapter.enquiryholder holder, int position) {
        enquiryModel enquirymodels = enquiryModels.get(position);

        String MainId = enquirymodels.getId();
        String orderno = enquirymodels.getOrdernumber();
        String rcvrname= enquirymodels.getReceiverName();
        String rcvrPhone = enquirymodels.getReceiverPhone();
        String toid = enquirymodels.getTo_id();
        String fromId = enquirymodels.getFrom_id();

        holder.orderViewII.setText("Order Number: "+String.valueOf(enquirymodels.getOrdernumber()));
        holder.titleViewII.setText(enquirymodels.getReceiverName());
        holder.date.setText("Created At: "+enquirymodels.getDate());

        holder.viewmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, feedbackDriverActivity.class);
                intent.putExtra("MainId",MainId);//msg id
                intent.putExtra("orderno", orderno);
                intent.putExtra("rcvrname", rcvrname);//driverphone
                intent.putExtra("rcvrPhone", rcvrPhone);//driverphone
                intent.putExtra("toid",toid);//driver
                intent.putExtra("fromId",fromId);//user
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {

        return enquiryModels.size();
    }

    public class enquiryholder extends RecyclerView.ViewHolder {
        TextView orderViewII,titleViewII,date,viewmsg;
        public enquiryholder(@NonNull View itemView) {
            super(itemView);
            orderViewII = itemView.findViewById(R.id.orderViewII);
            titleViewII = itemView.findViewById(R.id.titleViewII);
            date = itemView.findViewById(R.id.date);
            viewmsg = itemView.findViewById(R.id.viewmsg);

        }
    }
}
