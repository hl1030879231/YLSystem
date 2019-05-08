package com.example.hl_bob.yanglaosystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hl_bob.yanglaosystem.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by $HL on 2019/1/11.
 */

public class RegisterActivity extends AppCompatActivity {
    private Button button_reg;
    private EditText username;
    private EditText password;
    private EditText password_agine;

    private String reg_phone;
    private String reg_password;
    private String reg_repassword;
    private int register_key = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    public void init(){
        button_reg = (Button)findViewById(R.id.register_button);
        username = (EditText)findViewById(R.id.phonum_register);
        password = (EditText)findViewById(R.id.key_register);
        password_agine = (EditText)findViewById(R.id.rekey_register);
        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_key = 0;
                reg_phone = String.valueOf(username.getText());
                reg_password = String.valueOf(password.getText());
                reg_repassword = String.valueOf(password_agine.getText());
                if(reg_phone.equals("") || reg_phone == null){
                    Toast.makeText(RegisterActivity.this,"手机号不能为空！",Toast.LENGTH_SHORT).show();
                    register_key = 1;
                }
                else if(reg_password.equals("") || reg_password == null){
                    Toast.makeText(RegisterActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                    register_key = 1;
                }
                else if(reg_repassword.equals("") || reg_repassword == null){
                    Toast.makeText(RegisterActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                    register_key = 1;
                }
                else if(!reg_repassword.equals(reg_password)){
                    Toast.makeText(RegisterActivity.this,"两次输入的密码不一致！",Toast.LENGTH_SHORT).show();
                    register_key = 1;
                }
                if(register_key == 0){
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                finish();
                }
            }
        });
    }
}





