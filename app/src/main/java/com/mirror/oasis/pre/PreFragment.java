package com.mirror.oasis.pre;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mirror.oasis.MainActivity;
import com.mirror.oasis.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PreFragment extends Fragment {

    View v;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference feedRef = database.getReference("feed");
    DatabaseReference boardRef = database.getReference("board");

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    RecyclerView bannerRecyclerView;
    BannerAdapter bannerAdapter;
    LinearLayoutManager bannerLayoutManager;
    List<String> bannerList = new ArrayList<>();

    Timer timer;
    TimerTask timerTask;
    int position;

    RelativeLayout feed, board;
    LinearLayout feedLayout, boardLayout;
    TextView feedText, boardText;

    RecyclerView feedRecyclerView;
    FeedAdapter feedAdapter;
    RecyclerView.LayoutManager feedLayoutManager;
    List<CreateData> feedDataList = new ArrayList<>();

    RecyclerView boardRecyclerView;
    BoardAdapter boardAdapter;
    RecyclerView.LayoutManager boardLayoutManager;
    List<CreateData> boardDataList = new ArrayList<>();

    AppCompatImageButton writing;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pre, container, false);


        // Banner S
        bannerList.add(BitmapToString(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.banner1)));
        bannerList.add(BitmapToString(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.banner2)));
        bannerList.add(BitmapToString(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.banner3)));

        bannerRecyclerView = (RecyclerView) v.findViewById(R.id.bannerRecyclerView);
        bannerRecyclerView.setHasFixedSize(true);
        bannerLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        bannerRecyclerView.setLayoutManager(bannerLayoutManager);

        bannerAdapter = new BannerAdapter(bannerList);
        bannerRecyclerView.setAdapter(bannerAdapter);

        if(bannerList != null) {
            position = Integer.MAX_VALUE / 2;
            bannerRecyclerView.scrollToPosition(position);
        }

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(bannerRecyclerView);
        bannerRecyclerView.smoothScrollBy(5,0);

        bannerRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 1) {
                    stopAutoAScrollBanner();
                } else if (newState == 0) {
                    position = bannerLayoutManager.findFirstCompletelyVisibleItemPosition();
                    runAutoScrollBanner();
                }
            }
        });
        // Banner E

       /*
           RelativeLayout feed, board;
    LinearLayout feedLayout, boardLayout;

    RecyclerView feedRecyclerView;
    FeedAdapter feedAdapter;
    RecyclerView.LayoutManager feedLayoutManager;

    RecyclerView boardRecyclerView;
    BoardAdapter boardAdapter;
    RecyclerView.LayoutManager boardLayoutManager;
        */

        feed = (RelativeLayout) v.findViewById(R.id.feed);
        board = (RelativeLayout) v.findViewById(R.id.board);

        feedLayout = (LinearLayout) v.findViewById(R.id.feedLayout);
        boardLayout = (LinearLayout) v.findViewById(R.id.boardLayout);

        feedText = (TextView) v.findViewById(R.id.feedText);
        boardText = (TextView) v.findViewById(R.id.boardText);

        writing = (AppCompatImageButton) v.findViewById(R.id.writing);
        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateActivity.class);
                startActivity(intent);
            }
        });

        feed.setEnabled(true);
        feed.setClickable(true);

        board.setEnabled(true);
        board.setClickable(true);

        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boardRecyclerView.setVisibility(View.GONE);
                feedRecyclerView.setVisibility(View.VISIBLE);

                feedLayout.setBackgroundColor(Color.parseColor("#3D7244"));
                feedText.setTextColor(Color.parseColor("#3D7244"));

                boardLayout.setBackgroundColor(Color.parseColor("#CAC9C9"));
                boardText.setTextColor(Color.parseColor("#CAC9C9"));
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boardRecyclerView.setVisibility(View.VISIBLE);
                feedRecyclerView.setVisibility(View.GONE);

                boardLayout.setBackgroundColor(Color.parseColor("#3D7244"));
                boardText.setTextColor(Color.parseColor("#3D7244"));

                feedLayout.setBackgroundColor(Color.parseColor("#CAC9C9"));
                feedText.setTextColor(Color.parseColor("#CAC9C9"));


            }
        });

        feedRecyclerView = (RecyclerView) v.findViewById(R.id.feedRecyclerView);
        feedRecyclerView.setHasFixedSize(true);
        feedLayoutManager = new LinearLayoutManager(getActivity());
        feedRecyclerView.setLayoutManager(feedLayoutManager);

        feedAdapter = new FeedAdapter(feedDataList, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object tag = view.getTag();

                if (tag != null) {
                    int position = (int)tag;
                    Intent intent = new Intent(getActivity(), FeedDetailActivity.class);
                    intent.putExtra("key", feedDataList.get(position).getKey());
                    startActivity(intent);

                }
            }
        });
        feedRecyclerView.setAdapter(feedAdapter);

        boardRecyclerView = (RecyclerView) v.findViewById(R.id.boardRecyclerView);
        boardRecyclerView.setHasFixedSize(true);
        boardLayoutManager = new LinearLayoutManager(getActivity());
        boardRecyclerView.setLayoutManager(boardLayoutManager);

        boardAdapter = new BoardAdapter(boardDataList, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object tag = view.getTag();

                if (tag != null) {
                    int position = (int)tag;
                    // Intent intent = new Intent(getActivity(), StoreActivity.class);
                    //intent.putExtra("position", String.valueOf(position));

                    //startActivity(intent);

                }
            }
        });
        boardRecyclerView.setAdapter(boardAdapter);

        init();

        return v;
    }

    public void init() {

        feedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.toString());
                feedDataList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    System.out.println(snapshot1.toString());
                    CreateData store = snapshot1.getValue(CreateData.class);
                    feedDataList.add(store);
                }
                feedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        boardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.toString());
                boardDataList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    System.out.println(snapshot1.toString());
                    CreateData store = snapshot1.getValue(CreateData.class);
                    boardDataList.add(store);
                }
                boardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }



    public String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[]  b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private void stopAutoAScrollBanner() {
        if (timer != null && timerTask != null) {
            timerTask.cancel();
            timer.cancel();
            timer = null;
            timerTask = null;
            position = bannerLayoutManager.findFirstCompletelyVisibleItemPosition();
        }
    }

    private void runAutoScrollBanner() {
        if(timer == null && timerTask == null) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (position == Integer.MAX_VALUE) {
                        position = Integer.MAX_VALUE / 2;
                        bannerRecyclerView.scrollToPosition(position);
                        bannerRecyclerView.smoothScrollBy(5, 0);
                    } else {
                        position++;
                        bannerRecyclerView.smoothScrollToPosition(position);
                    }
                }
            };
            timer.schedule(timerTask, 3000, 3000);
        }

    }
}
