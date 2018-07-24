package com.outerspace.notificationnebula.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.outerspace.notificationnebula.R;
import com.outerspace.notificationnebula.background.NebulaWorker;
import com.outerspace.notificationnebula.background.NotificationNebula;

public class NebulaReceiver extends BroadcastReceiver {
    float progress;
    String message;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.hasExtra(NebulaWorker.KEY_TYPE)) {
            switch (intent.getStringExtra(NebulaWorker.KEY_TYPE)) {
                case NebulaWorker.broadcastType.SHOW:
                    Toast.makeText(context, "Show", Toast.LENGTH_SHORT).show();
                    NotificationNebula.show(context);
                    break;

                case NebulaWorker.broadcastType.MESSAGE:
                    if(intent.hasExtra(NebulaWorker.broadcastType.MESSAGE)) {
                        message = intent.getStringExtra(NebulaWorker.broadcastType.MESSAGE);
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }

                case NebulaWorker.broadcastType.PROGRESS:
                    if(intent.hasExtra(NebulaWorker.broadcastType.MESSAGE)) {
                        progress = intent.getFloatExtra(NebulaWorker.broadcastType.PROGRESS, 0.0f);
                        Toast.makeText(context, "Progress:" + progress, Toast.LENGTH_SHORT).show();
                        NotificationNebula.update(progress);
                    }
            }
        }
    }
}
