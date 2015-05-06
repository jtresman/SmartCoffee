package com.example.android.bluetoothchat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class Calibrate extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate);

        View btStart = findViewById(R.id.buttonStart);
        View btStop = findViewById(R.id.buttonStop);

        btStart.setEnabled(true);
        btStop.setEnabled(false);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calibrate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void calibrateStart(View v){

        byte[] signal = new byte[1];

        signal[0] = (byte) 's';

        if (CommonStuff.getBrewCycle() != 1) {

            CommonStuff.getmSerialService().write(signal);

            View btStart = findViewById(R.id.buttonStart);
            View btStop = findViewById(R.id.buttonStop);

            btStart.setEnabled(false);
            btStop.setEnabled(true);
        } else {
            Toast.makeText(this, "Brew In Progress!", Toast.LENGTH_LONG).show();
        }



    }

    public void calibrateStop(View v){

        byte[] signal = new byte[1];

        signal[0] = (byte) 's';

        CommonStuff.getmSerialService().write(signal);

        View btStart = findViewById(R.id.buttonStart);
        View btStop = findViewById(R.id.buttonStop);

        btStart.setEnabled(true);
        btStop.setEnabled(false);

    }

}
