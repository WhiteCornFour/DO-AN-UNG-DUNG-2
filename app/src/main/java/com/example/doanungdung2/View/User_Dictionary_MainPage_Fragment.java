package com.example.doanungdung2.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.doanungdung2.Controller.DictionaryHandler;
import com.example.doanungdung2.Controller.HistoryHandler;
import com.example.doanungdung2.Model.History;
import com.example.doanungdung2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_Dictionary_MainPage_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_Dictionary_MainPage_Fragment extends Fragment {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    RecyclerView rvSearchHistory;
    EditText edtSearchBoxDictionary;
    HistoryHandler historyHandler;
    ArrayList<History> historyArrayList = new ArrayList<>();
    User_History_Custom_Adapter_Recycler_View user_history_custom_adapter_recycler_view;

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

        historyHandler = new HistoryHandler(getActivity(), DB_NAME, null, DB_VERSION);
        
        setupRecyclerView();
        loadAllHistory();

        return view;
    }

    void addControl(View view) {
        rvSearchHistory = view.findViewById(R.id.rvSearchHistory);
        edtSearchBoxDictionary = view.findViewById(R.id.edtSearchBoxDictionary);
    }

    void loadAllHistory() {
        historyArrayList.clear();
        historyArrayList = historyHandler.loadAllDataOfHistory();
        user_history_custom_adapter_recycler_view.setHistoryList(historyArrayList);
    }

    void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
//        rvSearchHistory.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvSearchHistory.setLayoutManager(layoutManager);
        rvSearchHistory.setItemAnimator(new DefaultItemAnimator());
        user_history_custom_adapter_recycler_view = new User_History_Custom_Adapter_Recycler_View(historyArrayList, new User_History_Custom_Adapter_Recycler_View.ItemClickListener() {
            @Override
            public void onItemClick(History history) {

            }
        });
        rvSearchHistory.setAdapter(user_history_custom_adapter_recycler_view);
    }
}