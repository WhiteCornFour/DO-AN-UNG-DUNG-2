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
import com.example.doanungdung2.Controller.SampleSentenceHandler;
import com.example.doanungdung2.Model.SampleSentence;
import com.example.doanungdung2.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Admin_MainPage_SampleSentence#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin_MainPage_SampleSentence extends Fragment {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    SampleSentenceHandler sampleSentenceHandler;
    ArrayList<SampleSentence> sampleSentenceArrayList = new ArrayList<>();
    ArrayAdapter<String> stringArrayAdapter;

    LinearLayout lnThemSS, lnSuaSS, lnXoaSS;
    ListView lvDSSentenceFragment;
    TextView tvDSSentence;

    // TODO: Rename parameter arguments
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Admin_MainPage_SampleSentence() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_MainPage_SampleSentence.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin_MainPage_SampleSentence newInstance(String param1, String param2) {
        Admin_MainPage_SampleSentence fragment = new Admin_MainPage_SampleSentence();
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
    public void onResume() {
        super.onResume();
        loadAllDataToLV();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__main_page__sample_sentence, container, false);
        addControl(view);
        sampleSentenceHandler = new SampleSentenceHandler(getActivity(), DB_NAME, null, DB_VERSION);
        loadAllDataToLV();
        addEvent();
        return view;
    }

    void addControl(View view) {
        lnThemSS = view.findViewById(R.id.lnThemSS);
        lnSuaSS = view.findViewById(R.id.lnSuaSS);
        lnXoaSS = view.findViewById(R.id.lnXoaSS);
        lvDSSentenceFragment = view.findViewById(R.id.lvDSTopicSentenceFragment);
        tvDSSentence = view.findViewById(R.id.tvDSTopicSentence);
    }

    void addEvent() {
        lnThemSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Add_Sample_Sentence.class));
            }
        });

        lnSuaSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Edit_Sample_Sentence.class));
            }
        });

        lnXoaSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Delete_SampleSentence.class));
            }
        });
    }

    void loadAllDataToLV() {
        sampleSentenceArrayList = sampleSentenceHandler.loadAllDataOfSampleSentence();
        Collections.reverse(sampleSentenceArrayList);
        tvDSSentence.setText("Danh sách số lượng mẫu câu: " + String.valueOf(sampleSentenceArrayList.size()));
        ArrayList<String> dataLV = stringArrayList(sampleSentenceArrayList);
        stringArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dataLV);
        lvDSSentenceFragment.setAdapter(stringArrayAdapter);
    }

    ArrayList<String> stringArrayList(ArrayList<SampleSentence> sampleSentenceArrayList) {
        ArrayList<String> result = new ArrayList<>();
        for (SampleSentence ss : sampleSentenceArrayList) {
            String getResult = ss.getMauCau() + " - " + ss.getPhienDich();
            result.add(getResult);
        }
        return result;
    }
}
