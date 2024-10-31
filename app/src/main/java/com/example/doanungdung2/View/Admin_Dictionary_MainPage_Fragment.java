package com.example.doanungdung2.View;

import android.annotation.SuppressLint;
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

import com.example.doanungdung2.Controller.DictionaryHandler;
import com.example.doanungdung2.Controller.ExerciseHandler;
import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Admin_Dictionary_MainPage_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin_Dictionary_MainPage_Fragment extends Fragment {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;

    DictionaryHandler dictionaryHandler;

    ArrayList<Dictionary> dictionaryArrayList = new ArrayList<>();

    ArrayAdapter<String> stringArrayAdapter;

    LinearLayout lnThemTD, lnSuaTD, lnXoaTD;
    ListView lvDSTDFragment;
    TextView tvDSSLTD;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Admin_Dictionary_MainPage_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_Dictionary_MainPage_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin_Dictionary_MainPage_Fragment newInstance(String param1, String param2) {
        Admin_Dictionary_MainPage_Fragment fragment = new Admin_Dictionary_MainPage_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_admin__dictionary__main_page_, container, false);
        addControl(view);
        dictionaryHandler = new DictionaryHandler(getActivity(), DB_NAME,null, DB_VERSION);
        loadAllDataToLV();
        addEvent();
        return view;
    }

    void addControl(View view)
    {
        lnThemTD = view.findViewById(R.id.lnThemTD);
        lnSuaTD = view.findViewById(R.id.lnSuaTD);
        lnXoaTD = view.findViewById(R.id.lnXoaTD);
        lvDSTDFragment = view.findViewById(R.id.lvDSTDFragment);
        tvDSSLTD = view.findViewById(R.id.tvDSSLTD);
    }

    void addEvent()
    {
        lnThemTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Add_Dictionary.class));
            }
        });
        lnSuaTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Admin_Edit_Dictionary.class));
            }
        });
        lnXoaTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getActivity(), Admin_Delete_Dictionary.class));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    void loadAllDataToLV()
    {
        dictionaryArrayList = dictionaryHandler.loadAllDataOfDictionary();
        tvDSSLTD.setText("Danh sách số lượng từ điển: " + String.valueOf(dictionaryArrayList.size()));
        ArrayList<String> dataLV = stringArrayList(dictionaryArrayList);
        stringArrayAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                dataLV);
        lvDSTDFragment.setAdapter(stringArrayAdapter);
    }

    ArrayList<String> stringArrayList(ArrayList<Dictionary> dictionaryArrayList)
    {
        ArrayList<String> result = new ArrayList<>();
        String getResult;
        for (Dictionary d: dictionaryArrayList
        ) {
            getResult = d.getMaTuVung() + " - " + d.getTuTiengAnh() + " - " + d.getLoaiTu();
            result.add(getResult);
        }
        return result;
    }
}