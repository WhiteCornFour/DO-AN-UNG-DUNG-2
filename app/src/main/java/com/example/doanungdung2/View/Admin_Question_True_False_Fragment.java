package com.example.doanungdung2.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.doanungdung2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Admin_Question_True_False_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin_Question_True_False_Fragment extends Fragment {

    String [] dsTF = new String[]{"T", "F"};
    Spinner spinnerDapAnTFCH;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Admin_Question_True_False_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_Question_True_False.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin_Question_True_False_Fragment newInstance(String param1, String param2) {
        Admin_Question_True_False_Fragment fragment = new Admin_Question_True_False_Fragment();
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
        View view =inflater.inflate(R.layout.fragment_admin_question_true_false, container, false);
        addControl(view);
        spinnerTF();
        return view;
    }
    void addControl(View view)
    {
        spinnerDapAnTFCH = view.findViewById(R.id.spinnerDapAnTFCH);
    }
    void spinnerTF()
    {
        String[] dsDapAn = new String[dsTF.length];
        for (int i = 0; i < dsDapAn.length; i++) {
            dsDapAn[i] = String.valueOf(dsTF[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, dsTF);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDapAnTFCH.setAdapter(adapter);
    }
}