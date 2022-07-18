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

class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {

    private List<CreateData> dataList;
    static public View.OnClickListener onClick;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        private ImageView profile, photo;
        private TextView title, content, id;

        public MyViewHolder(View v) {
            super(v);

            rootView = v;
            rootView.setEnabled(true);
            rootView.setClickable(true);
            rootView.setOnClickListener(onClick);

            profile = (ImageView) v.findViewById(R.id.profile);
            photo = (ImageView) v.findViewById(R.id.photo);

            id = (TextView) v.findViewById(R.id.id);
            title = (TextView) v.findViewById(R.id.title);
            content = (TextView) v.findViewById(R.id.content);



        }
    }

    public FeedAdapter(List<CreateData> list, View.OnClickListener listener) {
        dataList = list;
        onClick = listener;
    }

    @Override
    public FeedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_feed, parent, false);

        FeedAdapter.MyViewHolder viewHolder = new FeedAdapter.MyViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(FeedAdapter.MyViewHolder holder, int position) {

        // click 이벤트 동작할 위젯에 setTag걸기
        holder.rootView.setTag(position);

        Uri uri = Uri.parse(dataList.get(position).getFirstUri());
        Glide.with(holder.rootView.getContext())
                .load(uri)
                .into(holder.photo);

        if (dataList.get(position).getProfile() != null) {
            Uri uri1 = Uri.parse(dataList.get(position).getProfile());
            Glide.with(holder.rootView.getContext())
                    .load(uri1)
                    .into(holder.profile);

        }

        holder.title.setText(dataList.get(position).getTitle());
        holder.content.setText(dataList.get(position).getContent());
        holder.id.setText(dataList.get(position).getId());

    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

}
