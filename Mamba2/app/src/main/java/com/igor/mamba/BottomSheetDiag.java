package com.igor.mamba;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.igor.mamba.URLs.ADMINS;
import static com.igor.mamba.URLs.DRIVERONLINE;

public class BottomSheetDiag extends BottomSheetDialogFragment {

    TextView texter,textView10;
    ImageView callcentr;
    Spinner spinner;
    ArrayList<String> dudu = new ArrayList<String>();
    ArrayAdapter<String> driverAdapter;
    ArrayList<AdminMod> adminlist;
    ArrayAdapter<String> adminAdapter;
    private String locationid,TextUser;
    BottomSheetDialog dialog;
    BottomSheetBehavior<View> bottomSheetBehaviour;
    Context context;
    RequestQueue requestQueue;
    String adminid,phone,texterese;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return dialog;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.row_contact,container, false);
        requestQueue = Volley.newRequestQueue(context);
        spinner = v.findViewById(R.id.algo_button);
        texter = v.findViewById(R.id.textView6);
        callcentr = v.findViewById(R.id.callcentr);
        textView10 = v.findViewById(R.id.textView10);
        TextView call = v.findViewById(R.id.callBtn);
        retrieveJSON();


        callcentr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                adminid = adminlist.get(i).getAdmin_id();
                phone = adminlist.get(i).getAdmin_contact();
                texterese = adminlist.get(i).getAdmin_job();


                texter.setText(texterese);
                textView10.setText(phone);




                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + phone));
                        // Start the activity to dial the phone number
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return v;
    }


    public BottomSheetDiag(Context context){

        this.context = context;

    }



    private void retrieveJSON() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ADMINS, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            adminlist = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("data");
                            for(int i=0; i<jsonArray.length(); i++) {

                                AdminMod playerModel = new AdminMod();
                                JSONObject dataobj = jsonArray.getJSONObject(i);

                                playerModel.setAdmin_id(dataobj.getString("admin_id"));
                                playerModel.setAdmin_contact(dataobj.getString("admin_contact"));
                                playerModel.setAdmin_job(dataobj.getString("admin_job"));

                                adminlist.add(playerModel);

                            }

                            for (int i = 0; i <adminlist.size(); i++){
                                dudu.add(adminlist.get(i).getAdmin_job());

                            }

                            adminAdapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_item,dudu);
                            adminAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adminAdapter);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);

    }


    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomSheetBehaviour = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
        //

        LinearLayout layout = dialog.findViewById(R.id.linearContact);
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
    }


}
