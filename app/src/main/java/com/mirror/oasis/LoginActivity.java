package com.mirror.oasis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    private EditText userId, userPassword;
    private Button loginButton, memberButton;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    ArrayList<UserInfo> userInfos = new ArrayList<UserInfo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        userId = (EditText) findViewById(R.id.userId);
        userPassword = (EditText) findViewById(R.id.userPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        memberButton = (Button) findViewById(R.id.memberButton);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String id = userId.getText().toString();
                String password = userPassword.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                if (id.length() <= 0 || password.length() <= 0) {
                    Toast.makeText(LoginActivity.this, "입력사항을 확인해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                userInfos.clear();

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1: snapshot.getChildren()) {
                            UserInfo value = snapshot1.getValue(UserInfo.class);
                            userInfos.add(value);

                        }
                        boolean check = true;
                        for (UserInfo userInfo : userInfos) {
                            if (userInfo.getId().equals(id) && userInfo.getPassword().equals(password)) {
                                check = false;

                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("key", userInfo.getKey());
                                intent.putExtra("id", userInfo.getId());
                                intent.putExtra("profile", userInfo.getProfileUri());
                                startActivity(intent);
                                finish();
                            }
                        }

                        if (check)
                            Toast.makeText(LoginActivity.this, "회원가입을 해주세요.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });


            }
        });

        memberButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
                finish();
                /*
                String id = userId.getText().toString();
                String password = userPassword.getText().toString();

                if (id.length() <= 0 || password.length() <= 0) {
                    Toast.makeText(LoginActivity.this, "입력사항을 확인해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                userInfos.clear();

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1: snapshot.getChildren()) {
                            UserInfo value = snapshot1.getValue(UserInfo.class);
                            userInfos.add(value);

                        }

                        boolean check = false;
                        for (UserInfo userInfo : userInfos) {
                            if (userInfo.getId().equals(id)) {
                                check = true;
                            }
                        }

                        if (check) {
                            Toast.makeText(LoginActivity.this, "이미 가입된 아이디입니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            myRef.push().setValue(new UserInfo(id, password));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

                 */
            }
        });
    }
}