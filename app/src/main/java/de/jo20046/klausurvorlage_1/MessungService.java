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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(DEBUG_TAG, "onStartCommand() - Service");
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
}