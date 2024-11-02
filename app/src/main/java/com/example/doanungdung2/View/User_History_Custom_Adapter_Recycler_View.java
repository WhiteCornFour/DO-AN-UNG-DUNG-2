package com.example.doanungdung2.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.History;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class User_History_Custom_Adapter_Recycler_View extends RecyclerView.Adapter<User_History_Custom_Adapter_Recycler_View.MyViewHolder> {

    ArrayList<History> historyArrayList = new ArrayList<>();
    ItemClickListener itemClickListener;

    public User_History_Custom_Adapter_Recycler_View(ArrayList<History> historyArrayList, ItemClickListener itemClickListener) {
        this.historyArrayList = historyArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_search_history_customadapter_list_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        History history = historyArrayList.get(position);
        holder.tvTuTiengAnhLoaiTuDictionary_CustomRecyclerView.setText(history.getMaLichSu() + history.getMaTuVung());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(history); // Pass the clicked item
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTuTiengAnhLoaiTuDictionary_CustomRecyclerView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTuTiengAnhLoaiTuDictionary_CustomRecyclerView = itemView.findViewById(R.id.tvTuTiengAnhLoaiTuDictionary_CustomRecyclerView);
        }
    }

    // Interface for click listener
    public interface ItemClickListener {
        void onItemClick(History history);
    }

    public void setHistoryList(ArrayList<History> newList) {
        this.historyArrayList = newList;
        notifyDataSetChanged(); // Notify adapter of data change
    }
}