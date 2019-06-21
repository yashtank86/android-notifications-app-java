package com.example.notification_ex;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private final String CHANNEL_ID = "Test Notification";
    public static final int NOTIFICATION_ID = 01;
    EditText title, msg;
    public static final String reply_text = "Text Replay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.edt_title);
        msg = findViewById(R.id.edt_message);
    }

    public void get_notification(View view) {
        get_notification_method();
    }

    public void reply_Notification(View view) {
        reply_notification_method();
    }

    public void progressbar_notification(View view) {
        progressbar_notification_method();
    }

    private void notification_channel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "personal Notification";
            String description = "personal notification description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }

    private void get_notification_method() {

        String get_title = title.getText().toString();
        String get_msg = msg.getText().toString();
        notification_channel();

        Intent intent = new Intent(this, Main2Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Intent like_intent = new Intent(this, Main3Activity.class);
        like_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent like_pendingIntent = PendingIntent.getActivity(this, 0, like_intent, PendingIntent.FLAG_ONE_SHOT);

        Intent dislike_intent = new Intent(this, Main4Activity.class);
        dislike_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent dislike_pendingIntent = PendingIntent.getActivity(this, 0, dislike_intent, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.notification_icon);
        builder.setContentTitle(get_title);
        builder.setContentText(get_msg);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.addAction(R.drawable.like_icon, "Like", like_pendingIntent);
        builder.addAction(R.drawable.dislike_icon, "Dislike", dislike_pendingIntent);
        builder.setColor(Color.RED);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo_notification);
        Bitmap large_icon_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_expand);
        /*builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));
        builder.setLargeIcon(bitmap);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(get_msg));*/
        builder.setLargeIcon(large_icon_bitmap);

        if (get_msg.isEmpty()) {
            builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));
            builder.setContentTitle("Set Picture");
            builder.setContentText("Test Big picture type Notification");
        } else {
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(get_msg));
        }

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

    }

    private void reply_notification_method() {

        notification_channel();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            RemoteInput remoteInput = new RemoteInput.Builder(reply_text).setLabel("Reply").build();

            Intent intent = new Intent(this, Main2Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent Main_pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            Intent reply_intent = new Intent(this, Main5Activity.class);
            reply_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent reply_pendingIntent = PendingIntent.getActivity(this, 0, reply_intent, PendingIntent.FLAG_ONE_SHOT);


            NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.notification_icon,
                    "Reply", reply_pendingIntent).addRemoteInput(remoteInput).build();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            builder.setSmallIcon(R.drawable.notification_icon);
            builder.setContentTitle("BABA_YOYO");
            builder.setContentText("Hello Hey, Bye Bye.");
            builder.setContentIntent(Main_pendingIntent);
            builder.setAutoCancel(true);
            builder.setColor(Color.RED);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.addAction(action);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_expand);
            builder.setLargeIcon(bitmap);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private void progressbar_notification_method() {

        /*final String get_title = title.getText().toString();*/
        final String get_msg = msg.getText().toString();
        notification_channel();


        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.icon_download);
        builder.setContentTitle("lykan_hypersport.jpg");
        builder.setContentText(get_msg);
        builder.setColor(Color.GREEN);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(get_msg));
        builder.setOnlyAlertOnce(true);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_expand);
        builder.setLargeIcon(bitmap);


        final int max_progress = 100;
        int current_progress = 0;
        builder.setProgress(max_progress, current_progress, false);

        final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

        Thread my_thread = new Thread() {

            @Override
            public void run() {

                int count = 0;
                while (count <= 100) {
                    count = count + 10;

                    try {
                        sleep(1000);
                        builder.setProgress(max_progress, count, false);
                        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                builder.setProgress(0, 0, false);
                builder.setContentText("Download completed.");

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo_notification);
                builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent main_pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                Intent like_intent = new Intent(MainActivity.this, Main3Activity.class);
                like_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent like_pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, like_intent, PendingIntent.FLAG_ONE_SHOT);

                Intent dislike_intent = new Intent(MainActivity.this, Main4Activity.class);
                dislike_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent dislike_pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, dislike_intent, PendingIntent.FLAG_ONE_SHOT);

                builder.setContentIntent(main_pendingIntent);
                builder.addAction(R.drawable.like_icon, "Like", like_pendingIntent);
                builder.addAction(R.drawable.dislike_icon, "Dislike", dislike_pendingIntent);

                notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
            }
        };
        my_thread.start();
    }
}
