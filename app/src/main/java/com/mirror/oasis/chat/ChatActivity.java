package com.mirror.oasis.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mirror.oasis.MainActivity;
import com.mirror.oasis.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference("chats");

    String key;
    String myId = MainActivity.myId;

    private AppCompatImageButton send;
    ImageButton backButton;
    private EditText message;
    private TextView nickName;


    List<ChatData> chatDataList = new ArrayList<>();
    RecyclerView chatActivityRecyclerView;
    ChatAdapter chatAdapter;
    RecyclerView.LayoutManager layoutManager;


    // 상세화면 주인 정보
    String userKey;
    String userProfile;
    String userId;
    String userNickName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        /*
                                        intent.putExtra("key", putKey);
                                        intent.putExtra("userProfile", userProfile);
                                        intent.putExtra("userId", userId);
                                        intent.putExtra("userKey", userKey);
                                        intent.putExtra("userNickName", userNickName);

         */
        Intent getIntent = getIntent();
        key = getIntent.getStringExtra("key");
        userKey = getIntent.getStringExtra("userKey");
        userProfile = getIntent.getStringExtra("userProfile");
        userId = getIntent.getStringExtra("userId");
        userNickName = getIntent.getStringExtra("userNickName");

        System.out.println("ChatActivity" + " " + userProfile + " " + userNickName);

        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        send = (AppCompatImageButton) findViewById(R.id.send);
        message = (EditText) findViewById(R.id.message);
        nickName = (TextView) findViewById(R.id.nickName);

        nickName.setText(userNickName);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendMessage = message.getText().toString();
                if (sendMessage.equals("")) {
                    return;
                }
                SimpleDateFormat format1 = new SimpleDateFormat ( "HH:mm");
                Calendar time = Calendar.getInstance();
                String format_time1 = format1.format(time.getTime());
                myRef.child(key).child("chat").push().setValue(new ChatData(MainActivity.myNickName, sendMessage, format_time1, MainActivity.myProfile));
                message.setText("");
            }
        });

        init();

        chatActivityRecyclerView = (RecyclerView) findViewById(R.id.chatActivityRecyclerView);
        chatActivityRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ChatActivity.this);
        chatActivityRecyclerView.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(chatDataList, MainActivity.myNickName);
        chatActivityRecyclerView.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();

    }

    public void init() {
        chatDataList.clear();
        myRef.child(key).child("chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded:" + snapshot.getKey());
                ChatData chatData = snapshot.getValue(ChatData.class);
                chatDataList.add(chatData);
                chatActivityRecyclerView.scrollToPosition(chatDataList.size() - 1);
                chatAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}