package com.example.doanungdung2.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.Model.SharedViewModel_Questions;
import com.example.doanungdung2.Model.SharedViewModel_AfterClickAnswer;
import com.example.doanungdung2.Model.SharedViewModel_Answer;
import com.example.doanungdung2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_Quiz_Test_True_False_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_Quiz_Test_True_False_Fragment extends Fragment {
    SharedViewModel_Answer shareViewModelAnswer;
    SharedViewModel_Questions sharedViewModel_questions;
    SharedViewModel_AfterClickAnswer sharedViewModel_afterClickAnswer;
    TextView tvFrameLayoutNoiDungCauHoiTF;
    RadioGroup rdgTrueFalse;
    RadioButton rdbTrue,rdbFalse;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public User_Quiz_Test_True_False_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User_Quiz_Test_True_False_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static User_Quiz_Test_True_False_Fragment newInstance(String param1, String param2) {
        User_Quiz_Test_True_False_Fragment fragment = new User_Quiz_Test_True_False_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel_questions = new ViewModelProvider(requireActivity()).get(SharedViewModel_Questions.class);
        shareViewModelAnswer = new ViewModelProvider(requireActivity()).get(SharedViewModel_Answer.class);
        sharedViewModel_afterClickAnswer = new ViewModelProvider(requireActivity()).get(SharedViewModel_AfterClickAnswer.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user__quiz__test__true__false_, container, false);
        addControl(view);
        //lấy thông tin câu hỏi từ Activity về Fragment để set up Data cho câu hỏi
        sharedViewModel_questions.getSelectedQuestion().observe(getViewLifecycleOwner(), question -> {
            if (question != null) {
                updateQuestionDetails(question);
            }
        });
        //lấy thông tin của đáp án được chọn từ những lần trước từ Activity để load lại câu trả lời trên Fragment
        sharedViewModel_afterClickAnswer.getSelectedAnswer().observe(getViewLifecycleOwner(), answer -> {
            if (answer != null && !answer.isEmpty()) {
                resetRadioButtons();
                setAnswer(answer);
            } else {
                resetRadioButtons();
            }
        });

        addEvent();
        return view;
    }

    void addControl(View view) {
        tvFrameLayoutNoiDungCauHoiTF = view.findViewById(R.id.tvFrameLayoutNoiDungCauHoiTF);
        rdgTrueFalse = view.findViewById(R.id.rdgTrueFalse);
        rdbTrue = view.findViewById(R.id.rdbTrue);
        rdbFalse = view.findViewById(R.id.rdbFalse);
    }

    void addEvent() {
        rdgTrueFalse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rdbTrue:
                        shareViewModelAnswer.setAnswer("True");
                        break;
                    case R.id.rdbFalse:
                        shareViewModelAnswer.setAnswer("False");
                        break;
                }
            }
        });
    }

    //Hàm set up Data cho các câu hỏi, với câu hỏi được truyền về từ Fragment
    private void updateQuestionDetails(Question question) {
        if (question != null) {
            tvFrameLayoutNoiDungCauHoiTF.setText(question.getNoiDungCauHoi());
        }
    }

    //Hàm set up CheckBox để nó hiển thị 1 trong 2 CheckBox true false mà thôi
    //Đồng thời nhận đáp án và gửi lên Activity qua SharedViewModel
//    private void handleRadioButtonClick(RadioButton selectedButton) {
//        resetRadioButtons();
//        selectedButton.setChecked(true);
//        shareViewModelAnswer.setAnswer(String.valueOf(selectedButton.getText()));
//    }

    private void resetRadioButtons() {
        rdgTrueFalse.clearCheck(); // Bỏ chọn tất cả các RadioButton trong RadioGroup
    }

    //Hàm nhận đáp án từ SharedViewModel để set up CheckBox
    private void setAnswer(String answer) {
        if (answer.equals("True")) {
            rdbTrue.setChecked(true);
            rdbFalse.setChecked(false);
        } else if (answer.equals("False")) {
            rdbFalse.setChecked(true);
            rdbTrue.setChecked(false);
        } else {
            rdbTrue.setChecked(false);
            rdbFalse.setChecked(false);
        }
    }

}