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

import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Delete_ExercisesCategory_CustomAdapter_LV extends ArrayAdapter {
    Context context;
    int layoutItem;
    ArrayList<ExercisesCategory> exercisesCategoryArrayList = new ArrayList<>();

    boolean[] checkedSates;

    public Admin_Delete_ExercisesCategory_CustomAdapter_LV(@NonNull Context context, int layoutItem,
                                                           @NonNull ArrayList<ExercisesCategory> exercisesCategoryArrayList,
                                                           boolean[]checkedSates) {
        super(context, layoutItem, exercisesCategoryArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.exercisesCategoryArrayList = exercisesCategoryArrayList;
        this.checkedSates = checkedSates;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ExercisesCategory exercisesCategory = exercisesCategoryArrayList.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);

        TextView tvTenDangBTDleteAd = convertView.findViewById(R.id.tvTenDangBTDleteAd);
        tvTenDangBTDleteAd.setText(exercisesCategory.getTenDangBaiTap());

        TextView tvMoTaDangBTDleteAd = convertView.findViewById(R.id.tvMoTaDangBTDleteAd);
        tvMoTaDangBTDleteAd.setText(exercisesCategory.getMoTa());

        CheckBox cbAdDeleteExerciseCate = convertView.findViewById(R.id.cbAdDeleteExerciseCate);

        cbAdDeleteExerciseCate.setChecked(checkedSates[position]);

        cbAdDeleteExerciseCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedSates[position] =((CheckBox) view).isChecked();
            }
        });

        return convertView;
    }
}
