package com.example.doanungdung2.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Controller.GrammarHandler;
import com.example.doanungdung2.R;
import com.example.doanungdung2.Model.Grammar;
import com.example.doanungdung2.Model.GrammarCategory;

import java.util.ArrayList;

public class Expandable_Grammar extends ArrayAdapter{
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    GrammarHandler grammarHandler;
    Context context;
    int layoutItem;
    User_Item_CustomAdapter_LV adapter_lv;
    ArrayList<GrammarCategory> grammarCategoryArrayList = new ArrayList<>();
    ArrayList<Grammar> grammarArrayList = new ArrayList<>();
    String maNP = "";
    ItemClickListener itemClickListener;
    public Expandable_Grammar(@NonNull Context context, int layoutItem, @NonNull ArrayList<GrammarCategory> grammarCategoryArrayList, ItemClickListener itemClickListener) {
        super(context, layoutItem, grammarCategoryArrayList);
        this.context = context;
        this.layoutItem = layoutItem;
        this.grammarCategoryArrayList = grammarCategoryArrayList;
        this.itemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GrammarCategory grammarCategory = grammarCategoryArrayList.get(position);
        grammarHandler = new GrammarHandler(context, DB_NAME, null, DB_VERSION);
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(layoutItem, parent, false);
        TextView tvTDBT;
        tvTDBT = convertView.findViewById(R.id.tvTDBT);
        tvTDBT.setText(grammarCategory.getTenDangNguPhap());
        ListView lvNP;
        lvNP = convertView.findViewById(R.id.lvNP);

        lvNP.setDivider(null);
        lvNP.setDividerHeight(0);

        // Vô hiệu hóa cuộn của ListView
        lvNP.setVerticalScrollBarEnabled(false); // Tắt thanh cuộn dọc
        lvNP.setHorizontalScrollBarEnabled(false); // Tắt thanh cuộn ngang

        // Vô hiệu hóa scrolling cache để tiết kiệm bộ nhớ và hiệu suất
        lvNP.setScrollingCacheEnabled(false);

        // Tắt over-scroll (cuộn quá giới hạn)
        lvNP.setOverScrollMode(View.OVER_SCROLL_NEVER);

        ImageView imgExpand_Grammar;
        imgExpand_Grammar = convertView.findViewById(R.id.imgExpand_Grammar);
        grammarArrayList = grammarHandler.searchGrammarByGramCateCode(grammarCategory.getMaDangNguPhap());
        adapter_lv = new User_Item_CustomAdapter_LV(getContext(), R.layout.layout_user_item_custom_adapter_lv,
                grammarArrayList);
        lvNP.setAdapter(adapter_lv);
        //setListViewHeightToWrapContent(lvNP);
        LinearLayout display;
        display = convertView.findViewById(R.id.display);
        if (grammarCategory.isSelected()) {
            display.setVisibility(View.VISIBLE);
            imgExpand_Grammar.setScaleY(1);
        } else {
            display.setVisibility(View.GONE);
            imgExpand_Grammar.setScaleY(-1);
        }
        // Xử lý sự kiện bấm vào item
        convertView.setOnClickListener(v -> {
            toggleSelection(position);

            // Chỉ cập nhật chiều cao nếu item được mở
            if (grammarCategory.isSelected()) {
                setListViewHeightToWrapContent(lvNP);
            }
        });
        lvNP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (grammarArrayList != null && i < grammarArrayList.size()) {
                    Grammar grammar = grammarArrayList.get(i);
                    maNP = grammar.getMaNguPhap();
                    itemClickListener.itemClicked(maNP);
                } else {
                    Log.e("Expandable_Grammar", "Danh sách trống hoặc chỉ số không hợp lệ!");
                }
            }
        });

        notifyDataSetChanged();
        return convertView;
    }
    public void toggleSelection(int position) {
        GrammarCategory selectedGrammar = grammarCategoryArrayList.get(position);
        boolean isSelected = selectedGrammar.isSelected();
        selectedGrammar.setSelected(!isSelected);

        // Bỏ chọn tất cả các item khác
        for (int i = 0; i < grammarCategoryArrayList.size(); i++) {
            if (i != position) {
                grammarCategoryArrayList.get(i).setSelected(false);
            }
        }
        // Cập nhật giao diện
        notifyDataSetChanged();
    }
    public interface ItemClickListener
    {
        void itemClicked(String maNP);
    }
    public void setListViewHeightToWrapContent(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null || listAdapter.getCount() == 0) {
            return;
        }

        int totalHeight = 0;
        View listItem = null;

        // Tạm thời hiển thị ListView nếu nó đang ẩn
        boolean wasGone = (listView.getVisibility() == View.GONE);
        if (wasGone) {
            listView.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < listAdapter.getCount(); i++) {
            listItem = listAdapter.getView(i, listItem, listView);

            if (listItem != null) {
                listItem.measure(
                        View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.UNSPECIFIED
                );
                totalHeight += listItem.getMeasuredHeight();
            }
        }

        int dividerHeight = listView.getDividerHeight() * (listAdapter.getCount() - 1);
        int finalHeight = totalHeight + dividerHeight;

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = finalHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();

        // Khôi phục trạng thái ẩn nếu ListView ban đầu bị ẩn
        if (wasGone) {
            listView.setVisibility(View.GONE);
        }
    }
}
