package com.mirror.oasis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mirror.oasis.pre.PhotoData;

import java.util.List;

class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder>{

    private List<PhotoData> dataList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        private ImageView photoImage;

        public MyViewHolder(View v) {
            super(v);

            photoImage = (ImageView) v.findViewById(R.id.photoImage);
            rootView = v;
        }
    }

    public DetailAdapter(List<PhotoData> dataList) {
        this.dataList = dataList;
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
    }


    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }
}
