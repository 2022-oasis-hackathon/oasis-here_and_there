package com.mirror.oasis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ForUserActivity1 extends AppCompatActivity {

    RecyclerView horiRecyclerView;
    ForUserHoriAdapter horiAdapter;
    LinearLayoutManager horiLayoutManager;
    List<String> horiData = new ArrayList<>();

    RecyclerView recyclerView;
    ForUserAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<String> dataList = new ArrayList<>();

    RelativeLayout nextButton;

    public static String myId;
    public static String myKey;
    public static String myProfile;
    public static String myNickName;
    public static String myUserInfo1;
    public static String myUserInfo2;
    public static String myUserInfo3;

    public static String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_user1);

        data = "";
        Intent intent = getIntent();
        myKey = intent.getStringExtra("key");
        myId = intent.getStringExtra("id");
        myProfile = intent.getStringExtra("profile");
        myNickName = intent.getStringExtra("nickName");

        System.out.println("Login: " + LoginActivity.myKey);
        System.out.println("Join: " + JoinActivity.myKey);
        
        if (LoginActivity.myKey == null) {
            myKey = JoinActivity.myKey;
            myId = JoinActivity.myId;
            myProfile = JoinActivity.myProfile;
            myNickName = JoinActivity.myNickName;
        } else {
            myKey = LoginActivity.myKey;
            myId = LoginActivity.myId;
            myProfile = LoginActivity.myProfile;
            myNickName = LoginActivity.myNickName;
        }

        nextButton = (RelativeLayout) findViewById(R.id.nextButton);
        nextButton.setEnabled(true);
        nextButton.setClickable(true);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForUserActivity1.this, ForUserActivity2.class);
                startActivity(intent);
            }
        });

        horiRecyclerView = (RecyclerView) findViewById(R.id.horizontalRecyclerView);
        horiRecyclerView.setHasFixedSize(true);
        horiLayoutManager = new LinearLayoutManager(this);
        horiLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        horiRecyclerView.setLayoutManager(horiLayoutManager);

        horiAdapter = new ForUserHoriAdapter(horiData, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object tag = view.getTag();
                if (tag != null) {
                    int position = (int)tag;


                }
            }
        });
        horiRecyclerView.setAdapter(horiAdapter);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        dataList.add("전라남도 순천시");
        dataList.add("전라북도 군산시");
        dataList.add("전라남도 구례군");
        dataList.add("전라북도 전주시");
        dataList.add("전라북도 익산시");
        dataList.add("전라북도 정읍시");
        dataList.add("전라북도 남원시");
        dataList.add("전라북도 김제시");
        dataList.add("전라북도 완주군");
        dataList.add("전라북도 진안군");
        dataList.add("전라북도 무주군");
        dataList.add("전라북도 장수군");
        dataList.add("전라북도 임실군");
        dataList.add("전라북도 순창군");
        dataList.add("전라북도 고창군");
        dataList.add("전라북도 부안군");
        dataList.add("전라남도 목포시");
        dataList.add("전라남도 여수시");
        dataList.add("전라남도 나주시");
        dataList.add("전라남도 광양시");
        dataList.add("전라남도 담양군");
        dataList.add("전라남도 곡성군");

        dataList.add("전라남도 고흥군");
        dataList.add("전라남도 보성군");
        dataList.add("전라남도 화순군");
        dataList.add("전라남도 장흥군");
        dataList.add("전라남도 강진군");
        dataList.add("전라남도 해남군");
        dataList.add("전라남도 영암군");
        dataList.add("전라남도 무안군");
        dataList.add("전라남도 함평군");
        dataList.add("전라남도 영광군");
        dataList.add("전라남도 장성군");
        dataList.add("전라남도 완도군");
        dataList.add("전라남도 진도군");
        dataList.add("전라남도 신안군");

        adapter = new ForUserAdapter(dataList, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object tag = view.getTag();
                if (tag != null) {
                    int position = (int)tag;
                    if (horiData.contains(dataList.get(position)))
                        return;
                    horiData.add(dataList.get(position));
                    data += dataList.get(position) + ",";
                    horiAdapter.notifyDataSetChanged();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }
}