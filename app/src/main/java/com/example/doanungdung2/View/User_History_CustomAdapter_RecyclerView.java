package com.example.doanungdung2.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.R;

import java.util.ArrayList;
import java.util.Locale;

public class User_History_CustomAdapter_RecyclerView extends RecyclerView.Adapter<User_History_CustomAdapter_RecyclerView.MyViewHolder> {

    ArrayList<Dictionary> dictionaryArrayList = new ArrayList<>();
    ItemClickListener itemClickListener;
    TextToSpeech textToSpeech;

    public User_History_CustomAdapter_RecyclerView(ArrayList<Dictionary> dictionaryArrayList, ItemClickListener itemClickListener) {
        this.dictionaryArrayList = dictionaryArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_search_history_customadapter_list_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Dictionary dictionary = dictionaryArrayList.get(position);

        holder.tvTuTiengAnhLoaiTuDictionary_CustomRecyclerView.setText(dictionary.getTuTiengAnh());
        holder.tvPhatAm_CustomRecyclerView.setText(dictionary.getCachPhatAm());

        byte[] imageBytes = dictionary.getAnhTuVung();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imgAnhTuVung_CustomAdapter.setImageBitmap(bitmap);
        } else {
            holder.imgAnhTuVung_CustomAdapter.setImageResource(R.drawable.no_img);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() != R.id.imgAudio_CustomAdapter) {
                    itemClickListener.onItemClick(dictionary); // Pass the clicked item
                }
            }
        });

        if (textToSpeech == null) {
            textToSpeech = new TextToSpeech(holder.itemView.getContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        textToSpeech.setLanguage(Locale.US);
                    }
                }
            });
        }

        holder.imgAudio_CustomAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakText(dictionary.getTuTiengAnh()); // Pass the word to be spoken
            }
        });
    }


    @Override
    public int getItemCount() {
        return dictionaryArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTuTiengAnhLoaiTuDictionary_CustomRecyclerView, tvPhatAm_CustomRecyclerView;
        ImageView imgAnhTuVung_CustomAdapter, imgAudio_CustomAdapter;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTuTiengAnhLoaiTuDictionary_CustomRecyclerView = itemView.findViewById(R.id.tvTuTiengAnhLoaiTuDictionary_CustomRecyclerView);
            tvPhatAm_CustomRecyclerView = itemView.findViewById(R.id.tvPhatAm_CustomRecyclerView);
            imgAnhTuVung_CustomAdapter = itemView.findViewById(R.id.imgAnhTuVung_CustomAdapter);
            imgAudio_CustomAdapter = itemView.findViewById(R.id.imgAudio_CustomAdapter);
        }
    }

    // Interface for click listener
    public interface ItemClickListener {
        void onItemClick(Dictionary dictionary);
    }

    public void setHistoryList(ArrayList<Dictionary> newList) {
        this.dictionaryArrayList = newList;
        notifyDataSetChanged(); // Notify adapter of data change
    }

    private void speakText(String text) {
        if (textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            //TextToSpeech.QUEUE_FLUSH:Cách thức xử lý hàng đợi của các lệnh phát âm.
            //QUEUE_FLUSH có nghĩa là xóa hết các văn bản đang chờ trong hàng đợi và phát âm văn bản mới ngay lập tức.
        }
    }
}
