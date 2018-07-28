package com.example.lily.serviceapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.Random;

/**
 * Created by ljq
 * on 2018/7/26.
 */

public class SService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    Messenger messenger = new Messenger(new SHandler());

    class SHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Messenger reMessenger = msg.replyTo;
                    Message message = Message.obtain(null,1);
                    Bundle b = new Bundle();
                    b.putInt("number",getNumber());
                    message.setData(b);
                    try {
                        reMessenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                default:
                    super.handleMessage(msg);
            }

        }
    }

    private Random util = new Random();

    public int getNumber() {
        return util.nextInt(100);
    }


}
