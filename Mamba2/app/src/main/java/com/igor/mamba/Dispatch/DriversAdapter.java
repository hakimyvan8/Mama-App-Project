package com.igor.mamba.Dispatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.igor.mamba.FilterAdapter.FilterDriver;
import com.igor.mamba.R;
import com.igor.mamba.driverdetail;

import java.util.ArrayList;
import java.util.List;

public class DriversAdapter extends RecyclerView.Adapter<DriversAdapter.DriverViewHolder> implements Filterable {
    private Context context;
    public List<driverdetail> dyga,drdetails;


    private FilterDriver filter;

    public DriversAdapter(Context context, List<driverdetail> dyga) {
        this.context = context;
        this.dyga = dyga;
        this.drdetails = dyga;
    }


    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row_shop.xml
        View view = LayoutInflater.from(context).inflate(R.layout.row_driver, parent,false);
        return new DriverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriversAdapter.DriverViewHolder holder, int position) {
        driverdetail driverdets = dyga.get(position);
        String id = driverdets.getDriverID();
        String FullName = driverdets.getFullName();
        String PhoneNumber = driverdets.getPhone();
        String NumberPlate = driverdets.getNumberPlate();
        String DriverLicense = driverdets.getDriverLicense();
        String Status = driverdets.getStatus();
        String adminEmail = driverdets.getAdmin_email();


        holder.shopNameTv.setText(FullName);
        holder.phoneT.setText(PhoneNumber);
        holder.statusT.setText(Status);
        holder.emailTv.setText(adminEmail);
        holder.plateTv.setText(NumberPlate);
        holder.licenseTv.setText(DriverLicense);

        if (Status.equals("online")){

            holder.statusT.setText("Available");
            holder.statusT.setTextColor(context.getResources().getColor(R.color.green));
        }
        else if(Status.equals("offline")){

            holder.statusT.setText("Unavailable");
            holder.statusT.setTextColor(context.getResources().getColor(R.color.red));
        }

        holder.nextIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        });
}

    @Override
    public int getItemCount() {

        return dyga.size();
    }


    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new FilterDriver(this,drdetails);
        }
        return filter;
    }

    public class DriverViewHolder extends RecyclerView.ViewHolder {

        TextView shopNameTv,phoneT,statusT,nextIv,emailTv,plateTv,licenseTv;
        public DriverViewHolder(@NonNull View itemView) {
            super(itemView);

            shopNameTv = itemView.findViewById(R.id.shopNameTv);
            phoneT = itemView.findViewById(R.id.phoneT);
            statusT = itemView.findViewById(R.id.statusT);
            nextIv = itemView.findViewById(R.id.nextIv);
            emailTv = itemView.findViewById(R.id.emailT);
            plateTv = itemView.findViewById(R.id.plateT);
            licenseTv = itemView.findViewById(R.id.licenseT);


        }
    }
}
