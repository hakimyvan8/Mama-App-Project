package com.igor.mamba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class driverdetailadapter extends RecyclerView.Adapter<driverdetailadapter.DriverDetailAdapter> {
    LayoutInflater inflater;
    List<driverdetail> driverdtls;
    Context context;

    public driverdetailadapter(Context context, List<driverdetail> driverdtls)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.driverdtls = driverdtls;
    }

    @NonNull
    @Override
    public DriverDetailAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.driver_layout,parent,false);
        return new DriverDetailAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverDetailAdapter holder, int position) {

        driverdetail detailsgtr = driverdtls.get(position);
        holder.driverTv.setText(detailsgtr.getFullName());
        holder.phoneTv.setText(detailsgtr.getPhone());
    }

    @Override
    public int getItemCount() {

        return driverdtls.size();
    }

    public class DriverDetailAdapter extends RecyclerView.ViewHolder {
        TextView driverTv,phoneTv;
        public DriverDetailAdapter(@NonNull View itemView) {
            super(itemView);
            driverTv = itemView.findViewById(R.id.driverTv);
            phoneTv = itemView.findViewById(R.id.phoneTv);
        }
    }
}
