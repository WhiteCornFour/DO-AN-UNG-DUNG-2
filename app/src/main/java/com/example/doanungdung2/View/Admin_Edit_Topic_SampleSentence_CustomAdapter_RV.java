package com.example.doanungdung2.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Model.TopicSentence; // Đảm bảo bạn đã nhập đúng lớp mô hình
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Edit_Topic_SampleSentence_CustomAdapter_RV extends RecyclerView.Adapter<Admin_Edit_Topic_SampleSentence_CustomAdapter_RV.MyViewHolder> {
    ArrayList<TopicSentence> topicSentenceArrayList;
    ItemClickListener itemClickListener;

    public Admin_Edit_Topic_SampleSentence_CustomAdapter_RV(ArrayList<TopicSentence> topicSentenceArrayList, ItemClickListener itemClickListener) {
        this.topicSentenceArrayList = topicSentenceArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_admin_edit_topic_sample_sentence_custom_adapter_rv, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TopicSentence ts = topicSentenceArrayList.get(position);
        holder.tvRVTen_CDMC.setText(ts.getTenChuDeMauCau());
        holder.tvRVMota_CDMC.setText(ts.getMoTa());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(ts);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topicSentenceArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRVTen_CDMC, tvRVMota_CDMC;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRVTen_CDMC = itemView.findViewById(R.id.tvRVTen_CDMC);
            tvRVMota_CDMC = itemView.findViewById(R.id.tvRVMoTa_CDMC);
        }
    }
    public void setTopicSentenceList(ArrayList<TopicSentence> newList) {
        this.topicSentenceArrayList = newList;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClick(TopicSentence topicSentence);
    }
}
