package com.example.hl_bob.yanglaosystem.BlueTooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

/**
 * Created by $HL on 2019/4/14.
 */
public class BlueToothController {
    private BluetoothAdapter bluetoothAdapter;

    public BlueToothController(){
        bluetoothAdapter =BluetoothAdapter.getDefaultAdapter();
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    /**
     * 打开蓝牙
     */
    public void turnOnBlueTooth(Activity activity, int requestCode) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 查找设备
     */
    public void findDevice(){
        assert (bluetoothAdapter != null);
        bluetoothAdapter.startDiscovery();
    }
}
