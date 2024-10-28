package com.example.doanungdung2.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.doanungdung2.Controller.GrammarCategoryHandler;
import com.example.doanungdung2.Model.GrammarCategory;
import com.example.doanungdung2.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Admin_GrammarCategory_Mainpage_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin_GrammarCategory_Mainpage_Fragment extends Fragment {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    GrammarCategoryHandler grammarCategoryHandler;
    ArrayList<GrammarCategory> grammarCategoryArrayList = new ArrayList<>();
    ArrayList<String> dataLV = new ArrayList<>();
    ArrayAdapter<String> adapter;
    LinearLayout lnThemDNP, lnSuaDNP, lnXoaDNP;
    ListView lvDSDNPFragment;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Admin_GrammarCategory_Mainpage_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_Grammar_Mainpage_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin_GrammarCategory_Mainpage_Fragment newInstance(String param1, String param2) {
        Admin_GrammarCategory_Mainpage_Fragment fragment = new Admin_GrammarCategory_Mainpage_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAllDataGrammarCategory();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin__grammar_category_mainpage_, container, false);
        addControl(view);
        grammarCategoryHandler = new GrammarCategoryHandler(getActivity(), DB_NAME, null, DB_VERSION);
        loadAllDataGrammarCategory();
        addEvent();
        return view;
    }
    void addControl(View view)
    {
        lnThemDNP = view.findViewById(R.id.lnThemDNP);
        lnSuaDNP = view.findViewById(R.id.lnSuaDNP);
        lnXoaDNP = view.findViewById(R.id.lnXoaDNP);
        lvDSDNPFragment = view.findViewById(R.id.lvDSDNPFragment);
    }
    void addEvent()
    {
        lnThemDNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Add_Grammar_Category.class));
            }
        });
        lnSuaDNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Edit_Grammar_Category.class));
            }
        });
        lnXoaDNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Delete_Grammar_Category.class));
            }
        });
    }

    public static ArrayList<String> convertObjectToString(ArrayList<GrammarCategory> grammarCategories)
    {
        ArrayList<String> data = new ArrayList<>();
        for (GrammarCategory gr: grammarCategories
             ) {
            String string = gr.getMaDangNguPhap() + " - " + gr.getTenDangNguPhap();
            data.add(string);
        }
        return data;
    }
    void loadAllDataGrammarCategory()
    {
        grammarCategoryArrayList = grammarCategoryHandler.loadAllDataGrammarCategory();
        Collections.reverse(grammarCategoryArrayList);
        dataLV = convertObjectToString(grammarCategoryArrayList);
        adapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                dataLV);
        lvDSDNPFragment.setAdapter(adapter);
    }
}