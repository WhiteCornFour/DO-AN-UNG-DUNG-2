package com.example.doanungdung2.View;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
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

        ImageView imgExpand_Grammar;
        imgExpand_Grammar = convertView.findViewById(R.id.imgExpand_Grammar);

        RecyclerView recyclerViewNP;
        recyclerViewNP = convertView.findViewById(R.id.recyclerViewNP);
        recyclerViewNP.setOverScrollMode(View.OVER_SCROLL_NEVER);

        grammarArrayList = grammarHandler.searchGrammarByGramCateCode(grammarCategory.getMaDangNguPhap());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(parent.getContext());
        recyclerViewNP.setLayoutManager(layoutManager);
        recyclerViewNP.setItemAnimator(new DefaultItemAnimator());
        adapter_lv = new User_Item_CustomAdapter_LV(grammarArrayList, new User_Item_CustomAdapter_LV.ItemClickListener() {
            @Override
            public void itemClicked(Grammar grammar) {
                maNP = grammar.getMaNguPhap();
                itemClickListener.itemClicked(maNP);
            }
        });

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
                setRecyclerViewHeightToWrapContent(recyclerViewNP);
            }
        });
        recyclerViewNP.setAdapter(adapter_lv);
        setRecyclerViewHeightToWrapContent(recyclerViewNP);
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
    public void setRecyclerViewHeightToWrapContent(RecyclerView recyclerView) {
        RecyclerView.Adapter<?> adapter = recyclerView.getAdapter();
        if (adapter == null || adapter.getItemCount() == 0) {
            return;
        }
        int totalHeight = 0;
        View view = null;
        // Tạm thời hiển thị RecyclerView nếu nó đang ẩn
        boolean wasGone = (recyclerView.getVisibility() == View.GONE);
        if (wasGone) {
            recyclerView.setVisibility(View.VISIBLE);
        }
        // Lấy layout manager của RecyclerView
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        // Tính chiều cao các item
        for (int i = 0; i < adapter.getItemCount(); i++) {
            view = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i)).itemView;

            // Đo kích thước của item
            view.measure(
                    View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.UNSPECIFIED
            );

            totalHeight += view.getMeasuredHeight();
        }
        // Tính chiều cao của các dividers (nếu có)
        int dividerHeight = 0;
        RecyclerView.State state = new RecyclerView.State();  // Khởi tạo state
        for (int i = 0; i < recyclerView.getItemDecorationCount(); i++) {
            RecyclerView.ItemDecoration itemDecoration = recyclerView.getItemDecorationAt(i);
            Rect offsets = new Rect();

            // Tính toán offsets cho mỗi item
            for (int j = 0; j < adapter.getItemCount(); j++) {
                itemDecoration.getItemOffsets(offsets, view, recyclerView, state);
                dividerHeight += offsets.top + offsets.bottom; // Tổng chiều cao của các divider
            }
        }
        // Tính chiều cao cuối cùng cho RecyclerView
        int finalHeight = totalHeight + dividerHeight;
        // Thiết lập chiều cao cho RecyclerView
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = finalHeight;
        recyclerView.setLayoutParams(params);
        recyclerView.requestLayout();
        // Khôi phục trạng thái ẩn nếu RecyclerView ban đầu bị ẩn
        if (wasGone) {
            recyclerView.setVisibility(View.GONE);
        }
    }
}
