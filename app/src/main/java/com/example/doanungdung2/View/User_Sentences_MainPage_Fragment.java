package com.example.doanungdung2.View;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.SampleSentenceHandler;
import com.example.doanungdung2.Controller.TopicSentenceHandler;
import com.example.doanungdung2.Model.SampleSentence;
import com.example.doanungdung2.Model.TopicSentence;
import com.example.doanungdung2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_Sentences_MainPage_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_Sentences_MainPage_Fragment extends Fragment {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    private int currentPosition = -1;


    TopicSentenceHandler topicSentenceHandler;
    ArrayList<TopicSentence> topicSentencesArrayList = new ArrayList<>();
    ArrayList<SampleSentence> sampleSentencesArrayList = new ArrayList<>();
    ListView lvTopic;
    Button btnX_us;
    SampleSentenceHandler sampleSentenceHandler;
    SampleSentence sampleSentence;
    User_Sentence_Dialog_lv user_sentence_dialog_lv;
    User_Sentence_CustomAdapter_LV user_sentence_customAdapter_lv;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public User_Sentences_MainPage_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Sentences.
     */
    // TODO: Rename and change types and number of parameters
    public static User_Sentences_MainPage_Fragment newInstance(String param1, String param2) {
        User_Sentences_MainPage_Fragment fragment = new User_Sentences_MainPage_Fragment();
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
        View view = inflater.inflate(R.layout.fragment__sentences, container, false);
        addControl(view);
        topicSentenceHandler = new TopicSentenceHandler(getContext(), DB_NAME, null, DB_VERSION);
        sampleSentenceHandler = new SampleSentenceHandler(getContext(), DB_NAME, null, DB_VERSION);
        loadDataListView();

        addEvent();
        return view;
    }

    void addControl(View view) {
        lvTopic = (ListView) view.findViewById(R.id.lvTopic);
    }

    void addEvent() {
        lvTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TopicSentence topicSentence = topicSentencesArrayList.get(position);
                showDialog(topicSentence);
            }
        });
    }

    private void loadDataListView() {
        topicSentencesArrayList = topicSentenceHandler.loadAllDataOfTopicSentence();
        user_sentence_customAdapter_lv = new User_Sentence_CustomAdapter_LV(getActivity(), R.layout.custom_user_sentence_lv, topicSentencesArrayList);
        lvTopic.setAdapter(user_sentence_customAdapter_lv);
    }

    private void showDialog(TopicSentence topicSentence) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog_sentences, null);
        dialogView.setBackgroundResource(R.drawable.custom_vienden);

        builder.setView(dialogView);

        TextView tvTopicTitle = dialogView.findViewById(R.id.tvTopicTitle_us);
        TextView tvTopicContent = dialogView.findViewById(R.id.tvTopicContent_us);
        ListView lvDialog = dialogView.findViewById(R.id.lvdialog_us);
        LinearLayout LnSupportDia = dialogView.findViewById(R.id.supportDialog);
        Button btnX_us = dialogView.findViewById(R.id.btnX_us);

        tvTopicTitle.setText(topicSentence.getTenChuDeMauCau());
        tvTopicContent.setText(topicSentence.getMoTa());

        ArrayList<SampleSentence> dialogSampleSentences = sampleSentenceHandler.getSampleSentencesByCodeTopic(topicSentence.getMaChuDeMauCau());
        User_Sentence_Dialog_lv dialogAdapter = new User_Sentence_Dialog_lv(getActivity(), R.layout.custom_dialog_sentence_lv, dialogSampleSentences);
        lvDialog.setAdapter(dialogAdapter);

        lvDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SampleSentence selectedSentence = dialogSampleSentences.get(position);
                TextView tvSp_MC = dialogView.findViewById(R.id.tvSp_MC);
                TextView tvSP_PD = dialogView.findViewById(R.id.tvSp_PD);

                tvSp_MC.setText(selectedSentence.getMauCau());
                Log.d("mauCau", selectedSentence.getMauCau() + "phienDich" + selectedSentence.getPhienDich());

                if (LnSupportDia.getVisibility() == View.GONE)
                {
                    LnSupportDia.setVisibility(View.VISIBLE);
                    tvSP_PD.setText(selectedSentence.getPhienDich());

                } else {
                    LnSupportDia.setVisibility(View.GONE);
                }
            }

        });

        AlertDialog dialog = builder.create();
        dialog.show();
        btnX_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.copyFrom(dialog.getWindow().getAttributes());
//        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialog.getWindow().setAttributes(layoutParams);
    }
}