package com.example.doanungdung2.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doanungdung2.Controller.GrammarHandler;
import com.example.doanungdung2.Model.Grammar;
import com.example.doanungdung2.R;

import java.util.ArrayList;
import java.util.Collections;

public class Admin_Grammar_Mainpage_Fragment extends Fragment {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ArrayList<Grammar> grammarArrayList = new ArrayList<>();
    GrammarHandler grammarHandler;
    ArrayAdapter<String> adapter;
    ArrayList<String> dataLV = new ArrayList<>();
    LinearLayout lnThemNP, lnSuaNP, lnXoaNP;
    ListView lvDSNPFragment;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_grammar_mainpage, container, false);
        addCotrol(view);

        grammarHandler = new GrammarHandler(getActivity(), DB_NAME, null, DB_VERSION);
        loadAdllDataLV();
        addEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAdllDataLV();
    }

    void addCotrol(View view)
    {
        lnThemNP = view.findViewById(R.id.lnThemNP);
        lnSuaNP = view.findViewById(R.id.lnSuaNP);
        lnXoaNP = view.findViewById(R.id.lnXoaNP);
        lvDSNPFragment = view.findViewById(R.id.lvDSNPFragment);
    }
    void addEvent()
    {
        lnThemNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Add_Grammar.class));
            }
        });
        lnSuaNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Edit_Grammar.class));
            }
        });
        lnXoaNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Delete_Grammar.class));
            }
        });
    }
    ArrayList<String> convertObjectToString(ArrayList<Grammar> grammarArrayList)
    {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Grammar gr: grammarArrayList
             ) {
            String kq = gr.getTenNguPhap() + " _ MaDangNguPhap: " + gr.getMaDangNguPhap();
            stringArrayList.add(kq);
        }
        return stringArrayList;
    }
    void loadAdllDataLV()
    {
        grammarArrayList = grammarHandler.loadAllDataOfGrammar();
        dataLV = convertObjectToString(grammarArrayList);
        Collections.reverse(dataLV);
        adapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                dataLV);
        lvDSNPFragment.setAdapter(adapter);
    }
}
