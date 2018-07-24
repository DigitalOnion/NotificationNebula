package com.outerspace.notificationnebula.background;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.outerspace.notificationnebula.R;

import androidx.work.Worker;

public class NebulaWorker extends Worker {
    public static final String KEY_DURATION = "KEY_DURATION";      // key for an int with milliseconds

    private int duration = 0;
    private int elapsed = 0;
    private int interval = 0;

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        String action = context.getString(R.string.notification_action);

        duration = getInputData().getInt(KEY_DURATION, 0);
        elapsed = 0;
        interval = 1000;

        NotificationNebula.show(context);

        do {

            if(elapsed + interval > duration) {
                interval = duration - elapsed;
            } else {
                elapsed += interval;
            }

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            float progress = (float) elapsed / (float) duration;
            String message = "Notificaation progress: " + elapsed + "/" + duration + " = " + progress;
            Log.d("LUIS", message);
            NotificationNebula.update((float) elapsed / (float) duration);

        } while (elapsed < duration);

        return Result.SUCCESS;
    }

}
