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

class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.MyViewHolder> {

    private List<CreateData> dataList;
    static public View.OnClickListener onClick;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        private ImageView photo;
        private TextView title, content;

        public MyViewHolder(View v) {
            super(v);

            rootView = v;
            rootView.setEnabled(true);
            rootView.setClickable(true);
            rootView.setOnClickListener(onClick);

            photo = (ImageView) v.findViewById(R.id.photo);

            title = (TextView) v.findViewById(R.id.title);
            content = (TextView) v.findViewById(R.id.content);



        }
    }

    public BoardAdapter(List<CreateData> list, View.OnClickListener listener) {
        dataList = list;
        onClick = listener;
    }

    @Override
    public BoardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_board, parent, false);

        BoardAdapter.MyViewHolder viewHolder = new BoardAdapter.MyViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(BoardAdapter.MyViewHolder holder, int position) {

        // click 이벤트 동작할 위젯에 setTag걸기
        holder.rootView.setTag(position);

        Uri uri = Uri.parse(dataList.get(position).getFirstUri());
        Glide.with(holder.rootView.getContext())
                .load(uri)
                .into(holder.photo);


        holder.title.setText(dataList.get(position).getTitle());
        holder.content.setText(dataList.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

}
