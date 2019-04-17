package com.example.hl_bob.yanglaosystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hl_bob.yanglaosystem.R;

import static com.example.hl_bob.yanglaosystem.R.id.back;

/**
 * Created by $HL on 2019/1/11.
 */

public class LoginActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init(){
        Button button_login =  (Button)findViewById(R.id.login_button);
        Button button_regier = (Button)findViewById(R.id.register);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_login = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent_login);
                finish();
            }
        });
        button_regier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_register = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent_register);
            }
        });
    }

}
