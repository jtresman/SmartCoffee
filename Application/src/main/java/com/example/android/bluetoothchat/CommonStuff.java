package com.example.android.bluetoothchat;

import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import android.os.Message;


public class CommonStuff {

    private static CommonStuff instance;

    private static BluetoothSerialService mSerialService = null;
    private static BluetoothAdapter mBluetoothAdapter = null;
    private static ByteQueue mByteQueue;

    private static int brewCycle;
    private static int runCycle;

    public static BluetoothSerialService getmSerialService() {
        return mSerialService;
    }

    public static BluetoothAdapter getmBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    public static ByteQueue getmByteQueue() {
        return mByteQueue;
    }

    public static int getBrewCycle() {
        return brewCycle;
    }

    public static int getRunCycle() {
        return runCycle;
    }

    public static void setRunCycle(int runCycle) {
        CommonStuff.runCycle = runCycle;
    }

    public static void setmSerialService(BluetoothSerialService mSerialService) {
        CommonStuff.mSerialService = mSerialService;
    }

    public static void setBrewCycle(int brewCycle) {
        CommonStuff.brewCycle = brewCycle;
    }

    public static void initInstance(){
        if (instance == null)
        {
            // Create the instance
            instance = new CommonStuff();
            mByteQueue = new ByteQueue(4 * 1024);
            brewCycle = 0;
            runCycle = 0;

            // Get local Bluetooth adapter
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        }

    }

    public static CommonStuff getInstance() {
        return instance;
    }

    static class ByteQueue {
        public ByteQueue(int size) {
            mBuffer = new byte[size];
        }

        public int getBytesAvailable() {
            synchronized(this) {
                return mStoredBytes;
            }
        }

        public int read(byte[] buffer, int offset, int length)
                throws InterruptedException {
            if (length + offset > buffer.length) {
                throw
                        new IllegalArgumentException("length + offset > buffer.length");
            }
            if (length < 0) {
                throw
                        new IllegalArgumentException("length < 0");

            }
            if (length == 0) {
                return 0;
            }
            synchronized(this) {
                while (mStoredBytes == 0) {
                    wait();
                }
                int totalRead = 0;
                int bufferLength = mBuffer.length;
                boolean wasFull = bufferLength == mStoredBytes;
                while (length > 0 && mStoredBytes > 0) {
                    int oneRun = Math.min(bufferLength - mHead, mStoredBytes);
                    int bytesToCopy = Math.min(length, oneRun);
                    System.arraycopy(mBuffer, mHead, buffer, offset, bytesToCopy);
                    mHead += bytesToCopy;
                    if (mHead >= bufferLength) {
                        mHead = 0;
                    }
                    mStoredBytes -= bytesToCopy;
                    length -= bytesToCopy;
                    offset += bytesToCopy;
                    totalRead += bytesToCopy;
                }
                if (wasFull) {
                    notify();
                }
                return totalRead;
            }
        }

        public void write(byte[] buffer, int offset, int length)
                throws InterruptedException {
            if (length + offset > buffer.length) {
                throw
                        new IllegalArgumentException("length + offset > buffer.length");
            }
            if (length < 0) {
                throw
                        new IllegalArgumentException("length < 0");

            }
            if (length == 0) {
                return;
            }
            synchronized(this) {
                int bufferLength = mBuffer.length;
                boolean wasEmpty = mStoredBytes == 0;
                while (length > 0) {
                    while(bufferLength == mStoredBytes) {
                        wait();
                    }
                    int tail = mHead + mStoredBytes;
                    int oneRun;
                    if (tail >= bufferLength) {
                        tail = tail - bufferLength;
                        oneRun = mHead - tail;
                    } else {
                        oneRun = bufferLength - tail;
                    }
                    int bytesToCopy = Math.min(oneRun, length);
                    System.arraycopy(buffer, offset, mBuffer, tail, bytesToCopy);
                    offset += bytesToCopy;
                    mStoredBytes += bytesToCopy;
                    length -= bytesToCopy;
                }
                if (wasEmpty) {
                    notify();
                }
            }
        }

        private byte[] mBuffer;
        private int mHead;
        private int mStoredBytes;
    }

}
