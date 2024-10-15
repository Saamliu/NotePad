package com.android.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private TextView tv_back;
    private Button bt_register;
    private EditText etName;
    private EditText etPwd;
    private EditText etRepwd;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

    }

    private void initView() {
        sqLiteHelper = new SQLiteHelper(this);
        tv_back = findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        etName = findViewById(R.id.et_name);
        etPwd = findViewById(R.id.et_pwd);
        etRepwd = findViewById(R.id.et_repwd);
        bt_register = findViewById(R.id.bt_register);
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                String repwd = etRepwd.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(repwd)) {
                    if (pwd.equals(repwd)) {
                        sqLiteHelper.insertUser(name, pwd);
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.putExtra("name", name);
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    } else if (!pwd.equals(repwd)) {
                        Toast.makeText(getApplicationContext(), "两次密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
                        etName.setText("");
                        etPwd.setText("");
                        etRepwd.setText("");
                    }
                }
            }
        });
    }

}