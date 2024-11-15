package com.example.doanungdung2.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.SampleSentence;
import com.example.doanungdung2.Model.TopicSentence;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class User_Sentence_Dialog_lv extends ArrayAdapter {

    Context context;
    int layoutItem;
    ArrayList<SampleSentence> sampleSentencesArrayList = new ArrayList<>();

    public User_Sentence_Dialog_lv (@NonNull Context context, int layoutItem,
                                         @NonNull ArrayList<SampleSentence> sampleSentencesArrayList) {
        super(context, layoutItem, sampleSentencesArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.sampleSentencesArrayList = sampleSentencesArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SampleSentence sampleSentence = sampleSentencesArrayList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutItem, parent, false);
        }

        TextView tvMC_US = convertView.findViewById(R.id.tvMC_Us);
        tvMC_US.setText(sampleSentence.getMauCau());

        return convertView;
    }
}

