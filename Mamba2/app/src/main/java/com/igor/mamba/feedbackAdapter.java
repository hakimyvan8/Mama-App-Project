package com.igor.mamba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.igor.mamba.DriverChat.enquiryDriverModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class feedbackAdapter extends RecyclerView.Adapter<feedbackAdapter.ViewHolder> {

    private int SENDER = 1;
    private int RECEIVER = 0;


    BottomSheetDialog dialog;
    Context context;

     List<enquiryDriverModel> messages;


    public feedbackAdapter(Context context, List<enquiryDriverModel> messages) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            View view = inflater.inflate(R.layout.enquiryreply, parent, false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        enquiryDriverModel message = messages.get(position);

        String orderid = String.valueOf(message.getOrderId());
        String custoid = message.getFrom_id();
        String Sname = message.getSenderName();
        String Sphone = message.getSenderNumber();


        holder.msg.setText(message.getMsg());
        holder.textViewTime.setText(message.getSenderName());
        holder.orderViewI.setText(String.valueOf(message.getOrderId()));
        holder.date.setText(message.getDate());

        holder.nextIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.feedbackdialogdriver, null);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

                EditText feedbackTxt = bottomSheetView.findViewById(R.id.feedback_edit_text);
                TextView driverT = bottomSheetView.findViewById(R.id.vadimII);

                driverT.setText(message.getSenderName());
                Button submit = bottomSheetView.findViewById(R.id.submit_feedback);


                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        StringRequest sr = new StringRequest(Request.Method.POST, URLs.FEEDBACKDRIVER, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject obj = new JSONObject(response);

                                    if(!(obj.getBoolean("error"))){


                                        Toast.makeText(context,"updated",Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }) {
                            protected Map<String,String> getParams(){
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("custoId", custoid);
                                params.put("FeedBack", feedbackTxt.getText().toString());
                                params.put("receiverName",  Sname);
                                params.put("receiverPhone", Sphone);
                                params.put("id", orderid);
                                params.put("userid", SharedPrefManagerDriver.getInstance(context.getApplicationContext()).getDriver().getDriverId());
                                params.put("senderName", SharedPrefManagerDriver.getInstance(context.getApplicationContext()).getDriver().getDriverFullname());
                                params.put("senderNumber", SharedPrefManagerDriver.getInstance(context.getApplicationContext()).getDriver().getDriverFullname());
                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("Content-Type","application/x-www-form-urlencoded");

                                return params;
                            }
                        };

                        RequestQueue requestQueue =  Volley.newRequestQueue(context);
                        requestQueue.add(sr);

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {

        return messages.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nextIv;
        TextView msg,textViewTime,orderViewI,date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            msg = itemView.findViewById(R.id.msg);

            textViewTime = itemView.findViewById(R.id.titleViewII);

            orderViewI = itemView.findViewById(R.id.orderViewII);

            date = itemView.findViewById(R.id.date);

            nextIv = itemView.findViewById(R.id.nextIv);

        }
        // Implement this class, it should define the view components of the item_message_user_b layout and method bind()
    }

}
