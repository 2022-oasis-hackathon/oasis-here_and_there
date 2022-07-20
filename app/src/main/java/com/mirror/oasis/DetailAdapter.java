package com.mirror.oasis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mirror.oasis.pre.PhotoData;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder>{

    private List<PhotoData> dataList;
    int dataSize;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        private ImageView photoImage;
        private TextView photoNumber;

        public MyViewHolder(View v) {
            super(v);

            photoImage = (ImageView) v.findViewById(R.id.photoImage);
            photoNumber = (TextView) v.findViewById(R.id.photoNumber);
            rootView = v;
        }
    }

    public DetailAdapter(List<PhotoData> dataList, int size) {
        this.dataList = dataList;
        this.dataSize = size;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_detail, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(holder.rootView.getContext())
                .load(dataList.get(position).getPhotoUri())
                .into(holder.photoImage);

        holder.photoNumber.setText(position + 1 + " / " + dataSize);
    }


    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }
}
