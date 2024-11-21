package com.example.doanungdung2.View;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Controller.AssignmentDetailHandler;
import com.example.doanungdung2.Model.AssignmentDetail;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class User_Quiz_Test_Custom_Adapter extends RecyclerView.Adapter<User_Quiz_Test_Custom_Adapter.MyViewHolder>{

    private final static String DB_NAME = "AppHocTiengAnh";
    private final static int DB_VERSION = 1;
    ArrayList<String> dataSource = new ArrayList<>();
    ArrayList<Question> arrayListQuestion = new ArrayList<>();
    ItemClickListener itemClickListener;
    AssignmentDetailHandler assignmentDetailHandler;
    ArrayList<AssignmentDetail> assigmentDetailArrayList = new ArrayList<>();
    private int selectedPosition = 0;

    public User_Quiz_Test_Custom_Adapter(ArrayList<String> dataSource, ArrayList<Question> arrayListQuestion, ItemClickListener itemClickListener) {
        this.dataSource = dataSource;
        this.arrayListQuestion = arrayListQuestion;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_quiz_test_custom_adapter_recyclerview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //chon san cau so 1 moi khi hien vao 1 bai test
        if(selectedPosition == 0) {
            Question question1 = arrayListQuestion.get(0);
            itemClickListener.onItemClick(question1);
        }

        Question question = arrayListQuestion.get(position);

        holder.tvRecyclerViewButtonNumberSoCau.setText(dataSource.get(position));

        // Cập nhật trạng thái hiển thị của item
        updateItemAppearance(holder, position == selectedPosition);
        // Xử lý sự kiện click
        holder.tvRecyclerViewButtonNumberSoCau.setOnClickListener(v -> {
            // Cập nhật vị trí đã chọn
            int previousSelectedPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();

            // Thông báo RecyclerView cập nhật giao diện toàn bộ danh sách
            notifyDataSetChanged();
            Log.d("Debug click", question.getNoiDungCauHoi());
            // Gọi sự kiện click từ giao diện
            itemClickListener.onItemClick(question);
        });

    }

    private void updateItemAppearance(MyViewHolder holder, boolean isSelected) {
        holder.imgButtonRecyclerViewSoCau.setBackgroundResource(isSelected ?
                R.drawable.shape_round_checked_radio_button : R.drawable.shape_round_unchecked_radio_button);
        int color = ContextCompat.getColor(holder.imgButtonRecyclerViewSoCau.getContext(),
                isSelected ? R.color.brown : R.color.white);
        holder.tvRecyclerViewButtonNumberSoCau.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        return arrayListQuestion.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageButton imgButtonRecyclerViewSoCau;
        TextView tvRecyclerViewButtonNumberSoCau;
        FrameLayout layoutTestQuizModel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutTestQuizModel = itemView.findViewById(R.id.layoutTestQuizModel);
            imgButtonRecyclerViewSoCau = itemView.findViewById(R.id.imgButtonRecyclerViewSoCau);
            tvRecyclerViewButtonNumberSoCau = itemView.findViewById(R.id.tvRecyclerViewButtonNumberSoCau);

            setIsRecyclable(false);
        }
    }

    public void setQuizTestList(ArrayList<Question> newList1, ArrayList<String> newList2) {
        this.arrayListQuestion = newList1;
        this.dataSource = newList2;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClick(Question question);
    }
}
