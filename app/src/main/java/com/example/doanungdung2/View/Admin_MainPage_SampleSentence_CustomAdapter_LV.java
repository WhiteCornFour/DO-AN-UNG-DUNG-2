package com.example.doanungdung2.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.SampleSentence;
import com.example.doanungdung2.Model.TopicSentence;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_MainPage_SampleSentence_CustomAdapter_LV extends ArrayAdapter {
    Context context;
    int layoutItem;
    ArrayList<SampleSentence> sampleSentencesArrayList;
    boolean[] checkedStates;

    public Admin_MainPage_SampleSentence_CustomAdapter_LV (@NonNull Context context, int layoutItem,
                                                           @NonNull ArrayList<SampleSentence> sampleSentencesArrayList,
                                                           boolean[] checkedStates) {
        super(context, layoutItem, sampleSentencesArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.sampleSentencesArrayList = sampleSentencesArrayList;
        this.checkedStates = checkedStates;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        SampleSentence sampleSentence = sampleSentencesArrayList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutItem, parent, false);
        }

        TextView tvTopicSentenceTitle = convertView.findViewById(R.id.tvTopicSentenceTitle);
        tvTopicSentenceTitle.setText(sampleSentence.getMauCau());

        TextView tvTopicSentenceDescription = convertView.findViewById(R.id.tvTopicSentenceDescription);
        tvTopicSentenceDescription.setText(sampleSentence.getPhienDich());


        return convertView;
    }
}

