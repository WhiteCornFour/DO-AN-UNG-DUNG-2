package com.example.doanungdung2.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.Model.SampleSentence;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Add_SampleSentence_CustomAdapter_LV extends ArrayAdapter {
    Context context;
    int layoutItem;

    ArrayList<SampleSentence> sampleSentenceArrayList = new ArrayList<>();;
    boolean[] checkedStates;

    public Admin_Add_SampleSentence_CustomAdapter_LV(@NonNull Context context, int layoutItem, @NonNull ArrayList<SampleSentence> sampleSentenceArrayList,
                                                     boolean[] checkedStates) {
        super(context, layoutItem, sampleSentenceArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.sampleSentenceArrayList = sampleSentenceArrayList;
        this.checkedStates = checkedStates;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SampleSentence sampleSentence = sampleSentenceArrayList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_admin_add_sample_sentence_custom_adapter_lv, null);
        }

        TextView tvTenMauCau_Add = convertView.findViewById(R.id.tvTenMauCau_Add);
        tvTenMauCau_Add.setText(sampleSentence.getMauCau());

        TextView tvPhienDich_Add = convertView.findViewById(R.id.tvPhienDich_Add);
        tvPhienDich_Add.setText(sampleSentence.getPhienDich());

        return convertView;
    }
}