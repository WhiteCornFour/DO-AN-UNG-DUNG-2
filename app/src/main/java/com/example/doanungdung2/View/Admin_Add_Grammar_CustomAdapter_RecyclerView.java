package com.example.doanungdung2.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Model.Grammar;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Add_Grammar_CustomAdapter_RecyclerView extends
        RecyclerView.Adapter<Admin_Add_Grammar_CustomAdapter_RecyclerView.MyViewHolder> {
        private ArrayList<Grammar> grammarArrayList = new ArrayList<>();
        private ItemClickListener itemClickListener;

        public Admin_Add_Grammar_CustomAdapter_RecyclerView(ArrayList<Grammar> grammarArrayList, ItemClickListener itemClickListener)
        {
                this.grammarArrayList = grammarArrayList;
                this.itemClickListener = itemClickListener;
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.layout_admin_add_grammar_customadapter_recyclerview,
                        parent, false
                );
                return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                Grammar grammar = grammarArrayList.get(position);
                holder.tvTenNP_Add.setText(grammar.getTenNguPhap());
                holder.tvMaDNP_Add.setText(grammar.getMaDangNguPhap());
                holder.tvNoiDung_Add.setText(grammar.getNoiDung());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                itemClickListener.onItemClick(grammar);
                        }
                });
        }

        @Override
        public int getItemCount() {
                return grammarArrayList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {
                TextView tvTenNP_Add, tvMaDNP_Add, tvNoiDung_Add;
                public MyViewHolder(@NonNull View itemView) {
                        super(itemView);
                        tvTenNP_Add = itemView.findViewById(R.id.tvTenNP_Add);
                        tvMaDNP_Add = itemView.findViewById(R.id.tvMaDNP_Add);
                        tvNoiDung_Add = itemView.findViewById(R.id.tvNoiDung_Add);
                }
        }
        public interface ItemClickListener
        {
                void onItemClick(Grammar grammar);
        }
        public void setGrammarArrayList(ArrayList<Grammar> grammars)
        {
                this.grammarArrayList = grammars;
                notifyDataSetChanged();
        }
}
