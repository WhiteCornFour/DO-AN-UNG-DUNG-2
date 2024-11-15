package com.example.doanungdung2.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doanungdung2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_Quiz_Test_Essay_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_Quiz_Test_Essay_Fragment extends Fragment {
    TextView tvNDCH_Essay_Quiz_User;
    EditText edtTLEssay_Quiz_User;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public User_Quiz_Test_Essay_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User_Quiz_Test_Essay_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static User_Quiz_Test_Essay_Fragment newInstance(String param1, String param2) {
        User_Quiz_Test_Essay_Fragment fragment = new User_Quiz_Test_Essay_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_user__quiz__test__essay_, container, false);
        addControl(view);
        addEvent();
        return view;
    }
    void addControl(View view)
    {
        tvNDCH_Essay_Quiz_User = view.findViewById(R.id.tvNDCH_Essay_Quiz_User);
        edtTLEssay_Quiz_User = view.findViewById(R.id.edtTLEssay_Quiz_User);
    }
    void addEvent()
    {

    }
}