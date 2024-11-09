package com.example.doanungdung2.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ListAdapter;
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
    User_Item_CustomAdapter_LV adapter;
    private ItemClickListener itemCLickListener;
    private String maNP = "";
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
                //lấy item đang được chọn
                itemCLickListener.onItemClick(grammarCategory, maNP);
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
                    setListViewHeightBasedOnChildren(holder.lvNP);
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
        //lấy grammar code đang chọn để truyền về main page qua interface itemCLickListener.onItemClick(grammarCategory, maNP);
        holder.lvNP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Grammar grammar = grammarArrayList.get(i);
                maNP = grammar.getMaNguPhap();
                itemCLickListener.onItemClick(grammarCategory, maNP);  // Gọi khi maNP đã được cập nhật
                //Log.d("maNP", maNP);
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
    @SuppressLint("NotifyDataSetChanged")
    public void setGrammarCategoryAL(ArrayList<GrammarCategory> newList) {
        this.grammarCategoryArrayList = newList;
        notifyDataSetChanged(); // Notify adapter of data change
    }
    public interface ItemClickListener {
        void onItemClick(GrammarCategory grammarCategory, String maNP);
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // Lấy adapter hiện tại của ListView (dữ liệu sẽ được hiển thị qua adapter)
        ListAdapter listAdapter = listView.getAdapter();

        // Nếu ListView chưa có dữ liệu (adapter là null), thoát phương thức
        if (listAdapter == null) {
            return;
        }

        // Khởi tạo biến để tính tổng chiều cao của tất cả các item trong ListView
        int totalHeight = 0;

        // Lặp qua từng item trong adapter để tính chiều cao của nó
        for (int i = 0; i < listAdapter.getCount(); i++) {
            // Lấy item view tại vị trí 'i' trong adapter
            View listItem = listAdapter.getView(i, null, listView);

            // Đo kích thước của item (width và height)
            listItem.measure(0, 0);

            // Cộng chiều cao của item vào totalHeight
            totalHeight += listItem.getMeasuredHeight();
        }

        // Lấy layout params hiện tại của ListView
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        // Thiết lập chiều cao mới cho ListView bằng tổng chiều cao của tất cả các item
        // và thêm vào chiều cao của các divider (nếu có) giữa các item
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        // Áp dụng layout params mới cho ListView để thay đổi kích thước
        listView.setLayoutParams(params);

        // Yêu cầu ListView sắp xếp lại layout dựa trên kích thước mới
        listView.requestLayout();
    }
}
