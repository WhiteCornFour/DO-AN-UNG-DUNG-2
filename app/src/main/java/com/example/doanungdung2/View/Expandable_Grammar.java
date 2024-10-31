package com.example.doanungdung2.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.doanungdung2.Controller.GrammarHandler;
import com.example.doanungdung2.R;
import com.example.doanungdung2.Model.Grammar;
import com.example.doanungdung2.Model.GrammarCategory;

import java.util.ArrayList;

public class Expandable_Grammar extends BaseAdapter {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ArrayList<GrammarCategory> grammarCategoryArrayList = new ArrayList<>();
    ArrayList<Grammar> grammarArrayList = new ArrayList<>();
    GrammarHandler grammarHandler;
    int layoutItem;
    Context context;
    User_Item_CustomAdapter_LV adapter;

    public Expandable_Grammar( Context context , int layoutItem, ArrayList<GrammarCategory> grammarCategoryArrayList) {
        this.layoutItem = layoutItem;
        this.context = context;
        this.grammarCategoryArrayList = grammarCategoryArrayList;
    }

    @Override
    public int getCount() {
        return grammarCategoryArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return grammarCategoryArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GrammarCategory grammarCategory = grammarCategoryArrayList.get(i);
        if (view == null)
            view = LayoutInflater.from(context).inflate(layoutItem, null);
        grammarHandler = new GrammarHandler(context, DB_NAME, null, DB_VERSION);
        TextView tvTDBT = view.findViewById(R.id.tvTDBT);
        tvTDBT.setText(grammarCategory.getTenDangNguPhap());
        RelativeLayout itemClicked = view.findViewById(R.id.itemClicked);
        LinearLayout dis = view.findViewById(R.id.dis);
        LinearLayout layoutMother = view.findViewById(R.id.layoutMother);
        ListView lvNP = view.findViewById(R.id.lvNP);
        lvNP.setDivider(null);
        lvNP.setDividerHeight(0);
        itemClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dis.getVisibility() == View.GONE)
                {
                    TransitionManager.beginDelayedTransition(layoutMother, new AutoTransition());
                    dis.setVisibility(View.VISIBLE);
                    grammarArrayList = grammarHandler.searchByCodeOrNameGrammar(grammarCategory.getMaDangNguPhap());
                    loadAllData(lvNP, grammarArrayList);
                }else {
                    TransitionManager.beginDelayedTransition(layoutMother, new AutoTransition());
                    dis.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

    private void loadAllData(ListView lvNP, ArrayList<Grammar> grammarArrayList)
    {
        adapter = new User_Item_CustomAdapter_LV(context, R.layout.layout_user_item_custom_adapter_lv,
                grammarArrayList);
        lvNP.setAdapter(adapter);
    }
}
