package com.example.hl_bob.yanglaosystem.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * 数据库帮助类
 * Created by $HL on 2019/1/12.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_OLDERS =  "create table Olders ("//创建一个数据表
            + "id integer ,"
            + "position_x real, "
            + "position_y real, "
            + "pressure integer, "
            + "heart integer, "
            + "blood_oxygen integer, "
            + "check_time integer, "
            + "balance verchar(20) ,"
            + "check_date integer)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_OLDERS);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
