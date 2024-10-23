package com.example.doanungdung2.View;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Controller.ExerciseHandler;
import com.example.doanungdung2.Controller.ExercisesCategoryHandler;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Edit_Exercises_CustomAdapter extends RecyclerView.Adapter<Admin_Edit_Exercises_CustomAdapter.MyViewHolder> {
    private ArrayList<Exercise> exerciseArrayList;

    private ItemClickListener itemClickListener;

    public Admin_Edit_Exercises_CustomAdapter(ArrayList<Exercise> exerciseArrayList, ItemClickListener itemClickListener) {
        this.exerciseArrayList = exerciseArrayList;
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
        Exercise e = exerciseArrayList.get(position);
        holder.tvRecyclerViewTenBT.setText(e.getTenBaiTap());
        holder.tvRecyclerViewMoTaBT.setText(e.getMoTa());
        holder.tvRecyclerViewMucDoBT.setText(e.getMucDo());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(e); // Pass the clicked item
            }
        });
    }


    @Override
    public int getItemCount() {
        return exerciseArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecyclerViewTenBT, tvRecyclerViewMoTaBT, tvRecyclerViewMucDoBT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecyclerViewTenBT = itemView.findViewById(R.id.tvRecyclerViewTenBT);
            tvRecyclerViewMoTaBT = itemView.findViewById(R.id.tvRecyclerViewMoTaBT);
            tvRecyclerViewMucDoBT= itemView.findViewById(R.id.tvRecyclerViewMucDoBT);
        }
    }

    public void setExercisesList(ArrayList<Exercise> newList) {
        this.exerciseArrayList = newList;
        notifyDataSetChanged(); // Notify adapter of data change
    }

    public interface ItemClickListener {
        void onItemClick(Exercise exercise);

    }
}
