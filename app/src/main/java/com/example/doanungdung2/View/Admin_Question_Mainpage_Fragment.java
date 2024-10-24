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

import com.example.doanungdung2.Controller.QuestionHandler;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Admin_Question_Mainpage_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin_Question_Mainpage_Fragment extends Fragment {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ArrayList<Question> questionArrayList = new ArrayList<>();
    Question question;
    QuestionHandler questionHandler;
    LinearLayout lnThemCH, lnSuaCH, lnXoaCH;
    ListView lvDSCHFragment;
    ArrayAdapter<String> stringArrayAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Admin_Question_Mainpage_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_Question_Mainpage_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin_Question_Mainpage_Fragment newInstance(String param1, String param2) {
        Admin_Question_Mainpage_Fragment fragment = new Admin_Question_Mainpage_Fragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin__question__mainpage_, container, false);
        addControl(view);
        questionHandler = new QuestionHandler(getActivity(), DB_NAME, null, DB_VERSION);
        loadAllDataLV();
        addEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAllDataLV();
    }

    void addControl(View view)
    {
        lnThemCH = view.findViewById(R.id.lnThemCH);
        lnSuaCH = view.findViewById(R.id.lnSuaCH);
        lnXoaCH = view.findViewById(R.id.lnXoaCH);
        lvDSCHFragment = view.findViewById(R.id.lvDSCHFragment);
    }
    void addEvent()
    {
        lnThemCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Add_Question.class));
            }
        });
        lnSuaCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Edit_Questions.class));
            }
        });
        lnXoaCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    void loadAllDataLV()
    {
        Collections.reverse(questionArrayList = questionHandler.loadAllDataOfQuestion());;
        ArrayList<String> data = stringArrayList(questionArrayList);
        stringArrayAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                data);
        lvDSCHFragment.setAdapter(stringArrayAdapter);
    }
    ArrayList<String> stringArrayList(ArrayList<Question> questionArrayList)
    {
        ArrayList<String> strings = new ArrayList<>();
        for (Question qt: questionArrayList
             ) {
            String string = qt.getNoiDungCauHoi() + " - " + qt.getMucDo() + " - " + qt.getMaDangBaiTap();
            strings.add(string);
        }
        return strings;
    }
}