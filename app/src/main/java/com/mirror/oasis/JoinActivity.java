package com.mirror.oasis;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class JoinActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    private Button memberButton;
    private EditText userId, nickName, userPassword, userPasswordCheck;
    private CircleImageView photo;
    private Bitmap photoBitmap;
    private Uri photoUri;
    private ProgressBar progressbar;

    ArrayList<UserInfo> userInfos = new ArrayList<UserInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        progressbar = (ProgressBar) findViewById(R.id.progressBar);
        photo = (CircleImageView) findViewById(R.id.photo);
        userId = (EditText) findViewById(R.id.userId);
        nickName = (EditText) findViewById(R.id.nickName);
        userPassword = (EditText) findViewById(R.id.userPassword);
        userPasswordCheck = (EditText) findViewById(R.id.userPasswordCheck);
        memberButton = (Button) findViewById(R.id.memberButton);
        memberButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String id = userId.getText().toString();
                String password = userPassword.getText().toString();
                String passwordCheck = userPasswordCheck.getText().toString();
                String nick = nickName.getText().toString();


                if (id.length() <= 0 || password.length() <= 0 ||  passwordCheck.length() <= 0) {
                    Toast.makeText(JoinActivity.this, "입력사항을 확인해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                userInfos.clear();

                progressbar.setVisibility(View.VISIBLE);
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
                            Toast.makeText(JoinActivity.this, "이미 가입된 아이디입니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            String key = myRef.push().getKey();
                            String photoKey = key;
                            StorageReference tempStorage = storageRef.child("users/" + key + ".jpg");
                            UploadTask uploadTask = tempStorage.putFile(photoUri);
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    tempStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Uri profile = uri;
                                            myRef.child(key).setValue(new UserInfo(key, id, password, nick, profile.toString()));
                                            progressbar.setVisibility(View.GONE);
                                            Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                                            intent.putExtra("key", key);
                                            intent.putExtra("id", id);
                                            intent.putExtra("nickName", nick);
                                            intent.putExtra("profile", profile.toString());
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure( Exception e) {
                                    System.out.println("사진 저장 실패");
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        });

        photo.setEnabled(true);
        photo.setClickable(true);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage(PICK_IMAGE);
            }
        });

        // 권한 설정
        if (ContextCompat.checkSelfPermission(JoinActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(JoinActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(JoinActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1002);
            }
        }
    }

    private void pickImage(int imageCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, imageCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photoBitmap = null;
        if (resultCode == RESULT_OK) {
            photoUri = data.getData();

            try {
                photoBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), photoUri));
                if (requestCode == 1) {
                    photo.setColorFilter(null);
                    photo.setImageBitmap(photoBitmap);

                }

            }catch (IOException e) {

            }
        }
    }
}