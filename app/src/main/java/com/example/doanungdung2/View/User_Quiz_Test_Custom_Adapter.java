package com.example.doanungdung2.View;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class User_Quiz_Test_Custom_Adapter extends RecyclerView.Adapter<User_Quiz_Test_Custom_Adapter.MyViewHolder>{
    ArrayList<String> dataSource = new ArrayList<>();
    ArrayList<Question> arrayListQuestion = new ArrayList<>();
    ItemClickListener itemClickListener;

    // Biến để lưu vị trí của item đã chọn
    private int selectedPosition = -1;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Question question = arrayListQuestion.get(position);
        holder.tvRecyclerViewButtonNumberSoCau.setText(dataSource.get(position));
        holder.imgButtonRecyclerViewSoCau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(question);
                holder.imgButtonRecyclerViewSoCau.setBackgroundResource(R.drawable.shape_round_checked_radio_button);
                int color = ContextCompat.getColor(holder.imgButtonRecyclerViewSoCau.getContext(), R.color.brown); // Thay 'your_color' bằng tên màu bạn muốn
                holder.tvRecyclerViewButtonNumberSoCau.setTextColor(color);
            }
        });

        // Nếu item này đã được chọn, thay đổi màu sắc và chữ
        if (position == selectedPosition) {
            holder.imgButtonRecyclerViewSoCau.setBackgroundResource(R.drawable.shape_round_checked_radio_button);
            int color = ContextCompat.getColor(holder.imgButtonRecyclerViewSoCau.getContext(), R.color.brown); // Thay màu theo ý muốn
            holder.tvRecyclerViewButtonNumberSoCau.setTextColor(color);
        } else {
            // Nếu không phải item được chọn, quay lại trạng thái ban đầu
            holder.imgButtonRecyclerViewSoCau.setBackgroundResource(R.drawable.shape_round_unchecked_radio_button);
            int defaultColor = ContextCompat.getColor(holder.imgButtonRecyclerViewSoCau.getContext(), R.color.white); // Màu mặc định
            holder.tvRecyclerViewButtonNumberSoCau.setTextColor(defaultColor);
        }

        // Xử lý sự kiện click
        holder.itemView.setOnClickListener(v -> {
            // Lưu lại vị trí item được chọn
            int previousSelectedPosition = selectedPosition;
            selectedPosition = position;

            // Thông báo để adapter cập nhật giao diện
            notifyItemChanged(previousSelectedPosition);
            notifyItemChanged(selectedPosition);

            itemClickListener.onItemClick(question); // Gọi sự kiện click của item
        });
    }


    @Override
    public int getItemCount() {
        return arrayListQuestion.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageButton imgButtonRecyclerViewSoCau;
        TextView tvRecyclerViewButtonNumberSoCau;
        FrameLayout cardViewTestQuiz;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewTestQuiz = itemView.findViewById(R.id.cardViewTestQuiz);
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
