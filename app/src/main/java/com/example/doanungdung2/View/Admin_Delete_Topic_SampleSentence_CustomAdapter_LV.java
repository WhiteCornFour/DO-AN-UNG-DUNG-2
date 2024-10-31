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

import com.example.doanungdung2.Model.TopicSentence;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Delete_Topic_SampleSentence_CustomAdapter_LV extends ArrayAdapter {
    Context context;
    int layoutItem;

    ArrayList<TopicSentence> topicSentenceArrayList = new ArrayList<>();
    boolean[] checkedStates;

    public Admin_Delete_Topic_SampleSentence_CustomAdapter_LV(@NonNull Context context, int layoutItem, @NonNull ArrayList<TopicSentence> topicSentenceArrayList,
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
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);
        }

        TextView tvDelete_CDMC = convertView.findViewById(R.id.tvDelete_CDMC);
        tvDelete_CDMC.setText(topicSentence.getTenChuDeMauCau()); //

        TextView tvDeleteMoTa_CDMC = convertView.findViewById(R.id.tvDeleteMoTa_CDMC);
        tvDeleteMoTa_CDMC.setText(topicSentence.getMoTa());

        CheckBox cbDeleteCDMC = convertView.findViewById(R.id.cbDeleteCDMC);
        cbDeleteCDMC.setChecked(checkedStates[position]);

        cbDeleteCDMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedStates[position] = ((CheckBox)view).isChecked();
            }
        });

        return convertView;
    }
}
