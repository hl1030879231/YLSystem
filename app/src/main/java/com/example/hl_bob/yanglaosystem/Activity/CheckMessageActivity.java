package com.example.hl_bob.yanglaosystem.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hl_bob.yanglaosystem.Adapter.OldersDatasAdapter;
import com.example.hl_bob.yanglaosystem.BlueTooth.BlueToothActivity;
import com.example.hl_bob.yanglaosystem.Database.MyDatabaseHelper;
import com.example.hl_bob.yanglaosystem.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by $HL on 2019/1/12.
 */

public class CheckMessageActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private OldersDatasAdapter oldersDatasAdapter;
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    private ListView listView;
    private Map<String, Object> map;
    private TextView title;
    private LinearLayout back;

    private SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkmessage);
        init();
        initTitleBar();
        //查询已有的数据库信息
        dbHelper = new MyDatabaseHelper(CheckMessageActivity.this, "OldersData.db", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        query(db);
        //data = getData();
        oldersDatasAdapter = new OldersDatasAdapter(CheckMessageActivity.this,data);
        listView.setAdapter(oldersDatasAdapter);
        oldersDatasAdapter.notifyDataSetChanged();
    }

    public void init(){
        listView = (ListView)findViewById(R.id.dataslist);
    }

    public void initTitleBar(){
        title = (TextView)findViewById(R.id.title_name);
        back = (LinearLayout)findViewById(R.id.back);
        title.setText("数据查看");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    //查询数据
    private void query(SQLiteDatabase db) {
            //查询获得游标
        Cursor cursor = db.query ("Olders",null,null,null,null,null,null);//查询Olders表
        map = new HashMap<String, Object>();//用来存一个键值对数据的map
            //判断游标是否为空
        if(cursor.moveToFirst()) {
            //遍历游标
           do{
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                double position_x=cursor.getDouble(cursor.getColumnIndex("position_x"));
                double position_y=cursor.getDouble(cursor.getColumnIndex("position_y"));
                int pressure=cursor.getInt(cursor.getColumnIndex("pressure"));
                int check_date=cursor.getInt(cursor.getColumnIndex("check_date"));
               String balance=cursor.getString(cursor.getColumnIndex("balance"));
               int heart=cursor.getInt(cursor.getColumnIndex("heart"));
               map.put("id",id);
               map.put("pos_x",position_x);
               map.put("pos_y",position_y);
               map.put("pressure",pressure);
               map.put("check_date",check_date);
               map.put("balance",balance);
               map.put("heart",heart);
               data.add(map);
              // Toast.makeText(CheckMessageActivity.this, String.valueOf(position_x), Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    //获取存储数据的列表
    private List<Map<String, Object>> getData()
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            list.add(map);
        return list;
    }
}
