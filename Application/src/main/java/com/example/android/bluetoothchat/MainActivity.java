/* ********************************************************
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*  Sources: https://github.com/venmo/android-pin
*/

package com.example.android.bluetoothchat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.android.common.activities.SampleActivityBase;

import java.sql.SQLOutput;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends SampleActivityBase {

    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_ENABLE_BT = 3;

    protected MyApplication app;
    private static BluetoothSerialService mSerialService = null;
    private NumberPicker np;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (MyApplication)getApplication();

        // If the adapter is null, then Bluetooth is not supported
        if (CommonStuff.getmBluetoothAdapter() == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            this.finish();
        }

        if (!CommonStuff.getmBluetoothAdapter().isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        np = (NumberPicker) findViewById(R.id.numberPicker);

        np.setMinValue(1);
        np.setMaxValue(12);
        np.setWrapSelectorWheel(false);
        np.setValue(1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.coffee_connect: {
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                return true;
            }
            case R.id.manual: {
                Intent serverIntent = new Intent(this, Manual_Mode.class);
                startActivityForResult(serverIntent, 2);
                return true;
            }
            case R.id.cup_calibrate: {
                Intent serverIntent = new Intent(this, Calibrate.class);
                startActivityForResult(serverIntent, 3);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void startBrew(View v) throws InterruptedException {

        CommonStuff.setRunCycle(1);
        int cups = np.getValue();
        byte[] signal = new byte[2];

        if (CommonStuff.getBrewCycle() != 1 && CommonStuff.getmSerialService().getState() == 3){

            signal[0] = (byte) '1';

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
            Toast.makeText(this, "Brew Started!", Toast.LENGTH_LONG).show();
        } else if (CommonStuff.getmSerialService().getState() == 3){
            Toast.makeText(this, "Action in Progress!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Bluetooth Not Connected!", Toast.LENGTH_LONG).show();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case 1:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == this.RESULT_OK) {
                    // Get the device MAC address
                    mSerialService = new BluetoothSerialService(this, mHandlerBT);
                    CommonStuff.setmSerialService(mSerialService);
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Get the BLuetoothDevice object
                    BluetoothDevice device = CommonStuff.getmBluetoothAdapter().getRemoteDevice(address);
                    // Attempt to connect to the device
                    mSerialService.connect(device);
                }
                break;

            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns

        }
    }

    // The Handler that gets information back from the BluetoothService
    private final Handler mHandlerBT = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Intent nextScreen = new Intent(getApplicationContext(), CreateNotificationActivity.class);
            startActivity(nextScreen);
            CommonStuff.setBrewCycle(0);
        }
    };
}
