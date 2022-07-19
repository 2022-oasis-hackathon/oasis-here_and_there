package com.mirror.oasis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mirror.oasis.home.HomeData;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    private EditText userId, userPassword;
    private Button loginButton;
    private ImageButton backButton;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    DatabaseReference homeRef = database.getReference("datas");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    ArrayList<UserInfo> userInfos = new ArrayList<UserInfo>();
    boolean firstCheck = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        userId = (EditText) findViewById(R.id.userId);
        userPassword = (EditText) findViewById(R.id.userPassword);
        loginButton = (Button) findViewById(R.id.loginButton);

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
                                Intent intent = new Intent(LoginActivity.this, ForUserActivity1.class);
                                intent.putExtra("key", userInfo.getKey());
                                intent.putExtra("id", userInfo.getId());
                                intent.putExtra("nickName", userInfo.getNickName());
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
    }

    public byte[] getData(int i) {
        Bitmap bitmap = BitmapFactory.decodeResource(LoginActivity.this.getResources(), i);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }
}