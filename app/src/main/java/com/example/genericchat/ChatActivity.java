package com.example.genericchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    private TextView chatLogTv;
    private EditText newMessageEt;
    private ImageButton sendMsgBtn;
    private String chatLog = "";
    private View.OnClickListener sendBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            String msg = newMessageEt.getText().toString();
            send(msg);
            newMessageEt.setText("");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // assign views
        chatLogTv = findViewById(R.id.tv_chat_log);
        newMessageEt = findViewById(R.id.et_new_msg);
        sendMsgBtn = findViewById(R.id.ib_send_msg);

        // make chat log scrollable
        chatLogTv.setMovementMethod(new ScrollingMovementMethod());

        sendMsgBtn.setOnClickListener(sendBtnClickListener);

        // check for log history
        if (savedInstanceState != null) {
            chatLog = savedInstanceState.getString("logHistory", "");
            chatLogTv.setText(chatLog);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        outState.putString("logHistory", chatLog);
        super.onSaveInstanceState(outState);
    }

    private void send(String msg) {
        String event = "Sent : " + msg;
        log(event);

        // Kartik calling Kartik !
        receive(msg);
    }

    private void receive(String msg) {
        String event = "Received : " + msg;
        log(event);
    }

    private void log(String event) {
        chatLog += "\n" + event;
        chatLogTv.setText(chatLog);
    }
}
