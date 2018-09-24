package com.example.genericchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    private TextView chatLogTv;
    private EditText newMessageEt;
    private ImageButton sendMsgBtn;
    private String chatLog = "";
    private boolean isBlocked = false;
    private boolean isOffline = false;
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

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            default: {
                return false;
            }
            case R.id.option_abt_sender: {
                /*noop*/
                break;
            }
            case R.id.option_block_sender: {
                if (isBlocked) {
                    // already blocked => unblock now
                    isBlocked = false;
                    String event = "Sender unblocked";
                    log(event);

                    // change option title
                    item.setTitle("Block Sender");
                } else {
                    // block now
                    isBlocked = true;
                    String event = "Sender blocked";
                    log(event);

                    // change option title
                    item.setTitle("Unblock Sender");
                }
                break;
            }
            case R.id.option_go_offline: {
                if (isOffline) {
                    // already offline => go online
                    isOffline = false;
                    String event = "Receiver Online";
                    log(event);

                    // change option title
                    item.setTitle("Go Offline");
                } else {
                    // go offline
                    isOffline = true;
                    String event = "Receiver Offline";
                    log(event);

                    // change option title
                    item.setTitle("Go Online");
                }
                break;
            }
        }
        return true;
    }

    private void log(String event) {
        chatLog += "\n" + event;
        chatLogTv.setText(chatLog);
    }

    private void send(String msg) {
        if (!isBlocked) {
            String event = "Sent : " + msg;
            log(event);
            // Kartik calling Kartik !
            receive(msg);
        } else {
            String event = "Can't Send : [" + msg + "] You're blocked";
            log(event);
        }
    }

    private void receive(String msg) {
        if (!isOffline) {
            String event = "Received : " + msg;
            log(event);
        }
    }
}
