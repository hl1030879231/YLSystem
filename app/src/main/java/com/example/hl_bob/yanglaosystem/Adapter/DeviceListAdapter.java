package com.example.hl_bob.yanglaosystem.Adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hl_bob.yanglaosystem.R;

import java.util.List;

/**
 * Created by $HL on 2019/4/14.
 */
public class DeviceListAdapter extends BaseAdapter {
    private List<BluetoothDevice> mData;
    private Context mContext;

    public DeviceListAdapter(List<BluetoothDevice> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext.getApplicationContext();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;
        //复用view，优化性能
        if(itemView == null){
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_devicelist ,viewGroup,false);
        }

        TextView device_name = itemView.findViewById(R.id.device_name);
        TextView device_addreess = itemView.findViewById(R.id.device_address);

        //获取对应的蓝牙设备
        BluetoothDevice device = (BluetoothDevice) getItem(i);

        //显示设备名称
        device_name.setText(device.getName());
        //显示设备地址
        device_addreess.setText(device.getAddress());

        return itemView;
    }

    //刷新列表，防止搜索结果重复出现
    public void refresh(List<BluetoothDevice> data){
        mData = data;
        notifyDataSetChanged();//这个是ListView刷新列表
    }
}
