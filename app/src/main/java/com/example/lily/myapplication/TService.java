package com.example.lily.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;

/**
 * Created by ljq
 * on 2018/7/26.
 */

public class TService extends Service{


    private IBinder binder = new TBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

     class TBinder extends Binder{
        TService getService(){
            return  TService.this;
        }

    }

    private Random util = new Random();


    public int getNumber (){
        return util.nextInt(100);
    }




}
