package com.example.doanungdung2.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Add_Exercise_CustomAdapter_RecylView extends RecyclerView.Adapter<
        Admin_Add_Exercise_CustomAdapter_RecylView.MyViewHolder> {

    private ArrayList<Exercise> exerciseArrayList = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public Admin_Add_Exercise_CustomAdapter_RecylView(ArrayList<Exercise> exerciseArrayList, ItemClickListener itemClickListener) {
        this.exerciseArrayList = exerciseArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_admin_add_exercise_customadapter_recylview,
                parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Exercise exer = exerciseArrayList.get(position);
        holder.tvTenBT_CustomRe.setText(exer.getTenBaiTap());
        holder.tvMoTaBT_CustomRe.setText(exer.getMoTa());
        holder.tvMucDoBT_CustomRe.setText(exer.getMucDo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(exer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenBT_CustomRe, tvMucDoBT_CustomRe, tvMoTaBT_CustomRe;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenBT_CustomRe = itemView.findViewById(R.id.tvTenBT_CustomRe);
            tvMucDoBT_CustomRe = itemView.findViewById(R.id.tvMucDoBT_CustomRe);
            tvMoTaBT_CustomRe = itemView.findViewById(R.id.tvMoTaBT_CustomRe);
        }
    }

    public void setExerciseList(ArrayList<Exercise> newList) {
        this.exerciseArrayList = newList;
        notifyDataSetChanged(); // Notify adapter of data change
    }
    public interface ItemClickListener {
        void onItemClick(Exercise exercise);
    }
}
