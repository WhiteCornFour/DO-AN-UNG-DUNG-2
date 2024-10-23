package com.example.doanungdung2.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Edit_ExercisesCategory_CustomAdapter extends RecyclerView.Adapter<Admin_Edit_ExercisesCategory_CustomAdapter.MyViewHolder> {
    private ArrayList<ExercisesCategory> exercisesCategoryArrayList;
    private ItemClickListener itemClickListener;

    public Admin_Edit_ExercisesCategory_CustomAdapter(ArrayList<ExercisesCategory> exercisesCategoryArrayList, ItemClickListener itemClickListener) {
        this.exercisesCategoryArrayList = exercisesCategoryArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_admin_edit_exercises_custom_adapter_recyclerview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ExercisesCategory ec = exercisesCategoryArrayList.get(position);
        holder.tvRecyclerViewMaDBT.setText(ec.getMaDangBaiTap());
        holder.tvRecyclerViewTenDBT.setText(ec.getTenDangBaiTap());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(ec); // Pass the clicked item
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercisesCategoryArrayList.size();
    }

    // ViewHolder class
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecyclerViewTenDBT, tvRecyclerViewMaDBT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecyclerViewTenDBT = itemView.findViewById(R.id.tvRecyclerViewTenDBT);
            tvRecyclerViewMaDBT = itemView.findViewById(R.id.tvRecyclerViewMaDBT);
        }
    }

    // Update the list and notify the adapter
    public void setExercisesCategoryList(ArrayList<ExercisesCategory> newList) {
        this.exercisesCategoryArrayList = newList;
        notifyDataSetChanged(); // Notify adapter of data change
    }

    // Interface for click listener
    public interface ItemClickListener {
        void onItemClick(ExercisesCategory exercisesCategory);
    }
}
