package com.example.doanungdung2.View;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanungdung2.Controller.QuestionHandler;
import com.example.doanungdung2.Model.AssigmentDetail;
import com.example.doanungdung2.Model.Question;
import com.example.doanungdung2.R;

import java.util.ArrayList;
import java.util.Objects;

public class User_Test_Details_CustomAdapter_RecylerView extends
        RecyclerView.Adapter<User_Test_Details_CustomAdapter_RecylerView.MyViewHolder> {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    QuestionHandler questionHandler;
    ArrayList<AssigmentDetail> assigmentDetailArrayList = new ArrayList<>();

    public User_Test_Details_CustomAdapter_RecylerView(ArrayList<AssigmentDetail> assigmentDetails) {
        this.assigmentDetailArrayList = assigmentDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_test_details_recylerview,
                parent, false);
        questionHandler = new QuestionHandler(parent.getContext(), DB_NAME, null, DB_VERSION);
        return new MyViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AssigmentDetail assigmentDetail = assigmentDetailArrayList.get(position);
        if (Objects.equals(assigmentDetail.getKetQuaCauTraLoi(), "Đúng"))
        {
            holder.tvKetQuaCauTraLoi.setText("Correct");
            holder.backgrCorAndWro.setBackgroundResource(R.drawable.backgr_item_correct_ans);
            holder.imgCorAndWro.setImageResource(R.drawable.correct_answer);
        }else
        {
            holder.tvKetQuaCauTraLoi.setText("Wrong");
            holder.backgrCorAndWro.setBackgroundResource(R.drawable.backgr_item_wrong_ans);
            holder.imgCorAndWro.setImageResource(R.drawable.wrong_answer);
        }
        Question question = questionHandler.searchInforAboutAQuesByCode(assigmentDetail.getMaCauHoi());
        holder.tvNoiDungCauHoi_TestDetail.setText(question.getNoiDungCauHoi());
        holder.tvDapAnDung_TestDetail.setText("- Correct answer: " + question.getDapAn()
                + ". " + setNoiDungDapAnDung(question));
        holder.tvCauTraLoi_TestDetail.setText("- Selected answer: " + assigmentDetail.getCauTraLoi()
                + ". " + setNoiDunDapAnDaChon(question, assigmentDetail.getCauTraLoi()));

    }
    @Override
    public int getItemCount() {
        return assigmentDetailArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvKetQuaCauTraLoi, tvNoiDungCauHoi_TestDetail, tvDapAnDung_TestDetail,
                tvCauTraLoi_TestDetail;
        ImageView imgCorAndWro;
        LinearLayout backgrCorAndWro;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKetQuaCauTraLoi = itemView.findViewById(R.id.tvKetQuaCauTraLoi);
            tvNoiDungCauHoi_TestDetail = itemView.findViewById(R.id.tvNoiDungCauHoi_TestDetail);
            tvDapAnDung_TestDetail = itemView.findViewById(R.id.tvDapAnDung_TestDetail);
            tvCauTraLoi_TestDetail = itemView.findViewById(R.id.tvCauTraLoi_TestDetail);
            imgCorAndWro = itemView.findViewById(R.id.imgCorAndWro);
            backgrCorAndWro = itemView.findViewById(R.id.backgrCorAndWro);
        }
    }
    private String setNoiDungDapAnDung(Question question)
    {
        String noiDungDapAnDung = "";
        if (question.getDapAn().equals("A"))
        {
            noiDungDapAnDung = question.getCauA();
        }else if (question.getDapAn().equals("B"))
        {
            noiDungDapAnDung = question.getCauB();
        }else if (question.getDapAn().equals("C"))
        {
            noiDungDapAnDung = question.getCauC();
        }else if (question.getDapAn().equals("D"))
        {
            noiDungDapAnDung = question.getCauD();
        }
        return noiDungDapAnDung;
    }
    private String setNoiDunDapAnDaChon(Question question, String cauTraLoi) {
        String noiDungCauTraLoi = "";
        if (cauTraLoi.equals("A"))
        {
            noiDungCauTraLoi = question.getCauA();
        }else if (cauTraLoi.equals("B"))
        {
            noiDungCauTraLoi = question.getCauB();
        }else if (cauTraLoi.equals("C"))
        {
            noiDungCauTraLoi = question.getCauC();
        }else if (cauTraLoi.equals("D"))
        {
            noiDungCauTraLoi = question.getCauD();
        }
        return noiDungCauTraLoi;
    }
}
