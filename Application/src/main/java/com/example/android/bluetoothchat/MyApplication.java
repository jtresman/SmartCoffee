package com.example.android.bluetoothchat;

/**
 * Created by Jacob on 4/29/2015.
 */
import android.app.Application;

public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        // Initialize the singletons so their instances
        // are bound to the application process.
        initSingletons();
    }

    protected void initSingletons()
    {
        // Initialize the instance of MySingleton
        CommonStuff.initInstance();
    }

}
