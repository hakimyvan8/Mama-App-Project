package com.igor.mamba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.igor.mamba.DriverChat.enquiryDriverModel;

import java.util.List;

public class feedbackAdapterReply extends RecyclerView.Adapter<feedbackAdapterReply.ViewHolder> {

    BottomSheetDialog dialog;
    Context context;

    List<enquiryDriverModel> messages;


    public feedbackAdapterReply(Context context, List<enquiryDriverModel> messages) {
        this.messages = messages;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.enquirylist,null);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final enquiryDriverModel message = messages.get(position);

        holder.msg.setText(message.getMsg());
        holder.textViewTime.setText(message.getSenderName());
        holder.orderViewII.setText(String.valueOf(message.getOrderId()));
        holder.date.setText(message.getDate());
    }


    @Override
    public int getItemCount() {

        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView msg,textViewTime,orderViewII,date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            msg = itemView.findViewById(R.id.msg);

            textViewTime = itemView.findViewById(R.id.titleViewII);

            orderViewII = itemView.findViewById(R.id.orderViewII);

            date = itemView.findViewById(R.id.date);
        }
    }
}
