package com.example.doanungdung2.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.doanungdung2.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Model.Grammar;

import java.util.ArrayList;

public class User_Item_CustomAdapter_LV extends RecyclerView.Adapter<User_Item_CustomAdapter_LV.MyViewHolder> {
    ArrayList<Grammar> grammarArrayList = new ArrayList<>();
    ItemClickListener itemClickListener;

    public User_Item_CustomAdapter_LV(ArrayList<Grammar> grammarArrayList, ItemClickListener itemClickListener) {
        this.grammarArrayList = grammarArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_item_custom_adapter_lv,
                parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Grammar grammar = grammarArrayList.get(position);
        holder.tvTNP.setText(grammar.getTenNguPhap());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.itemClicked(grammar);
            }
        });
    }
    @Override
    public int getItemCount() {
        return grammarArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTNP;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTNP = itemView.findViewById(R.id.tvTNP);
        }
    }
    public interface ItemClickListener
    {
        void itemClicked(Grammar grammar);
    }
}
