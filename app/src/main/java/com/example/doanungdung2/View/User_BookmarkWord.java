package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.doanungdung2.Controller.DictionaryHandler;
import com.example.doanungdung2.Controller.HistoryHandler;
import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.Model.History;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;

import java.util.ArrayList;
import java.util.Collections;

public class User_BookmarkWord extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView imgBackToSettingsFromBookmark;
    RecyclerView rvBookMarkWord;
    User user = new User();
    ArrayList<History> historyArrayList= new ArrayList<>();
    ArrayList<Dictionary> dictionaryArrayList = new ArrayList<>();
    ArrayList<Dictionary> filteredDictionaryList = new ArrayList<>();
    HistoryHandler historyHandler;
    DictionaryHandler dictionaryHandler;
    User_BookmarkWord_CustomAdapter_RecyclerView user_bookmarkWord_customAdapter_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bookmark_word);
        addControl();
        historyHandler = new HistoryHandler(User_BookmarkWord.this, DB_NAME, null, DB_VERSION);
        dictionaryHandler = new DictionaryHandler(User_BookmarkWord.this, DB_NAME, null, DB_VERSION);
        user = getUserFromIntent();
        dictionaryArrayList = dictionaryHandler.loadAllDataOfDictionary();

        setupRecyclerView();
        loadAllBookmark();
        addEvent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    void addControl() {
        imgBackToSettingsFromBookmark = findViewById(R.id.imgBackToSettingsFromBookmark);
        rvBookMarkWord = findViewById(R.id.rvBookMarkWord);
    }

    void addEvent() {
        imgBackToSettingsFromBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_BookmarkWord.this, User_Profile.class);
                intent.putExtra("userBackFromPageToProfile", user);
                startActivity(intent);
                finish();
            }
        });
    }

    private User getUserFromIntent() {
        Intent intent = getIntent();
        User userIntent = (User) intent.getSerializableExtra("userFromProfileToBookmarkWord");
        return userIntent;
    }

    void loadAllBookmark() {
        filteredDictionaryList.clear();
        String maNguoiDungInput = user.getMaNguoiDung();
        ArrayList<History> historyList = historyHandler.loadAllDataOfBookmark(maNguoiDungInput);
        for (History history : historyList) {
            for (Dictionary dictionary : dictionaryArrayList) {
                if (dictionary.getMaTuVung().equals(history.getMaTuVung())) {
                    filteredDictionaryList.add(dictionary);
                    break;
                }
            }
        }
        Collections.reverse(filteredDictionaryList);
        user_bookmarkWord_customAdapter_recyclerView.setBookmarkList(filteredDictionaryList);
    }

    void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(User_BookmarkWord.this, RecyclerView.VERTICAL, false);
        rvBookMarkWord.setLayoutManager(layoutManager);
        rvBookMarkWord.setItemAnimator(new DefaultItemAnimator());
        user_bookmarkWord_customAdapter_recyclerView = new User_BookmarkWord_CustomAdapter_RecyclerView(filteredDictionaryList, new User_BookmarkWord_CustomAdapter_RecyclerView.ItemClickListener() {
            @Override
            public void onItemClick(Dictionary dictionary) {
                Intent intent = new Intent(User_BookmarkWord.this, User_Dictionary_Details.class);
                intent.putExtra("dictionary", dictionary);
                startActivity(intent);
                finish();
            }
        });
        rvBookMarkWord.setAdapter(user_bookmarkWord_customAdapter_recyclerView);
    }
}