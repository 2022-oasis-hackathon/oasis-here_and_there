package com.mirror.oasis.pre;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.mirror.oasis.MainActivity;
import com.mirror.oasis.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PreFragment extends Fragment {

    View v;

    RecyclerView bannerRecyclerView;
    BannerAdapter bannerAdapter;
    LinearLayoutManager bannerLayoutManager;
    List<String> bannerList = new ArrayList<>();

    Timer timer;
    TimerTask timerTask;
    int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pre, container, false);


        bannerList.add(BitmapToString(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.banner1)));
        bannerList.add(BitmapToString(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.banner2)));
        bannerList.add(BitmapToString(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.banner3)));
        bannerList.add(BitmapToString(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.banner4)));

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
        return v;
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
