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
import android.widget.TextView;

import com.example.doanungdung2.Controller.TopicSentenceHandler;
import com.example.doanungdung2.Model.TopicSentence;
import com.example.doanungdung2.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Admin_TopicSentence_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin_TopicSentence_Fragment extends Fragment {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    TopicSentenceHandler topicSentenceHandler;

    ArrayList<TopicSentence> topicSentenceArrayList = new ArrayList<>();

    ArrayAdapter<String> stringArrayAdapter;

    LinearLayout lnThemCTS, lnSuaCTS, lnXoaCTS;
    ListView lvDSTopicSentenceFragment;
    TextView tvDSTopicSentence;
    boolean[] checkedStates;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Admin_TopicSentence_Fragment() {

    }

    public static Admin_TopicSentence_Fragment newInstance(String param1, String param2) {
        Admin_TopicSentence_Fragment fragment = new Admin_TopicSentence_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_admin__topic_sentence_, container, false);
        addControl(view);
        topicSentenceHandler = new TopicSentenceHandler(getActivity(), DB_NAME, null, DB_VERSION);
        loadAllDataToLV();

        checkedStates = new boolean[topicSentenceArrayList.size()];
//        Admin_MainPage_TopicSentence_CustomAdapter_LV adapter = new Admin_MainPage_TopicSentence_CustomAdapter_LV(.this, R.layout.layout_topic_sentence_fragment_custom_adapter_lv, topicSentenceArrayList, checkedStates);
//        lvDSTopicSentenceFragment.setAdapter(adapter);
        addEvent();
        return view;
    }

    void addControl(View view) {
        lnThemCTS = view.findViewById(R.id.lnThemCT);
        lnSuaCTS = view.findViewById(R.id.lnSuaCT);
        lnXoaCTS = view.findViewById(R.id.lnXoaCT);
        tvDSTopicSentence = view.findViewById(R.id.tvDSTopicSentence);
        lvDSTopicSentenceFragment = view.findViewById(R.id.lvDSTopicSentenceFragment);
    }

    void addEvent() {
        lnThemCTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(getActivity(), Admin_Add_Topic_SampleSentence.class));
            }
        });
        lnSuaCTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Edit_Topic_SampleSentence.class));
            }
        });
        lnXoaCTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Delete_Topic_SampleSentence.class));
            }
        });
    }

    void loadAllDataToLV() {
        topicSentenceArrayList = topicSentenceHandler.loadAllDataOfTopicSentence();
        Collections.reverse(topicSentenceArrayList);
        ArrayList<String> dataLV = stringArrayList(topicSentenceArrayList);
        stringArrayAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dataLV);
        lvDSTopicSentenceFragment.setAdapter(stringArrayAdapter);

        int itemCount = topicSentenceArrayList.size();
        tvDSTopicSentence.setText("Danh sách số lượng chủ đề mẫu câu: " + itemCount + "");
    }

    ArrayList<String> stringArrayList(ArrayList<TopicSentence> topicSentenceArrayList) {
        ArrayList<String> result = new ArrayList<>();
        for (TopicSentence ts : topicSentenceArrayList) {
            String getResult = ts.getMaChuDeMauCau() + " - " + ts.getTenChuDeMauCau() + " - " + ts.getMoTa();
            result.add(getResult);
        }
        return result;
    }
}
