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
    DatabaseReference myRef = database.getReference("datas");

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    String myKey;
    String myId;
    String myProfile;
    String myNickName;

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
        myNickName = MainActivity.myNickName;


        search = (EditText) v.findViewById(R.id.search);
        userId = (TextView) v.findViewById(R.id.userId);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        userId.setText(myNickName);

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


}

