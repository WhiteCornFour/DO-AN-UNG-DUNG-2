package com.example.doanungdung2.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.R;
import com.example.doanungdung2.Model.SharedViewModel;

public class Admin_Question_Multiple_Choice_Fragment extends Fragment {

    private static final String[] dsDA = new String[]{"A", "B", "C", "D"};
    private EditText edtCauASuaCauHoi, edtCauBSuaCauHoi, edtCauCSuaCauHoi, edtCauDSuaCauHoi;
    private Spinner spinnerDapAnTNCH;
    private SharedViewModel sharedViewModel;

    public Admin_Question_Multiple_Choice_Fragment() {
        // Required empty public constructor
    }

    public static Admin_Question_Multiple_Choice_Fragment newInstance(String param1, String param2) {
        Admin_Question_Multiple_Choice_Fragment fragment = new Admin_Question_Multiple_Choice_Fragment();
        Bundle args = new Bundle();
        args.putString("ARG_PARAM1", param1);
        args.putString("ARG_PARAM2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_question_multiple_choice, container, false);
        initializeViews(view);
        setupSpinner();
        // Quan sát thay đổi từ ViewModel
        sharedViewModel.getSelectedQuestion().observe(getViewLifecycleOwner(), this::updateQuestionDetails);

        return view;
    }
    private void initializeViews(View view) {
        edtCauASuaCauHoi = view.findViewById(R.id.edtCauASuaCauHoi);
        edtCauBSuaCauHoi = view.findViewById(R.id.edtCauBSuaCauHoi);
        edtCauCSuaCauHoi = view.findViewById(R.id.edtCauCSuaCauHoi);
        edtCauDSuaCauHoi = view.findViewById(R.id.edtCauDSuaCauHoi);
        spinnerDapAnTNCH = view.findViewById(R.id.spinnerDapAnTNCH);
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, dsDA);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDapAnTNCH.setAdapter(adapter);
    }

    private void updateQuestionDetails(Question question) {
        if (question != null) {
            edtCauASuaCauHoi.setText(question.getCauA());
            edtCauBSuaCauHoi.setText(question.getCauB());
            edtCauCSuaCauHoi.setText(question.getCauC());
            edtCauDSuaCauHoi.setText(question.getCauD());

            // Cập nhật vị trí của spinner dựa trên đáp án đúng
            ArrayAdapter<String> spinnerAdapter = (ArrayAdapter<String>) spinnerDapAnTNCH.getAdapter();
            int index = spinnerAdapter.getPosition(question.getDapAn());
            if (index != -1) {
                spinnerDapAnTNCH.setSelection(index);
            }
        }
    }
}
