package com.mirror.oasis.pre;

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

class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private List<CommentData> dataList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        private TextView nickName, comment, time;

        public MyViewHolder(View v) {
            super(v);

            rootView = v;


            nickName = (TextView) v.findViewById(R.id.nickName);
            comment = (TextView) v.findViewById(R.id.comment);
            time = (TextView) v.findViewById(R.id.time);


        }
    }

    public CommentAdapter(List<CommentData> list) {
        dataList = list;
    }

    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_comment, parent, false);

        CommentAdapter.MyViewHolder viewHolder = new CommentAdapter.MyViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CommentAdapter.MyViewHolder holder, int position) {

        // click 이벤트 동작할 위젯에 setTag걸기
        holder.rootView.setTag(position);
        holder.nickName.setText(dataList.get(position).getNickName());

        holder.comment.setText(dataList.get(position).getComment());
        holder.time.setText(dataList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

}
