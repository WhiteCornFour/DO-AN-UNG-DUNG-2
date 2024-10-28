package com.example.doanungdung2.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Questions_Exercises_CustomAdapter extends RecyclerView.Adapter<Admin_Questions_Exercises_CustomAdapter.MyViewHolder>{
    private ArrayList<Question> questionArrayList = new ArrayList<>();
//    boolean[] checkStated;
    ItemClickListener itemClickListener;


    public Admin_Questions_Exercises_CustomAdapter(ArrayList<Question> questionArrayList, ItemClickListener itemClickListener) {
        this.questionArrayList = questionArrayList;
//        this.checkStated = checkStated;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_admin_questions_exercises_custom_adapter_recycler_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Question question = questionArrayList.get(position);
        holder.tvRecyclerViewNoiDungCHBT.setText(question.getNoiDungCauHoi());
        holder.tvRecyclerViewMucDoCHBT.setText("Mức độ: " + question.getMucDo());
        holder.tvRecyclerViewDapAnCHBT.setText(question.getDapAn());
        holder.cbCauHoi.setChecked(question.isChecked());


        // Cập nhật trạng thái khi người dùng tick vào checkbox
        holder.cbCauHoi.setOnCheckedChangeListener((buttonView, isChecked) -> {
            question.setChecked(isChecked);
        });

    }

    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox cbCauHoi;
        TextView tvRecyclerViewNoiDungCHBT, tvRecyclerViewMucDoCHBT, tvRecyclerViewDapAnCHBT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cbCauHoi = itemView.findViewById(R.id.cbCauHoi);
            tvRecyclerViewNoiDungCHBT = itemView.findViewById((R.id.tvRecyclerViewNoiDungCHBT));
            tvRecyclerViewMucDoCHBT = itemView.findViewById(R.id.tvRecyclerViewMucDoCHBT);
            tvRecyclerViewDapAnCHBT = itemView.findViewById(R.id.tvRecyclerViewDapAnCHBT);
        }
    }

    public void setQuestionsList(ArrayList<Question> newList) {
        this.questionArrayList= newList;
        notifyDataSetChanged(); // Notify adapter of data change
    }

    public interface ItemClickListener {
        void onItemClick(Question question);
    }
}
