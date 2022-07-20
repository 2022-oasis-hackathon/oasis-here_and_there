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
import com.mirror.oasis.pre.CreateData;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    private EditText userId, userPassword;
    private Button loginButton;
    private ImageButton backButton;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    DatabaseReference homeRef = database.getReference("feed");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    ArrayList<UserInfo> userInfos = new ArrayList<UserInfo>();
    boolean firstCheck = true;

    public static String myId;
    public static String myKey;
    public static String myNickName;
    public static String myProfile;

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

        userId.setText("test123@naver.com");
        userPassword.setText("zxc123");

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
                                myKey = userInfo.getKey();
                                myId = userInfo.getId();
                                myNickName = userInfo.getNickName();
                                myProfile = userInfo.getProfileUri();
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

        /*
        byte[] data1 = getData(R.drawable.data1);
        byte[] data2 = getData(R.drawable.data2);
        byte[] data3 = getData(R.drawable.data3);
        byte[] data4 = getData(R.drawable.data4);
        ArrayList<byte[]> dataList = new ArrayList<>();
        dataList.add(data1);
        dataList.add(data2);
        dataList.add(data3);
        dataList.add(data4);

        String realKey = homeRef.push().getKey();
        ArrayList<String> photoKeys = new ArrayList<>();
        ArrayList<Uri> photoUri = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            byte[] data = dataList.get(i);
            String key = homeRef.push().getKey();
            photoKeys.add(key);
            StorageReference tempStorage = storageRef.child("feeds/" + key + ".jpg");
            UploadTask uploadTask = tempStorage.putBytes(data);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if (firstCheck) {
                        tempStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                photoUri.add(uri);
                                Uri photo = uri;

                                CreateData createData = new CreateData("test123@naver.com", "귀농짱", "알로에 농장 후기", "오늘은 정읍에 있는 알로에사랑에 가서 알로에체험 프로그램에 참여해봤어요\n" +
                                        "여기는 6차 산업 인증을 받은 농장으로  건강한 땅에서 재배한 알로에 품질 좋은 먹거리를 제조, 판매하고 있는 농장이라네요\n" +
                                        "알로에키트를 받아서 알로에청도 한번 만들어봤어요 잘만들었나요? ㅎㅎ",
                                        photoKeys, realKey, photo.toString(), "");

                                homeRef.child(realKey).setValue(createData);
                                System.out.println("데이터: " + photo);
                            }
                        });
                        firstCheck =  false;
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure( Exception e) {
                }
            });
     }
         */

    }

    public byte[] getData(int i) {
        Bitmap bitmap = BitmapFactory.decodeResource(LoginActivity.this.getResources(), i);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }
}