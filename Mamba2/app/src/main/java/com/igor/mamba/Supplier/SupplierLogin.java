package com.igor.mamba.Supplier;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.igor.mamba.R;
import com.igor.mamba.SharedPrefManagerSUPPLIER;
import com.igor.mamba.URLs;
import com.igor.mamba.User.RequestHandler;
import com.igor.mamba.User.Supplier;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SupplierLogin extends AppCompatActivity implements View.OnClickListener {

    private EditText SupNumberTxt,SupPasswordTxt;
    private String phonenumber,Password;

    private TextView errortext;
    Dialog dialog;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_login);
        findViewById(R.id.addsupplier).setOnClickListener(this);
        SupNumberTxt = findViewById(R.id.SupNumber);
        SupPasswordTxt = findViewById(R.id.SupPassword);
        errortext = findViewById(R.id.error);

        errortext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (errortext.getRight() - errortext.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        errortext.setVisibility(View.INVISIBLE);
                        return true;
                    }
                } else {
                    errortext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        return;
    }

    public void login()
    {
        phonenumber = SupNumberTxt.getText().toString().trim();
        Password  = SupPasswordTxt.getText().toString().trim();

        class LoginSupplier extends AsyncTask<Void, Void, String> {



            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("phone", phonenumber);
                params.put("password", Password);


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_SUPPLIERLOGIN, params);
            }

            public void showDialogue( String condition ){


                dialog = new Dialog(SupplierLogin.this);
                if(condition == "show")
                {


                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.CENTER;

                    LayoutInflater layoutInflater = SupplierLogin.this.getLayoutInflater();
                    View root = layoutInflater.inflate(R.layout.dialog_droppoint3, null);
                    dialog.setContentView(root);

                    dialog.setCancelable(true);
                    dialog.show();
                    window.setAttributes(wlp);


                }

                else if (condition == "dismiss")
                {
                    dialog.dismiss();

                    return ;
                }

            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                showDialogue("show");



            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                },2000);




                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("supplier");

                        //creating a new user object
                        Supplier supplier = new Supplier(

                                userJson.getString("uuid"),
                                userJson.getString("supplier_email"),
                                userJson.getString("supplier_state"),
                                userJson.getString("supplier_district"),
                                userJson.getString("supplier_Business"),
                                userJson.getString("supplier_phone"));

                        //storing the user in shared preferences
                        SharedPrefManagerSUPPLIER.getInstance(getApplicationContext()).supplierLogin(supplier);
                        dialog.dismiss();
                        //starting the profile activity

                        startActivity(new Intent(getApplicationContext(), HomeSupplier.class));
                        SupplierLogin.this.finish();
                    } else {
                        errortext.setVisibility(View.VISIBLE);
                        errortext.setText(obj.getString("message"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        LoginSupplier ru = new LoginSupplier();
        ru.execute();
    }

    @Override
    public void onClick(View view) {


        if(view.getId() == R.id.addsupplier)
        {

            if(!(TextUtils.isEmpty(SupNumberTxt.getText().toString()) && TextUtils.isEmpty(SupPasswordTxt.getText().toString())))
            {

                login();
            }

            else
            {

                errortext.setText("Fields can not be empty!");
                errortext.setVisibility(View.VISIBLE);

            }
        }
    }
}