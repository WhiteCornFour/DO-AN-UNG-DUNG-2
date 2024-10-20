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

import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Delete_Exercise_CustomAdapter_LV extends ArrayAdapter {
    Context context;
    int layoutItem;
    ArrayList<Exercise> exerciseArrayList = new ArrayList<>();

    boolean[] checkedSates;

    public Admin_Delete_Exercise_CustomAdapter_LV(@NonNull Context context, int layoutItem, @NonNull ArrayList<Exercise> exerciseArrayList, boolean[] checkedSates) {
        super(context, layoutItem, exerciseArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.exerciseArrayList = exerciseArrayList;
        this.checkedSates = checkedSates;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Exercise exercise = exerciseArrayList.get(position);
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);

        TextView tvTenBTDleteAd = convertView.findViewById(R.id.tvTenBTDleteAd);
        tvTenBTDleteAd.setText(exercise.getTenBaiTap());

        TextView tvMoTaBTDleteAd = convertView.findViewById(R.id.tvMoTaBTDleteAd);
        tvMoTaBTDleteAd.setText(exercise.getMoTa());

        CheckBox cbAdDeleteExercise = convertView.findViewById(R.id.cbAdDeleteExercise);

        cbAdDeleteExercise.setChecked(checkedSates[position]);

        cbAdDeleteExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedSates[position] =((CheckBox) view).isChecked();
            }
        });

        return convertView;
    }
}
