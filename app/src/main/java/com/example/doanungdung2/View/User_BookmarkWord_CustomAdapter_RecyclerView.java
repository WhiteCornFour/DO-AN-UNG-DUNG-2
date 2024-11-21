package com.example.doanungdung2.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class User_BookmarkWord_CustomAdapter_RecyclerView extends RecyclerView.Adapter<User_BookmarkWord_CustomAdapter_RecyclerView.MyViewHolder>{

    ArrayList<Dictionary> dictionaryArrayList = new ArrayList<>();
    ItemClickListener itemClickListener;

    public User_BookmarkWord_CustomAdapter_RecyclerView(ArrayList<Dictionary> dictionaryArrayList, ItemClickListener itemClickListener) {
        this.dictionaryArrayList = dictionaryArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_bookmarkwords_custom_adapter_recyclerview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Dictionary dictionary = dictionaryArrayList.get(position);
        byte[] anhTuVung = dictionary.getAnhTuVung();
        if (anhTuVung != null && anhTuVung.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(anhTuVung, 0, anhTuVung.length);
            holder.imgAnhTuVungBMW.setImageBitmap(bitmap);
        } else {
            holder.imgAnhTuVungBMW.setImageResource(R.drawable.logo);
        }
        holder.tvTuVungBMW.setText(dictionary.getTuTiengAnh());
        holder.tvLoaiTuBMW.setText(dictionary.getLoaiTu());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(dictionary);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dictionaryArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAnhTuVungBMW;
        TextView tvTuVungBMW, tvLoaiTuBMW;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhTuVungBMW = itemView.findViewById(R.id.imgAnhTuVungBMW);
            tvTuVungBMW = itemView.findViewById(R.id.tvTuVungBMW);
            tvLoaiTuBMW = itemView.findViewById(R.id.tvLoaiTuBMW);
        }
    }

    public interface ItemClickListener {
        void onItemClick(Dictionary dictionary);
    }

    public void setBookmarkList(ArrayList<Dictionary> newList) {
        this.dictionaryArrayList = newList;
        notifyDataSetChanged(); // Notify adapter of data change
    }

}
