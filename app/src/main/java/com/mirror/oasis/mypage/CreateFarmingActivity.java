package com.mirror.oasis.mypage;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mirror.oasis.MainActivity;
import com.mirror.oasis.R;
import com.mirror.oasis.home.HomeData;
import com.mirror.oasis.pre.CreateActivity;
import com.mirror.oasis.pre.CreateContentAdapter;
import com.mirror.oasis.pre.PhotoData;

import java.util.ArrayList;
import java.util.List;

public class CreateFarmingActivity extends AppCompatActivity {

    RelativeLayout createButton;

    RadioGroup radio;
    EditText location, detailLocation, title, detail, product, representative, phone, info, date1, date21, date22, date31, date32;
    ProgressBar progress;

    boolean firstCheck = true;
    Uri firstUri;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("datas");

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    private RecyclerView createContentRecyclerView;
    private CreateContentAdapter adapter;
    private LinearLayoutManager layoutManager;

    private List<PhotoData> photoDataList = new ArrayList<>();

    private ImageButton backButton;
    private RelativeLayout gallery;
    private TextView photoCount;
    private Uri photo;

    String getWriter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_farming);

        createButton = (RelativeLayout) findViewById(R.id.createButton);
        radio = (RadioGroup) findViewById(R.id.radio);
        location = (EditText) findViewById(R.id.location);
        detailLocation = (EditText) findViewById(R.id.detailLocation);
        title = (EditText) findViewById(R.id.title);
        detail = (EditText) findViewById(R.id.detail);
        product = (EditText) findViewById(R.id.product);
        representative = (EditText) findViewById(R.id.representative);
        phone = (EditText) findViewById(R.id.phone);
        info = (EditText) findViewById(R.id.info);
        date1 = (EditText) findViewById(R.id.date1);
        date21 = (EditText) findViewById(R.id.date21);
        date22 = (EditText) findViewById(R.id.date22);
        date31 = (EditText) findViewById(R.id.date31);
        date32 = (EditText) findViewById(R.id.date32);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        gallery = (RelativeLayout) findViewById(R.id.gallery);
        backButton = (ImageButton) findViewById(R.id.backButton);
        photoCount = (TextView) findViewById(R.id.photoCount);
        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int radioGroup = radio.getCheckedRadioButtonId();
                    /*
            location: 전라남도 순천시
        detaillocation: 전라남도 순천시 월등면 계월길 138
        name: 순천향매실마을
        detail: 귀농형(일반)
        date1: 2022.08.01            // 입주가능일
        date2: 2022.06.25~2022.07.24 // 신청기간
        date3: 2022.08.01~2022.09.30 // 운영기간
        info: 지리산의 끝자락 문유산의 정기를 받은 순천향매실마을은 30만평의 매실나무가 조성되어 마을전체가 매실농사를 지으며 전국에서 매실이 가장 많이 나는 마을입니다.
27가구 정도의 귀농귀촌 농가가 마을과 조화를 이루며 활기차게 살아가는 마을입니다.
        product: 매실,복숭아
        representative: 이정x
        phone: 01033044680
        writer: 지자체

     */
                String getLocation = location.getText().toString();
                String getDetailLocation = detailLocation.getText().toString();
                String getName = title.getText().toString();
                String getDetail = detail.getText().toString();
                String getDate1 = date1.getText().toString();
                String getDate2 = date21.getText().toString() + "~" + date22.getText().toString();
                String getDate3 = date31.getText().toString() + "~" + date32.getText().toString();
                String getInfo = info.getText().toString();
                String getProduct = product.getText().toString();
                String getRepresentative = representative.getText().toString();
                String getPhone = phone.getText().toString();

                if (photoDataList.size() == 0) {
                    Toast.makeText(CreateFarmingActivity.this, "이미지를 하나 이상 추가해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                progress.setVisibility(View.VISIBLE);

                String key;
                ArrayList<String> photoKeys = new ArrayList<>();
                ArrayList<Uri> photoUri = new ArrayList<>();
                if (radioGroup == R.id.one) {
                    getWriter = "지자체";
                } else {
                    getWriter = MainActivity.myKey;
                }


                key = myRef.push().getKey();
                for (int i = 0; i < photoDataList.size(); i++) {
                    String photoKey = myRef.push().getKey();
                    photoKeys.add(photoKey);
                    StorageReference tempStorage = storageRef.child("datas/" + photoKey + ".jpg");
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
                                        HomeData homeData = new HomeData(key, getLocation, getDetailLocation, getName, getDetail, getDate1, getDate2
                                                , getDate3, getInfo, getProduct, getRepresentative, getPhone, getWriter, firstUri.toString(), photoKeys);
                                        myRef.child(key).setValue(homeData);
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

    }



    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();

                        photo = intent.getData();

                        if (photo != null) {
                            PhotoData data = new PhotoData(photo);
                            photoDataList.add(data);
                            createContentRecyclerView.setAdapter(adapter);
                            photo = null;
                            photoCount.setText(String.valueOf(photoDataList.size()));
                        }
                    }
                }
            });
}