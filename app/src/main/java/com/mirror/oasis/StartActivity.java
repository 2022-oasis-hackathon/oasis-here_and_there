package com.mirror.oasis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class StartActivity extends AppCompatActivity {

    RelativeLayout loginButton, joinButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        loginButton = (RelativeLayout) findViewById(R.id.loginButton);
        joinButton = (RelativeLayout) findViewById(R.id.joinButton);

        loginButton.setEnabled(true);
        loginButton.setClickable(true);

        joinButton.setEnabled(true);
        joinButton.setClickable(true);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
               // finish();
            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, JoinActivity.class);
                startActivity(intent);
               // finish();
            }
        });
    }
}