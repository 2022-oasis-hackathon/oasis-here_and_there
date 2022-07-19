package com.mirror.oasis.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mirror.oasis.DetailActivity;
import com.mirror.oasis.R;
import com.mirror.oasis.home.HomeAdapter;
import com.mirror.oasis.home.HomeData;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {
    View v;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("datas");

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    private RecyclerView homeRecyclerView;
    HomeAdapter homeAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<HomeData> homeDataList = new ArrayList<>();

    private ProgressBar progressBar;

    private EditText search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_community, container, false);


        search = (EditText) v.findViewById(R.id.search);
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

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        homeRecyclerView = (RecyclerView)v.findViewById(R.id.communityRecyclerView);
        homeRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        homeRecyclerView.setLayoutManager(layoutManager);

        homeAdapter = new HomeAdapter(homeDataList, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object tag = view.getTag();
                if (tag != null) {
                    int position = (int)tag;
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("key", homeDataList.get(position).getKey());
                    startActivity(intent);
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

