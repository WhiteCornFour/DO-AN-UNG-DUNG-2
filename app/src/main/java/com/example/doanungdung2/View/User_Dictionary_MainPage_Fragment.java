package com.example.doanungdung2.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.DictionaryHandler;
import com.example.doanungdung2.Controller.HistoryHandler;
import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.History;
import com.example.doanungdung2.Model.SharedViewModel_User;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_Dictionary_MainPage_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_Dictionary_MainPage_Fragment extends Fragment {

    TextView tvDictionaryChoose, tvTranslationChoose, tvDictionaryTitle;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public User_Dictionary_MainPage_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Dictionary.
     */
    // TODO: Rename and change types and number of parameters
    public static User_Dictionary_MainPage_Fragment newInstance(String param1, String param2) {
        User_Dictionary_MainPage_Fragment fragment = new User_Dictionary_MainPage_Fragment();
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
        View view =  inflater.inflate(R.layout.fragment__dictionary, container, false);
        addControl(view);

        if (savedInstanceState == null) {
            replaceFragment(new User_Dictionary_Fragment());
        }

        addEvent();
        return view;
    }

    void addControl(View view) {
        tvDictionaryChoose = view.findViewById(R.id.tvDictionaryChoose);
        tvTranslationChoose = view.findViewById(R.id.tvTranslationChoose);
        tvDictionaryTitle = view.findViewById(R.id.tvDictionaryTitle);
    }

    void addEvent() {
        tvDictionaryChoose.setBackgroundResource(R.drawable.tab_bar_button);
        tvTranslationChoose.setBackgroundResource(R.drawable.tab_bar_button_default);
        tvDictionaryChoose.setTextColor(getContext().getColor(R.color.white));
        tvTranslationChoose.setTextColor(getContext().getColor(R.color.shape_green));
        tvDictionaryTitle.setText("Dictionary");
        tvDictionaryChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDictionaryChoose.setBackgroundResource(R.drawable.tab_bar_button);
                tvTranslationChoose.setBackgroundResource(R.drawable.tab_bar_button_default);
                tvDictionaryChoose.setTextColor(getContext().getColor(R.color.white));
                tvTranslationChoose.setTextColor(getContext().getColor(R.color.shape_green));
                tvDictionaryTitle.setText("Dictionary");
                replaceFragment(new User_Dictionary_Fragment());
            }
        });

        // Handle tvTranslationChoose click event
        tvTranslationChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTranslationChoose.setBackgroundResource(R.drawable.tab_bar_button);
                tvDictionaryChoose.setBackgroundResource(R.drawable.tab_bar_button_default);
                tvDictionaryChoose.setTextColor(getContext().getColor(R.color.shape_green));
                tvTranslationChoose.setTextColor(getContext().getColor(R.color.white));
                tvDictionaryTitle.setText("Translation");
                replaceFragment(new User_Translation_Fragment());
            }
        });
    }
  
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutDictionaryFragment, fragment);
        fragmentTransaction.commit();
    }
}