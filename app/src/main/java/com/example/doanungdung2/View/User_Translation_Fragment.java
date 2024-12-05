package com.example.doanungdung2.View;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.util.ArrayList;
import java.util.Locale;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_Translation_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_Translation_Fragment extends Fragment {

    private static int REQUEST_PERMISSION_CODE = 1;
    Spinner spinnerFromCountry, spinnerToCountry;
    TextInputEditText edtInputContent;
    GifImageView gifViewTranslateMic;
    MaterialButton btnTranslate;
    TextView tvTextTranslate;
    ArrayAdapter<String> fromAdapter;
    ArrayAdapter<String> toAdapter;
    String[] fromLanguages = {"From", "English", "Vietnamese"};
    String[] toLanguages = {"To", "English", "Vietnamese"};
    int languageCode, fromLanguageCode, toLanguageCode = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public User_Translation_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User_Traslation_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static User_Translation_Fragment newInstance(String param1, String param2) {
        User_Translation_Fragment fragment = new User_Translation_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_user__translation_, container, false);
        addControl(view);
        fromAdapter = new ArrayAdapter<>(getActivity(), R.layout.user_translation_fragment_custom_adapter_spinner, fromLanguages);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromCountry.setAdapter(fromAdapter);

        toAdapter = new ArrayAdapter<>(getActivity(), R.layout.user_translation_fragment_custom_adapter_spinner, toLanguages);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerToCountry.setAdapter(toAdapter);

        addEvent();
        return view;
    }

    void addControl(View view) {
        spinnerFromCountry = view.findViewById(R.id.spinnerFromCountry);
        spinnerToCountry = view.findViewById(R.id.spinnerToCountry);
        edtInputContent = view.findViewById(R.id.edtInputContent);
        gifViewTranslateMic = view.findViewById(R.id.gifViewTranslateMic);
        btnTranslate = view.findViewById(R.id.btnTranslate);
        tvTextTranslate = view.findViewById(R.id.tvTextTranslate);
    }

    void addEvent() {
        spinnerFromCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fromLanguageCode = getLanguageCode(fromLanguages[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerToCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toLanguageCode = getLanguageCode(toLanguages[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTextTranslate.setText("");
                if(edtInputContent.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(),"Please enter your text to translate", Toast.LENGTH_SHORT).show();
                    return;
                } else if (fromLanguageCode == 0){
                    Toast.makeText(getActivity(),"Please select source language", Toast.LENGTH_SHORT).show();
                    return;
                } else if (toLanguageCode == 0) {
                    Toast.makeText(getActivity(),"Please select language to make translation", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    translateText(fromLanguageCode, toLanguageCode, edtInputContent.getText().toString());
                }
            }
        });



        gifViewTranslateMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra và yêu cầu quyền nếu chưa được cấp để mở microphone
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
                } else {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to convert into text");
                    try {
                        startActivityForResult(intent, REQUEST_PERMISSION_CODE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), ""+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                edtInputContent.setText(result.get(0));
            } else {
                Toast.makeText(getActivity(), "Không nhận diện được giọng nói. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Không thể nhận diện giọng nói. Kiểm tra micro hoặc quyền ứng dụng!", Toast.LENGTH_SHORT).show();
        }
    }

    //xử lý khi nếu câps quyền thì sao mà nếu không thì sao
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission granted. You can now use microphone!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permission denied. You cannot use microphone!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public int getLanguageCode(String language) {
        int languageCode = 0;
        switch (language) {
            case "English":
                languageCode = FirebaseTranslateLanguage.EN;
                break;
            case "Vietnamese":
                languageCode = FirebaseTranslateLanguage.VI;
                break;
            default:
                languageCode = 0;
        }
        return languageCode;
    }

    private void translateText(int fromLanguageCode, int toLanguageCode, String source) {
        tvTextTranslate.setText("Downloading model...");

        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(fromLanguageCode)
                .setTargetLanguage(toLanguageCode)
                .build();

        FirebaseTranslator translator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

//        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().build();
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi() // Chỉ tải qua Wi-Fi
                .build();

        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                tvTextTranslate.setText("Translating...");
                translator.translate(source).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        tvTextTranslate.setText(s);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Fail to translate. Try again later !!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Fail to download language model: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}