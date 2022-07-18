package com.mirror.oasis.home;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mirror.oasis.R;

import java.util.ArrayList;
import java.util.List;

class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements Filterable {
    private List<HomeData> unFilteredlist;
    private List<HomeData> filteredList;
    static public View.OnClickListener onClickListener;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String location = charSequence.toString();
                if (location.isEmpty()) {
                    filteredList = unFilteredlist;
                } else {
                    List<HomeData> temp = new ArrayList<>();
                    for (HomeData homeData: unFilteredlist) {
                        if (homeData.getLocation().contains(location)) {
                            temp.add(homeData);
                        }
                    }
                    filteredList = temp;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (List<HomeData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        private ImageView photo;
        private TextView location, name, detail, date1, date2, date3;

        public MyViewHolder(View v) {
            super(v);

            rootView = v;
            rootView.setEnabled(true);
            rootView.setClickable(true);
            rootView.setOnClickListener(onClickListener);

            photo = (ImageView) v.findViewById(R.id.photo);
            location = (TextView) v.findViewById(R.id.location);
            name = (TextView) v.findViewById(R.id.name);
            detail = (TextView) v.findViewById(R.id.detail);
            date1 = (TextView) v.findViewById(R.id.date1);
            date2 = (TextView) v.findViewById(R.id.date2);
            date3 = (TextView) v.findViewById(R.id.date3);

        }
    }

    public HomeAdapter(List<HomeData> homeDataList, View.OnClickListener onClickListener) {
        this.filteredList = homeDataList;
        this.unFilteredlist = homeDataList;
        this.onClickListener = onClickListener;
    }

    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home, parent, false);

        HomeAdapter.MyViewHolder viewHolder = new HomeAdapter.MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeAdapter.MyViewHolder holder, int position) {

        holder.rootView.setTag(position);

        Uri uri = Uri.parse(filteredList.get(position).getFirstPhoto());
        Glide.with(holder.rootView.getContext())
                .load(uri)
                .into(holder.photo);

        holder.location.setText(filteredList.get(position).getLocation());
        holder.name.setText(filteredList.get(position).getName());
        holder.detail.setText("세부유형: " + filteredList.get(position).getDetail());
        holder.date1.setText("입주가능일: " + filteredList.get(position).getDate1());
        holder.date2.setText("신청기간: " + filteredList.get(position).getDate2());
        holder.date3.setText("운영기간: " + filteredList.get(position).getDate3());


    }

    @Override
    public int getItemCount() {
        return filteredList == null ? 0 : filteredList.size();
    }

}
