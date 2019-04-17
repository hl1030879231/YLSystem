package com.example.hl_bob.yanglaosystem.BlueTooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hl_bob.yanglaosystem.Activity.MainActivity;
import com.example.hl_bob.yanglaosystem.Adapter.DeviceListAdapter;
import com.example.hl_bob.yanglaosystem.BlueTooth.connect.AcceptThread;
import com.example.hl_bob.yanglaosystem.BlueTooth.connect.ConnectThread;
import com.example.hl_bob.yanglaosystem.BlueTooth.connect.Constant;
import com.example.hl_bob.yanglaosystem.Database.MyDatabaseHelper;
import com.example.hl_bob.yanglaosystem.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by $HL on 2019/1/12.
 */

public class BlueToothActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;
    private int id = 0;

    private MyDatabaseHelper dbHelper;
    private TextView title;
    private LinearLayout back;

    private BlueToothController mController = new BlueToothController();

    private List<BluetoothDevice> mDeviceList = new ArrayList<>();
    private DeviceListAdapter deviceListAdapter;
    private ListView device_listView;
    private Toast mToast;

    private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;
    private Handler mUIHandler = new MyHandler();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        initTitleBar();
        init();
        event();
        registerBluetoothReceiver();
    }

    private void registerBluetoothReceiver() {
        IntentFilter filter = new IntentFilter();
        //开始查找
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        //结束查找
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        //查找设备
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        //设备扫描模式改变
        filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        //绑定状态
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

        registerReceiver(receiver, filter);
    }

    //注册广播监听搜索结果
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                //setProgressBarIndeterminateVisibility(true);
                //初始化数据列表
                mDeviceList.clear();
                deviceListAdapter.notifyDataSetChanged();
            } else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                //setProgressBarIndeterminateVisibility(false);
            }
            else if(BluetoothDevice.ACTION_FOUND.equals(action)) {//广播监听到是查找到设备列表
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //找到一个添加一个
                mDeviceList.add(device);
                deviceListAdapter.notifyDataSetChanged();

            } else if(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)) {  //此处作用待细查
                int scanMode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, 0);
                if(scanMode == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
                    setProgressBarIndeterminateVisibility(true);
                } else {
                    setProgressBarIndeterminateVisibility(false);
                }

            } else if(BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(remoteDevice == null) {
                    showToast("无设备");
                    return;
                }
                int status = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, 0);
                if(status == BluetoothDevice.BOND_BONDED) {
                    showToast("已绑定" + remoteDevice.getName());
                } else if(status == BluetoothDevice.BOND_BONDING) {
                    showToast("正在绑定" + remoteDevice.getName());
                } else if(status == BluetoothDevice.BOND_NONE) {
                    showToast("未绑定" + remoteDevice.getName());
                }
            }
        }
    };

    public void init(){
        device_listView = (ListView)findViewById(R.id.device_list);
        deviceListAdapter = new DeviceListAdapter(mDeviceList, this);
        device_listView.setAdapter(deviceListAdapter);

        //下面这部分代码是上次添加数据时候用的数据库
        dbHelper = new MyDatabaseHelper(BlueToothActivity.this, "OldersData.db", null, 1);
        dbHelper.getWritableDatabase();
    }

    public void initTitleBar(){
        title = (TextView)findViewById(R.id.title_name);
        back = (LinearLayout)findViewById(R.id.back);
        title.setText("蓝 牙");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public void event(){
        Button button_add = (Button)findViewById(R.id.add_message);
        Button button_open = (Button)findViewById(R.id.turnOn_bluetooth);
        Button button_findDevice = (Button)findViewById(R.id.find_Device);
        Button button_satHello = (Button)findViewById(R.id.say_hello);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                insert(db);
                id++;
            }
        });

        //打开蓝牙
        button_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mController.turnOnBlueTooth(BlueToothActivity.this,REQUEST_CODE);
            }
        });

        //查找设备的功能
        button_findDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deviceListAdapter.refresh(mDeviceList);
                mController.findDevice();
                //设备列表点击即请求配对
                device_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        BluetoothDevice device = mDeviceList.get(i);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            device.createBond();
                        }
                    }
                });
            }
        });

        //say_hello

    }

    //设置toast的标准格式
    private void showToast(String text){
        if(mToast == null){
            mToast = Toast.makeText(this, text,Toast.LENGTH_SHORT);
            mToast.show();
        }
        else {
            mToast.setText(text);
            mToast.show();
        }

    }

    private class MyHandler extends Handler {

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case Constant.MSG_GOT_DATA:
                    showToast("data:" + String.valueOf(message.obj));
                    break;
                case Constant.MSG_ERROR:
                    showToast("error:" + String.valueOf(message.obj));
                    break;
                case Constant.MSG_CONNECTED_TO_SERVER:
                    showToast("连接到服务端");
                    break;
                case Constant.MSG_GOT_A_CLINET:
                    showToast("找到服务端");
                    break;
            }
        }
    }

    private void say(String word) {
        if (mAcceptThread != null) {
            try {
                mAcceptThread.sendData(word.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        else if( mConnectThread != null) {
            try {
                mConnectThread.sendData(word.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }
    


    //数据库插入数据
    private void insert(SQLiteDatabase db){
        //实例化常量值
        ContentValues cValue = new ContentValues();//用来储存数据的对象
        cValue.put("id",id);
        cValue.put("position_x",11.11);
        cValue.put("position_y",22.22);
        cValue.put("pressure",95);
        cValue.put("check_date",16);
        cValue.put("heart",100);
        cValue.put("balance","正常");

        Log.d("test_id",String.valueOf(id));
        //调用insert()方法插入数据
        db.insert("Olders",null,cValue);
    }

    /**
     * 更新数据
     */
    private void update(SQLiteDatabase db){
        ContentValues cValue = new ContentValues();//用来储存数据的对象
        cValue.put("position_x",11.11);
        cValue.put("position_y",22.22);
        cValue.put("pressure",90);
        cValue.put("check_date",15);
        cValue.put("blood_oxygen",60);
        cValue.put("balance","正常");
        db.update("Olders", cValue, "id=?", new String[]{"1"});
    }



}
