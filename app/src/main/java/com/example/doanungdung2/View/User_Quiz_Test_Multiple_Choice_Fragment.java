package com.example.doanungdung2.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.Model.SharedViewModel_Answer;
import com.example.doanungdung2.Model.SharedViewModel_Questions;
import com.example.doanungdung2.Model.SharedViewModel_AfterClickAnswer;
import com.example.doanungdung2.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_Quiz_Test_Multiple_Choice_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_Quiz_Test_Multiple_Choice_Fragment extends Fragment {
    SharedViewModel_Answer shareViewModelAnswer;
    private SharedViewModel_Questions sharedViewModel_questions;
    SharedViewModel_AfterClickAnswer sharedViewModel_afterClickAnswer;
    TextView tvFrameLayoutNoiDungCauHoiMC, tvFrameLayoutNDCauAQuizTest, tvFrameLayoutNDCauBQuizTest,tvFrameLayoutNDCauCQuizTest, tvFrameLayoutNDCauDQuizTest;
    RadioButton rdbFrameLayoutA,rdbFrameLayoutB, rdbFrameLayoutC, rdbFrameLayoutD;

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
        sharedViewModel_questions = new ViewModelProvider(requireActivity()).get(SharedViewModel_Questions.class);
        shareViewModelAnswer = new ViewModelProvider(requireActivity()).get(SharedViewModel_Answer.class);
        sharedViewModel_afterClickAnswer = new ViewModelProvider(requireActivity()).get(SharedViewModel_AfterClickAnswer.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user__quiz__test__multiple__choice_, container, false);
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
        //set up cho các radio button với các text view, cái nào được chọn thì đổi màu text sang brpwm
        rdbFrameLayoutA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRadioButtonClick(rdbFrameLayoutA);
                updateAnswerState("A");
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
                updateAnswerState("B");
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
                updateAnswerState("C");
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
                updateAnswerState("D");
                tvFrameLayoutNDCauAQuizTest.setTextColor(getResources().getColor(R.color.mist));
                tvFrameLayoutNDCauBQuizTest.setTextColor(getResources().getColor(R.color.mist));
                tvFrameLayoutNDCauCQuizTest.setTextColor(getResources().getColor(R.color.mist));
                tvFrameLayoutNDCauDQuizTest.setTextColor(getResources().getColor(R.color.brown));
            }
        });

    }

    //Hàm gửi câu trả lời lên trên Activity để thực hiện các thao tác
    private void updateAnswerState(String selectedAnswer) {
        // Lưu câu trả lời đã chọn vào ViewModel
        shareViewModelAnswer.setAnswer(selectedAnswer);
        Toast.makeText(getActivity(), "Selected from fragment: " + selectedAnswer, Toast.LENGTH_SHORT).show();
    }

    //Hàm set up Data cho câu hỏi cho Fragment sau khi nhận thông tin từ Activity
    private void updateQuestionDetails(Question question) {
        if (question != null) {
            tvFrameLayoutNoiDungCauHoiMC.setText(question.getNoiDungCauHoi());
            tvFrameLayoutNDCauAQuizTest.setText(question.getCauA());
            tvFrameLayoutNDCauBQuizTest.setText(question.getCauB());
            tvFrameLayoutNDCauCQuizTest.setText(question.getCauC());
            tvFrameLayoutNDCauDQuizTest.setText(question.getCauD());

        }
    }
    //Set up data cho radiobutton mỗi khi click và gửi đáp án được chọn dưới fragment lên activity
    private void handleRadioButtonClick(RadioButton selectedRadioButton) {
        resetRadioButtons();
        selectedRadioButton.setChecked(true);
        shareViewModelAnswer.setAnswer(String.valueOf(selectedRadioButton.getText()));
        Toast.makeText(getActivity(), "Selected from fragment: " + selectedRadioButton.getText(), Toast.LENGTH_SHORT).show();
    }

    //hàm trả radio button về trạng thái ban đầu, thường là khi mới bắt đầu quiz hoặc câu hỏi chưa được người dùng chọn đáp án
    private void resetRadioButtons() {
        rdbFrameLayoutA.setChecked(false);
        rdbFrameLayoutB.setChecked(false);
        rdbFrameLayoutC.setChecked(false);
        rdbFrameLayoutD.setChecked(false);

        tvFrameLayoutNDCauAQuizTest.setTextColor(getResources().getColor(R.color.mist));
        tvFrameLayoutNDCauBQuizTest.setTextColor(getResources().getColor(R.color.mist));
        tvFrameLayoutNDCauCQuizTest.setTextColor(getResources().getColor(R.color.mist));
        tvFrameLayoutNDCauDQuizTest.setTextColor(getResources().getColor(R.color.mist));
    }

    //thiết lập radio button với đáp án được gửi về từ SharedViewModel
    private void setAnswer(String answer) {
        switch (answer) {
            case "A":
                rdbFrameLayoutA.setChecked(true);
                tvFrameLayoutNDCauAQuizTest.setTextColor(getResources().getColor(R.color.brown));
                break;
            case "B":
                rdbFrameLayoutB.setChecked(true);
                tvFrameLayoutNDCauBQuizTest.setTextColor(getResources().getColor(R.color.brown));
                break;
            case "C":
                rdbFrameLayoutC.setChecked(true);
                tvFrameLayoutNDCauCQuizTest.setTextColor(getResources().getColor(R.color.brown));
                break;
            case "D":
                rdbFrameLayoutD.setChecked(true);
                tvFrameLayoutNDCauDQuizTest.setTextColor(getResources().getColor(R.color.brown));
                break;
            default:
                resetRadioButtons();
                tvFrameLayoutNDCauAQuizTest.setTextColor(getResources().getColor(R.color.mist));
                tvFrameLayoutNDCauBQuizTest.setTextColor(getResources().getColor(R.color.mist));
                tvFrameLayoutNDCauCQuizTest.setTextColor(getResources().getColor(R.color.mist));
                tvFrameLayoutNDCauDQuizTest.setTextColor(getResources().getColor(R.color.mist));
                break;
        }
    }


}