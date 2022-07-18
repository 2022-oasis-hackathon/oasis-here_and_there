package com.mirror.oasis.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.mirror.oasis.JoinActivity;
import com.mirror.oasis.MainActivity;
import com.mirror.oasis.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("home");

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    String myKey;
    String myId;
    String myProfile;

    private EditText search;
    private TextView userId;

    private List<HomeData> homeDataList = new ArrayList<>();
    private List<HomeData> tempHomeDataList = new ArrayList<>();
    private List<HomeData> temp2HomeDataList = new ArrayList<>();

    private RecyclerView homeRecyclerView;
    private HomeAdapter homeAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ProgressBar progressBar;


    View v;

    @SuppressLint("WrongThread")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);

        myKey = MainActivity.myKey;
        myId = MainActivity.myId;
        myProfile = MainActivity.myProfile;


        search = (EditText) v.findViewById(R.id.search);
        userId = (TextView) v.findViewById(R.id.userId);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        userId.setText(myId + "님을 위한 추천지");


        /*
        sampleDataSave

        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.sample2);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        String key = myRef.push().getKey();
        StorageReference tempStorage = storageRef.child("homes/" + key + ".jpg");
        UploadTask uploadTask = tempStorage.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                tempStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri photo = uri;

                           // private String location;
                           // private String title;
                           // private String detail;
                           // private String date;
                           // private String photo;

                       //HomeData homeData = new HomeData(key, "전라남도 울주군", "소호마을", "귀농형(일반)", "2022.07.15", photo.toString());
                        //HomeData homeData = new HomeData(key, "전라북도 춘천시", "은행나무마을", "귀촌형(중심지거주)", "2022.04.25", photo.toString());
                        //HomeData homeData = new HomeData(key, "전라남도 삼척시", "신리너와마을", "귀농형(일반)", "2022.03.31", photo.toString());

                        HomeData homeData = new HomeData(key, "전라남도 울주군", "소호마을", "귀농형(일반)", "2022.07.15", photo.toString());
                        myRef.child(key).setValue(homeData);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception e) {
            }
        });
*/

        search.setImeOptions(EditorInfo.IME_ACTION_DONE);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    homeAdapter.getFilter().filter(textView.getText().toString());
                    return true;
                }
                return false;
            }
        });

        homeRecyclerView = (RecyclerView)v.findViewById(R.id.homeRecyclerView);
        homeRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        homeRecyclerView.setLayoutManager(layoutManager);

        homeAdapter = new HomeAdapter(homeDataList, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object tag = v.getTag();

                if (tag != null) {
                    int position = (int)tag;
                    Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();

                }
            }
        });

        homeRecyclerView.setAdapter(homeAdapter);
        init();
        return v;
    }

    public void init() {
        progressBar.setVisibility(View.VISIBLE);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                homeDataList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    HomeData homeData = snapshot1.getValue(HomeData.class);
                    homeDataList.add(homeData);
                    temp2HomeDataList.add(homeData);
                }
                homeAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void dataFilter(String location) {

        tempHomeDataList.clear();
        for (HomeData homeData : homeDataList) {
            if (homeData.getLocation().contains(location)) {
                tempHomeDataList.add(homeData);
            }
        }
        homeDataList.clear();

        for (HomeData homeData: tempHomeDataList) {
            homeDataList.add(homeData);
        }

        homeAdapter.notifyDataSetChanged();

       // homeDataList.clear();
        for (HomeData homeData: temp2HomeDataList) {
            System.out.println(homeData.getLocation());
           // homeDataList.add(homeData);
        }
    }

}

