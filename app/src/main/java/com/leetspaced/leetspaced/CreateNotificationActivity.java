package com.leetspaced.leetspaced;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class CreateNotificationActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "LeetSpaced Notification";
    public static final String TAG = CreateNotificationActivity.class.getSimpleName();
    int countTodayTasks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_notification);

        Log.d(TAG, "Inside onCreateNotification yay!");

//        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
//        viewModel.getTodaysQuestionCount().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                countTodayTasks = integer;
//            }
//        });
//
//        // Create an explicit intent for an Activity in your app
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//        String textTitle = "Today's Questions";
//        String textContent = "You have " + countTodayTasks + " questions to review today.";
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(textTitle)
//                .setContentText(textContent)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                // Set the intent that will fire when the user taps the notification
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);
//
//        createNotificationChannel();
//
//        int notificationId = 1;
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        // notificationId is a unique int for each notification that you must define;
//        notificationManager.notify(notificationId, builder.build());
//
//        finish();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notifications";
            String description = "Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}