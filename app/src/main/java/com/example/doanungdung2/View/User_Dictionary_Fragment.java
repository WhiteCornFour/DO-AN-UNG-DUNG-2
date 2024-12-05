package com.example.doanungdung2.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.DictionaryHandler;
import com.example.doanungdung2.Controller.HistoryHandler;
import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.History;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_Dictionary_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_Dictionary_Fragment extends Fragment {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    RecyclerView rvSearchHistory;
    TextView tvTuTiengAnh, tvViDuTiengAnh;
    ImageView imgViewAnhTuVung, imgAudioTuTiengAnh;
    AutoCompleteTextView autoCompleteSearchTV;
    HistoryHandler historyHandler;
    UserHandler userHandler;
    DictionaryHandler dictionaryHandler;
    ArrayList<Dictionary> dictionaryArrayList = new ArrayList<>();
    ArrayList<Dictionary> filteredDictionaryList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    User_History_CustomAdapter_RecyclerView user_history_custom_adapter_recycler_view;
    TextToSpeech textToSpeech;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public User_Dictionary_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User_Dictionary_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static User_Dictionary_Fragment newInstance(String param1, String param2) {
        User_Dictionary_Fragment fragment = new User_Dictionary_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_user__dictionary_, container, false);
        addControl(view);

        historyHandler = new HistoryHandler(getActivity(), DB_NAME, null, DB_VERSION);
        userHandler = new UserHandler(getActivity(), DB_NAME, null, DB_VERSION);
        dictionaryHandler = new DictionaryHandler(getActivity(), DB_NAME, null, DB_VERSION);

        dictionaryArrayList = dictionaryHandler.loadAllDataOfDictionary();
        //set search goi y se hien ra khi chi moi danh 1 tu, mac dinh la 2
        autoCompleteSearchTV.setThreshold(1);

        setupRecyclerView();
        loadAllHistory();
        setUpRandomWord();

        addEvent();
        return view;
    }

    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    void addControl(View view) {
        rvSearchHistory = view.findViewById(R.id.rvSearchHistory);
        autoCompleteSearchTV = view.findViewById(R.id.autoCompleteSearchTV);
        tvViDuTiengAnh = view.findViewById(R.id.tvViDuTiengAnh);
        tvTuTiengAnh = view.findViewById(R.id.tvTuTiengAnh);
        imgViewAnhTuVung = view.findViewById(R.id.imgViewAnhTuVung);
        imgAudioTuTiengAnh = view.findViewById(R.id.imgAudioTuTiengAnh);
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
                        String uaThich = historyHandler.getDictionaryBookmarkStatus(maTuVung, maNguoiDung);
//                        Log.d("uaThich", uaThich);

                        //them lich su tim kiem
                        History history = new History(maLichSu, maTuVung, maNguoiDung, uaThich);
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
        if (maNguoiDungInput == null)
        {
            //lấy dữ liệu từ local lên để load thông tin cho người dùng
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ThongTinKhachHang", Context.MODE_PRIVATE);
            String userName = sharedPreferences.getString("userName", null);
            String passWord = sharedPreferences.getString("passWord", null);
            User user = userHandler.getUserInfo(userName, passWord);
            maNguoiDungInput = user.getMaNguoiDung();
        }
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
        user_history_custom_adapter_recycler_view = new User_History_CustomAdapter_RecyclerView(dictionaryArrayList, new User_History_CustomAdapter_RecyclerView.ItemClickListener() {
            @Override
            public void onItemClick(Dictionary dictionary) {
                Intent intent = new Intent(getActivity(), User_Dictionary_Details.class);
                intent.putExtra("dictionary", dictionary);
                startActivity(intent);
            }
        });
        rvSearchHistory.setAdapter(user_history_custom_adapter_recycler_view);
    }

    private void speakText(String text) {
        if (textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            //TextToSpeech.QUEUE_FLUSH:Cách thức xử lý hàng đợi của các lệnh phát âm.
            //QUEUE_FLUSH có nghĩa là xóa hết các văn bản đang chờ trong hàng đợi và phát âm văn bản mới ngay lập tức.
        }
    }

    String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    void setUpRandomWord() {
        if (!dictionaryArrayList.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(dictionaryArrayList.size());
            Dictionary randomWord = dictionaryArrayList.get(randomIndex);

            String formattedWord = capitalizeFirstLetter(randomWord.getTuTiengAnh());
            tvTuTiengAnh.setText(formattedWord);
            tvViDuTiengAnh.setText("Exp: " + randomWord.getViDu());
            byte[] anhNguoiDung = randomWord.getAnhTuVung();
            if (anhNguoiDung == null || anhNguoiDung.length == 0) {
                imgViewAnhTuVung.setImageResource(R.drawable.no_img);
            } else {
                Bitmap bitmap = BitmapFactory.decodeByteArray(anhNguoiDung, 0, anhNguoiDung.length);
                imgViewAnhTuVung.setImageBitmap(bitmap);
            }
            textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        textToSpeech.setLanguage(Locale.US); // ngon ngu chon la US
                    }
                }
            });

            imgAudioTuTiengAnh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    speakText(randomWord.getTuTiengAnh());
                }
            });


        } else {
            Toast.makeText(getActivity(), "Try again something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

}