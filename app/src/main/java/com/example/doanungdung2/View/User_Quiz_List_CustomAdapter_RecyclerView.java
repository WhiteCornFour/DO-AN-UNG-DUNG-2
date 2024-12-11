package com.example.doanungdung2.View;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Controller.AssignmentHandler;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class User_Quiz_List_CustomAdapter_RecyclerView extends RecyclerView.Adapter<User_Quiz_List_CustomAdapter_RecyclerView.MyViewHolder>{
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ArrayList<Exercise> exerciseArrayList = new ArrayList<>();
    ItemClickListener itemClickListener;
    String maNguoiDung;
    AssignmentHandler assignmentHandler;
    public User_Quiz_List_CustomAdapter_RecyclerView(ArrayList<Exercise> exerciseArrayList,
                                                     ItemClickListener itemClickListener, String maNguoiDung) {
        this.exerciseArrayList = exerciseArrayList;
        this.itemClickListener = itemClickListener;
        this.maNguoiDung = maNguoiDung;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_quiz_list_custom_adapter_recycler_view, parent, false);
        assignmentHandler = new AssignmentHandler(parent.getContext(), DB_NAME, null, DB_VERSION);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Exercise exercise = exerciseArrayList.get(position);
        //Kiểm tra xem bài tập này đã làm trước đó hay chưa để cập nhật trạng thái cho phù hợp
        boolean rs = assignmentHandler.updateStatusQuiz(maNguoiDung, exercise.getMaBaiTap());
        if (rs)
        {
            holder.tvStatusQuiz_User.setText("Attempt");
        }else
        {
            holder.tvStatusQuiz_User.setText("Not Attempt");
        }
        holder.tvRecyclerViewTenBTQuizList.setText(exercise.getTenBaiTap());
        holder.tvRecyclerViewMoTaBTQuizList.setText(exercise.getMoTa());
        holder.tvRecyclerViewSoCauQuizList.setText(exercise.getSoCau() + " Questions");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(exercise); // Pass the clicked item
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecyclerViewTenBTQuizList, tvRecyclerViewMoTaBTQuizList,
                tvRecyclerViewSoCauQuizList, tvStatusQuiz_User;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecyclerViewTenBTQuizList = itemView.findViewById(R.id.tvRecyclerViewTenBTQuizList);
            tvRecyclerViewMoTaBTQuizList = itemView.findViewById(R.id.tvRecyclerViewMoTaBTQuizList);
            tvRecyclerViewSoCauQuizList = itemView.findViewById(R.id.tvRecyclerViewSoCauQuizList);
            tvStatusQuiz_User = itemView.findViewById(R.id.tvStatusQuiz_User);
        }
    }

    public interface ItemClickListener {
        void onItemClick(Exercise exercise);
    }

    public void setQuizList(ArrayList<Exercise> newList) {
        this.exerciseArrayList = newList;
        notifyDataSetChanged(); // Notify adapter of data change
    }
}
