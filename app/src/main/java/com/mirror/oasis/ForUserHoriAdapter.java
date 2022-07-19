package com.mirror.oasis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ForUserHoriAdapter extends RecyclerView.Adapter<ForUserHoriAdapter.MyViewHolder>{

    private List<String> dataList;
    static public View.OnClickListener onClick;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        TextView location;

        public MyViewHolder(View v) {
            super(v);

            location = (TextView) v.findViewById(R.id.location);
            rootView = v;
            rootView.setEnabled(true);
            rootView.setClickable(true);
            rootView.setOnClickListener(onClick);
        }
    }

    public ForUserHoriAdapter(List<String> dataList, View.OnClickListener listener) {
        this.dataList = dataList;
        onClick = listener;
    }

    @Override
    public ForUserHoriAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_for_user1_horizontal, parent, false);

        ForUserHoriAdapter.MyViewHolder viewHolder = new ForUserHoriAdapter.MyViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ForUserHoriAdapter.MyViewHolder holder, int position) {
        holder.rootView.setTag(position);
        holder.location.setText(dataList.get(position));
    }


    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }
}

