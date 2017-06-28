package com.example.vita.remoteservicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.vita.service.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "#########";
    private Button mButton;

    private IMyAidlInterface mIMyAidlInterface;

    private ServiceConnection mRemoteConn =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: ");
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            try {
                int result = mIMyAidlInterface.plus(11,12);
                String str = mIMyAidlInterface.toUpperCase("who are you");
                Log.i(TAG, "onServiceConnected: result: "+ result+" str:"+ str);
            }catch (RemoteException e){
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected: ");
        }
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton=(Button)findViewById(R.id.bind_service);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent remoteIntent = new Intent("com.example.vita.service.RemoteService");//这是一个action
                remoteIntent.setClassName("com.example.vita.service","com.example.vita.service.RemoteService");
                bindService(remoteIntent,mRemoteConn,BIND_AUTO_CREATE);
            }
        });

    }
}
