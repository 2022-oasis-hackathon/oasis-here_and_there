package com.mirror.oasis.chat;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mirror.oasis.R;

import java.util.List;

class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {

    private List<ChatListData> dataList;
    static public View.OnClickListener onClick;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        private ImageView profile;
        private TextView nickName, lastMessage;

        public MyViewHolder(View v) {
            super(v);

            rootView = v;
            rootView.setEnabled(true);
            rootView.setClickable(true);
            rootView.setOnClickListener(onClick);

            profile = (ImageView) v.findViewById(R.id.profile);

            nickName = (TextView) v.findViewById(R.id.nickName);
            lastMessage = (TextView) v.findViewById(R.id.lastMessage);
        }
    }

    public ChatListAdapter(List<ChatListData> dataList, View.OnClickListener onClickListener) {
        this.dataList = dataList;
        this.onClick = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View v;
        v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat_list, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // click 이벤트 동작할 위젯에 setTag걸기
        holder.rootView.setTag(position);

        Uri uri = Uri.parse(dataList.get(position).getUserProfile());
        Glide.with(holder.rootView.getContext())
                .load(uri)
                .into(holder.profile);

        holder.nickName.setText(dataList.get(position).getUserNickName());
        holder.lastMessage.setText(dataList.get(position).getLastMessage());

    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }
}
