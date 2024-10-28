package com.example.doanungdung2.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.example.doanungdung2.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.Grammar;

import java.util.ArrayList;

public class Admin_Delete_Grammar_CustomAdapter_LV extends ArrayAdapter {
    Context context;
    int layoutItem;
    ArrayList<Grammar> grammarArrayList = new ArrayList<>();
    boolean[] checkedSates;

    public Admin_Delete_Grammar_CustomAdapter_LV(@NonNull Context context, int layoutItem,
                                                 @NonNull ArrayList<Grammar> grammarArrayList, boolean[] checkedSates) {
        super(context, layoutItem, grammarArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.grammarArrayList = grammarArrayList;
        this.checkedSates = checkedSates;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Grammar grammar = grammarArrayList.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);

        TextView tvTenNP_Delete = convertView.findViewById(R.id.tvTenNP_Delete);
        tvTenNP_Delete.setText(grammar.getTenNguPhap());
        TextView tvMaDNP_Delete = convertView.findViewById(R.id.tvMaDNP_Delete);
        tvMaDNP_Delete.setText(grammar.getMaDangNguPhap());
        TextView tvNoiDung_Delete = convertView.findViewById(R.id.tvNoiDung_Delete);
        tvNoiDung_Delete.setText(grammar.getNoiDung());

        CheckBox cbNP_Delete = convertView.findViewById(R.id.cbNP_Delete);
        cbNP_Delete.setChecked(checkedSates[position]);
        cbNP_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedSates[position] =((CheckBox) view).isChecked();
            }
        });
        return convertView;
    }
}
