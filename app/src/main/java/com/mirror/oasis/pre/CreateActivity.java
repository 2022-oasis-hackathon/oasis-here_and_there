package com.mirror.oasis.pre;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mirror.oasis.MainActivity;
import com.mirror.oasis.R;

import java.util.ArrayList;
import java.util.List;


public class CreateActivity extends AppCompatActivity {


    private static final String TAG = "CreateContent";

    boolean firstCheck = true;
    Uri firstUri;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference feedRef = database.getReference("feed");
    DatabaseReference boardRef = database.getReference("board");

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    private RecyclerView createContentRecyclerView;
    private CreateContentAdapter adapter;
    private LinearLayoutManager layoutManager;

    private List<PhotoData> photoDataList = new ArrayList<>();

    private ImageView cancelButton;
    private RelativeLayout createButton, gallery;

    private EditText title, content;
    private TextView photoCount;

    private RadioGroup radio;
    private RadioButton feedCheck, boardCheck;


    private ProgressBar progress;


    private Uri photo;

    String myId;
    String myKey;
    String myProfile;


    ImageView tempPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        myId = MainActivity.myId;
        myKey = MainActivity.myKey;
        myProfile = MainActivity.myProfile;

        cancelButton = (ImageView) findViewById(R.id.cancelButton);
        cancelButton.setEnabled(true);
        cancelButton.setClickable(true);

        gallery = (RelativeLayout) findViewById(R.id.gallery);
        gallery.setEnabled(true);
        gallery.setClickable(true);

        createButton = (RelativeLayout) findViewById(R.id.createButton);
        createButton.setEnabled(true);
        createButton.setClickable(true);

