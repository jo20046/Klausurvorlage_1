package de.jo20046.klausurvorlage_1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MessungActivity extends AppCompatActivity {

    private final String DEBUG_TAG = "mytag";
    MessungService messungService;
    boolean serviceBound = false;

    TextView tvMessung;
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messung);

        tvMessung = (TextView) findViewById(R.id.tv_messung);

        findViewById(R.id.btn_richtung).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceBound) {
                    Toast.makeText(MessungActivity.this, "countForwards = " + messungService.getCountForwards(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        notificationManager = getSystemService(NotificationManager.class);
        NotificationChannel notificationChannel1 = new NotificationChannel("channel1", "Channel 1", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel1);
        Notification.Builder builder = new Notification.Builder(getApplicationContext(), "channel1")
                .setSmallIcon(R.drawable.ic_stat_check)
                .setContentTitle("Enthöfer")
                .setContentText("Measurement Process");
        Notification notification = builder.build();
        notificationManager.notify(1, notification);

        Intent intent = new Intent(this, MessungService.class);
        startService(intent);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        Log.d(DEBUG_TAG, "onDestroy() - Activity");
        super.onDestroy();

        notificationManager.cancel(1);

        if (serviceBound) {
            unbindService(serviceConnection);
            serviceBound = false;
        }
        Intent intent = new Intent(this, MessungService.class);
        stopService(intent);
    }

    @Override
    public void onBackPressed() {
        Log.d(DEBUG_TAG, "onBackPressed() - Activity");
        super.onBackPressed();

        Intent intent = new Intent(MessungActivity.this, MainActivity.class);
        if (serviceBound) {
            intent.putExtra("messwert", messungService.getMesswert());
        }
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (serviceBound) {
            messungService.richtungsumkehr();
        }
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(DEBUG_TAG, "onServiceConnected() - Activity");
            MessungService.MyBinder myBinder = (MessungService.MyBinder) service;
            messungService = myBinder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, 0, "Start Messung");
        menu.add(Menu.NONE, 2, 1, "Richtungsumkehr");
        menu.add(Menu.NONE, 3, 2, "Stopp Messung");
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(MessungActivity.this, "Start Messung", Toast.LENGTH_SHORT).show();
                if(serviceBound) {
                    tvMessung.setText(messungService.getMessung());
                }
                return true;
            case 2:
                Toast.makeText(MessungActivity.this, "Richtungsumkehr", Toast.LENGTH_SHORT).show();
                if (serviceBound) {
                    messungService.richtungsumkehr();
                }
                return true;
            case 3:
                Toast.makeText(MessungActivity.this, "Stopp Messung", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}