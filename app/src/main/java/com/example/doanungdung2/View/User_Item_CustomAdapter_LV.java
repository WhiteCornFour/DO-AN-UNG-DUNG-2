package com.example.doanungdung2.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.doanungdung2.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doanungdung2.Model.Grammar;

import java.util.ArrayList;

public class User_Item_CustomAdapter_LV extends ArrayAdapter {
    Context context;
    int layoutItem;
    ArrayList<Grammar> grammarArrayList = new ArrayList<>();

    public User_Item_CustomAdapter_LV(@NonNull Context context, int layoutItem, @NonNull ArrayList<Grammar> grammarArrayList) {
        super(context, layoutItem, grammarArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.grammarArrayList = grammarArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Grammar grammar = grammarArrayList.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);
        TextView tvTNP = convertView.findViewById(R.id.tvTNP);
        tvTNP.setText(grammar.getTenNguPhap());
        return convertView;
    }
}
