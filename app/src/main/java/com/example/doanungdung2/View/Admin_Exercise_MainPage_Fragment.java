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

import com.example.doanungdung2.Controller.ExerciseHandler;
import com.example.doanungdung2.Controller.ExercisesCategoryHandler;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Admin_Exercise_MainPage_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin_Exercise_MainPage_Fragment extends Fragment {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    ExerciseHandler exercisesHandler;

    ArrayList<Exercise> exerciseArrayList = new ArrayList<>();

    ArrayAdapter<String> stringArrayAdapter;

    LinearLayout lnThemBT, lnSuaBT, lnXoaBT;
    ListView lvDSBTFragment;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Admin_Exercise_MainPage_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_Exercise_MainPage_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin_Exercise_MainPage_Fragment newInstance(String param1, String param2) {
        Admin_Exercise_MainPage_Fragment fragment = new Admin_Exercise_MainPage_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_admin__exercise__main_page_, container, false);
        addControll(view);
        exercisesHandler = new ExerciseHandler(getActivity(),
                DB_NAME,null, DB_VERSION);
        loadAllDataToLV();
        addEvent();
        return view;
    }
    void addControll(View view)
    {
        lnThemBT = view.findViewById(R.id.lnThemBT);
        lnSuaBT = view.findViewById(R.id.lnSuaBT);
        lnXoaBT = view.findViewById(R.id.lnXoaBT);
        lvDSBTFragment = view.findViewById(R.id.lvDSBTFragment);
    }

    void addEvent()
    {
        lnThemBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        lnSuaBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Edit_Exercises.class));
            }
        });
        lnXoaBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Delete_Exercise.class));
            }
        });
    }

    void loadAllDataToLV()
    {
        exerciseArrayList = exercisesHandler.loadAllDataOfExercise();
        ArrayList<String> dataLV = stringArrayList(exerciseArrayList);
        stringArrayAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                dataLV);
        lvDSBTFragment.setAdapter(stringArrayAdapter);
    }

    ArrayList<String> stringArrayList(ArrayList<Exercise> exerciseArrayList)
    {
        ArrayList<String> result = new ArrayList<>();
        String getResult;
        for (Exercise ex: exerciseArrayList
        ) {
            getResult = ex.getMaBaiTap() + " - " +ex.getTenBaiTap() + " - " + ex.getMucDo();
            result.add(getResult);
        }
        return result;
    }
}