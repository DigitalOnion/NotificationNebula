package com.outerspace.notificationnebula;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.outerspace.notificationnebula.background.NebulaWorker;
import com.outerspace.notificationnebula.service.NebulaService;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickBtnFire(View view) {
        Switch switchToService = (Switch) findViewById(R.id.switch_to_service);
        if( !switchToService.isChecked()) {
            Data nebulaData = new Data.Builder()
                    .putInt(NebulaWorker.KEY_DURATION, 5000)
                    .build();

            OneTimeWorkRequest nebulaRequest =
                    new OneTimeWorkRequest.Builder(NebulaWorker.class)
                            .setInputData(nebulaData)
                            .build();
            WorkManager.getInstance().enqueue(nebulaRequest);
        } else {
            Intent intent = new Intent(this, NebulaService.class);
            startService(intent);
        }
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

    public void onClickSwitchToService(View view) {
        Switch switchToService = (Switch) view;
        String text = switchToService.isChecked()
                ? getString(R.string.using_service)
                : getString(R.string.using_work_manager);
        TextView textView = findViewById(R.id.text_to_service);
        textView.setText(text);
    }
}
