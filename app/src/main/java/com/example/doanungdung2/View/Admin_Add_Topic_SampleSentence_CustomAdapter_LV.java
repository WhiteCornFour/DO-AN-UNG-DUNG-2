package com.example.doanungdung2.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.doanungdung2.Model.TopicSentence; // Ensure to import the TopicSentence model
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Add_Topic_SampleSentence_CustomAdapter_LV extends ArrayAdapter {
    Context context;
    int layoutItem;
    ArrayList<TopicSentence> topicSentenceArrayList = new ArrayList<>();

    public Admin_Add_Topic_SampleSentence_CustomAdapter_LV(@NonNull Context context, int layoutItem,
                                                           @NonNull ArrayList<TopicSentence> topicSentenceArrayList) {
        super(context, layoutItem, topicSentenceArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.topicSentenceArrayList = topicSentenceArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TopicSentence topicSentence = topicSentenceArrayList.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutItem,null);
        }

        TextView tvTenChuDeMauCau = convertView.findViewById(R.id.tvChuDeMauCau);
        tvTenChuDeMauCau.setText(topicSentence.getTenChuDeMauCau());

        TextView tvMoTa = convertView.findViewById(R.id.tvMoTaCDMC);
        tvMoTa.setText(topicSentence.getMoTa());


        return convertView;
    }
}
