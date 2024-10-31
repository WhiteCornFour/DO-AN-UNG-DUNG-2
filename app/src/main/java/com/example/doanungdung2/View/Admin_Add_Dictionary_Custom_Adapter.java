package com.example.doanungdung2.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Add_Dictionary_Custom_Adapter extends  RecyclerView.Adapter<Admin_Add_Dictionary_Custom_Adapter.MyViewHolder> {

    private ArrayList<Dictionary> dictionaryArrayList;
    ItemClickListener itemClickListener;

    public Admin_Add_Dictionary_Custom_Adapter(ArrayList<Dictionary> dictionaryArrayList, ItemClickListener itemClickListener) {
        this.dictionaryArrayList = dictionaryArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_admin_add_dictionary_custom_adapter_recycler_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Dictionary dictionary = dictionaryArrayList.get(position);
        holder.tvRecyclerViewTuTiengAnhTD.setText(dictionary.getTuTiengAnh());
        holder.tvRecyclerViewTuTiengVietTD.setText(dictionary.getTuTiengViet());
        holder.tvRecyclerViewCachPhatAmTD.setText(dictionary.getCachPhatAm());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(dictionary); // Pass the clicked item
            }
        });
    }

    @Override
    public int getItemCount() {
        return dictionaryArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecyclerViewTuTiengAnhTD, tvRecyclerViewTuTiengVietTD, tvRecyclerViewCachPhatAmTD;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecyclerViewTuTiengAnhTD = itemView.findViewById(R.id.tvRecyclerViewTuTiengAnhTD);
            tvRecyclerViewTuTiengVietTD = itemView.findViewById(R.id.tvRecyclerViewTuTiengVietTD);
            tvRecyclerViewCachPhatAmTD = itemView.findViewById(R.id.tvRecyclerViewCachPhatAmTD);
        }
    }

    public void setDictionaryList(ArrayList<Dictionary> newList) {
        this.dictionaryArrayList = newList;
        notifyDataSetChanged(); // Notify adapter of data change
    }

    // Interface for click listener
    public interface ItemClickListener {
        void onItemClick(Dictionary dictionary);
    }
}
