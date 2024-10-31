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

import com.example.doanungdung2.Model.GrammarCategory;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Delete_Grammar_Category_CustomAdapter_LV extends ArrayAdapter {
    Context context;
    int layoutItem;
    ArrayList<GrammarCategory> grammarCategoryArrayList = new ArrayList<>();
    boolean[] checkStates;

    public Admin_Delete_Grammar_Category_CustomAdapter_LV(@NonNull Context context, int layoutItem,
                                                          @NonNull ArrayList<GrammarCategory> grammarCategoryArrayList,
                                                          boolean[] checkStates) {
        super(context, layoutItem, grammarCategoryArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.grammarCategoryArrayList = grammarCategoryArrayList;
        this.checkStates = checkStates;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GrammarCategory grammarCategory = grammarCategoryArrayList.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);

        TextView tvXoaTenDNP, tvXoaMaDNP, tvXoaMoTaDNP;
        tvXoaMaDNP = convertView.findViewById(R.id.tvXoaMaDNP);
        tvXoaTenDNP = convertView.findViewById(R.id.tvXoaTenDNP);
        tvXoaMoTaDNP = convertView.findViewById(R.id.tvXoaMoTaDNP);

        tvXoaMaDNP.setText(grammarCategory.getMaDangNguPhap());
        tvXoaTenDNP.setText(grammarCategory.getTenDangNguPhap());
        tvXoaMoTaDNP.setText(grammarCategory.getMoTa());

        CheckBox cbXoaDNP = convertView.findViewById(R.id.cbXoaDNP);
        cbXoaDNP.setChecked(checkStates[position]);

        cbXoaDNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStates[position] = ((CheckBox) view).isChecked();
            }
        });

        return convertView;
    }
}
