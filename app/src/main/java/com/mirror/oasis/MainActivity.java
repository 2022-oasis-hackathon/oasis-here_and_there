package com.mirror.oasis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.mirror.oasis.chat.ChatFragment;
import com.mirror.oasis.community.CommunityFragment;
import com.mirror.oasis.home.HomeFragment;
import com.mirror.oasis.mypage.MyPageFragment;
import com.mirror.oasis.pre.PreFragment;

public class MainActivity extends AppCompatActivity {

    // test
    //안녕아아아

    public static String TAG = "MainActivity";
    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    CommunityFragment communityFragment = new CommunityFragment();
    PreFragment preFragment = new PreFragment();
    ChatFragment chatFragment = new ChatFragment();
    MyPageFragment myPageFragment = new MyPageFragment();

    public static String myId;
    public static String myKey;
    public static String myProfile;
    public static String myNickName;
    public static String myUserInfo1;
    public static String myUserInfo2;
    public static String myUserInfo3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    /*
        Intent intent = getIntent();
        myKey = intent.getStringExtra("key");
        myId = intent.getStringExtra("id");
        myProfile = intent.getStringExtra("profile");
        myNickName = intent.getStringExtra("nickName");
        //myUserInfo1 = intent.getStringExtra("userInfo1");
        //myUserInfo2 = intent.getStringExtra("userInfo2");
        //myUserInfo3 = intent.getStringExtra("userInfo3");




                myId = ForUserActivity1.myId;
        myKey = ForUserActivity1.myKey;
        myProfile = ForUserActivity1.myProfile;
        myNickName = ForUserActivity1.myNickName;
        myUserInfo1 = ForUserActivity1.data;
        myUserInfo2 = ForUserActivity2.data;
        myUserInfo3 = ForUserActivity3.data;

                myKey = "-N7GXhGksk9Y0GP_7b5i";
        myId = "test123@naver.com";
        myNickName = "귀농짱";
        myProfile = "https://firebasestorage.googleapis.com/v0/b/oasis-8f075.appspot.com/o/users%2F-N7GXhGksk9Y0GP_7b5i.jpg?alt=media&token=18394287-aef8-42f4-8785-5480b07d86ad";
        myUserInfo1 = "전라북도 익산시,전라남도 여수시, 전라남도 곡성군";
        myUserInfo2 = "농업";
        myUserInfo3 = "마을회관,";
 */

        myId = ForUserActivity1.myId;
        myKey = ForUserActivity1.myKey;
        myProfile = ForUserActivity1.myProfile;
        myNickName = ForUserActivity1.myNickName;
        myUserInfo1 = ForUserActivity1.data;
        myUserInfo2 = ForUserActivity2.data;
        myUserInfo3 = ForUserActivity3.data;


        Log.d(TAG, myKey + " " + myId + " " + myNickName + " " + myProfile);
        System.out.println(myUserInfo1 + " " + myUserInfo2 + " " + myUserInfo3);


        bottomNavigationView = findViewById(R.id.bottomNavigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                    case R.id.preExperience:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, preFragment).commit();
                        return true;
                    case R.id.community:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, communityFragment).commit();
                        return true;
                    case R.id.chat:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, chatFragment).commit();
                        return true;
                    case R.id.mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, myPageFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}