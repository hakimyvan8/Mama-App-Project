package com.igor.mamba.DriverChat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.volley.toolbox.Volley;
//import com.igor.mamba.DriverChat.ChatDriverModel;
//import com.igor.mamba.User.ThreadDriverAdapter;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.igor.mamba.R;
import com.igor.mamba.RequestHandle;
import com.igor.mamba.SharedPrefManagerDriver;
import com.igor.mamba.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class feedbackDriverActivity extends AppCompatActivity implements View.OnClickListener {



    Bundle bundlex;
    private ProgressDialog dialog;

    private RecyclerView recyclerView;
//    private RecyclerView recyclerView;
//    ThreadDriverAdapter adapter;
//    ArrayList<ChatDriverModel> messages;

    //Button to send new message on the thread
    private Button buttonSend;

    ThreadDriverAdapter adapter;
    ArrayList<ChatDriverModel> messages;


    //EditText to send new message on the thread
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_driver);

        buttonSend = (Button) findViewById(R.id.buttonSend);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);

        buttonSend.setOnClickListener(this);


        //Displaying dialog while the chat room is being ready
        dialog = new ProgressDialog(this);
        dialog.setMessage("Opening chat room");
        dialog.show();

        //Initializing recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        messages = new ArrayList<>();

        fetchMessages();
        sendmessage();

        bundlex = getIntent().getExtras();

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), driverenquiryActivity.class));
                feedbackDriverActivity.this.finish();

            }
        });


        if (bundlex != null) {
            TextView username = findViewById(R.id.usrname);
            TextView ordrname = findViewById(R.id.ordrname);

            username.setText(bundlex.getString("rcvrname"));
            ordrname.setText(bundlex.getString("orderno"));
        }
    }


    //fetchMessages
    private void fetchMessages() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CHATDETAILS, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject orderi = jsonArray.getJSONObject(i);

                        String id = orderi.getString("id");

                        String msg_id = orderi.getString("msg_id");

                        String from_id = orderi.getString("from_id");

                        String to_id = orderi.getString("to_id");

                        String msgcont = orderi.getString("msgcont");

                        String sentat = orderi.getString("sentat");


                        ChatDriverModel chtmdl = new ChatDriverModel(id,msg_id,from_id,to_id,msgcont,sentat);
                        messages.add(chtmdl);

                    }
                    adapter = new ThreadDriverAdapter(feedbackDriverActivity.this, messages, SharedPrefManagerDriver.getInstance(getApplicationContext()).getDriver().getDriverId());
                    recyclerView.setAdapter(adapter);
                    scrollToBottom();

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("chatid", bundlex.getString("MainId"));

                return params;
            }
        };
        RequestHandle.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    //send message function
    private void sendmessage() {


        final String Message = editTextMessage.getText().toString().trim();
        if (Message.equalsIgnoreCase(""))
            return;

        adapter.notifyDataSetChanged();
        scrollToBottom();
        editTextMessage.setText("");

        StringRequest vr = new StringRequest(Request.Method.POST, URLs.FEEDBACKREPLY, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);

                    fetchMessages();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(feedbackDriverActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("driverId", bundlex.getString("fromId"));//user receiving
                params.put("FeedBack", Message);
                params.put("msgid", bundlex.getString("MainId"));
                params.put("userid", SharedPrefManagerDriver.getInstance(feedbackDriverActivity.this).getDriver().getDriverId());//driver sending

                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;
            }
        };


        //Disabling retry to prevent duplicate messages
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        vr.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(feedbackDriverActivity.this);
        requestQueue.add(vr);

    }

    private void scrollToBottom() {
        adapter.notifyDataSetChanged();
        if (adapter.getItemCount() > 1)
            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, adapter.getItemCount() -1);
    }



    @Override
    public void onClick(View v) {
        if (v == buttonSend)
            sendmessage();
    }
}


