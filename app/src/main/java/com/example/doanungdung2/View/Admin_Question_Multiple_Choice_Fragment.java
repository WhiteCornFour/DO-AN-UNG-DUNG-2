package com.example.doanungdung2.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Admin_Question_Multiple_Choice_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin_Question_Multiple_Choice_Fragment extends Fragment {

    String [] dsDA = new String[]{"A", "B", "C", "D"};
    EditText edtCauASuaCauHoi, edtCauBSuaCauHoi, edtCauCSuaCauHoi, edtCauDSuaCauHoi;
    Spinner spinnerDapAnTNCH;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Admin_Question_Multiple_Choice_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_Question_Multiple_Choice.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin_Question_Multiple_Choice_Fragment newInstance(String param1, String param2) {
        Admin_Question_Multiple_Choice_Fragment fragment = new Admin_Question_Multiple_Choice_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_admin_question_multiple_choice, container, false);
        addControl(view);
        spinnerDapAn();

        FragmentManager fm = getParentFragmentManager();
        fm.setFragmentResultListener("ch", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Question question = (Question) result.getSerializable("question");
                if (question != null) {
                    updateQuestionDetails(question);
                }
            }
        });
        return view;
    }

    void addControl(View view)
    {
        edtCauASuaCauHoi = view.findViewById(R.id.edtCauASuaCauHoi);
        edtCauBSuaCauHoi = view.findViewById(R.id.edtCauBSuaCauHoi);
        edtCauCSuaCauHoi = view.findViewById(R.id.edtCauCSuaCauHoi);
        edtCauDSuaCauHoi = view.findViewById(R.id.edtCauDSuaCauHoi);
        spinnerDapAnTNCH = view.findViewById(R.id.spinnerDapAnTNCH);
    }

    void spinnerDapAn() {
        String[] dsDapAn = new String[dsDA.length];
        for (int i = 0; i < dsDapAn.length; i++) {
            dsDapAn[i] = String.valueOf(dsDA[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, dsDA);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDapAnTNCH.setAdapter(adapter);
    }

    public void updateQuestionDetails(Question question) {
        edtCauASuaCauHoi.setText(question.getCauA());
        edtCauBSuaCauHoi.setText(question.getCauB());
        edtCauCSuaCauHoi.setText(question.getCauC());
        edtCauDSuaCauHoi.setText(question.getCauD());

        if (spinnerDapAnTNCH != null) {
            ArrayAdapter<String> spinnerAdapter = (ArrayAdapter<String>) spinnerDapAnTNCH.getAdapter();
            int index = spinnerAdapter.getPosition(question.getDapAn());
            if (index != -1) {
                spinnerDapAnTNCH.setSelection(index);
            }
        }
    }
}