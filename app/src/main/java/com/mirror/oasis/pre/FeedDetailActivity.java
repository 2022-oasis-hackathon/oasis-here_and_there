package com.mirror.oasis.pre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mirror.oasis.DetailAdapter;
import com.mirror.oasis.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedDetailActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("feed");
    DatabaseReference commentRef = database.getReference("comment");

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    private RecyclerView feedPhotoRecyclerView;
    private DetailAdapter detailAdapter;
    private LinearLayoutManager feedDetailLayoutManager;
    private List<String> photoKeys;
    private List<PhotoData> photoDataList = new ArrayList<>();

    CircleImageView profile;
    TextView userNickName, title, content;
    ProgressBar progressBar;
    EditText comment;


    private RecyclerView commentRecyclerView;
    private CommentAdapter commentAdapter;
    private RecyclerView.LayoutManager commentLayoutManager;
    private List<CommentData> commentDataList;

    String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

        Intent intent = getIntent();
        key = intent.getStringExtra("key");

        comment = (EditText) findViewById(R.id.comment);
        comment.setImeOptions(EditorInfo.IME_ACTION_DONE);
        comment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    String commentData = textView.getText().toString();
                    return true;
                }
                return false;
            }
        });

        feedPhotoRecyclerView = (RecyclerView) findViewById(R.id.feedPhotoRecyclerView);
        feedPhotoRecyclerView.setHasFixedSize(true);
        feedDetailLayoutManager = new LinearLayoutManager(this);
        feedDetailLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        feedPhotoRecyclerView.setLayoutManager(feedDetailLayoutManager);
        detailAdapter = new DetailAdapter(photoDataList);
        feedPhotoRecyclerView.setAdapter(detailAdapter);

        /*
            private RecyclerView commentRecyclerView;
    private CommentAdapter commentAdapter;
    private RecyclerView.LayoutManager commentLayoutManager;
         */
        commentRecyclerView = (RecyclerView) findViewById(R.id.commentRecyclerView);
        commentRecyclerView.setHasFixedSize(true);
        commentLayoutManager = new LinearLayoutManager(this);
        commentRecyclerView.setLayoutManager(commentLayoutManager);
       // commentAdapter = new CommentAdapter(photoDataList);
       // commentRecyclerView.setAdapter(commentAdapter);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        profile = (CircleImageView) findViewById(R.id.profile);
        userNickName = (TextView) findViewById(R.id.userNickName);
        title =  (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        init();
    }

    public void init() {
        progressBar.setVisibility(View.VISIBLE);
        myRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                CreateData createData = snapshot.getValue(CreateData.class);
                Uri uri = Uri.parse(createData.getProfile());
                Glide.with(FeedDetailActivity.this)
                        .load(uri)
                        .into(profile);
                userNickName.setText(createData.getNickName());
                title.setText(createData.getTitle());
                content.setText(createData.getContent());
                photoKeys = createData.getPhotoKeys();

                for (int i = 0; i < photoKeys.size(); i++) {
                    StorageReference tempStorage = storageRef.child("feeds/" + photoKeys.get(i) + ".jpg");
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

        commentRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    CommentData commentData = snapshot1.getValue(CommentData.class);
                    commentDataList.add(commentData);
                }
                detailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}