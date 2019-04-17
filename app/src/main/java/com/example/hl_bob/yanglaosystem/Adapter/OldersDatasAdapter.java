package com.example.hl_bob.yanglaosystem.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hl_bob.yanglaosystem.R;

import java.util.List;
import java.util.Map;

import static android.R.attr.data;
import static android.R.attr.theme;
import static android.R.id.list;

/**
 * Created by $HL on 2019/1/12.
 */

public class OldersDatasAdapter extends BaseAdapter {
    private LayoutInflater mInflater = null;
    private List<Map<String, Object>> data;

    public OldersDatasAdapter(Context context,List<Map<String, Object>> datalist)
    {
        //根据context上下文加载布局，这里的是Demo17Activity本身，即this
        this.mInflater = LayoutInflater.from(context);
        this.data = datalist;
    }

    @Override
    public int getCount() {
        //How many items are in the data set represented by this Adapter.
        //在此适配器中所代表的数据集中的条目数
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        // Get the data item associated with the specified position in the data set.
        //获取数据集中与指定索引对应的数据项
        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        //Get the row id associated with the specified position in the list.
        //获取在列表中与指定索引对应的行id
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        //如果缓存convertView为空，则需要创建View
        if(convertView == null)
            {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.oldersdata_item, null);
            holder.id = (TextView)convertView.findViewById(R.id.id_item);
            holder.pos_x = (TextView)convertView.findViewById(R.id.pos_x_item);
            holder.pos_y = (TextView)convertView.findViewById(R.id.pos_y_item);
            holder.pressure = (TextView)convertView.findViewById(R.id.pressure_item);
            holder.check_date = (TextView)convertView.findViewById(R.id.checkdate_item);
            holder.balance = (TextView)convertView.findViewById(R.id.blood_oxygen);
            holder.heart = (TextView)convertView.findViewById(R.id.balance);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.id.setText(String.valueOf(data.get(position).get("id")));
        holder.pos_x.setText(String.valueOf(data.get(position).get("pos_x")));
        holder.pos_y.setText(String.valueOf(data.get(position).get("pos_y")));
        holder.pressure.setText(String.valueOf(data.get(position).get("pressure")));
        holder.check_date.setText(String.valueOf(data.get(position).get("check_date")));
        holder.heart.setText(String.valueOf(data.get(position).get("heart")));
        holder.balance.setText(String.valueOf(data.get(position).get("balance")));

        Log.d("test_heart",String.valueOf(data.get(position).get("heart")));
        Log.d("test_balance",String.valueOf(data.get(position).get("balance")));
        return convertView;
    }
    //ViewHolder静态类
    static class ViewHolder
    {
        public TextView id;
        public TextView pos_x;
        public TextView pos_y;
        public TextView pressure;
        public TextView check_date;
        public TextView balance;
        public TextView heart;
    }
}
