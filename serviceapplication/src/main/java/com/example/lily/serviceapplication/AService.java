package com.example.lily.serviceapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by ljq
 * on 2018/7/27.
 */

public class AService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return Abinder;
    }


    Binder Abinder = new IMyAidlInterface.Stub() {


        @Override
        public String getNotice() throws RemoteException {
            return "WO XIANG HUI JIA";
        }
};
}
