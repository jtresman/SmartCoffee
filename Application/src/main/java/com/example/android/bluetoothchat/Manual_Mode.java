package com.example.android.bluetoothchat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;


public class Manual_Mode extends Activity {

    NumberPicker np;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_mode);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manual_mode, menu);

        np = (NumberPicker) findViewById(R.id.numberPicker2);

        np.setMinValue(1);
        np.setMaxValue(12);
        np.setWrapSelectorWheel(false);
        np.setValue(1);

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

    public void grind(View v){

        CommonStuff.setRunCycle(2);
        int cups = np.getValue();
        byte[] signal = new byte[2];

        if (CommonStuff.getBrewCycle() != 1 && CommonStuff.getmSerialService().getState() == 3){

            signal[0] = (byte) '2';

            switch (cups){
                case 1:
                    signal[1] = (byte) 'a';
                    break;
                case 2:
                    signal[1] = (byte) 'b';
                    break;
                case 3:
                    signal[1] = (byte) 'c';
                    break;
                case 4:
                    signal[1] = (byte) 'd';
                    break;
                case 5:
                    signal[1] = (byte) 'e';
                    break;
                case 6:
                    signal[1] = (byte) 'f';
                    break;
                case 7:
                    signal[1] = (byte) 'g';
                    break;
                case 8:
                    signal[1] = (byte) 'h';
                    break;
                case 9:
                    signal[1] = (byte) 'i';
                    break;
                case 10:
                    signal[1] = (byte) 'j';
                    break;
                case 11:
                    signal[1] = (byte) 'k';
                    break;
                case 12:
                    signal[1] = (byte) 'l';
                    break;
            }

            CommonStuff.getmSerialService().write(signal);
            CommonStuff.setBrewCycle(1);

        } else if (CommonStuff.getmSerialService().getState() == 3){
            Toast.makeText(this, "Action in Progress!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Bluetooth Not Connected!", Toast.LENGTH_LONG).show();
        }
    }

    public void fill(View v)  {

        CommonStuff.setRunCycle(3);
        int cups = np.getValue();
        byte[] signal = new byte[2];

        if (CommonStuff.getBrewCycle() != 1 && CommonStuff.getmSerialService().getState() == 3){

            signal[0] = (byte) '3';

            switch (cups){
                case 1:
                    signal[1] = (byte) 'a';
                    break;
                case 2:
                    signal[1] = (byte) 'b';
                    break;
                case 3:
                    signal[1] = (byte) 'c';
                    break;
                case 4:
                    signal[1] = (byte) 'd';
                    break;
                case 5:
                    signal[1] = (byte) 'e';
                    break;
                case 6:
                    signal[1] = (byte) 'f';
                    break;
                case 7:
                    signal[1] = (byte) 'g';
                    break;
                case 8:
                    signal[1] = (byte) 'h';
                    break;
                case 9:
                    signal[1] = (byte) 'i';
                    break;
                case 10:
                    signal[1] = (byte) 'j';
                    break;
                case 11:
                    signal[1] = (byte) 'k';
                    break;
                case 12:
                    signal[1] = (byte) 'l';
                    break;
            }

            CommonStuff.getmSerialService().write(signal);
            CommonStuff.setBrewCycle(1);

        } else if (CommonStuff.getmSerialService().getState() == 3){
            Toast.makeText(this, "Action in Progress!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Bluetooth Not Connected!", Toast.LENGTH_LONG).show();
        }
    }

    public void brew(View v){

        CommonStuff.setRunCycle(4);
        int cups = np.getValue();
        byte[] signal = new byte[2];

        if (CommonStuff.getBrewCycle() != 1 && CommonStuff.getmSerialService().getState() == 3){

            signal[0] = (byte) '4';

            switch (cups){
                case 1:
                    signal[1] = (byte) 'a';
                    break;
                case 2:
                    signal[1] = (byte) 'b';
                    break;
                case 3:
                    signal[1] = (byte) 'c';
                    break;
                case 4:
                    signal[1] = (byte) 'd';
                    break;
                case 5:
                    signal[1] = (byte) 'e';
                    break;
                case 6:
                    signal[1] = (byte) 'f';
                    break;
                case 7:
                    signal[1] = (byte) 'g';
                    break;
                case 8:
                    signal[1] = (byte) 'h';
                    break;
                case 9:
                    signal[1] = (byte) 'i';
                    break;
                case 10:
                    signal[1] = (byte) 'j';
                    break;
                case 11:
                    signal[1] = (byte) 'k';
                    break;
                case 12:
                    signal[1] = (byte) 'l';
                    break;
            }

            CommonStuff.setBrewCycle(1);
            CommonStuff.getmSerialService().write(signal);

        } else if (CommonStuff.getmSerialService().getState() == 3){
            Toast.makeText(this, "Action in Progress!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Bluetooth Not Connected!", Toast.LENGTH_LONG).show();
        }
    }

}
