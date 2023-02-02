package com.igor.mamba.DriverChat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.igor.mamba.CustomerChat.ThreadAdapter;
import com.igor.mamba.R;
import com.igor.mamba.SharedPrefManagerDriver;

import java.util.ArrayList;

public class ThreadDriverAdapter extends RecyclerView.Adapter{

    Context context;
    private String id;
    int SELF = 1;
    int OTHER = 2;

    ArrayList<ChatDriverModel> messages;

    public ThreadDriverAdapter(Context context, ArrayList<ChatDriverModel> messages, String id){
        this.id = id;
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SELF)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.enquirylistsender,parent,false);
            return new SenderViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.enquirylistresponder,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ChatDriverModel chatModel = messages.get(position);
        if (holder.getClass()==SenderViewHolder.class){
            SenderViewHolder sender=(SenderViewHolder)holder;
            sender.msg.setText(chatModel.getMsgcont());
            sender.date.setText(chatModel.getSentat());
        }
        else{
            ReceiverViewHolder receiver=(ReceiverViewHolder)holder;
            receiver.msg.setText(chatModel.getMsgcont());
            receiver.date.setText(chatModel.getSentat());
        }

    }



    @Override
    public int getItemViewType(int position) {
        ChatDriverModel chatmodel = messages.get(position);
        if(SharedPrefManagerDriver.getInstance(context.getApplicationContext()).getDriver().getDriverId().equals(chatmodel.getFrom_id()))
        {
            return SELF;
        }
        else{
            return OTHER;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView msg;
        TextView date;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            msg = itemView.findViewById(R.id.msg);
            date = itemView.findViewById(R.id.date);
        }
    }


    class ReceiverViewHolder extends RecyclerView.ViewHolder{

        TextView msg;
        TextView date;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            msg = itemView.findViewById(R.id.msg);
            date = itemView.findViewById(R.id.date);
        }
    }
}