package com.example.doanungdung2.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.GrammarCategoryHandler;
import com.example.doanungdung2.Controller.GrammarHandler;
import com.example.doanungdung2.Model.Grammar;
import com.example.doanungdung2.Model.GrammarCategory;
import com.example.doanungdung2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_Grammar_MainPage_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_Grammar_MainPage_Fragment extends Fragment {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    GrammarCategoryHandler grammarCategoryHandler;
    GrammarHandler grammarHandler;
    ArrayList<GrammarCategory> grammarCategoryArrayList = new ArrayList<>();
    ArrayList<Grammar> grammarArrayList = new ArrayList<>();
    ListView lvGrammar_User;
    Expandable_Grammar expandable_grammar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public User_Grammar_MainPage_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Grammar.
     */
    // TODO: Rename and change types and number of parameters
    public static User_Grammar_MainPage_Fragment newInstance(String param1, String param2) {
        User_Grammar_MainPage_Fragment fragment = new User_Grammar_MainPage_Fragment();
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
        View view = inflater.inflate(R.layout.fragment__grammar, container, false);
        addControl(view);
        grammarCategoryHandler = new GrammarCategoryHandler(getActivity(), DB_NAME, null, DB_VERSION);
        grammarHandler = new GrammarHandler(getActivity(), DB_NAME, null, DB_VERSION);
        setUpDataLV();
        addEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpDataLV();
    }

    void addControl(View view)
    {
        lvGrammar_User = view.findViewById(R.id.lvGrammar_User);
    }
    void addEvent()
    {

    }
    void setUpDataLV()
    {
        grammarCategoryArrayList = grammarCategoryHandler.loadAllDataGrammarCategory();
        expandable_grammar = new Expandable_Grammar(getActivity(), R.layout.layout_expandble_grammar,
                grammarCategoryArrayList, new Expandable_Grammar.ItemClickListener() {
            @Override
            public void itemClicked(String maNP) {
                //Log.d("maNP mainpage: ", maNP);
                if (maNP.isEmpty() || maNP == null)
                {
                    Toast.makeText(getActivity(), "Null Grammar Code!", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getActivity(), User_Grammar_Details.class);
                    intent.putExtra("maNP", maNP);
                    startActivity(intent);
                }
            }
        });
        lvGrammar_User.setDivider(null);
        lvGrammar_User.setDividerHeight(0);
        lvGrammar_User.setAdapter(expandable_grammar);
    }
}