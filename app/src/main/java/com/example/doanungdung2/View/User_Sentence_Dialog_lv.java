package com.example.doanungdung2.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Model.SampleSentence;
import com.example.doanungdung2.Model.TopicSentence;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class User_Sentence_Dialog_lv extends ArrayAdapter {

    Context context;
    int layoutItem;
    ArrayList<SampleSentence> sampleSentencesArrayList = new ArrayList<>();

        public User_Sentence_Dialog_lv (@NonNull Context context, int layoutItem,
                                         @NonNull ArrayList<SampleSentence> sampleSentencesArrayList) {
        super(context, layoutItem, sampleSentencesArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.sampleSentencesArrayList = sampleSentencesArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SampleSentence sampleSentence = sampleSentencesArrayList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutItem, parent, false);
        }

        TextView tvMauCau = convertView.findViewById(R.id.tvSp_MC);
        TextView tvPhienDich = convertView.findViewById(R.id.tvSp_PD);
        TextView tvTinhHuongSuDung = convertView.findViewById(R.id.tvTHSD_Us);

        tvMauCau.setText(sampleSentence.getMauCau());
        tvPhienDich.setText(sampleSentence.getPhienDich());
        tvTinhHuongSuDung.setText(sampleSentence.getTinhHuongSuDung());




        LinearLayout lnSupport = convertView.findViewById(R.id.supportDia);
        if (sampleSentence.isSelected()) {
            lnSupport.setVisibility(View.VISIBLE);
        } else {
            lnSupport.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void toggleSelection(int position) {
        SampleSentence selectedSentence = sampleSentencesArrayList.get(position);

//         Nếu item được chọn, thay đổi trạng thái của nó
        boolean isSelected = selectedSentence.isSelected();
        selectedSentence.setSelected(!isSelected); // Đảo ngược trạng thái chọn

        // Ẩn phần support của tất cả các item khác
        for (int i = 0; i < sampleSentencesArrayList.size(); i++) {
            if (i != position) {
                sampleSentencesArrayList.get(i).setSelected(false); // Đặt lại trạng thái không chọn cho các item khác
            }
        }
        notifyDataSetChanged();
    }
}

