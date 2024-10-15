package com.android.notepad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    private TextView tVback;
    private TextView tVlogout;
    private User user;
    private TextView tv_userName;
    private TextView tv_share;
    private TextView tv_help;
    private TextView tv_banben;
    private TextView tv_use;
    private TextView tv_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        tv_userName = findViewById(R.id.user_name);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        tv_userName.setText(name);

        tVback = findViewById(R.id.back);
        tVback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tv_set = findViewById(R.id.tv_set);
        tv_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SettingActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("设置")
                        .setMessage("设置待完善中...")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();

                            }
                        }).create().show();
            }
        });


        tVlogout = findViewById(R.id.tv_logout);
        tVlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tv_share = findViewById(R.id.tv_share);
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String msg = "我的备忘录分享给你";
                intent.putExtra(Intent.EXTRA_TEXT, msg);
                startActivity(Intent.createChooser(intent, "备忘录分享"));
            }
        });
    }
}