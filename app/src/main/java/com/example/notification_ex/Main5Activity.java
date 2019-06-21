package com.example.notification_ex;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Main5Activity extends AppCompatActivity {

    private TextView text_reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        text_reply = findViewById(R.id.reply_text);
        Bundle remote_input = RemoteInput.getResultsFromIntent(getIntent());

        if (remote_input != null) {
            String message = remote_input.getCharSequence(MainActivity.reply_text).toString();
            text_reply.setText(message);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(MainActivity.NOTIFICATION_ID);
    }
}
