package com.example.doanungdung2.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Edit_Questions_CustomAdapter extends RecyclerView.Adapter<Admin_Edit_Questions_CustomAdapter.MyViewHolder>{
    private ArrayList<Question> questionArrayList = new ArrayList<>();
    ItemClickListener itemClickListener;

    public Admin_Edit_Questions_CustomAdapter(ArrayList<Question> questionArrayList, ItemClickListener itemClickListener) {
        this.questionArrayList = questionArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_admin_edit_questions_custom_adapter_recyclerview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Question q = questionArrayList.get(position);
        holder.tvRecyclerViewNoiDungCH.setText(q.getNoiDungCauHoi());
        holder.tvRecyclerViewDapAnCH.setText(q.getDapAn());
        holder.tvRecyclerViewMucDoCH.setText("Level: " + q.getMucDo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(q); // Pass the clicked item
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecyclerViewNoiDungCH, tvRecyclerViewDapAnCH, tvRecyclerViewMucDoCH;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecyclerViewNoiDungCH = itemView.findViewById(R.id.tvRecyclerViewNoiDungCH);
            tvRecyclerViewDapAnCH = itemView.findViewById(R.id.tvRecyclerViewDapAnCH);
            tvRecyclerViewMucDoCH = itemView.findViewById(R.id.tvRecyclerViewMucDoCH);
        }
    }

    public void setQuestionsList(ArrayList<Question> newList) {
        this.questionArrayList = newList;
        notifyDataSetChanged(); // Notify adapter of data change
    }

    public interface ItemClickListener {
        void onItemClick(Question question);
    }
}
