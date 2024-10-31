package com.example.doanungdung2.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Custom_Add_DBT_Adapter extends ArrayAdapter {

    Context context;
    ArrayList<ExercisesCategory> exercisesCategoryArrayList;

    // Constructor
    public Admin_Custom_Add_DBT_Adapter(Context context, int resource, ArrayList<ExercisesCategory> exercisesCategoryArrayList) {
        super(context, resource, exercisesCategoryArrayList);
        this.context = context;
        this.exercisesCategoryArrayList = exercisesCategoryArrayList;
    }

    @Override
    public int getCount() {
        return exercisesCategoryArrayList.size();
    }

    @Override
    public ExercisesCategory getItem(int position) {
        return exercisesCategoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_admin_lv_dbt, parent, false);
        }

        TextView tv_lv_DBT = convertView.findViewById(R.id.tv_lv_DBT);
        TextView tv_lv_MoTaDBT = convertView.findViewById(R.id.tv_lv_MoTaDBT);

        ExercisesCategory exercisesCategory = getItem(position);
        tv_lv_DBT.setText(exercisesCategory.getTenDangBaiTap());
        tv_lv_MoTaDBT.setText(exercisesCategory.getMoTa());

        return convertView;
    }
}

