package de.jo20046.klausurvorlage_1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MessungActivity extends AppCompatActivity {

    private final String DEBUG_TAG = "mytag";
    MessungService messungService;
    boolean serviceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messung);

        Intent intent = new Intent(this, MessungService.class);
        startService(intent);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        Log.d(DEBUG_TAG, "onDestroy() - Activity");
        super.onDestroy();

        if (serviceBound) {
            unbindService(serviceConnection);
            serviceBound = false;
        }
        Intent intent = new Intent(this, MessungService.class);
        stopService(intent);
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
                return true;
            case 2:
                Toast.makeText(MessungActivity.this, "Richtungsumkehr", Toast.LENGTH_SHORT).show();
                return true;
            case 3:
                Toast.makeText(MessungActivity.this, "Stopp Messung", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}