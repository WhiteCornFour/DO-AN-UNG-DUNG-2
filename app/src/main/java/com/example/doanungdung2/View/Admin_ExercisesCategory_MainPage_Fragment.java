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

import com.example.doanungdung2.Controller.ExercisesCategoryHandler;
import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Admin_ExercisesCategory_MainPage_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin_ExercisesCategory_MainPage_Fragment extends Fragment {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    ExercisesCategoryHandler exercisesCategoryHandler;

    ArrayList<ExercisesCategory> exercisesCategoryArrayList = new ArrayList<>();

    ArrayAdapter<String> stringArrayAdapter;
    LinearLayout lnThemDBT, lnSuaDBT, lnXoaDBT;
    ListView lvDSDangBTFragment;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Admin_ExercisesCategory_MainPage_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_DangBaiTap_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin_ExercisesCategory_MainPage_Fragment newInstance(String param1, String param2) {
        Admin_ExercisesCategory_MainPage_Fragment fragment = new Admin_ExercisesCategory_MainPage_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onResume() {
        super.onResume();
        loadAllDataToLV();
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
        View view = inflater.inflate(R.layout.fragment_admin__dang_bai_tap_, container, false);
        addControll(view);
        exercisesCategoryHandler = new ExercisesCategoryHandler(getActivity(),
                DB_NAME,null, DB_VERSION);
        loadAllDataToLV();
        addEvent();
        return view;
    }

    void addControll(View view)
    {
        lnThemDBT = view.findViewById(R.id.lnThemDBT);
        lnSuaDBT = view.findViewById(R.id.lnSuaDBT);
        lnXoaDBT = view.findViewById(R.id.lnXoaDBT);
        lvDSDangBTFragment = view.findViewById(R.id.lvDSDangBTFragment);
    }

    void addEvent()
    {
        lnThemDBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        lnSuaDBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Edit_ExercisesCategory.class));
            }
        });
        lnXoaDBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Delete_ExercisesCategory.class));
            }
        });
    }

    void loadAllDataToLV()
    {
        exercisesCategoryArrayList = exercisesCategoryHandler.loadAllDataOfExercisesCategory();
        ArrayList<String> dataLV = stringArrayList(exercisesCategoryArrayList);
        stringArrayAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                dataLV);
        lvDSDangBTFragment.setAdapter(stringArrayAdapter);
    }

    ArrayList<String> stringArrayList(ArrayList<ExercisesCategory> exercisesCategoryArrayList)
    {
        ArrayList<String> result = new ArrayList<>();
        String getResult;
        for (ExercisesCategory ex: exercisesCategoryArrayList
             ) {
            getResult = ex.getMaDangBaiTap() + " - " +ex.getTenDangBaiTap();
            result.add(getResult);
        }
        return result;
    }
}