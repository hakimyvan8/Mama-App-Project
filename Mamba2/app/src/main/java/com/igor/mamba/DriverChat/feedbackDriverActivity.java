package com.igor.mamba.DriverChat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//import com.android.volley.toolbox.Volley;
//import com.igor.mamba.DriverChat.ChatDriverModel;
//import com.igor.mamba.User.ThreadDriverAdapter;
import com.igor.mamba.R;

public class feedbackDriverActivity extends AppCompatActivity implements View.OnClickListener {



    Bundle bundlex;
    private ProgressDialog dialog;
//    private RecyclerView recyclerView;
//    ThreadDriverAdapter adapter;
//    ArrayList<ChatDriverModel> messages;

    //Button to send new message on the thread
    private Button buttonSend;

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

    }

    @Override
    public void onClick(View v) {

    }
}


