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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.doanungdung2.Controller.UserHandler;
import com.example.doanungdung2.Model.User;
import com.example.doanungdung2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_Quiz_MainPage_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_Quiz_MainPage_Fragment extends Fragment {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    TextView tvUserNameQuiz, tvWelcomeText;
    LinearLayout beginnerButton, starterButton, intermediateButton ,proficientButton ,masterButton;
    UserHandler userHandler;
    User user;
  
    public static String idMaNguoiDungStatic;
    String tk = "";
    String mk = "";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public User_Quiz_MainPage_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Quiz.
     */
    // TODO: Rename and change types and number of parameters
    public static User_Quiz_MainPage_Fragment newInstance(String param1, String param2) {
        User_Quiz_MainPage_Fragment fragment = new User_Quiz_MainPage_Fragment();
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
        View view = inflater.inflate(R.layout.fragment__quiz, container, false);
        addControl(view);
        userHandler = new UserHandler(getActivity(), DB_NAME, null, DB_VERSION);
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.setFragmentResultListener("userResult", this, new FragmentResultListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                user = (User) result.getSerializable("user");
                idMaNguoiDungStatic = user.getMaNguoiDung();
                tvUserNameQuiz.setText(user.getTenNguoiDung());
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("tk", user.getTaiKhoan());
                editor.putString("mk", user.getMatKhau());
                editor.apply();
            }
        });
        setRandomWelcomeMessage();
        addEvent();
        return view;
    }

    public static String getIdMaNguoiDungStatic() {
        return idMaNguoiDungStatic;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        tk = sharedPreferences.getString("tk", null);
        mk =  sharedPreferences.getString("mk", null);
        if (tk == null || mk == null)
        {
            Log.d("Tk && MK", tk + mk);
        }else {
            user = new User();
            user = userHandler.getUserInfo(tk, mk);
            tvUserNameQuiz.setText(user.getTenNguoiDung());
        }
    }

    void addControl(View view) {
        tvWelcomeText = view.findViewById(R.id.tvWelcomeText);
        tvUserNameQuiz = view.findViewById(R.id.tvUserNameQuiz);
        beginnerButton = view.findViewById(R.id.beginnerButton);
        starterButton = view.findViewById(R.id.starterButton);
        intermediateButton = view.findViewById(R.id.intermediateButton);
        proficientButton = view.findViewById(R.id.proficientButton);
        masterButton = view.findViewById(R.id.masterButton);
    }
    void addEvent()
    {
        beginnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), User_Quiz_List.class);
                intent.putExtra("mucDo", "Beginner");
                startActivity(intent);
            }
        });

        starterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), User_Quiz_List.class);
                intent.putExtra("mucDo", "Starter");
                startActivity(intent);
            }
        });

        intermediateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), User_Quiz_List.class);
                intent.putExtra("mucDo", "Intermediate");
                startActivity(intent);
            }
        });

        proficientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), User_Quiz_List.class);
                intent.putExtra("mucDo", "Proficient");
                startActivity(intent);
            }
        });

        masterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), User_Quiz_List.class);
                intent.putExtra("mucDo", "Master");
                startActivity(intent);
            }
        });
    }

    private void setRandomWelcomeMessage() {
        List<String> welcomeMessages = new ArrayList<>();
        welcomeMessages.add("It's a great day to start learning English!");
        welcomeMessages.add("Let’s make today the best day to learn English!");
        welcomeMessages.add("Every day is a new opportunity to learn English!");
        welcomeMessages.add("It’s a beautiful day to improve your English skills!");
        welcomeMessages.add("Learning English is fun – let’s enjoy today’s lesson!");
        welcomeMessages.add("Today is a perfect day to practice English!");
        welcomeMessages.add("Let’s take another step toward mastering English today!");
        welcomeMessages.add("A bright day ahead for improving your English!");
        welcomeMessages.add("Time to learn and grow – let’s start with English!");
        welcomeMessages.add("Today is another chance to speak English confidently!");

        Random random = new Random();
        int randomIndex = random.nextInt(welcomeMessages.size());

        tvWelcomeText.setText(welcomeMessages.get(randomIndex));
    }
}