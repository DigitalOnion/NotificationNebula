package com.outerspace.notificationnebula;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.outerspace.notificationnebula.application.NebulaReceiver;
import com.outerspace.notificationnebula.background.NebulaWorker;
import com.outerspace.notificationnebula.background.NotificationNebula;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String action = getString(R.string.notification_action);
        BroadcastReceiver receiver = new NebulaReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(action);
        getApplicationContext().registerReceiver(receiver, filter);
    }

    public void onClickBtnFire(View view) {
        Data nebulaData = new Data.Builder()
                .putInt(NebulaWorker.KEY_DURATION, 10000)
                .build();

        OneTimeWorkRequest nebulaRequest =
                new OneTimeWorkRequest.Builder( NebulaWorker.class )
                        .setInputData(nebulaData)
                        .build();
        WorkManager.getInstance().enqueue(nebulaRequest);
    }

    public void onClickBtnKillIt(View view) {
        this.finishAffinity();
        System.exit(0);
    }

    public void onClickBtnTest(View view) {
        String channelId = getString(R.string.channel_id);
        NotificationManagerCompat manager =
                NotificationManagerCompat.from(getApplicationContext());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext(), channelId);
        builder.setSmallIcon(R.drawable.ic_favorite)
                .setContentTitle("Test Notification Title")
                .setContentText("Notification content. It is very important")
                .setPriority(NotificationManager.IMPORTANCE_HIGH);
        manager.notify(0, builder.build());
    }
}
