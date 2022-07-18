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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mirror.oasis.home.HomeData;
import com.mirror.oasis.pre.PhotoData;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static String TAG = "DetailActivity";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("datas");

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    private RecyclerView detailRecyclerView;
    private DetailAdapter detailAdapter;
    private LinearLayoutManager detailLayoutManager;
    private List<String> photoKeys;
    private List<PhotoData> photoDataList = new ArrayList<>();

    ImageButton backButton;
    TextView location, name, detailLocation, info, detail, date1, date2, date3, representative;
    Button callButton;
    String phone;
    ProgressBar progressBar;

    String key;


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
                phone = homeData.getPhone();
                photoKeys = homeData.getPhotoKeys();

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
    }
}