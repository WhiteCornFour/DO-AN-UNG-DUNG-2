package com.example.doanungdung2.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanungdung2.Model.TopicSentence;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class User_Sentence_CustomAdapter_LV extends ArrayAdapter {

    Context context;
    int layoutItem;
    ArrayList<TopicSentence> topicSentenceArrayList;

    public User_Sentence_CustomAdapter_LV(@NonNull Context context, int layoutItem,
                                                         @NonNull ArrayList<TopicSentence> topicSentenceArrayList) {
        super(context, layoutItem, topicSentenceArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.topicSentenceArrayList = topicSentenceArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TopicSentence topicSentence = topicSentenceArrayList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutItem, parent, false);
        }


        TextView tvCDMC_US= convertView.findViewById(R.id.tvCDMC_Us);
        tvCDMC_US.setText(topicSentence.getTenChuDeMauCau());

        TextView tvMoTaCDMC_US = convertView.findViewById(R.id.tvMoTaCDMC_Us);
        tvMoTaCDMC_US.setText(topicSentence.getMoTa());

        ImageView imgAnhCDMC_Us = convertView.findViewById(R.id.imgAnhCDMC_Us);

        if (topicSentence.getAnhChuDeMauCau() != null) {
            byte[] imageBytes = topicSentence.getAnhChuDeMauCau();
            Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imgAnhCDMC_Us.setImageBitmap(image);
        }

        return convertView;
    }
}
