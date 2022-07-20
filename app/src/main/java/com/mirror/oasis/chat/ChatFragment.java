package com.mirror.oasis.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirror.oasis.ForUserActivity1;
import com.mirror.oasis.LoginActivity;
import com.mirror.oasis.MainActivity;
import com.mirror.oasis.R;
import com.mirror.oasis.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    View v;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("chats");
    DatabaseReference userRef = database.getReference("users");

    RecyclerView chatFragmentRecyclerView;
    ChatListAdapter chatListAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<ChatListData> chatListDataList = new ArrayList<>();

    String myId;
    String myNickName;

    String key = "";
    String userId;
    String userProfile;
    String userKey;
    String userNickName;
    String lastMessage;

    ChatListData chatListData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_chat, container, false);

        myId = MainActivity.myId;
        myNickName = MainActivity.myNickName;

        init();

        Log.d("ChatFragment1", myId+ " ") ;
        Log.d("ChatFragment2", MainActivity.myId);

        chatFragmentRecyclerView = (RecyclerView) v.findViewById(R.id.chatFragmentRecyclerView);
        chatFragmentRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        chatFragmentRecyclerView.setLayoutManager(layoutManager);
        chatListAdapter = new ChatListAdapter(chatListDataList, new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Object tag = v.getTag();

                if (tag != null) {
                    int position = (int)tag;
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    /*
                            key = getIntent.getStringExtra("key");
        userKey = getIntent.getStringExtra("userKey");
        userProfile = getIntent.getStringExtra("userProfile");
        userId = getIntent.getStringExtra("userId");
        userNickName = getIntent.getStringExtra("userNickName");
                     */
                    intent.putExtra("key", chatListDataList.get(position).getKey());
                    intent.putExtra("userProfile", chatListDataList.get(position).getUserProfile());
                    intent.putExtra("userId", chatListDataList.get(position).getUserId());
                    intent.putExtra("userNickName", chatListDataList.get(position).getUserNickName());
                    intent.putExtra("userKey", chatListDataList.get(position).getUserKey());

                    startActivity(intent);

                }
            }
        });

        chatFragmentRecyclerView.setAdapter(chatListAdapter);

        return v;
    }

    public void init() {
        /*
        DB 구조
        chats
            -N6v.....(key) 1.
                chat
                  - N6vFq0....(key)
                        - message: ""
                        - time: ""
                        - user: ""
                  - N6vQa0....(key)
                        - message: ""
                        - time: ""
                        - user: ""
                        .
                        .
                        .
               info
                  - N6rLao....(key)
                        content: ""
                        firstUri: ""
                        .
                        .
               users
                 - user1: ""
                 - user2: ""

         */
        chatListDataList.clear();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

                            if (users.getUser1().equals(MainActivity.myNickName) || users.getUser2().equals(MainActivity.myNickName)) {
                                key = snapshot1.getKey();
                                System.out.println("users: " + users.getUser1() + " " + users.getUser2());
                                if (users.getUser1().equals(MainActivity.myNickName)) {
                                    userNickName = users.getUser2();
                                } else {
                                    userNickName = users.getUser1();
                                }

                                for (DataSnapshot snapshot3: snapshot1.child("chat").getChildren()) {
                                    ChatData chatdata = snapshot3.getValue(ChatData.class);

                                    // 내가 보낸 메시지가 아닐 경우
                                    if (!chatdata.getUser().equals(MainActivity.myNickName)) {
                                        lastMessage = chatdata.getMessage();
                                    }
                                }

                                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        for(DataSnapshot snapshot1: snapshot.getChildren()) {
                                            UserInfo value = snapshot1.getValue(UserInfo.class);
                                            if (value.getNickName().equals(userNickName)) {

                                                userId = value.getId();
                                                userProfile = value.getProfileUri();
                                                userKey = value.getKey();
                                                chatListData = new ChatListData(key, userId, userProfile, userKey, userNickName, lastMessage);
                                                chatListDataList.add(chatListData);
                                                System.out.println(userId + " " + userProfile + " " + userKey + " " + userNickName + "$$$$$$$$$$$");
                                                // String key, String userId, String userProfile, String userKey, String userNickName, String lastMessage
                                                break;



                                            }
                                        }

                                        chatListAdapter.notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                    }
                                });





                                //ChatListData chatListData = new ChatListData(key, userId, userProfile, userKey, userNickName, lastMessage);
                                //chatListDataList.add(chatListData);
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

