package com.example.doanungdung2.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.QuestionHandler;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.Model.ShareViewModel_Answer;
import com.example.doanungdung2.Model.SharedViewModel;
import com.example.doanungdung2.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_Quiz_Test_Multiple_Choice_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_Quiz_Test_Multiple_Choice_Fragment extends Fragment {
    ShareViewModel_Answer shareViewModelAnswer;
    TextView tvFrameLayoutNoiDungCauHoiMC, tvFrameLayoutNDCauAQuizTest, tvFrameLayoutNDCauBQuizTest,tvFrameLayoutNDCauCQuizTest, tvFrameLayoutNDCauDQuizTest;
    RadioButton rdbFrameLayoutA,rdbFrameLayoutB, rdbFrameLayoutC, rdbFrameLayoutD;
    private SharedViewModel sharedViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public User_Quiz_Test_Multiple_Choice_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User_Quiz_Test_Multiple_Choice_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static User_Quiz_Test_Multiple_Choice_Fragment newInstance(String param1, String param2) {
        User_Quiz_Test_Multiple_Choice_Fragment fragment = new User_Quiz_Test_Multiple_Choice_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        shareViewModelAnswer = new ViewModelProvider(requireActivity()).get(ShareViewModel_Answer.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user__quiz__test__multiple__choice_, container, false);
        addControl(view);
        sharedViewModel.getSelectedQuestion().observe(getViewLifecycleOwner(), question -> {
            if (question != null) {
                updateQuestionDetails(question);
            }
        });

        addEvent();
        return view;
    }

    void addControl (View view) {
        tvFrameLayoutNoiDungCauHoiMC = view.findViewById(R.id.tvFrameLayoutNoiDungCauHoiMC);
        tvFrameLayoutNDCauAQuizTest = view.findViewById(R.id.tvFrameLayoutNDCauAQuizTest);
        tvFrameLayoutNDCauBQuizTest = view.findViewById(R.id.tvFrameLayoutNDCauBQuizTest);
        tvFrameLayoutNDCauCQuizTest = view.findViewById(R.id.tvFrameLayoutNDCauCQuizTest);
        tvFrameLayoutNDCauDQuizTest = view.findViewById(R.id.tvFrameLayoutNDCauDQuizTest);
        rdbFrameLayoutA = view.findViewById(R.id.rdbFrameLayoutA);
        rdbFrameLayoutB = view.findViewById(R.id.rdbFrameLayoutB);
        rdbFrameLayoutC = view.findViewById(R.id.rdbFrameLayoutC);
        rdbFrameLayoutD = view.findViewById(R.id.rdbFrameLayoutD);
    }

    void addEvent () {
        rdbFrameLayoutA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRadioButtonClick(rdbFrameLayoutA);
                tvFrameLayoutNDCauAQuizTest.setTextColor(getResources().getColor(R.color.brown));
                tvFrameLayoutNDCauBQuizTest.setTextColor(getResources().getColor(R.color.mist));
                tvFrameLayoutNDCauCQuizTest.setTextColor(getResources().getColor(R.color.mist));
                tvFrameLayoutNDCauDQuizTest.setTextColor(getResources().getColor(R.color.mist));
            }
        });

        rdbFrameLayoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRadioButtonClick(rdbFrameLayoutB);
                tvFrameLayoutNDCauAQuizTest.setTextColor(getResources().getColor(R.color.mist));
                tvFrameLayoutNDCauBQuizTest.setTextColor(getResources().getColor(R.color.brown));
                tvFrameLayoutNDCauCQuizTest.setTextColor(getResources().getColor(R.color.mist));
                tvFrameLayoutNDCauDQuizTest.setTextColor(getResources().getColor(R.color.mist));
            }
        });

        rdbFrameLayoutC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRadioButtonClick(rdbFrameLayoutC);
                tvFrameLayoutNDCauAQuizTest.setTextColor(getResources().getColor(R.color.mist));
                tvFrameLayoutNDCauBQuizTest.setTextColor(getResources().getColor(R.color.mist));
                tvFrameLayoutNDCauCQuizTest.setTextColor(getResources().getColor(R.color.brown));
                tvFrameLayoutNDCauDQuizTest.setTextColor(getResources().getColor(R.color.mist));
            }
        });

        rdbFrameLayoutD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRadioButtonClick(rdbFrameLayoutD);
                tvFrameLayoutNDCauAQuizTest.setTextColor(getResources().getColor(R.color.mist));
                tvFrameLayoutNDCauBQuizTest.setTextColor(getResources().getColor(R.color.mist));
                tvFrameLayoutNDCauCQuizTest.setTextColor(getResources().getColor(R.color.mist));
                tvFrameLayoutNDCauDQuizTest.setTextColor(getResources().getColor(R.color.brown));
            }
        });

    }

    private void updateQuestionDetails(Question question) {
        if (question != null) {
            tvFrameLayoutNoiDungCauHoiMC.setText(question.getNoiDungCauHoi());
            tvFrameLayoutNDCauAQuizTest.setText(question.getCauA());
            tvFrameLayoutNDCauBQuizTest.setText(question.getCauB());
            tvFrameLayoutNDCauCQuizTest.setText(question.getCauC());
            tvFrameLayoutNDCauDQuizTest.setText(question.getCauD());
        }
    }

    void handleRadioButtonClick(RadioButton selectedRadioButton) {
        rdbFrameLayoutA.setChecked(false);
        rdbFrameLayoutB.setChecked(false);
        rdbFrameLayoutC.setChecked(false);
        rdbFrameLayoutD.setChecked(false);
        //chon dap an moi
        selectedRadioButton.setChecked(true);
        if (selectedRadioButton.getText() != null)
        {
            shareViewModelAnswer.setAnswer(String.valueOf(selectedRadioButton.getText()));
        }
        Toast.makeText(getActivity(), "Selected from fragment: " + selectedRadioButton.getText(), Toast.LENGTH_SHORT).show();
    }
}