package com.example.hl_bob.yanglaosystem.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.hl_bob.yanglaosystem.BlueTooth.BlueToothActivity;
import com.example.hl_bob.yanglaosystem.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        LinearLayout button_bluetooth = (LinearLayout) findViewById(R.id.bluetooth);
        LinearLayout button_check = (LinearLayout) findViewById(R.id.checkdata);

        //蓝牙入口
        button_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_bluetooth = new Intent(MainActivity.this, BlueToothActivity.class);
                startActivity(intent_bluetooth);
            }
        });

        //数据查询入口
        button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
