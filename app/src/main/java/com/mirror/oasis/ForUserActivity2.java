package com.mirror.oasis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ForUserActivity2 extends AppCompatActivity {

    RelativeLayout one, two, three, nextButton;

    public static String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_user2);

        data = "";
        one = (RelativeLayout) findViewById(R.id.one);
        two = (RelativeLayout) findViewById(R.id.two);
        three = (RelativeLayout) findViewById(R.id.three);
        nextButton = (RelativeLayout) findViewById(R.id.nextButton);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBackground();
                one.setBackgroundResource(R.drawable.layout6);
                data = "농업";
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBackground();
                two.setBackgroundResource(R.drawable.layout6);
                data = "양봉업";
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBackground();
                three.setBackgroundResource(R.drawable.layout6);
                data = "어업";
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ForUserActivity2.this, data , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForUserActivity2.this, ForUserActivity3.class);
                startActivity(intent);
            }
        });

    }

    public void initBackground() {
        one.setBackgroundResource(R.drawable.layout5);
        two.setBackgroundResource(R.drawable.layout5);
        three.setBackgroundResource(R.drawable.layout5);
    }
}