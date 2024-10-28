package com.example.doanungdung2.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Delete_Dictionary_Custom_Adapter extends RecyclerView.Adapter<Admin_Delete_Dictionary_Custom_Adapter.MyViewHolder> {

    private ArrayList<Dictionary> dictionaryArrayList;
    Admin_Add_Dictionary_Custom_Adapter.ItemClickListener itemClickListener;

    public Admin_Delete_Dictionary_Custom_Adapter(ArrayList<Dictionary> dictionaryArrayList, Admin_Add_Dictionary_Custom_Adapter.ItemClickListener itemClickListener) {
        this.dictionaryArrayList = dictionaryArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_admin_delete_dictionary_custom_adpater_recyler_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Dictionary dictionary = dictionaryArrayList.get(position);
        holder.tvRecyclerViewTuTiengAnhXTD.setText(dictionary.getTuTiengAnh());
        holder.tvRecyclerViewTuTiengVietXTD.setText(dictionary.getTuTiengViet());
        holder.tvRecyclerViewCachPhatAmXTD.setText(dictionary.getCachPhatAm());

        // Set the checkbox listener
        holder.cbRecyclerViewXTD.setOnCheckedChangeListener(null);
        holder.cbRecyclerViewXTD.setChecked(dictionary.isChecked());
        holder.cbRecyclerViewXTD.setOnCheckedChangeListener((buttonView, isChecked) -> {
            dictionary.setChecked(isChecked);
        });

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
        TextView tvRecyclerViewTuTiengAnhXTD, tvRecyclerViewCachPhatAmXTD, tvRecyclerViewTuTiengVietXTD;
        CheckBox cbRecyclerViewXTD;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecyclerViewTuTiengAnhXTD = itemView.findViewById(R.id.tvRecyclerViewTuTiengAnhXTD);
            tvRecyclerViewTuTiengVietXTD = itemView.findViewById(R.id.tvRecyclerViewTuTiengVietXTD);
            tvRecyclerViewCachPhatAmXTD = itemView.findViewById(R.id.tvRecyclerViewCachPhatAmXTD);
            cbRecyclerViewXTD = itemView.findViewById(R.id.cbRecyclerViewXTD);
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
