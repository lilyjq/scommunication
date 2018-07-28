package com.example.lily.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lily.serviceapplication.IMyAidlInterface;

/**
 * Created by ljq
 * on 2018/7/26.
 */

public class ClientActivity extends Activity implements View.OnClickListener {

    Messenger messenger;
    ServiceConnection connection;
    Button button;
    Button button_aidl;
    ServiceConnection aidl_connection;
    IMyAidlInterface iMyAidlInterface;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        button = findViewById(R.id.button);
        button_aidl = findViewById(R.id.aidl);
        button.setOnClickListener(this);
        button_aidl.setOnClickListener(this);
        bindServiceUseAction();
        bindServiceUseAidl();
    }

    private void bindServiceUseAction(){
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                messenger = new Messenger(service);
                Log.e("ClientActivity","onServiceConnected");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.e("ClientActivity","onServiceDisconnected");
            }
        };
        Intent intent = new Intent("com.example.lily.SService");
        intent.setPackage("com.example.lily.serviceapplication");
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    private void bindServiceUseAidl(){

        aidl_connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
                Log.e("ClientActivity","onServiceConnected");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.e("ClientActivity","onServiceDisconnected");
            }
        };
        Intent intent = new Intent("com.example.lily.AService");
        intent.setPackage("com.example.lily.serviceapplication");
        bindService(intent, aidl_connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            request();
        }else if(v.getId() ==R.id.aidl){

            try {
               String i= iMyAidlInterface.getNotice();
               Toast.makeText(this,i,Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void request() {
        Message message = Message.obtain(null, 1);
        message.replyTo = reMessenger;
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    Messenger reMessenger = new Messenger(new SHandler());

    class SHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bundle b = msg.getData();
                    int s = b.getInt("number");
                    Toast.makeText(ClientActivity.this, "getNumber:" + s, Toast.LENGTH_SHORT).show();
                default:
                    super.handleMessage(msg);
            }
        }
    }


}
