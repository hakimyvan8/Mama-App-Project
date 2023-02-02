package com.igor.mamba.Dispatch;

import androidx.appcompat.app.AppCompatActivity;

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
import com.igor.mamba.URLs;
import com.igor.mamba.User.Dispatch;
import com.igor.mamba.User.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class dispatchActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView signa, errorTxt;
    private EditText phoneTxt, PasswordTxt;
    private String phonenumber, Password;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);

        findViewById(R.id.adddispatch).setOnClickListener(this);

        phoneTxt = findViewById(R.id.phone);
        PasswordTxt = findViewById(R.id.Password);
        errorTxt = findViewById(R.id.error);
        signa = findViewById(R.id.signa);


        errorTxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (errorTxt.getRight() - errorTxt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){

                        errorTxt.setVisibility(View.INVISIBLE);
                        return true;
                    }
                }
                else {
                    errorTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                }
                return false;
            }
        });

        signa.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         onBackPressed();
                                     }
                                 }
        );
    }

    private void login() {
        phonenumber = phoneTxt.getText().toString().trim();
        Password = PasswordTxt.getText().toString().trim();

        class LoginDisplay extends AsyncTask<Void, Void, String> {


            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("phone", phonenumber);
                params.put("password", Password);


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_DISPATCHERLOGIN, params);
            }

            public void showDialogue(String condition) {


                dialog = new Dialog(dispatchActivity.this);
                if (condition == "show") {


                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.CENTER;

                    LayoutInflater layoutInflater = dispatchActivity.this.getLayoutInflater();
                    View root = layoutInflater.inflate(R.layout.dialog_droppoint3, null);
                    dialog.setContentView(root);

                    dialog.setCancelable(true);
                    dialog.show();
                    window.setAttributes(wlp);


                } else if (condition == "dismiss") {
                    dialog.dismiss();

                    return;
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
                }, 2000);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("dispatch");

                        //creating a new user object
                        Dispatch dispatch = new Dispatch(

                                userJson.getString("adminId"),
                                userJson.getString("adminName"),
                                userJson.getString("adminEmail"),
                                userJson.getString("adminImage"),
                                userJson.getString("adminContact"),
                                userJson.getString("adminCountry"),
                                userJson.getString("adminJob"),
                                userJson.getString("adminAbout"));

                        //storing the user in shared preferences
                        SharedPrefManagerDISPATCH.getInstance(getApplicationContext()).dispatchLogin(dispatch);
                        dialog.dismiss();
                        //starting the profile activity

                        startActivity(new Intent(getApplicationContext(), dispatchHome.class));
                        dispatchActivity.this.finish();
                    } else {
                        errorTxt.setVisibility(View.VISIBLE);
                        errorTxt.setText(obj.getString("message"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        LoginDisplay ru = new LoginDisplay();
        ru.execute();
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.adddispatch)
        {

            if(!(TextUtils.isEmpty(phoneTxt.getText().toString()) && TextUtils.isEmpty(PasswordTxt.getText().toString())))
            {

                login();
            }

            else
            {

                errorTxt.setText("Fields can not be empty!");
                errorTxt.setVisibility(View.VISIBLE);

            }
        }
    }
}
