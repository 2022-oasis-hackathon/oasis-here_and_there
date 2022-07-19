package com.mirror.oasis.chat;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.mirror.oasis.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder>{

    private List<ChatData> dataList;
    private String user;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        private TextView message;
        private CircleImageView profile;
        public MyViewHolder(View v) {
            super(v);
            rootView = v;
           // userName = (TextView) v.findViewById(R.id.userName);
            profile = (CircleImageView) v.findViewById(R.id.profile);
            message = (TextView) v.findViewById(R.id.message);
           // date = (TextView) v.findViewById(R.id.time);
        }
    }

    public ChatAdapter(List<ChatData> dataList, String user1) {
        this.dataList = dataList;
        this.user = user1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == 1) {
            v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.my_chat_layout, parent, false);
        } else {
            v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout, parent, false);
        }

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        if(dataList.get(position).getUser().equals(user)) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
      //  holder.date.setText(dataList.get(position).getTime());
        Uri uri = Uri.parse(dataList.get(position).getProfile());
        Glide.with(holder.rootView.getContext())
                .load(uri)
                .into(holder.profile);
        holder.message.setText(dataList.get(position).getMessage());
        //holder.userName.setText(dataList.get(position).getUser());
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }


}
