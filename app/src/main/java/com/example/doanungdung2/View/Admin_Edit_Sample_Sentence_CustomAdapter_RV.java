package com.example.doanungdung2.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Model.SampleSentence; // Đảm bảo nhập đúng lớp mô hình
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Edit_Sample_Sentence_CustomAdapter_RV extends RecyclerView.Adapter<Admin_Edit_Sample_Sentence_CustomAdapter_RV.MyViewHolder> {
    ArrayList<SampleSentence> sampleSentenceArrayList;
    ItemClickListener itemClickListener;

    public Admin_Edit_Sample_Sentence_CustomAdapter_RV(ArrayList<SampleSentence> sampleSentenceArrayList, ItemClickListener itemClickListener) {
        this.sampleSentenceArrayList = sampleSentenceArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_admin_edit_sample_sentence_custom_adapter_rv, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SampleSentence sampleSentence = sampleSentenceArrayList.get(position);
        holder.tvRVTenMauCau.setText(sampleSentence.getMauCau()); // Hiển thị mẫu câu
        holder.tvRVPhienDich.setText(sampleSentence.getPhienDich()); // Hiển thị phiên dịch

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(sampleSentence);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sampleSentenceArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRVTenMauCau, tvRVPhienDich;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRVTenMauCau = itemView.findViewById(R.id.tvRVTenMauCau);
            tvRVPhienDich = itemView.findViewById(R.id.tvRVPhienDich);
        }
    }

    public void setSampleSentenceList(ArrayList<SampleSentence> newList) {
        this.sampleSentenceArrayList = newList;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClick(SampleSentence sampleSentence);
    }
}
