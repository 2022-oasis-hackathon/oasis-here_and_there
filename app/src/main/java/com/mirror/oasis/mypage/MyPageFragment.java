package com.mirror.oasis.mypage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.mirror.oasis.MainActivity;
import com.mirror.oasis.R;
import com.mirror.oasis.pre.FeedDetailActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPageFragment extends Fragment {


    View v;

    CircleImageView profile;
    TextView nickName, userId, textView1, textView2, textView3, textView4, textView5;
    CardView createFarming;

    String myKey;
    String myId;
    String myProfile;
    String myNickName;
    String myUserInfo1[];
    String myUserInfo2;
    String myUserInfo3;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_mypage, container, false);

        profile = (CircleImageView) v.findViewById(R.id.profile);
        nickName = (TextView) v.findViewById(R.id.nickName);
        userId = (TextView) v.findViewById(R.id.userId);
        textView1 = (TextView) v.findViewById(R.id.textView1);
        textView2 = (TextView) v.findViewById(R.id.textView2);
        textView3 = (TextView) v.findViewById(R.id.textView3);
        textView4 = (TextView) v.findViewById(R.id.textView4);
        textView5 = (TextView) v.findViewById(R.id.textView5);
        createFarming = (CardView) v.findViewById(R.id.createFarming);
        createFarming.setEnabled(true);
        createFarming.setClickable(true);
        createFarming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateFarmingActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();
            }
        });

        myKey = MainActivity.myKey;
        myId = MainActivity.myId;
        myProfile = MainActivity.myProfile;
        myNickName = MainActivity.myNickName;
        myUserInfo1 = MainActivity.myUserInfo1.split(",");
        myUserInfo2 = MainActivity.myUserInfo2;
        myUserInfo3 = MainActivity.myUserInfo3;

        nickName.setText(myNickName);
        userId.setText(myId);
        textView1.setText(myUserInfo1[0]);
        textView2.setText(myUserInfo1[1]);
        textView3.setText(myUserInfo1[2]);
        textView4.setText(myUserInfo2);
        textView5.setText(myUserInfo3);
        Uri uri = Uri.parse(myProfile);
        Glide.with(MyPageFragment.this)
                .load(uri)
                .into(profile);




        return v;
    }
}

