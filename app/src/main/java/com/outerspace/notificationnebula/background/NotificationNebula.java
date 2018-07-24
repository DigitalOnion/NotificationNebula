package com.outerspace.notificationnebula.background;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.outerspace.notificationnebula.R;

public class NotificationNebula {
    private static NotificationManagerCompat manager;
    private static NotificationCompat.Builder builder;

    private static final NotificationNebula instance = new NotificationNebula();

    private NotificationNebula() { }

    public static final int notificationId = 928267;
    private static final int MAX_PROGRESS = 100;

    public static void show(Context context) {
        String channelId = context.getString(R.string.channel_id);
        builder = new NotificationCompat.Builder(context, channelId);
        builder.setSmallIcon(R.drawable.ic_favorite)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH   )
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(getNotificationText());

        manager = NotificationManagerCompat.from(context);
        manager.notify(notificationId, builder.build());
    }

    public static void update(float percentage) {
        Log.d("LUIS:", "Percentage:" + percentage);
        int progress = Math.round((percentage) * MAX_PROGRESS);
        builder.setContentText("Progress:" + progress + " %");
        builder.setProgress(MAX_PROGRESS, progress, false);
        manager.notify(notificationId, builder.build());
    }

    private static String getNotificationText() {
        return "This is the notification text";
    }
}