        photoCount = (TextView) findViewById(R.id.photoCount);

        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);

        progress = (ProgressBar) findViewById(R.id.progress);

        radio = (RadioGroup) findViewById(R.id.radio); 
        feedCheck = (RadioButton) findViewById(R.id.feedCheck);
        boardCheck = (RadioButton) findViewById(R.id.boardCheck);



        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int radioGroup = radio.getCheckedRadioButtonId();
                String ti = title.getText().toString();
                String co = content.getText().toString();

                if (photoDataList.size() == 0) {
                    Toast.makeText(CreateActivity.this, "이미지를 하나 이상 추가해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                progress.setVisibility(View.VISIBLE);

                String key;
                ArrayList<String> photoKeys = new ArrayList<>();
                ArrayList<Uri> photoUri = new ArrayList<>();
                if (radioGroup == R.id.feedCheck) {

                    key = feedRef.push().getKey();
                    for (int i = 0; i < photoDataList.size(); i++) {
                        String photoKey = feedRef.push().getKey();
                        photoKeys.add(photoKey);
                        StorageReference tempStorage = storageRef.child("feeds/" + photoKey + ".jpg");
                        UploadTask uploadTask = tempStorage.putFile(photoDataList.get(i).getPhotoUri());

                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                if (firstCheck) {
                                    tempStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            photoUri.add(uri);
                                            firstUri = uri;
                                            System.out.println("test: ! " + myProfile);
                                            CreateData create = new CreateData(myId, ti, co, photoKeys, key, firstUri.toString(), myProfile);
                                            feedRef.child(key).setValue(create);
                                            progress.setVisibility(View.GONE);
                                            finish();
                                        }
                                    });
                                    firstCheck = false;
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure( Exception e) {
                                System.out.println("사진 저장 실패");
                            }
                        });
                    }
                } else {

                    key = boardRef.push().getKey();
                    for (int i = 0; i < photoDataList.size(); i++) {
                        String photoKey = boardRef.push().getKey();
                        photoKeys.add(photoKey);
                        StorageReference tempStorage = storageRef.child("boards/" + photoKey + ".jpg");
                        UploadTask uploadTask = tempStorage.putFile(photoDataList.get(i).getPhotoUri());

                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                if (firstCheck) {
                                    tempStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            photoUri.add(uri);
                                            firstUri = uri;
                                            CreateData create = new CreateData(myId, ti, co, photoKeys, key, firstUri.toString(), myProfile);
                                            boardRef.child(key).setValue(create);
                                            progress.setVisibility(View.GONE);
                                            finish();
                                        }
                                    });
                                    firstCheck = false;
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure( Exception e) {
                                System.out.println("사진 저장 실패");
                            }
                        });
                    }
                }
                

            }
        });


        createContentRecyclerView = (RecyclerView) findViewById(R.id.createContentRecyclerView);
        createContentRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        createContentRecyclerView.setLayoutManager(layoutManager);

        adapter = new CreateContentAdapter(photoDataList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object object = v.getTag();
                if (object != null) {
                    int removePosition = (int) object;
                    Log.d(TAG, String.valueOf(removePosition));
                    photoDataList.remove(removePosition);
                    adapter.notifyItemRemoved(removePosition);
                    adapter.notifyItemRangeChanged(removePosition, photoDataList.size());
                    photoCount.setText(String.valueOf(photoDataList.size()));
                }
            }
        });

        createContentRecyclerView.setAdapter(adapter);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                launcher.launch(intent);
            }
        });

        /*




        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ti = title.getText().toString();
                String co = content.getText().toString();

//                if (TextUtils.isEmpty(ti) || TextUtils.isEmpty(co)) {
//                    Log.d(TAG, "입력 사항을 확인해 주세요.");
//                    return;

                if (photoDataList.size() == 0) {
                    Toast.makeText(CreateStoreActivity.this, "이미지를 하나 이상 추가해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                progress.setVisibility(View.VISIBLE);


                Log.d(TAG, "글 작성 내용");
                Log.d(TAG, "제목: " + ti);
                Log.d(TAG, "상세내용: " + co);

                String key = myRef.push().getKey();



                ArrayList<String> photoKeys = new ArrayList<>();
                ArrayList<Uri> photoUri = new ArrayList<>();

                for (int i = 0; i < photoDataList.size(); i++) {
                    String photoKey = myRef.push().getKey();
                    photoKeys.add(photoKey);
                    StorageReference tempStorage = storageRef.child("store/" + photoKey + ".jpg");
                    UploadTask uploadTask = tempStorage.putFile(photoDataList.get(i).getPhotoUri());

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            if (firstCheck) {
                                tempStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        photoUri.add(uri);
                                        firstUri = uri;
                                        StoreData create = new StoreData(user, ti, priceString, co, photoKeys, key, firstUri.toString());
                                        myRef.child(key).setValue(create);
                                        progress.setVisibility(View.GONE);
                                        finish();
                                    }
                                });
                                firstCheck = false;
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure( Exception e) {
                            System.out.println("사진 저장 실패");
                        }
                    });
                }

                System.out.println("PhotoUri");
                for (int i = 0; i < photoUri.size(); i++) {
                    System.out.println(photoUri.get(i).toString());
                }

                System.out.println("PhotoKeys");
                for (int i = 0; i < photoKeys.size(); i++) {
                    System.out.println(photoKeys.get(i));
                }
                
            }
        });


        createContentRecyclerView = (RecyclerView) findViewById(R.id.createContentRecyclerView);
        createContentRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        createContentRecyclerView.setLayoutManager(layoutManager);

        adapter = new CreateContentAdapter(photoDataList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object object = v.getTag();
                if (object != null) {
                    int removePosition = (int) object;
                    Log.d(TAG, String.valueOf(removePosition));
                    photoDataList.remove(removePosition);
                    adapter.notifyItemRemoved(removePosition);
                    adapter.notifyItemRangeChanged(removePosition, photoDataList.size());
                    photoCount.setText(String.valueOf(photoDataList.size()));
                }
            }
        });

        createContentRecyclerView.setAdapter(adapter);
*/

    }



    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Log.d(TAG, "result: " + result);
                        Intent intent = result.getData();

                        Log.d(TAG, "intent: " + intent);
                        photo = intent.getData();

                        if (photo != null) {
                            PhotoData data = new PhotoData(photo);
                            photoDataList.add(data);
                            createContentRecyclerView.setAdapter(adapter);
                            Log.d(TAG, "이미지 추가");
                            //cameraImage.setVisibility(View.VISIBLE);
                            photo = null;
                            //photoImage.setImageBitmap(null);
                            photoCount.setText(String.valueOf(photoDataList.size()));
                        }

                        Log.d(TAG, "uri: " + photo);

//                        Glide.with(CreateContentActivity.this)
//                                .load(photo)
//                                .into(photoImage);

                        // cameraImage.setVisibility(View.GONE);
                    }
                }
            });


}