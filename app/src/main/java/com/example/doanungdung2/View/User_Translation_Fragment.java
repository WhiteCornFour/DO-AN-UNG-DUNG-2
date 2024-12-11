package com.example.doanungdung2.View;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
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
import com.google.android.material.textfield.TextInputLayout;
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
    TextInputLayout layoutInputContent, layoutOutputContent;
    TextInputEditText edtInputContent, edtOutputContent;
    ImageView imgChangeLanguage, imgInputLanguage, imgOutputLanguage;
    GifImageView gifViewTranslateMic, gifCopyWordOutput;
    MaterialButton btnTranslate;
    TextView tvInputLanguage, tvOutputLanguage;
    int fromLanguageCode = 11;
    int toLanguageCode = 57;

    TextToSpeech textToSpeech;

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

        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US); // Mặc định
                }
            }
        });

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
        layoutInputContent = view.findViewById(R.id.layoutInputContent);
        layoutOutputContent = view.findViewById(R.id.layoutOutputContent);
        edtInputContent = view.findViewById(R.id.edtInputContent);
        edtOutputContent = view.findViewById(R.id.edtOutputContent);
        gifViewTranslateMic = view.findViewById(R.id.gifViewTranslateMic);
        gifCopyWordOutput = view.findViewById(R.id.gifCopyWordOutput);
        btnTranslate = view.findViewById(R.id.btnTranslate);
        imgChangeLanguage = view.findViewById(R.id.imgChangeLanguage);
        imgInputLanguage = view.findViewById(R.id.imgInputLanguage);
        imgOutputLanguage = view.findViewById(R.id.imgOutputLanguage);
        tvInputLanguage = view.findViewById(R.id.tvInputLanguage);
        tvOutputLanguage = view.findViewById(R.id.tvOutputLanguage);
    }

    void addEvent() {
        imgChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable inputImageDrawable = imgInputLanguage.getDrawable();
                Drawable outputImageDrawable = imgOutputLanguage.getDrawable();
                imgInputLanguage.setImageDrawable(outputImageDrawable);
                imgOutputLanguage.setImageDrawable(inputImageDrawable);

                String inputLanguageText = tvInputLanguage.getText().toString();
                String outputLanguageText = tvOutputLanguage.getText().toString();
                tvInputLanguage.setText(outputLanguageText);
                tvOutputLanguage.setText(inputLanguageText);

                layoutInputContent.setHint(outputLanguageText);
                layoutOutputContent.setHint(inputLanguageText);

                fromLanguageCode = getLanguageCode(tvInputLanguage.getText().toString());
                toLanguageCode = getLanguageCode(tvOutputLanguage.getText().toString());

                Log.d("LanguageSwitch", "From Code: " + fromLanguageCode + ", To Code: " + toLanguageCode);
            }
        });

        layoutInputContent.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale inputLocale = getLocaleFromLanguageCode(fromLanguageCode);
                speakText(edtInputContent.getText().toString().trim(), inputLocale);
            }
        });

        layoutOutputContent.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale outputLocale = getLocaleFromLanguageCode(toLanguageCode);
                speakText(edtOutputContent.getText().toString().trim(), outputLocale);
            }
        });


        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtOutputContent.setText("");
                if(edtInputContent.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(),"Please enter your text to translate", Toast.LENGTH_SHORT).show();
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

        gifCopyWordOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtOutputContent.getText().toString().isEmpty())
                {
                    ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("Copy: ", edtOutputContent.getText().toString().trim());
                    clipboardManager.setPrimaryClip(clipData);

                    clipData.getDescription();

                    Toast.makeText(getActivity(), "Copy text successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "There is nothing to copy!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void speakText(String text, Locale locale) {
        if (textToSpeech != null) {
            textToSpeech.setLanguage(locale);
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            Log.e("TextToSpeech", "TextToSpeech not initialized");
        }
    }


    private Locale getLocaleFromLanguageCode(int languageCode) {
        switch (languageCode) {
            case 57: //tieng viet
                return new Locale("vi");
            case 11: //tieng anh
                return Locale.US;
            default:
                return Locale.US; // Mặc định là tiếng Anh
        }
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
        edtOutputContent.setText("Downloading model...");

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
                edtOutputContent.setText("Translating...");
                translator.translate(source).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        edtOutputContent.setText(s);
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