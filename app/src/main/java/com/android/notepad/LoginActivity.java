package com.android.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    private Button mBtLogin;
    private TextView mBtRegister;
    private TextView tv_back;
    private EditText username;
    private EditText password;
    private CheckBox ck_rememer;
    private User user;
    private SQLiteDatabase db;
    private SQLiteHelper sqLiteHelper;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sqLiteHelper = new SQLiteHelper(this);
        initView();
    }

    private void initView() {
        tv_back = findViewById(R.id.tv_backmain);
        mBtRegister = findViewById(R.id.log_register);
        mBtLogin = findViewById(R.id.bt_login);
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        ck_rememer = findViewById(R.id.cb_remember);
        final boolean isRemember = preferences.getBoolean("remember_password", false);
        if (isRemember) {
            String account = preferences.getString("account", "");
            String pass = preferences.getString("pass", "");
            username.setText(account);
            password.setText(pass);
            ck_rememer.setChecked(true);
        }

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mBtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim();
                String passwd = password.getText().toString().trim();
                if (login(user, passwd)) {
                    editor = preferences.edit();
                    if (ck_rememer.isChecked()) {
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", user);
                        editor.putString("pass", passwd);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("name", user);
                    startActivity(intent);
                    finish();
                } else {
                    username.setText("");
                    password.setText("");
                    Toast.makeText(getApplicationContext(), "用户名或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public boolean login(String username, String password) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        String sql = "select * from User where username=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }
}

