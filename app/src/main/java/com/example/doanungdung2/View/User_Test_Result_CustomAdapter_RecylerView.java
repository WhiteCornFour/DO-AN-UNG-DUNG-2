package com.example.doanungdung2.View;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Controller.ExerciseHandler;
import com.example.doanungdung2.Model.Assignment;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class User_Test_Result_CustomAdapter_RecylerView extends
        RecyclerView.Adapter<User_Test_Result_CustomAdapter_RecylerView.MyViewHolder> {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ExerciseHandler exerciseHandler;
    ArrayList<Assignment> assigmentArrayList = new ArrayList<>();
    ItemClickListener itemClickListener;

    public User_Test_Result_CustomAdapter_RecylerView(ArrayList<Assignment> assigmentArrayList, ItemClickListener itemClickListener) {
        this.assigmentArrayList = assigmentArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_test_result_recylerview,
                parent, false);
        exerciseHandler = new ExerciseHandler(parent.getContext(), DB_NAME, null, DB_VERSION);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Assignment assigment = assigmentArrayList.get(position);
        String tenBT = exerciseHandler.getNameExerciseByCode(assigment.getMaBaiTap());
        holder.tvTenBT_TestList.setText(tenBT);
        holder.tvLL_TestList.setText("Do assignment the " + String.valueOf(assigment.getLanLam()) + "th time");
        holder.tvNgayLam_TestList.setText(assigment.getThoiGianBatDau());
        holder.tvTongThoiGian_TestList.setText(assigment.getTongThoiGianLamBai());
        holder.tvDiem_TestList.setText(String.valueOf(assigment.getDiem()) + " points");
        holder.btn_ShowTestDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.itemClicked(assigment, tenBT, assigment.getMaNguoiDung());
            }
        });
    }

    @Override
    public int getItemCount() {
        return assigmentArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenBT_TestList, tvLL_TestList, tvNgayLam_TestList,
                tvTongThoiGian_TestList, tvDiem_TestList;
        Button btn_ShowTestDetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenBT_TestList = itemView.findViewById(R.id.tvTenBT_TestList);
            tvLL_TestList = itemView.findViewById(R.id.tvLL_TestList);
            tvNgayLam_TestList = itemView.findViewById(R.id.tvNgayLam_TestList);
            tvTongThoiGian_TestList = itemView.findViewById(R.id.tvTongThoiGian_TestList);
            tvDiem_TestList = itemView.findViewById(R.id.tvDiem_TestList);
            btn_ShowTestDetails = itemView.findViewById(R.id.btn_ShowTestDetails);
        }
    }
    public interface ItemClickListener
    {
        void itemClicked(Assignment assigment, String tenBT, String maND);
    }
    public void setAssigmentArrayList(ArrayList<Assignment> newList)
    {
        this.assigmentArrayList = newList;
        notifyDataSetChanged();
    }
}
