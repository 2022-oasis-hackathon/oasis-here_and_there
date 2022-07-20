package com.mirror.oasis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mirror.oasis.chat.ChatActivity;
import com.mirror.oasis.chat.Users;
import com.mirror.oasis.home.HomeData;
import com.mirror.oasis.pre.PhotoData;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static String TAG = "DetailActivity";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("users");
    DatabaseReference myRef = database.getReference("datas");

    DatabaseReference chatRef = database.getReference("chats");

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    private RecyclerView detailRecyclerView;
    private DetailAdapter detailAdapter;
    private LinearLayoutManager detailLayoutManager;
    private List<String> photoKeys;
    private List<PhotoData> photoDataList = new ArrayList<>();

    ImageButton backButton;
    TextView location, name, detailLocation, info, detail, date1, date2, date3, representative, textView;
    Button callButton, chatButton;
    String phone;
    ProgressBar progressBar;

    String key;

    // 상세화면 주인 정보
    String userKey;
    String userProfile;
    String userId;
    String userNickName;

    boolean already = false;
    String alreadykey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        key = intent.getStringExtra("key");

        detailRecyclerView = (RecyclerView) findViewById(R.id.detailRecyclerView);
        detailRecyclerView.setHasFixedSize(true);
        detailLayoutManager = new LinearLayoutManager(this);
        detailLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        detailRecyclerView.setLayoutManager(detailLayoutManager);

        detailAdapter = new DetailAdapter(photoDataList);

        detailRecyclerView.setAdapter(detailAdapter);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        location = (TextView) findViewById(R.id.location);
        name = (TextView) findViewById(R.id.name);
        detailLocation = (TextView) findViewById(R.id.detailLocation);
        info = (TextView) findViewById(R.id.info);
        detail = (TextView) findViewById(R.id.detail);
        date1 = (TextView) findViewById(R.id.date1);
        date2 = (TextView) findViewById(R.id.date2);
        date3 = (TextView) findViewById(R.id.date3);
        textView = (TextView) findViewById(R.id.textView);


        representative = (TextView) findViewById(R.id.representative);
        callButton = (Button) findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tel = "tel:"+phone;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tel));
                startActivity(intent);
            }
        });

        chatButton = (Button) findViewById(R.id.chatButton);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userKey.equals("지자체")) {
                    Toast.makeText(DetailActivity.this, "해당 상대와는 채팅을 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (already) {
                    Intent intent = new Intent(DetailActivity.this, ChatActivity.class);
                    intent.putExtra("key", alreadykey);
                    intent.putExtra("userProfile", userProfile);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userKey", userKey);
                    intent.putExtra("userNickName", userNickName);
                    startActivity(intent);
                    finish();
                } else {
                    String chatKey = chatRef.push().getKey();
                    System.out.println(MainActivity.myNickName + " " + userNickName + "~~~~~~~~~~~~~~~~~~~~~~~`");
                    chatRef.child(chatKey).child("users").setValue(new Users(MainActivity.myNickName, userNickName));
                    Intent intent = new Intent(DetailActivity.this, ChatActivity.class);
                    intent.putExtra("key", chatKey);
                    intent.putExtra("userProfile", userProfile);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userKey", userKey);
                    intent.putExtra("userNickName", userNickName);
                    startActivity(intent);
                    System.out.println("Gd11212gd");
                }




            }
        });

        init();
    }

    public void init() {
        progressBar.setVisibility(View.VISIBLE);
        myRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                HomeData homeData = snapshot.getValue(HomeData.class);
                // location, name, detailLocation, info, detail, date1, date2, date3, representative
                location.setText(homeData.getLocation());
                name.setText(homeData.getName());
                detailLocation.setText(homeData.getDetailLocation());
                info.setText(homeData.getInfo());
                detail.setText(homeData.getDetail());
                date1.setText(homeData.getDate1());
                date2.setText(homeData.getDate2());
                date3.setText(homeData.getDate3());
                representative.setText(homeData.getRepresentative());
                textView.setText(homeData.getWriter());
                phone = homeData.getPhone();
                photoKeys = homeData.getPhotoKeys();

                userKey = homeData.getWriter();

                if (!userKey.equals("지자체")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            for (DataSnapshot snapshot1: snapshot.getChildren())
                            {
                                UserInfo value = snapshot1.getValue(UserInfo.class);
                                if (value.getKey().equals(userKey)) {
                                    userId = value.getId();
                                    userProfile = value.getProfileUri();
                                    userNickName = value.getNickName();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                }

                for (int i = 0; i < photoKeys.size(); i++) {
                    StorageReference tempStorage = storageRef.child("datas/" + photoKeys.get(i) + ".jpg");
                    tempStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            PhotoData photoData = new PhotoData(uri);
                            photoDataList.add(photoData);
                            detailAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // 1번 key 전부 가져옴
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    //Log.d("Snapshot1", snapshot1.toString());

                    for (DataSnapshot snapshot2: snapshot1.getChildren()) {
                        // snapshot2 key: chat, info, users   value :

                        // users ==> myId
                        if (snapshot2.getKey().equals("users")) {
                            Users users = snapshot2.getValue(Users.class);
                            System.out.println(users.getUser1() + " " + users.getUser2() + " " + MainActivity.myNickName + " " + userNickName);
                            if (users.getUser1().equals(MainActivity.myNickName) && users.getUser2().equals(userNickName)
                                    ||
                                    users.getUser1().equals(userNickName) && users.getUser2().equals(MainActivity.myNickName)) {
                                already = true;
                                alreadykey = snapshot1.getKey();
                                //String putKey = snapshot1.getKey();
//                                Intent intent = new Intent(DetailActivity.this, ChatActivity.class);
//                                intent.putExtra("key", putKey);
//                                intent.putExtra("userProfile", userProfile);
//                                intent.putExtra("userId", userId);
//                                intent.putExtra("userKey", userKey);
//                                intent.putExtra("userNickName", userNickName);
//                                startActivity(intent);
//                                return;

                            } else {

                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}