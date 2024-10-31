package com.example.doanungdung2.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Delete_Questions_CustomAdapter_LV extends ArrayAdapter {
    Context context;
    int layoutItem;

    ArrayList<Question> questionArrayList = new ArrayList<>();;
    boolean[] checkedStates;

    public Admin_Delete_Questions_CustomAdapter_LV(@NonNull Context context, int layoutItem, @NonNull ArrayList<Question> questionArrayList,
                                                   boolean[] checkedStates) {
        super(context, layoutItem, questionArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.questionArrayList = questionArrayList;
        this.checkedStates = checkedStates;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Question question = questionArrayList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);
        }

        TextView tvNoiDungCauHoi = convertView.findViewById(R.id.tvNoiDungCauHoi);
        tvNoiDungCauHoi.setText(question.getNoiDungCauHoi());

        String dapAnDung = question.getDapAn();
        TextView tvDapAnDung = convertView.findViewById(R.id.tvDapAnDung);
        tvDapAnDung.setText("Đáp án đúng:" + dapAnDung);

        TextView tvMucDo = convertView.findViewById(R.id.tvMucDo);
        tvMucDo.setText(question.getMucDo());

        CheckBox cbDeleteCauHoi = convertView.findViewById(R.id.cbDeleteCauHoi);

        cbDeleteCauHoi.setChecked(checkedStates[position]);

        cbDeleteCauHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedStates[position] = ((CheckBox) view).isChecked();
            }
        });

        return convertView;
    }
}