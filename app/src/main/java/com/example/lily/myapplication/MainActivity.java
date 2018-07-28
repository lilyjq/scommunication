package com.example.lily.myapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TService services;
    ServiceConnection connection;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= findViewById(R.id.iii);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        connection= new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                TService.TBinder binder = (TService.TBinder) service;
                services = binder.getService();
                Log.e("servcice","onServiceConnected");

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.e("servcice","onServiceDisconnected");
            }
        };
        Intent intent = new Intent(this,TService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);

        textView.setOnClickListener(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iii){
            Toast.makeText(this,"getNumbers:"+services.getNumber(),Toast.LENGTH_LONG).show();
        }else if(v.getId() == R.id.button){
            Intent intent = new Intent(this,ClientActivity.class);
            startActivity(intent);
        }

    }
}
