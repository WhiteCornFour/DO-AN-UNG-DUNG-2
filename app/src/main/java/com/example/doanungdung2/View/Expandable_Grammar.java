package com.example.doanungdung2.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Controller.GrammarHandler;
import com.example.doanungdung2.R;
import com.example.doanungdung2.Model.Grammar;
import com.example.doanungdung2.Model.GrammarCategory;

import java.util.ArrayList;

public class Expandable_Grammar extends RecyclerView.Adapter<Expandable_Grammar.MyViewHolder> {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ArrayList<GrammarCategory> grammarCategoryArrayList = new ArrayList<>();
    ArrayList<Grammar> grammarArrayList = new ArrayList<>();
    GrammarHandler grammarHandler;
    int layoutItem;
    Context context;
    User_Item_CustomAdapter_LV adapter;
    private ItemClickListener itemCLickListener;
    private int expandedPosition = -1;

    public Expandable_Grammar(ArrayList<GrammarCategory> grammarCategoryArrayList, ItemClickListener itemCLickListener) {
        this.grammarCategoryArrayList = grammarCategoryArrayList;
        this.itemCLickListener = itemCLickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_expandble_grammar, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        GrammarCategory grammarCategory = grammarCategoryArrayList.get(position);
        holder.tvTDBT.setText(grammarCategory.getTenDangNguPhap());
        // Kiểm tra nếu item hiện tại là item được mở
        if (position == expandedPosition)
        {
            holder.display.setVisibility(View.VISIBLE);
        }else
        {
            holder.display.setVisibility(View.GONE);
        }
        holder.itemClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Position", String.valueOf(position));
                //lấy item đang được chọn
                itemCLickListener.onItemClick(grammarCategory);
                //gọi handler cho grammar
                grammarHandler = new GrammarHandler(view.getContext(),
                        DB_NAME, null, DB_VERSION);
                //load grammar có mã dạng ngữ pháp trùng với grammarCate đang được chọn
                grammarArrayList = grammarHandler.searchByCodeOrNameGrammar(grammarCategory.getMaDangNguPhap());
                //set up listview
                if (holder.display.getVisibility() == View.GONE)
                {
                    //list đang ẩn thì hiện ra và load data cho list view
                    TransitionManager.beginDelayedTransition(holder.layoutMother, new AutoTransition());
                    holder.display.setVisibility(View.VISIBLE);
                    grammarArrayList = grammarHandler.searchByCodeOrNameGrammar(grammarCategory.getMaDangNguPhap());
                    //custom cho list view
                    adapter = new User_Item_CustomAdapter_LV(view.getContext(), R.layout.layout_user_item_custom_adapter_lv,
                            grammarArrayList);
                    holder.lvNP.setAdapter(adapter);
                    expandedPosition = position;
                }else {
                    expandedPosition = -1;
                    //Người dùng ấn lại 1 lần nữa vào item thì sẽ ẩn list view
                    TransitionManager.beginDelayedTransition(holder.layoutMother, new AutoTransition());
                    holder.display.setVisibility(View.GONE);
                }
                notifyDataSetChanged(); // Cập nhật toàn bộ view để áp dụng thay đổi
            }
        });
    }
    @Override
    public int getItemCount() {
        return grammarCategoryArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout layoutMother, display;
        TextView tvTDBT;
        ListView lvNP;
        RelativeLayout itemClicked;
        public MyViewHolder (@NonNull View itemView)
        {
            super(itemView);
            tvTDBT = itemView.findViewById(R.id.tvTDBT);
            lvNP = itemView.findViewById(R.id.lvNP);
            lvNP.setDivider(null);
            lvNP.setDividerHeight(0);
            layoutMother = itemView.findViewById(R.id.layoutMother);
            display = itemView.findViewById(R.id.display);
            itemClicked = itemView.findViewById(R.id.itemClicked);
        }
    }
    public void setGrammarCategoryAL(ArrayList<GrammarCategory> newList) {
        this.grammarCategoryArrayList = newList;
        notifyDataSetChanged(); // Notify adapter of data change
    }
    public interface ItemClickListener {
        void onItemClick(GrammarCategory grammarCategory);
    }
}
