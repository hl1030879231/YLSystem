package com.example.hl_bob.yanglaosystem.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hl_bob.yanglaosystem.R;
import com.example.hl_bob.yanglaosystem.Service.WebService;

import static com.example.hl_bob.yanglaosystem.R.id.back;

/**
 * Created by $HL on 2019/1/11.
 */

public class LoginActivity extends AppCompatActivity{
    // 创建等待框
    private ProgressDialog dialog;
    // 返回的数据
    private String info;
    // 显示用户名和密码
    EditText username, password;
    // 返回主线程更新数据
    private static Handler handler = new Handler();

    private String login_phone;
    private String login_password;
    private int login_key = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init(){
        Button button_login =  (Button)findViewById(R.id.login_button);
        TextView text_regier = (TextView) findViewById(R.id.register);
        username = (EditText)findViewById(R.id.phonum_login);
        password = (EditText)findViewById(R.id.password_login);
        //登录按钮
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 提示框
                //dialog = new ProgressDialog(LoginActivity.this);
                //dialog.setTitle("提示");
               // dialog.setMessage("正在登陆，请稍后...");
               // dialog.setCancelable(false);
               // dialog.show();
                // 创建子线程，分别进行Get和Post传输
                //new Thread(new MyThread()).start();

                login_key = 0;
                login_phone = String.valueOf(username.getText());
                login_password = String.valueOf(password.getText());
                if(login_phone.equals("") || login_phone == null){
                    Toast.makeText(LoginActivity.this,"手机号不能为空！",Toast.LENGTH_SHORT).show();
                    login_key = 1;
                }
                else if(login_password.equals("") || login_password == null){
                    Toast.makeText(LoginActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                    login_key = 1;
                }
                if(login_key == 0){
                Intent intent_login = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent_login);
                Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                finish();
                }
            }
        });
        text_regier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_register = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent_register);
            }
        });
    }
    // 子线程接收数据，主线程修改数据
    public class MyThread implements Runnable {
        @Override
        public void run() {
            info = WebService.executeHttpGet(username.getText().toString(), password.getText().toString());
            // info = WebServicePost.executeHttpPost(username.getText().toString(), password.getText().toString());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this,String.valueOf(info),Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        }
    }


}
