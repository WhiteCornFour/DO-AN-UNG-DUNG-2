package com.example.doanungdung2.View;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.ReportHandler;
import com.example.doanungdung2.Model.Report;
import com.example.doanungdung2.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Admin_ManagementCategory_MainPage_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin_ManagementCategory_MainPage_Fragment extends Fragment {
    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    TextView tvTenADKhiLogin;
    EditText edtSearchReportByDMY;
    ImageView imgSearchReportByDMY;
    RecyclerView recyclerViewReport_Admin;
    Spinner spinnerViewReport_Admin;
    ReportHandler reportHandler;
    ArrayList<Report> reportArrayList = new ArrayList<>();
    Admin_Report_Custom_Adapter_RecyclerView adapterRecyclerView;
    String [] dataSpiner = new String[]{"", "Chưa xử lý", "Đã xử lý"};
    ArrayAdapter<String> adapter_Spinner;
    String selectedStatus = "";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Admin_ManagementCategory_MainPage_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_DanhMucQuanLy_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin_ManagementCategory_MainPage_Fragment newInstance(String param1, String param2) {
        Admin_ManagementCategory_MainPage_Fragment fragment = new Admin_ManagementCategory_MainPage_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin__danh_muc_quan_ly_, container, false);
        addControl(view);
        reportHandler = new ReportHandler(getActivity(), DB_NAME, null, DB_VERSION);
        //Nhận data từ local để hiển thị xem admin nào đang đăng nhập
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ThongTinAdmin", MODE_PRIVATE);
        String tenAd= sharedPreferences.getString("tenAdmin", null);
        tvTenADKhiLogin.setText("Xin chào " + tenAd + " đã quay trở lại!");
        //Hiển thị data cho spinner
        setUpDataSpinner(dataSpiner);
        //Chuẩn bị cho recyclerView
        setUpRecylerView();
        //load tat ca data len cho recyclerview
        setUpDataRecyclerView();

        addEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpDataRecyclerView();
    }

    private void addEvent() {
        spinnerViewReport_Admin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedStatus = dataSpiner[i];
                //Log.d("selectedStatus: ", selectedStatus);
                if (selectedStatus != null && !selectedStatus.isEmpty())
                {
                    reportArrayList = reportHandler.loadDataByReportStatus(selectedStatus);
                    setUpDataRecyclerViewBySearching(reportArrayList);
                }else {
                    setUpDataRecyclerView();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        imgSearchReportByDMY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyWord = edtSearchReportByDMY.getText().toString();
                if (keyWord != null && !keyWord.isEmpty())
                {
                    reportArrayList = reportHandler.loadDataSearchByKeyWord(keyWord, selectedStatus);
                    if (reportArrayList.size() != 0)
                    {
                        Collections.reverse(reportArrayList);
                        setUpDataRecyclerViewBySearching(reportArrayList);
                    }else
                    {
                        setUpDataRecyclerView();
                        setUpDataSpinner(dataSpiner);
                        Toast.makeText(getActivity(), "Không tìm thấy kết quả phù hợp!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    setUpDataRecyclerView();
                    setUpDataSpinner(dataSpiner);
                    Toast.makeText(getActivity(), "Bạn cần nhập thông tin trước khi tìm kiếm!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addControl(View view) {
        tvTenADKhiLogin = view.findViewById(R.id.tvTenADKhiLogin);
        edtSearchReportByDMY = view.findViewById(R.id.edtSearchReportByDMY);
        imgSearchReportByDMY = view.findViewById(R.id.imgSearchReportByDMY);
        recyclerViewReport_Admin = view.findViewById(R.id.recyclerViewReport_Admin);
        spinnerViewReport_Admin = view.findViewById(R.id.spinnerViewReport_Admin);
    }
    void setUpDataSpinner(String[] dataSpiner)
    {
        adapter_Spinner = new ArrayAdapter<>(getActivity(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                dataSpiner);
        spinnerViewReport_Admin.setAdapter(adapter_Spinner);
    }
    void setUpRecylerView()
    {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerViewReport_Admin.setLayoutManager(layoutManager);
        recyclerViewReport_Admin.setItemAnimator(new DefaultItemAnimator());
        adapterRecyclerView = new Admin_Report_Custom_Adapter_RecyclerView(reportArrayList,
                new Admin_Report_Custom_Adapter_RecyclerView.ItemClickListener() {
                    @Override
                    public void itemClicked(Report report) {
                        Intent intent = new Intent(getActivity(), Admin_Report_Details.class);
                        intent.putExtra("ReportInfor", report);
                        //Log.d("Ma report: ", report.getMaBaoCao());
                        startActivity(intent);
                    }
                });
        recyclerViewReport_Admin.setAdapter(adapterRecyclerView);
    }

    private void setUpDataRecyclerView() {
        reportArrayList = reportHandler.loadAllDataReport();
        Collections.reverse(reportArrayList);
        adapterRecyclerView.setNewDataRecyclerView(reportArrayList);
    }

    private void setUpDataRecyclerViewBySearching(ArrayList<Report> newList)
    {
        Collections.reverse(newList);
        adapterRecyclerView.setNewDataRecyclerView(newList);
    }
}