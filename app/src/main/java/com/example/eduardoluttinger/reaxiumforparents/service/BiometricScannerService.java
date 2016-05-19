package com.example.eduardoluttinger.reaxiumforparents.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Eduardo Luttinger on 15/04/2016.
 */
public class BiometricScannerService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initFingerPrint();
        return START_STICKY;
    }


    private void initFingerPrint(){

    }

}

