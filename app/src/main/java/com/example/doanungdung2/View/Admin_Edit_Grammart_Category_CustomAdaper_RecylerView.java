package com.example.doanungdung2.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Model.ExercisesCategory;
import com.example.doanungdung2.Model.GrammarCategory;
import com.example.doanungdung2.R;

import java.util.ArrayList;

public class Admin_Edit_Grammart_Category_CustomAdaper_RecylerView extends
        RecyclerView.Adapter<Admin_Edit_Grammart_Category_CustomAdaper_RecylerView.MyViewHolder> {

    private ArrayList<GrammarCategory> grammarCategoryArrayList = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public Admin_Edit_Grammart_Category_CustomAdaper_RecylerView(ArrayList<GrammarCategory> grammarCategoryArrayList, ItemClickListener itemClickListener) {
        this.grammarCategoryArrayList = grammarCategoryArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_admin_edit_grammarcategory_customadapter_recylerview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GrammarCategory grammarCategory = grammarCategoryArrayList.get(position);
        holder.tvMaDNP.setText(grammarCategory.getMaDangNguPhap());
        holder.tvTenDNP.setText(grammarCategory.getTenDangNguPhap());
        holder.tvMoTaDNP.setText(grammarCategory.getMoTa());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(grammarCategory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return grammarCategoryArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTenDNP, tvMaDNP, tvMoTaDNP;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvTenDNP = itemView.findViewById(R.id.tvTenDNP);
            tvMaDNP = itemView.findViewById(R.id.tvMaDNP);
            tvMoTaDNP = itemView.findViewById(R.id.tvMoTaDNP);
        }
    }
    public void setGrammarCategoryArrayList(ArrayList<GrammarCategory> newList) {
        this.grammarCategoryArrayList = newList;
        notifyDataSetChanged(); // Notify adapter of data change
    }
    public interface ItemClickListener {
        void onItemClick(GrammarCategory grammarCategory);
    }
}
