package com.example.doanungdung2.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Model.Report;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Report_Custom_Adapter_RecyclerView extends RecyclerView.Adapter<
        Admin_Report_Custom_Adapter_RecyclerView.MyViewHolder> {
    ArrayList<Report> reportArrayList = new ArrayList<>();
    ItemClickListener itemClickListener;

    public Admin_Report_Custom_Adapter_RecyclerView(ArrayList<Report> reportArrayList, ItemClickListener itemClickListener) {
        this.reportArrayList = reportArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom_item_report_recyclerview,
                parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Report report = reportArrayList.get(position);
        holder.tvNgayBaoCao_Admin.setText(report.getNgayBaoCao());
        
        if (report.getTrangThaiBaoCao().equals("Đã xử lý"))
        {
            holder.tvTrangThaiBaoCao_Admin.setText(report.getTrangThaiBaoCao());
            holder.tvTrangThaiBaoCao_Admin.setTextColor(Color.parseColor("#00FF1A"));
        }else if (report.getTrangThaiBaoCao().equals("Chưa xử lý"))
        {
            holder.tvTrangThaiBaoCao_Admin.setText(report.getTrangThaiBaoCao());
            holder.tvTrangThaiBaoCao_Admin.setTextColor(Color.parseColor("#FF0000"));
        }

        holder.tvNoiDungBaoCao_Admin.setText(report.getNoiDungBaoCao());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.itemClicked(report);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reportArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNgayBaoCao_Admin, tvTrangThaiBaoCao_Admin, tvNoiDungBaoCao_Admin;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNgayBaoCao_Admin = itemView.findViewById(R.id.tvNgayBaoCao_Admin);
            tvTrangThaiBaoCao_Admin = itemView.findViewById(R.id.tvTrangThaiBaoCao_Admin);
            tvNoiDungBaoCao_Admin = itemView.findViewById(R.id.tvNoiDungBaoCao_Admin);
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setNewDataRecyclerView(ArrayList<Report> newData)
    {
        this.reportArrayList = newData;
        notifyDataSetChanged();
    }
    public interface ItemClickListener
    {
        void itemClicked(Report report);
    }
}
