package com.outerspace.notificationnebula.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.outerspace.notificationnebula.R;
import com.outerspace.notificationnebula.background.NotificationNebula;

public class NebulaService extends Service {

    public static final int MSG_SAY_HELLO = 1;

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Toast.makeText(getApplicationContext(), "hello!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    final Messenger mMessenger = new Messenger(new IncomingHandler());

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
        return mMessenger.getBinder();
    }

//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        Context context = getApplicationContext();
//
//        duration = 5000;
//        elapsed = 0;
//        interval = 1000;
//
//        NotificationNebula.show(context);
//
//        do {
//
//            if(elapsed + interval > duration) {
//                interval = duration - elapsed;
//            } else {
//                elapsed += interval;
//            }
//
//            try {
//                Thread.sleep(interval);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            float progress = (float) elapsed / (float) duration;
//            String message = "Notificaation progress: " + elapsed + "/" + duration + " = " + progress;
//            Log.d("LUIS", message);
//            NotificationNebula.update((float) elapsed / (float) duration);
//
//        } while (elapsed < duration);
//    }
}
