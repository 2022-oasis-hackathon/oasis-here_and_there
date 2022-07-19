package com.mirror.oasis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ForUserActivity3 extends AppCompatActivity {

    RelativeLayout one, two, three, four, nextButton;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    DatabaseReference foruserRef = database.getReference("foruers");

    public static String data = "";

    String key ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_user3);
        one = (RelativeLayout) findViewById(R.id.one);
        two = (RelativeLayout) findViewById(R.id.two);
        three = (RelativeLayout) findViewById(R.id.three);
        four = (RelativeLayout) findViewById(R.id.four);
        nextButton = (RelativeLayout) findViewById(R.id.nextButton);

        key = ForUserActivity1.myKey;

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBackground();
                one.setBackgroundResource(R.drawable.layout6);
                data = "마을 회관 근처";
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBackground();
                two.setBackgroundResource(R.drawable.layout6);
                data = "버스 정류장 근처";
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBackground();
                three.setBackgroundResource(R.drawable.layout6);
                data = "사람이 적은 곳";
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBackground();
                four.setBackgroundResource(R.drawable.layout6);
                data = "사람이 많은 곳";
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ForUserActivity2.this, ForUserActivity3.class);
//                startActivity(intent);

                foruserRef.child(key).setValue(new ForUserData(ForUserActivity1.data, ForUserActivity2.data, ForUserActivity3.data)) ;
                Intent intent = new Intent(ForUserActivity3.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void initBackground() {
        one.setBackgroundResource(R.drawable.layout5);
        two.setBackgroundResource(R.drawable.layout5);
        three.setBackgroundResource(R.drawable.layout5);
        four.setBackgroundResource(R.drawable.layout5);

    }
}