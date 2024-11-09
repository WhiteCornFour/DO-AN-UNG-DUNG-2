package com.example.doanungdung2.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import android.widget.EditText;
import android.widget.Toast;

import com.example.doanungdung2.Controller.DictionaryHandler;
import com.example.doanungdung2.Controller.HistoryHandler;
import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.History;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_Dictionary_MainPage_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_Dictionary_MainPage_Fragment extends Fragment {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    RecyclerView rvSearchHistory;
    AutoCompleteTextView autoCompleteSearchTV;
    HistoryHandler historyHandler;
    UserHandler userHandler;
    DictionaryHandler dictionaryHandler;
    ArrayList<Dictionary> dictionaryArrayList = new ArrayList<>();
    ArrayList<Dictionary> filteredDictionaryList = new ArrayList<>();
    ArrayAdapter<String> adapter;
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
        userHandler = new UserHandler(getActivity(), DB_NAME, null, DB_VERSION);
        dictionaryHandler = new DictionaryHandler(getActivity(), DB_NAME, null, DB_VERSION);

        dictionaryArrayList = dictionaryHandler.loadAllDataOfDictionary();
        //set search goi y se hien ra khi chi moi danh 1 tu, mac dinh la 2
        autoCompleteSearchTV.setThreshold(1);

        setupRecyclerView();
        loadAllHistory();
        addEvent();
        return view;
    }

    void addControl(View view) {
        rvSearchHistory = view.findViewById(R.id.rvSearchHistory);
        autoCompleteSearchTV = view.findViewById(R.id.autoCompleteSearchTV);
    }

    void addEvent() {
        autoCompleteSearchTV.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        autoCompleteSearchTV.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                // Lấy gợi ý đầu tiên từ adapter
                if (adapter.getCount() > 0) {
                    String searchText = autoCompleteSearchTV.getText().toString().trim();

                    // Thực hiện tìm kiếm với gợi ý đã chọn
                    Dictionary dictionaryResult = dictionaryHandler.searchDictionary(searchText);

                    if (dictionaryResult != null) {
                        Toast.makeText(getActivity(), "Tu duoc Search " + dictionaryResult.getTuTiengAnh(), Toast.LENGTH_SHORT).show();
                        String maTuVung = dictionaryResult.getMaTuVung();
                        String maLichSu = createAutoHistoryCode("LS");
                        String maNguoiDung = User_Quiz_MainPage_Fragment.getIdMaNguoiDungStatic();

                        //them lich su tim kiem
                        History history = new History(maLichSu, maTuVung, maNguoiDung, null);
                        //xoa lich su cu them lich su moi
                        historyHandler.deleteHistory(maTuVung, maNguoiDung);
                        historyHandler.insertHistory(history);

                        //set up lai danh sach trong recycler view
                        setupRecyclerView();
                        loadAllHistory();

                        Dictionary d = new Dictionary();
                        d = dictionaryHandler.getDetailDictionary(maTuVung);
                        //truyen du lieu qua trang detail dictionary
                        Intent intent = new Intent(getActivity(), User_Dictionary_Details.class);
                        intent.putExtra("dictionary", d);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getActivity(), "No results found for: " + searchText, Toast.LENGTH_SHORT).show();
                    }
                    autoCompleteSearchTV.setText("");
                }
                return true;
            }
            return false;
        });

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        autoCompleteSearchTV.setAdapter(adapter);

        autoCompleteSearchTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString();
                ArrayList<String> suggestions = dictionaryHandler.getSuggestions(keyword);

                // Giới hạn số lượng gợi ý tối đa là 5
                if (suggestions.size() > 5) {
                    suggestions = new ArrayList<>(suggestions.subList(0, 5));
                }

                adapter.clear();
                adapter.addAll(suggestions);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public static String createAutoHistoryCode(String kyTuDau)
    {
        Random random = new Random();
        // Tạo chuỗi số ngẫu nhiên 9 chữ số
        StringBuilder code = new StringBuilder(kyTuDau);
        for (int i = 0; i < 9; i++) {
            int digit = random.nextInt(10); // Tạo số ngẫu nhiên từ 0 đến 9
            code.append(digit);
        }
        return code.toString();
    }

    void loadAllHistory() {
        filteredDictionaryList.clear();
        String maNguoiDungInput = User_Quiz_MainPage_Fragment.getIdMaNguoiDungStatic();
        ArrayList<History> historyList = historyHandler.loadAllDataOfHistory(maNguoiDungInput);
        for (History history : historyList) {
            for (Dictionary dictionary : dictionaryArrayList) {
                if (dictionary.getMaTuVung().equals(history.getMaTuVung())) {
                    filteredDictionaryList.add(dictionary);
                    break;
                }
            }
        }
        Collections.reverse(filteredDictionaryList);
        user_history_custom_adapter_recycler_view.setHistoryList(filteredDictionaryList);
    }


    void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rvSearchHistory.setLayoutManager(layoutManager);
        rvSearchHistory.setItemAnimator(new DefaultItemAnimator());
        user_history_custom_adapter_recycler_view = new User_History_Custom_Adapter_Recycler_View(dictionaryArrayList, new User_History_Custom_Adapter_Recycler_View.ItemClickListener() {
            @Override
            public void onItemClick(Dictionary dictionary) {
                Intent intent = new Intent(getActivity(), User_Dictionary_Details.class);
                intent.putExtra("dictionary", dictionary);
                startActivity(intent);
            }
        });
        rvSearchHistory.setAdapter(user_history_custom_adapter_recycler_view);
    }

}