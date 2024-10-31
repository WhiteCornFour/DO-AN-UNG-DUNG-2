package com.example.doanungdung2.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.doanungdung2.Model.TopicSentence;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_MainPage_TopicSentence_CustomAdapter_LV extends ArrayAdapter {

    Context context;
    int layoutItem;
    ArrayList<TopicSentence> topicSentenceArrayList;
    boolean[] checkedStates;

    public Admin_MainPage_TopicSentence_CustomAdapter_LV(@NonNull Context context, int layoutItem,
                                                         @NonNull ArrayList<TopicSentence> topicSentenceArrayList,
                                                         boolean[] checkedStates) {
        super(context, layoutItem, topicSentenceArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.topicSentenceArrayList = topicSentenceArrayList;
        this.checkedStates = checkedStates;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TopicSentence topicSentence = topicSentenceArrayList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutItem, parent, false);
        }


        TextView tvTopicSentenceTitle = convertView.findViewById(R.id.tvTopicSentenceTitle);
        tvTopicSentenceTitle.setText(topicSentence.getTenChuDeMauCau());

        TextView tvTopicSentenceDescription = convertView.findViewById(R.id.tvTopicSentenceDescription);
        tvTopicSentenceDescription.setText(topicSentence.getMoTa());

        CheckBox cbTopicSentenceSelect = convertView.findViewById(R.id.cbTopicSentenceSelect);
        cbTopicSentenceSelect.setChecked(checkedStates[position]);

        cbTopicSentenceSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedStates[position] = cbTopicSentenceSelect.isChecked();
            }
        });

        return convertView;
    }
}
