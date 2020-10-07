package com.leetspaced.leetspaced;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String TAG = AlarmReceiver.class.getSimpleName();
    private static final String CHANNEL_ID = "LeetSpaced Notification";
    private Repository mRepository;
    private Thread mThread;


    @Override
    public void onReceive(final Context context, Intent intent) {
//        Intent notificationIntent = new Intent(context, CreateNotificationActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(notificationIntent);

        mRepository = new Repository((Application) context.getApplicationContext());
        Runnable createNotificationRunnable = new Runnable() {
            @Override
            public void run() {
                int todayTasksCount = mRepository.getTodaysQuestionsCount();
                createNotification(context, todayTasksCount);
            }
        };

        if (mThread != null){
            mThread.interrupt();
        }
        mThread = new Thread(createNotificationRunnable);
        mThread.start();
    }

    private void createNotification(Context context, int todayTasksCount) {
        PendingIntent pendingIntent = createPendingIntent(context);
        String textTitle = "Today's Questions";
        String textContent = "You have " + todayTasksCount + " questions to review today.";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        createNotificationChannel(context);

        int notificationId = 1;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define;
        notificationManager.notify(notificationId, builder.build());
    }

    private PendingIntent createPendingIntent(Context context) {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(context , MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(context, 0, intent, 0);
    }

    private void createNotificationChannel(Context context) {
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
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
