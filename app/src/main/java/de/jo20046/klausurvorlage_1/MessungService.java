package de.jo20046.klausurvorlage_1;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MessungService extends Service {

    private final IBinder myBinder = new MyBinder();
    private final String DEBUG_TAG = "mytag";
    private long startTime = 0;
    private long messwert = 0;
    private boolean countForwards = true;

    // Start des Service und Initialisierung des Timers
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(DEBUG_TAG, "onStartCommand() - Service");
        startTime = System.currentTimeMillis();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(DEBUG_TAG, "onBind() - Service");
        return myBinder;
    }

    public class MyBinder extends Binder {
        MessungService getService() {
            Log.d(DEBUG_TAG, "getService() - Binder");
            return MessungService.this;
        }
    }

    public boolean getCountForwards() {
        return countForwards;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getMesswert() {
        return messwert;
    }

    // Rückgabe des Messwerts durch Abgleich der aktuellen Zeit mit der Startzeit
    public String getMessung() {
        StringBuilder sb = new StringBuilder();
        sb.append("Messwert = ");
        long newTime = System.currentTimeMillis();
        messwert = (newTime - startTime) / 1000;
        sb.append(messwert);
        return sb.toString();
    }

    public void richtungsumkehr() {
        countForwards = !countForwards;
    }
}